PROGRAM=Homework1

# compile and create executable
all:
	javac -cp . $(PROGRAM).java
	jar cmf $(PROGRAM).mf $(PROGRAM).jar $(PROGRAM).class $(PROGRAM).java Friend.java Friend.class

# run program
run:
	java -jar $(PROGRAM).jar
