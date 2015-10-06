# lekstuga

create a jar by first compiling

javac -cp jsoup-1.8.3.jar App.java

jar -cfme einstein.jar manifest.mf App App.class jsoup-1.8.3.jar

run with 
java -jar einstein.jar
