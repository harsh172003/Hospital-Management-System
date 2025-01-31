package HospitalManegementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital_system";
    private static final String username="root";
    private static final String password="Root@123";

    public static void main(String[] args)
    {
        // JDBC Connection Steps

        // 1. Load Drivers
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);
        // 2.Connection with Databse
        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            // we pass the argument to the constructor of patient and doctors
            Patient patient=new Patient(connection,scanner);
            Doctors doctors=new Doctors(connection);
            System.out.println("+----------------------------+");
            System.out.println("| HOSPITAL MANAGEMENT SYSTEM |");
            System.out.println("+----------------------------+");

            while (true)
            {

                System.out.println("1.Add Patient");
                System.out.println("2.View Patient");
                System.out.println("3.Check For Doctors");
                System.out.println("4.Book Appointment");
                System.out.println("5.Exit");
                System.out.println("Select Option :");
                int user_choice=scanner.nextInt();

                //Switch Case
                switch (user_choice)
                {
                    case 1:
                    {
                        //Add Patient
                        patient.add_Patient();
                        System.out.println("");
                        break;
                    }
                    case 2:
                    {
                        //View Patient
                        patient.view_Patient();
                        System.out.println("");
                        break;
                    }
                    case 3:
                    {
                        //Check For Doctors
                        doctors.view_Doctor();
                        System.out.println("");
                        break;
                    }
                    case 4:
                    {
                        //Book Appointment
                        book_appointment(patient,doctors,connection ,scanner);
                        System.out.println("");
                        break;
                    }
                    case 5:
                    {
                        // Exit1
                        return;
                    }
                    default:
                    {
                        System.out.println("Invalid Choice !");
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error : " + e.getMessage());
        }
    }

    public static void book_appointment(Patient patient,Doctors doctors,Connection connection,Scanner scanner)
    {
        System.out.print("Enter Patient ID :");
        int patient_id=scanner.nextInt();
        System.out.print("Enter Doctor ID :");
        int doctor_id=scanner.nextInt();
        System.out.print("Enter Appointment Date (YYYY-MM-DD) :");
        String appointment_date=scanner.next();

        if(patient.check_patient(patient_id) && doctors.check_doctor_by_id(doctor_id))
        {
            //
            if(check_doctor_availability(doctor_id,appointment_date,connection))
            {
                //Query
                String appointment_query="INSERT INTO appointment(Patients_id,Doctors_id,Appointment_Date) VALUES (?,?,?)";
                try {
                    PreparedStatement preparedStatement=connection.prepareStatement(appointment_query);
                    //set values for placeholder
                    preparedStatement.setInt(1,patient_id);
                    preparedStatement.setInt(2,doctor_id);
                    preparedStatement.setString(3,appointment_date);

                    int rowsAffected=preparedStatement.executeUpdate();
                    if(rowsAffected>0)
                    {
                        System.out.println("Appointment Booked !");
                    }
                    else
                    {
                        System.out.println("Failed to book Appointment !");
                    }
                }
                catch (SQLException e)
                {
                    System.err.println("Error : " + e.getMessage());
                }
            }
            else
            {
                System.out.println("Sorry for Inconvinience ! Doctor not available for that Date ");
            }
        }
        else
        {
            System.out.println("Something went Wrong ! Either Doctor or Patient not Exist !");
        }
    }

    public static boolean check_doctor_availability(int doctor_id,String app_date,Connection connection)
    {
        String query ="SELECT COUNT(*) FROM appointment WHERE Doctors_id=? AND  Appointment_Date=?";

        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2,app_date);

            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next())
            {
                int row_count=resultSet.getInt(1);
                if(row_count==0)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Error : " + e.getMessage());
        }
        return  false;
    }
}
