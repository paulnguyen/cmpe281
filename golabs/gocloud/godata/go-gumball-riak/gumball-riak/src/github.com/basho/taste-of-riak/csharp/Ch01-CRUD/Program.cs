namespace Ch01_CRUD
{
    using System;
    using System.Collections.Generic;
    using System.Diagnostics;
    using System.Text;
    using RiakClient;
    using RiakClient.Models;

    static class Program
    {
        private static readonly DictEqualityComparer<string, int> dictEqualityComparer = new DictEqualityComparer<string, int>();

        static void Main(string[] args)
        {
            const string bucket = "test";

            try
            {
                using (IRiakEndPoint endpoint = RiakCluster.FromConfig("riakConfig"))
                {
                    IRiakClient client = endpoint.CreateClient();

                    // Creating Objects In Riak
                    Console.WriteLine("Creating Objects In Riak...");

                    int val1 = 1;
                    var objectId1 = new RiakObjectId(bucket, "one");
                    var riakObject1 = new RiakObject(objectId1, val1);
                    var result = client.Put(riakObject1);
                    CheckResult(result);

                    string val2 = "two";
                    var objectId2 = new RiakObjectId(bucket, "two");
                    var riakObject2 = new RiakObject(objectId2, val2);
                    result = client.Put(riakObject2);
                    CheckResult(result);

                    var val3 = new Dictionary<string, int>
                    {
                        { "myValue1", 3 },
                        { "myValue2", 4 }
                    };
                    var objectId3 = new RiakObjectId(bucket, "three");
                    var riakObject3 = new RiakObject(objectId3, val3);
                    result = client.Put(riakObject3);
                    CheckResult(result);

                    // Fetching Objects From Riak
                    Console.WriteLine("Reading Objects From Riak...");

                    var fetchResult1 = client.Get(objectId1);
                    CheckResult(fetchResult1);
                    RiakObject fetchObject1 = fetchResult1.Value;
                    int fetchVal1 = fetchObject1.GetObject<int>();
                    Debug.Assert(val1 == fetchVal1, "Assert Failed", "val1 {0} != fetchVal1 {1}", val1, fetchVal1);

                    var fetchResult2 = client.Get(objectId2);
                    CheckResult(fetchResult2);
                    RiakObject fetchObject2 = fetchResult2.Value;
                    string fetchVal2 = fetchObject2.GetObject<string>();
                    Debug.Assert(val2 == fetchVal2, "Assert Failed", "val2 {0} != fetchVal2 {1}", val2, fetchVal2);

                    var fetchResult3 = client.Get(objectId3);
                    CheckResult(fetchResult3);
                    RiakObject fetchObject3 = fetchResult3.Value;
                    var fetchVal3 = fetchObject3.GetObject<Dictionary<string, int>>();
                    Debug.Assert(dictEqualityComparer.Equals(val3, fetchVal3), "Assert Failed", "val3 {0} != fetchVal3 {1}", val3, fetchVal3);

                    // Updating Objects In Riak
                    Console.WriteLine("Updating Objects In Riak");

                    fetchVal3["myValue1"] = 42;
                    var updateObject1 = new RiakObject(bucket, "three", fetchVal3);
                    var updateResult1 = client.Put(updateObject1);
                    CheckResult(updateResult1);

                    var fetchResult4 = client.Get(objectId3);
                    CheckResult(fetchResult4);
                    RiakObject fetchObject4 = fetchResult4.Value;
                    var fetchVal4 = fetchObject4.GetObject<Dictionary<string, int>>();
                    Debug.Assert(false == dictEqualityComparer.Equals(val3, fetchVal4), "Assert Failed", "val3 {0} == fetchVal4 {1}", val3, fetchVal4);
                    Debug.Assert(fetchVal4["myValue1"] == 42, "Assert Failed", "myValue1 should have been 42");

                    // Deleting Objects From Riak
                    Console.WriteLine("Deleting Objects From Riak...");

                    RiakResult delResult1 = client.Delete(objectId1);
                    CheckResult(delResult1);

                    RiakResult delResult2 = client.Delete(objectId2);
                    CheckResult(delResult2);

                    RiakResult delResult3 = client.Delete(objectId3);
                    CheckResult(delResult3);


                    // Working With Complex Objects
                    Console.WriteLine("Working With Complex Objects...");

                    var book = new Book();
                    book.ISBN = "1111979723";
                    book.Title = "Moby Dick";
                    book.Author = "Herman Melville";
                    book.Body = "Call me Ishmael. Some years ago...";
                    book.CopiesOwned = 3;

                    var bookId = new RiakObjectId("books", book.ISBN);
                    var bookObject = new RiakObject(bookId, book);
                    var bookPutResult = client.Put(bookObject);
                    CheckResult(bookPutResult);

                    var fetchBookResult = client.Get(bookId);
                    CheckResult(fetchBookResult);
                    RiakObject fetchedBookObject = fetchBookResult.Value;
                    string bookJson = Encoding.UTF8.GetString(fetchedBookObject.Value);
                    Console.WriteLine("Serialized Object: {0}", bookJson);

                    var bookDeleteResult = client.Delete(bookId);
                    CheckResult(bookDeleteResult);
                }
            }
            catch (Exception e)
            {
                Console.Error.WriteLine("Exception: {0}", e.Message);
            }
        }

        private static void CheckResult(RiakResult result)
        {
            if (!result.IsSuccess)
            {
                Console.Error.WriteLine("Error: {0}", result.ErrorMessage);
                Environment.Exit(1);
            }
        }

        private static void CheckResult(RiakResult<RiakObject> result)
        {
            if (!result.IsSuccess)
            {
                Console.Error.WriteLine("Error: {0}", result.ErrorMessage);
                Environment.Exit(1);
            }
        }
    }
}
