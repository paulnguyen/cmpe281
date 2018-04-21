<?php

require __DIR__.'/../vendor/autoload.php';

define('RIAK_HOST', getenv('RIAK_HOST') ?: '127.0.0.1');
define('RIAK_PORT', getenv('RIAK_PORT') ?: 8098);

use Basho\Riak;
use Basho\Riak\Node;
use Basho\Riak\Command;

$node = (new Node\Builder())
    ->atHost(RIAK_HOST)
    ->onPort(RIAK_PORT)
    ->build();

$riak = new Riak([$node]);

// Note: Use this line instead of the former if using a local devrel cluster
// $node = (new Node\Builder)->atHost('127.0.0.1')->onPort(10018)->build();

// Creating Objects In Riak
print('Creating Objects In Riak...'.PHP_EOL);

$val1 = 1;
$val2 = 'two';
$val3 = ['myValue' => 3];

$bucket = new Riak\Bucket('testBucket');
$location1 = new Riak\Location('one', $bucket);
$location2 = new Riak\Location('two', $bucket);
$location3 = new Riak\Location('three', $bucket);

$storeCommand1 = (new Command\Builder\StoreObject($riak))
                    ->buildObject($val1)
                    ->atLocation($location1)
                    ->build();
$storeCommand1->execute();

$storeCommand2 = (new Command\Builder\StoreObject($riak))
                    ->buildObject($val2)
                    ->atLocation($location2)
                    ->build();
$storeCommand2->execute();

$storeCommand3 = (new Command\Builder\StoreObject($riak))
                    ->buildJsonObject($val3)
                    ->atLocation($location3)
                    ->build();
$storeCommand3->execute();

// Reading Objects From Riak
print('Reading Objects From Riak...'.PHP_EOL);

$response1 = (new Command\Builder\FetchObject($riak))->atLocation($location1)->build()->execute();
$response2 = (new Command\Builder\FetchObject($riak))->atLocation($location2)->build()->execute();

$response3 = (new Command\Builder\FetchObject($riak))->atLocation($location3)
                ->withDecodeAsAssociative()->build()->execute();

assert($val1 == $response1->getObject()->getData());
assert($val2 == $response2->getObject()->getData());
assert($val3 == $response3->getObject()->getData());

// Updating Objects In Riak
print('Updating Objects In Riak...'.PHP_EOL);

$object3 = $response3->getObject();
$data3 = $object3->getData();

$data3['myValue'] = 42;
$object3 = $object3->setData($data3);

$updateCommand = (new Command\Builder\StoreObject($riak))
    ->withObject($object3)
    ->atLocation($location3)
    ->build();

$updateCommand->execute();

// Deleting Objects From Riak
print('Deleting Objects From Riak...'.PHP_EOL);

(new Command\Builder\DeleteObject($riak))->atLocation($location1)->build()->execute();
(new Command\Builder\DeleteObject($riak))->atLocation($location2)->build()->execute();
(new Command\Builder\DeleteObject($riak))->atLocation($location3)->build()->execute();

// Working With Complex Objects
print('Working With Complex Objects...'.PHP_EOL);

class Book
{
    public $title;
    public $author;
    public $body;
    public $isbn;
    public $copiesOwned;
}

$book = new Book();
$book->isbn = '1111979723';
$book->title = 'Moby Dick';
$book->author = 'Herman Melville';
$book->body = 'Call me Ishmael. Some years ago...';
$book->copiesOwned = 3;

$bookLocation = new Riak\Location($book->isbn, new Riak\Bucket('books'));

$storeCommand1 = (new Command\Builder\StoreObject($riak))
    ->buildJsonObject($book)
    ->atLocation($bookLocation)
    ->build();

$storeCommand1->execute();

$fetchBookResponse = (new Command\Builder\FetchObject($riak))
                        ->atLocation($bookLocation)->build()->execute();

print('Serialized Object:'.PHP_EOL);
print($fetchBookResponse->getBody().PHP_EOL);

(new Command\Builder\DeleteObject($riak))->atLocation($bookLocation)->build()->execute();
