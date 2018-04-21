/*********************************************************************
 *
 * Copyright (c) 2007-2014 Basho Technologies, Inc.  All Rights Reserved.
 *
 * This file is provided to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain
 * a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 *********************************************************************/

// NOTE: this is a demo program to show off basic operations
// against Riak. For a more in-depth example, see:
// https://github.com/basho/riak-c-client/blob/develop/examples/example.c
//

#include <riak.h>

/*  This is an example struct to hold config data
 * for a key/value operations on a single bucket.
 *
 *  It caches default options to cover the
 * most common use cases.  They can be modified
 * by changing the behavior of riak_req_new,
 * or modifying the struct and its callers (get/put/update/delete).
 *
 */
struct riak_req {
    riak_config *cfg;
    riak_connection *cxn;

    riak_binary *btype; // bucket type (usu. default)
    riak_binary *bucket;

    // default options for get/put/del
    riak_get_options *getopt;
    riak_put_options *putopt;
    riak_delete_options *delopt;
};

struct riak_req *riak_req_new(riak_config *cfg, riak_connection *cxn,
                              char *bucket) {
    struct riak_req *req = malloc(sizeof(struct riak_req));

    // Basic stuff
    req->cfg = cfg;
    req->cxn = cxn;
    req->btype  = riak_binary_copy_from_string(cfg, "default");
    req->bucket = riak_binary_copy_from_string(cfg, bucket);

    // Set sensible default basic_quorum & r options.
    req->getopt = riak_get_options_new(cfg);
    riak_get_options_set_basic_quorum(req->getopt, RIAK_TRUE);
    riak_get_options_set_r(req->getopt, 2);

    // Just return the head by default.
    req->putopt = riak_put_options_new(cfg);
    riak_put_options_set_return_head(req->putopt, RIAK_TRUE);

    // Yes, delete.
    req->delopt = riak_delete_options_new(cfg);
    if(req->delopt == NULL) {
        return NULL;
    }
    riak_delete_options_set_w(req->delopt, 1);
    riak_delete_options_set_dw(req->delopt, 1);

    return req;
}

void riak_req_free(struct riak_req **reqp) {
    struct riak_req *req = *reqp;
    riak_binary_free(req->cfg, &req->btype);
    riak_binary_free(req->cfg, &req->bucket);

    riak_get_options_free(   req->cfg, &req->getopt);
    riak_put_options_free(   req->cfg, &req->putopt);
    riak_delete_options_free(req->cfg, &req->delopt);
}

// Create a new riak_object on the heap.
riak_object *mk_riak_object(struct riak_req *req,
                            riak_binary *key, riak_binary *value) {
    riak_object *obj = riak_object_new(req->cfg);

    // populate the riak_object with bucket, key and value
    riak_object_set_bucket(req->cfg, obj, req->bucket);
    riak_object_set_key(req->cfg, obj, key);
    riak_object_set_value(req->cfg, obj, value);
    return obj;
}

char *put(struct riak_req *req, riak_binary *key, riak_binary *value) {
    char output[10240];
    riak_error err;
    char *err_s = NULL;
    riak_object *obj = mk_riak_object(req, key, value);
    riak_put_response *put_response = NULL;

    // run the PUT and display the result (from return_head)
    err = riak_put(req->cxn, obj, req->putopt, &put_response);
    if (err == ERIAK_OK) {
        // use a riak-c-client convenience function to display the response
        riak_print_state out;
        riak_print_init(&out, output, sizeof(output));
        riak_put_response_print(&out, put_response);
        printf("%s\n", output);
    }

    // cleanup
    riak_put_response_free(req->cfg, &put_response);
    riak_object_free(req->cfg, &obj);

    if (err) {
        asprintf(&err_s, "Error executing PUT: %s\n", riak_strerror(err));
    }
    return err_s;
}

char *get(struct riak_req *req, riak_binary *key, riak_get_response **resp) {
    char output[10240];
    riak_error err;
    char *err_s = NULL;

    *resp = NULL;
    err = riak_get(req->cxn, req->btype, req->bucket, key, req->getopt, resp);
    if(err == ERIAK_OK) {
        riak_print_state out;
        riak_print_init(&out, output, sizeof(output));

        riak_get_response_print(&out, *resp);
        printf("%s\n", output);
    }

    if(err) {
        asprintf(&err_s, "Error executing GET: %s\n", riak_strerror(err));
        riak_get_response_free(req->cfg, resp);
    }
    return err_s;
}

