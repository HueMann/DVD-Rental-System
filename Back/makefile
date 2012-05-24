all: backend.class

backend.class: library.class transactionList.class transaction.class constraintWarningException.class constraintFailureException.class fatalErrorException.class Backend.java
	javac Backend.java

library.class: fatalErrorException.class masterDVD.class constraintFailureException.class constraintWarningException.class transaction.class titleConflictException.class dvd.class Library.java
	javac Library.java

constraintFailureException.class: transaction.class ConstraintFailureException.java
	javac ConstraintFailureException.java

constraintWarningException.class: transaction.class ConstraintWarningException.java
	javac ConstraintWarningException.java

masterDVD.class: dvd.class utility.class MasterDVD.java
	javac MasterDVD.java

transactionList.class: transaction.class fatalErrorException.class TransactionList.java
	javac TransactionList.java

transaction.class: dvd.class utility.class Transaction.java
	javac Transaction.java

dvd.class: utility.class DVD.java
	javac DVD.java

fatalErrorException.class: FatalErrorException.java
	javac FatalErrorException.java

titleConflictException.class: TitleConflictException.java
	javac TitleConflictException.java

utility.class: Utility.java
	javac Utility.java

clean:
	rm *.class
