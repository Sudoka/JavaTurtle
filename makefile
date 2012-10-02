JC = javac
JR = java -Xdock:icon=turtle.png

default:
	$(JC) Turtle/TestTurtle.java Turtle/Turtle.java

run:
	$(JR) Turtle/TestTurtle

clean:
	rm Turtle/*.class