char *update(struct riak_req *req, riak_binary *key, riak_binary *new_value) {
    riak_error err;
    char *err_s = NULL;
    riak_get_response *resp = NULL;
    riak_put_response *put_resp = NULL;

    err = riak_get(req->cxn, req->btype, req->bucket, key,
                   req->getopt, &resp);
    if(err) {
        asprintf(&err_s, "Error fetching object for update\n");
        return err_s;
    }
    riak_object *obj = riak_get_get_content(resp)[0];
    riak_object_set_value(req->cfg, obj, new_value);

    err = riak_put(req->cxn, obj, req->putopt, &put_resp);
    if(err) {
        asprintf(&err_s, "Error storing updated object\n");
        return err_s;
    }

    // cleanup
    riak_get_response_free(req->cfg, &resp);
    riak_put_response_free(req->cfg, &put_resp);

    return NULL;
}

char *delete(struct riak_req *req, riak_binary *key) {
    riak_error err;
    char *err_s = NULL;

    err = riak_delete(req->cxn, req->btype, req->bucket, key, req->delopt);

    if(err) {
        asprintf(&err_s, "Error executing DELETE [%s]\n", riak_strerror(err));
    }
    return err_s;
}


// Create a connection with the default address resolver
// 8087 is the default protocol buffers port if you are using
//   Riak from a pre-built package or source build using "make rel"
// 10017 is the default port if you have built from source
//   using "make devrel"
riak_connection *riak_open(riak_config *cfg,
                           const char *host, const char *port) {
    riak_connection *cxn = NULL;
    riak_error err = riak_connection_new(cfg, &cxn, host, port, NULL);
    if(err != ERIAK_OK) {
        fprintf(stderr, "Cannot connect to Riak on %s:%s\n", host, port);
        return NULL;
    }
    return cxn;
}

int main(int argc, char *argv[]) {
    // a riak_config serves as your per-thread state to interact with Riak
    riak_config *cfg;
    // use the default configuration
    riak_error err = riak_config_new_default(&cfg);
    if(err != ERIAK_OK) {
        fprintf(stderr, "Error creating client configuration\n");
        exit(1);
    }
    riak_connection *cxn = riak_open(cfg, "localhost", "8087");
    if(cxn == NULL) {
        exit(1);
    }
    char *err_s;

    struct riak_req *req = riak_req_new(cfg, cxn, "TestBucket");
    riak_binary *key      = riak_binary_copy_from_string(req->cfg, "TestKey");
    riak_binary *value    = riak_binary_copy_from_string(req->cfg, "TestData");
    riak_get_response *resp;


    printf("-------------------------------\n");
    printf("Test Riak PUT\n");
    printf("-------------------------------\n");
    if( (err_s = put(req, key, value)) != NULL) {
        printf("%s", err_s);
        free(err_s);
        exit(1);
    }
    riak_binary_free(cfg, &value);

    printf("-------------------------------\n");
    printf("Test Riak GET\n");
    printf("-------------------------------\n");
    if( (err_s = get(req, key, &resp)) != NULL) {
        printf("%s", err_s);
        free(err_s);
        exit(1);
    }
    riak_get_response_free(cfg, &resp);

    // Change the value
    value = riak_binary_copy_from_string(req->cfg, "MyValue");

    printf("-------------------------------\n");
    printf("Test Riak UPDATE\n");
    printf("-------------------------------\n");
    if( (err_s = update(req, key, value)) != NULL) {
        printf("%s", err_s);
        free(err_s);
        exit(1);
    }
    printf("Ok.\n");

    printf("-------------------------------\n");
    printf("Test Riak GET\n");
    printf("-------------------------------\n");
    if( (err_s = get(req, key, &resp)) != NULL) {
        printf("%s", err_s);
        free(err_s);
        exit(1);
    }
    riak_get_response_free(cfg, &resp);

    printf("-------------------------------\n");
    printf("Test Riak DELETE\n");
    printf("-------------------------------\n");
    if( (err_s = delete(req, key)) != NULL) {
        printf("%s", err_s);
        free(err_s);
        exit(1);
    }
    printf("Ok.\n");

    // cleanup
    riak_binary_free(cfg, &key);
    riak_binary_free(cfg, &value);

    riak_req_free(&req);
    riak_connection_free(&cxn);
    riak_config_free(&cfg);

    return 0;
}
