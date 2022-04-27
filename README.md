# Bloom Filter

## Requirements
In this service, we have bloom filter implementation. There are many circumstances where we need to find out if something is a member of a set, and many algorithms for doing it. If the set is small, you can use bitmaps. When they get larger, hashes are a useful technique. But when the sets get big, we start bumping in to limitations. Holding 250,000 words in memory for a spell checker might be too big an overhead if your target environment is a PDA or cell phone. Keeping a list of web-pages visited might be extravagant when you get up to tens of millions of pages. Fortunately, there’s a technique that can help.

Bloom filters are a 30-year-old statistical way of testing for membership in a set. They greatly reduce the amount of storage you need to represent the set, but at a price: they’ll sometimes report that something is in the set when it isn’t (but it’ll never do the opposite; if the filter says that the set doesn’t contain your object, you know that it doesn’t). And the nice thing is you can control the accuracy; the more memory you’re prepared to give the algorithm, the fewer false positives you get. I once wrote a spell checker for a PDP-11 which stored a dictionary of 80,000 words in 16kbytes, and I very rarely saw it let though an incorrect word. (Update: I must have mis-remembered these figures, because they are not in line with the theory. Unfortunately, I can no longer read the 8” floppies holding the source, so I can’t get the correct numbers. Let’s just say that I got a decent sized dictionary, along with the spell checker, all in under 64k.)

Bloom filters are very simple. Take a big array of bits, initially all zero. Then take the things you want to look up (in our case we’ll use a dictionary of words). Produce ‘n’ independent hash values for each word. Each hash is a number which is used to set the corresponding bit in the array of bits. Sometimes there’ll be clashes, where the bit will already be set from some other word. This doesn’t matter.

To check to see of a new word is already in the dictionary, perform the same hashes on it that you used to load the bitmap. Then check to see if each of the bits corresponding to these hash values is set. If any bit is not set, then you never loaded that word in, and you can reject it.

The Bloom filter reports a false positive when a set of hashes for a word all end up corresponding to bits that were set previously by other words. In practice this doesn’t happen too often as long as the bitmap isn’t too heavily loaded with one-bits (clearly if every bit is one, then it’ll give a false positive on every lookup). There’s a discussion of the math in Bloom filters at www.cs.wisc.edu/~cao/papers/summary-cache/node8.html.

So, this kata is fairly straightforward. Implement a Bloom filter based spell checker. You’ll need some kind of bitmap, some hash functions, and a simple way of reading in the dictionary and then the words to check. For the hash function, remember that you can always use something that generates a fairly long hash (such as MD5) and then take your smaller hash values by extracting sequences of bits from the result. On a Unix box you can find a list of words in /usr/dict/words (or possibly in /usr/share/dict/words). For others, I’ve put a word list up here.1

Play with using different numbers of hashes, and with different bitmap sizes.

This service has the following operations:
- You can upload a dictionary having a word for each line.
- You can inject words in this dictionary to RoaringBitmap instance.
- You can query your map with a specific key to check that map contains it or not.

## Environment
* Java 8
* Springboot
* JUnit
* Docker

## Details

* RoaringBitmap is used for stating keys as bit.
* 2 hash functions(Object Hashcode and Custom Hash(WordHashServiceImpl)) are injected. The complexity of hash and hash 
  count are dynamic.
* JUnit tests are available to check functionality of services(upload a test file and test some keys).
* Dockerfile is added for generating image exposing 8090 and is able to monitor VisualVM.

_____________________________________________________________________________________

## API documentation
### Capabilities
Uploading a dictionary file
```java
{
        WordLoadServiceImpl wordLoadService = new WordLoadServiceImpl();
        String filePath = "/some/path";
        wordLoadService.load(filePath);
}
```
_____________________________________________________________________________________
Injecting keys to bitmap
```java
{
        BloomFilterServiceImpl bloomFilterService = new BloomFilterServiceImpl();
        List<String> words = wordLoadService.getWords();
        words.forEach(bloomFilterService::add);
}
```
_____________________________________________________________________________________
Checking key if bitmap contains or not
```java
{
        BloomFilterServiceImpl bloomFilterService = new BloomFilterServiceImpl();
        if(bloomFilterService.contains("Achaia")){
            //Some operations for containing scenario
        }
        else{
            //Some operations for not containing scenario
        }
}
```

### Working Environment

File parameter could be injected in yml file as 'dictionary.file.path'. Project could be built by maven command 'mvn 
clean install' and jar file automatically your 'com.cube.cyber.bloom-filter' in '.m2'.

Also you can build and run with as docker image.
```docker
$ docker build -t bloom-filter .
$ docker run -d -p 9010:9010 --name bloom-filter bloom-filter
$ docker ps -a
```

BloomFilterAppDemo is an example demo class. It shows how functionalities are implemented.

Note: spring-boot-starter-web is added dependency to be up as java process to monitor it on VisualVM. You can remove 
if you don't need.