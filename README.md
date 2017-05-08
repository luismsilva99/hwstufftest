# Anagrams
Run
```sh
$ cd anagrams
$ mvn clean compile assembly:single
$ java -jar target\anagrams-1.0-SNAPSHOT-jar-with-dependencies.jar
```

# Whats wrong
Run
```sh
$ cd whatswrong
$ mvn clean compile assembly:single
$ java -jar target\whatswrong-1.0-SNAPSHOT-jar-with-dependencies.jar
```

# API testing

Place your GitHub access token and username
```sh
apitesting\src\test\java\com\hw\luis\github\gist\models\GistAPI.java
```
Then run
```sh
$ cd apitesting
$ mvn clean test
```

# UI testing
Run
```sh
$ cd uiqaautomation
$ mvn clean test
```
