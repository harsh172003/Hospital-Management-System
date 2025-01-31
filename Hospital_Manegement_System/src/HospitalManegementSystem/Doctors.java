package HospitalManegementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {
    private Connection connection; // Interface

    public Doctors(Connection connection)
    {
        this.connection=connection;
    }

    public void view_Doctor()
    {
        String query="select * from doctors";

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            // we do not set any placeholder , so no need to here

            // for the printing purpose of the database we use resulset
            // it hold the pointing address of the databse then it will be execute
            ResultSet resultSet=preparedStatement.executeQuery();


            // For descibing the table properly we need format specifier
            System.out.println("Doctor_Data :");
            System.out.println("+------+-------------------+--------------+");
            System.out.println("|  ID  |        Name       |   Department |");
            System.out.println("+------+-------------------+--------------+");

            while (resultSet.next())// set pointer on table to start
            {
                // we store the data into java's local variable from databse feild
                int doctor_id=resultSet.getInt("Id");// table name from databse
                String doctor_name=resultSet.getString("Name");
                String doctor_department=resultSet.getString("Department");

                //printing here
                System.out.printf("|%-6s|%-19s|%-14s|\n",doctor_id,doctor_name,doctor_department);
                System.out.println("+------+-------------------+--------------+");
            }

        }
        catch (SQLException e)
        {
            System.err.println("Error : " + e.getMessage());
        }
    }

    public boolean check_doctor_by_id(int p_id)
    {
        String query="SELECT * FROM doctors WHERE Id=?";

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
