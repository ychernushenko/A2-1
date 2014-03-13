/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author yingda
 */
public class UserImplementation implements UserInterface {

    /**
     *
     * @param SQLServerIP
     * @param username
     * @param pw
     * @return
     * @throws RemoteException
     */
    @Override
    public String authentication(String SQLServerIP,String username, String pw ) throws RemoteException {
        Connection DBConn;       // MySQL connection handle
        String errString;        // String for displaying errors
        ResultSet res;           // SQL query result set pointer
        java.sql.Statement s;    // SQL statement pointer
        String SQLstatement;     // String for building SQL queries

        try{
            String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/users";
            DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
        }catch (SQLException e){
            errString =  "\nProblem connecting to database:: " + e;
            return errString;
        }
        try {
            s = DBConn.createStatement();
            res = s.executeQuery( "select * from user_info where user_id = '" + username + "' and pw = password('" + pw + "');");
                
            if( res.first() ){
                SQLstatement  = ( "INSERT INTO login_record (user_id, activity, time) VALUES ( '" + username + "', 'login', NOW() );");
                s.executeUpdate(SQLstatement);
                return "";
            }else{
                errString =  "\nIncorrect username or password. ";
                return errString;
            }
        } catch (SQLException ex) {
            errString =  "\nError when executing database query : "+ex;
            return errString;
        }
    }

    @Override
    public String RecordUserActivity(String SQLServerIP, String username) throws RemoteException {
        Connection DBConn = null;       // MySQL connection handle
        String errString = null;        // String for displaying errors
        java.sql.Statement s = null;    // SQL statement pointer
        String SQLstatement = null;     // String for building SQL queries
       
        if( username.length() == 0 )
           return "No username found";
        try{
            String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/users";
            DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
            try{
                s = DBConn.createStatement(); 
                SQLstatement  = ( "INSERT INTO login_record (user_id, activity, time) VALUES ( '" + username + "', 'logout', NOW() );");
                s.executeUpdate(SQLstatement);
                return "";
            }catch (Exception e){
                errString =  "\nProblem executing query. "+ e;
                return errString;
            }
            
        }catch (Exception e){
            errString =  "\nProblem connecting to database:: " + e;
            return errString;
        }
        
        
    }
    
    @Override
    public String getUserAccess (String SQLServerIP, String username)throws RemoteException{
        System.out.println("hahaha" + username +" " +SQLServerIP);
        Connection DBConn;       // MySQL connection handle
        ResultSet res;           // SQL query result set pointer
        java.sql.Statement s;    // SQL statement pointer
        
        try{
            String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/users";
            DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
        }catch (SQLException e){
            System.out.println("ERR in connection");
            return "";
        }
        try {
            s = DBConn.createStatement();
            
            res = s.executeQuery("select `accessability` from user_info where user_id = '"+username+"';");
            System.out.println("select `accessability` from user_info where user_id = '"+username+"';");
            String result ="";
            while(res.next()){
                result = res.getString(1);
            }
            return result;
        } catch (SQLException e) {
            System.out.println("ERR in query1111");
            return "";
        }
    }
}
