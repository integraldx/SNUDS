main: *.java
	javac Matching.java -encoding utf8
	javac UnitTestor.java -encoding utf8

test: *.java
	javac UnitTestor.java -encoding utf8
	java UnitTestor

run: *.java
	javac Matching.java -encoding utf8
	java Matching

clean:
	pwsh -Command rm *.class