JC = javac
JR = java -Xdock:icon=turtle.png

default:
	$(JC) MSU/Turtle/TestTurtle.java MSU/Turtle/Turtle.java MSU/Turtle/TurtleCanvas.java

run:
	$(JR) MSU/Turtle/TestTurtle

clean:
	rm Turtle/*.class

package:
	jar cfm Turtle.jar Manifest.txt MSU