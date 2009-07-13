#!/bin/sh
CLASSPATH=compojure.jar
 
for f in deps/*.jar; do
    CLASSPATH=$CLASSPATH:$f
done
echo $CLASSPATH 
java -cp $CLASSPATH clojure.main
