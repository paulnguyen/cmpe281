

# Setup API Gateway In Front of Network Load Balancer

* https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-private-integration.html
* https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-private-integration.html
* https://docs.aws.amazon.com/apigateway/latest/developerguide/getting-started-with-private-integration.html
* https://aws.amazon.com/premiumsupport/knowledge-center/iam-authentication-api-gateway/
* https://docs.aws.amazon.com/apigateway/latest/developerguide/how-to-use-postman-to-call-api.html
* https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-control-access-using-iam-policies-to-invoke-api.html
* https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-iam-policy-examples-for-api-execution.html
* https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-create-api-from-example-console.html

## Create Initial Empty API

    1. Name:            RiakAPI
    2. End Point Type:  Regional

## Create API Gateway VPC Link

    1. Name:            RiakLink
    2. Target NLB:      aws-riak-elb-net

## Update API Settings

    1. Create a Proxy Resource:     
            Name:   proxy
            Path:   /{proxy}+

    2. Create an ANY Method:
            Integration:            VPC Link
            Authorization:          AWS_IAM
            Use Proxy Integration:  True
            VPC Link:               Select RiakLink
            Endpoint URL:           http://endpoint/{proxy}
                                    Use Network Load Balancer End Point
                                    For Example:
                                    http://aws-riak-elb-net-6f2f46db7180948d.elb.us-west-2.amazonaws.com/{proxy}

    3. Set API Resource Policy

            Note the API ARN from "ANY METHOD".  For Example:
            ARN: arn:aws:execute-api:us-west-2:633868400030:5kce6w7kwj/*/*/*

            Format is as follows:
            "arn:aws:execute-api:{region}:{account-id}:{api-id}/{stage}/{method}/{path}"

            Edit the API Resource Policy as follows:

            {
                "Version": "2012-10-17",
                "Statement": [
                    {
                        "Effect": "Allow",
                        "Principal": "*",
                        "Action": "execute-api:Invoke",
                        "Resource": "arn:aws:execute-api:us-west-2:633868400030:5kce6w7kwj/*/*/*"
                    }
                ]
            }

            This Policy allows any IAM Authenticated User from your Account to Invoke the Gateway API.

    4. Deploy API to "prod" Stage

        Note your Stage Invoke Endpoint Generated.  For Example:

            https://bnn2cma4c4.execute-api.us-west-2.amazonaws.com/prod 

    5. Create IAM User and Note Access and Security Keys for API Authentication

            Use Postman to Test API:

            https://docs.aws.amazon.com/apigateway/latest/developerguide/how-to-use-postman-to-call-api.html


## Test API Gateway From Postman (For Example -- Note, AWS Auth Header Excluded)

    curl -X GET \
        https://5kce6w7kwj.execute-api.us-west-2.amazonaws.com/prod/ping 

    curl -X GET \
        https://5kce6w7kwj.execute-api.us-west-2.amazonaws.com/prod/buckets?buckets=true

    curl -X PUT \
        https://5kce6w7kwj.execute-api.us-west-2.amazonaws.com/prod/buckets/bucket/keys/key1?returnbody=true \
      -d '{
        "foo": "bar"
        }'

    curl -X GET \
      https://5kce6w7kwj.execute-api.us-west-2.amazonaws.com/prod/buckets/bucket/keys/key1






