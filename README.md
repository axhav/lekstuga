# lekstuga

create a jar by first compiling

javac -cp jsoup-1.8.3.jar ShowMeal.java App.java

jar -cfme einstein.jar manifest.mf App App.class ShowMeal.class jsoup-1.8.3.jar

run with 
java -jar einstein.jar

flags:
-w for full week menu
-i for show images of todays meals