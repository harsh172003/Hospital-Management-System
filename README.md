Project Description and Instruction

S/W Requirements : JAVA IDE , MYSQL , JAR CONNECTOR

• Database Schema :-

Patient_table
CREATE TABLE Patient_table( ID int auto_increment primary key, Name varchar(30) not null, Age int not null, Gender varchar (10) not null );

Doctor_table
CREATE TABLE Doctor_table( ID int auto_increment primary key, Name varchar(30) not null, Department varchar (10) not null );

Appointment_table
CREATE TABLE Appointment_table( Appointment_ID int auto_increment primary key, Patient_ID int auto_increment not null, Doctor_ID int auto_increment not null, Appointment_date date not null, Foreign key (Patient_ID) references Patient_table(id), Foreign key (Doctor_ID) references Doctor_table(id) );

JAR File Setup link :- (248) JDBC Setup with IntelliJ - Configure IntelliJ 💻 to Work with MySQL using MySQL Connector 📊 - YouTube
