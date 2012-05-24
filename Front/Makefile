all: mohawk.class

mohawk.class: messages.class timechecker.class dvd_file.class admin.class default.class  mohawk.java
		javac mohawk.java

admin.class: user.class transaction.class admin.java
		javac admin.java
		
default.class: user.class transaction.class default.java
		javac default.java
		
messages.class: messages.java
		javac messages.java
		
dvd_file.class: dvd.class dvd_file.java
		javac dvd_file.java
		
user.class: User.java
		javac User.java
		
dvd.class: dvd.java
		javac dvd.java

transaction.class: transaction.java
		javac transaction.java

timechecker.class: timechecker.java
		javac timechecker.java


clean: 
		rm *.class
