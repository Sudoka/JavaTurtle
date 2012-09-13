JC = javac
JR = java

default: 
	$(JC) Turtle/TestTurtle.java Turtle/Turtle.java

run:
	$(JR) Turtle/TestTurtle

clean:
	rm *.class
