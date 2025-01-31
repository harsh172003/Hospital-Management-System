package HospitalManegementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection; // Interface
    private Scanner scanner;  // both are data members

    public Patient(Connection connection,Scanner scanner)
    {
        this.connection=connection;
        this.scanner=scanner;
    }

    public void add_Patient()
    {
        System.out.print("Enter Name of Patient :");
        String patient_name =scanner.next();

        System.out.print("Enter Age of Patient :");
        int patient_age =scanner.nextInt();

        System.out.print("Enter Gender of Patient :");
        String patient_gender =scanner.next();

        try {
            //
            String query="INSERT INTO patients (name,age,gender) VALUES (?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            // This are the placeholders .
            preparedStatement.setString(1,patient_name);
            preparedStatement.setInt(2,patient_age);
            preparedStatement.setString(3,patient_gender);
            // we run the execute method , and they returnns the integer value as how many
            // record are affected in the database
            int affectedRows=preparedStatement.executeUpdate();

            if(affectedRows>0)
            {
                System.out.println("Patient Data Added !");
            }
            else
            {
                System.out.println("Failed , Try Again !");
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error adding patient: " + e.getMessage());
        }
    }

    public void view_Patient()
    {
        String query="select* from patients";

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            // we do not set any placeholder , so no need to here

            // for the printing purpose of the database we use resulset
            // it hold the pointing address of the databse then it will be execute
            ResultSet resultSet=preparedStatement.executeQuery();


            // For descibing the table properly we need format specifier
            System.out.println("Patient_Data :");
            System.out.println("+------+-------------------+-----+--------+");
            System.out.println("|  ID  |        Name       | Age | Gender |");
            System.out.println("+------+-------------------+-----+--------+");

            while (resultSet.next())// set pointer on table to start
            {
                // we store the data into java's local variable from databse feild
                int patient_id=resultSet.getInt("Id");// table name from databse
                String patient_name=resultSet.getString("Name");
                int patient_age=resultSet.getInt("age");
                String patient_gender=resultSet.getString("Gender");

                //printing here
                System.out.printf("|%-6s|%-19s|%-5s|%-8s|\n",patient_id,patient_name,patient_age,patient_gender);
                System.out.println("+------+-------------------+-----+--------+");
            }

        }
        catch (SQLException e)
        {
            System.err.println("Error in View: " + e.getMessage());
        }
    }

    public boolean check_patient(int p_id)
    {
        String query="SELECT * FROM patients WHERE Id=?";

        try
        {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            //set value for placeholder
            preparedStatement.setInt(1,p_id);
            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error : " + e.getMessage());
        }
        return false;
    }
}
