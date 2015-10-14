# lekstuga

create a jar by first compiling

javac -cp jsoup-1.8.3.jar ShowMeal.java App.java
jar -cfme einstein.jar manifest.mf App App.class ShowMeal.class jsoup-1.8.3.jar

note for linux users this is created with ANSI character encodeing 
add -encoding ISO-8859-1 on the compiling step (thanks Joacim)

run with 
java -jar einstein.jar

flags:
Only one of the flags will be used (i.e. the first one added), showing images for all the weeks meals is not on the todo-list as of now.
-w for full week menu
-i for show images of todays meals

