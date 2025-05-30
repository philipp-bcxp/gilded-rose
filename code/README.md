# Gilded Rose

On Windows without WSL use `gradlew.bat` instead of `gradlew`

You might have to make `gradlew` executable with `chmod u+x gradlew`

## Run the Simulation from Command-Line
```sh
./gradlew -q simulate
```


### Specify Number of Days

For e.g. 10 days:

```sh
./gradlew -q simulate --args 10
```

You should make sure the gradle commands shown above work when you execute them in a terminal before trying to use TextTest (see below).

## Compare Simulation Output with expected Output
Use this if you just want to start refactoring or adding the feature without writing tests.
```
./gradlew -q compare
```

For the feature you might have to adjust the expected output here: `/src/test/resources/expected_output.txt`


