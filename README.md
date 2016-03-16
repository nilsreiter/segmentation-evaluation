[master ![Build Status](https://travis-ci.org/nilsreiter/segmentation-evaluation.svg?branch=master)](https://travis-ci.org/nilsreiter/segmentation-evaluation)
[develop ![Build Status](https://travis-ci.org/nilsreiter/segmentation-evaluation.svg?branch=develop)](https://travis-ci.org/nilsreiter/segmentation-evaluation)

# segmentation-evaluation
Java package for evaluating text segmentation

This is mainly a re-implementation of https://github.com/cfournie/segmentation.evaluation.

Usable with Apache UIMA and UIMAfit.



## Usage

The code to produce segments should depend on the api package and add `Segment`-annotations.
```
<dependency>
  <groupId>de.unistuttgart.ims</groupId>
  <artifactId>segmentation.api</artifactId>
  <version>0.1</version>
</dependency>
```

The evaluation can then be done using this package:

```
<dependency>
  <groupId>de.unistuttgart.ims</groupId>
  <artifactId>segmentation.evaluation</artifactId>
  <version>0.1</version>
</dependency>
```