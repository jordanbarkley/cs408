PROGRAM=Homework1

# compile and create executable
all:
	javac -cp . $(PROGRAM).java
	jar cmf $(PROGRAM).mf $(PROGRAM).jar $(PROGRAM).class $(PROGRAM).java Player.java Player.class

# run program
run:
	java -jar $(PROGRAM).jar input.txt

# run tests
test:
	java -jar $(PROGRAM).jar

# generate input
generate:
	javac -cp . Generate.java
	java -cp . Generate > out.txt