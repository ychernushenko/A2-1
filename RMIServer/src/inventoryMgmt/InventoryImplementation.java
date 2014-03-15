/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventoryMgmt;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Statement;

/**
 *
 * @author Yury
 */
public class InventoryImplementation extends UnicastRemoteObject implements InventoryInterface {
    public InventoryImplementation() throws RemoteException{	
	}
    
    @Override
    public Result selectInvetoryEntriesFromDatabase(String serverIP, String dbName, String tableName)throws RemoteException{
            // Database parameters
            Connection DBConn = null;           // MySQL connection handle
            String errString = null;            // String for displaying errors
            ResultSet res = null;               // SQL query result set pointer
            Statement s = null;                 // SQL statement pointer

            Result resWithErr = new Result();

            // Connect to the inventory database

            String sourceURL = "jdbc:mysql://" + serverIP + ":3306/" + dbName;
            try {
                DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
            }catch (SQLException e) {
                errString =  "\nProblem connecting to database:: " + e;
                resWithErr.setErrMsg(errString);
                return resWithErr;
            } // end try-catch
            try{
                s = DBConn.createStatement();
                res = s.executeQuery( "Select * from "+ tableName.replaceAll("\\s+","") );
                while (res.next())
                {
                    resWithErr.addtoRes(res.getString(1), res.getString(2), res.getString(3), res.getString(4));     
                } // while
                return resWithErr;
            } catch (SQLException e) {
                errString =  "\nProblem getting inventory:: " + e;
                resWithErr.setErrMsg(errString);
                return resWithErr;
            } // end try-catch
        }
    
        @Override
        public Result isertInvetoryEntryToDatabase(String serverIP, String dbName, String tableName, 
                                                                String description, String productID, 
                                                                Integer quantity, Float perUnitCost) throws RemoteException{
            // Database parameters
            Connection DBConn = null;           // MySQL connection handle
            String errString = null;            // String for displaying errors
            int executeUpdateVal;           // Return value from execute indicating effected rows
            Statement s = null;                 // SQL statement pointer
            String SQLstatement = "";

            Result resWithErr = new Result();

            // Connect to the inventory database

            String sourceURL = "jdbc:mysql://" + serverIP + ":3306/" + dbName;
            try {
                DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
            }catch (SQLException e) {
                errString =  "\nProblem connecting to database:: " + e;
                resWithErr.setErrMsg(errString);
                return resWithErr;
            } // end try-catch
            try{
                s = DBConn.createStatement();
                
                
                if(dbName.equals("inventory")) {
                    SQLstatement = ( "INSERT INTO " + tableName + " (product_code, " +
                                        "description, quantity, price) VALUES ( '" +
                                        productID + "', " + "'" + description + "', " +
                                        quantity + ", " + perUnitCost + ");");
                }
                else if (dbName.equals("leaftech")) {
                    SQLstatement = ( "INSERT INTO " + tableName + " (productid, " +
                                        "productdescription, productquantity, productprice) VALUES ( '" +
                                        productID + "', " + "'" + description + "', " +
                                        quantity + ", " + perUnitCost + ");");
                }
                
                executeUpdateVal = s.executeUpdate( SQLstatement );

                resWithErr.setChanged(executeUpdateVal);
                return resWithErr;
            } catch (SQLException e) {
                errString =  "\nProblem inserting into inventory:: " + e;
                resWithErr.setErrMsg(errString);
                return resWithErr;
            } // end try-catch
        }
        
      @Override  
      public Result deleteInvetoryEntryFromDatabase(String serverIP, String dbName, String tableName, String productID) throws RemoteException {
          // Database parameters
            Connection DBConn = null;           // MySQL connection handle
            String errString = null;            // String for displaying errors
            int executeUpdateVal;           // Return value from execute indicating effected rows
            Statement s = null;                 // SQL statement pointer
            String SQLstatement = "";

            Result resWithErr = new Result();

            // Connect to the inventory database

            String sourceURL = "jdbc:mysql://" + serverIP + ":3306/" + dbName;
            try {
                DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
            }catch (SQLException e) {
                errString =  "\nProblem connecting to database:: " + e;
                resWithErr.setErrMsg(errString);
                return resWithErr;
            } // end try-catch
            try{
                s = DBConn.createStatement();
                
                if(dbName.equals("inventory")) {
                    SQLstatement = ( "DELETE FROM " + tableName + " WHERE product_code = '" + productID + "';");
                }
                else if (dbName.equals("leaftech")) {
                    SQLstatement = ( "DELETE FROM " + tableName + " WHERE productid = '" + productID + "';");
                }
                
                executeUpdateVal = s.executeUpdate( SQLstatement );

                resWithErr.setChanged(executeUpdateVal);
                return resWithErr;
            } catch (SQLException e) {
                errString =  "\nProblem with deleting inventory:: " + e;
                resWithErr.setErrMsg(errString);
                return resWithErr;
            } // end try-catch
      }
      
      @Override  
      public Result decrementInvetoryEntryFromDatabase(String serverIP, String dbName, String tableName, String productID) throws RemoteException {
            // Database parameters
            Connection DBConn = null;           // MySQL connection handle
            String errString = null;            // String for displaying errors
            int executeUpdateVal;           // Return value from execute indicating effected rows
            Statement s = null;                 // SQL statement pointer
            String SQLstatementDec = "";
            String SQLstatementSel = "";
            ResultSet res = null;               // SQL query result set pointer

            Result resWithErr = new Result();

            // Connect to the inventory database

            String sourceURL = "jdbc:mysql://" + serverIP + ":3306/" + dbName;
            try {
                DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
            }catch (SQLException e) {
                errString =  "\nProblem connecting to database:: " + e;
                resWithErr.setErrMsg(errString);
                return resWithErr;
            } // end try-catch
            try{
                s = DBConn.createStatement();
                
                if(dbName.equals("inventory")) {
                    SQLstatementDec = ( "UPDATE " + tableName + " set quantity = (quantity -1) WHERE product_code = '" + productID + "';");
                    SQLstatementSel = ( "SELECT * FROM " + tableName + " WHERE product_code = '" + productID + "';");
                }
                else if (dbName.equals("leaftech")) {
                    SQLstatementDec = ( "UPDATE " + tableName + " set productquantity = (productquantity -1) WHERE productid = '" + productID + "';");
                    SQLstatementSel = ( "SELECT * FROM " + tableName + " WHERE productid = '" + productID + "';");
                }
                
                executeUpdateVal = s.executeUpdate( SQLstatementDec );
                resWithErr.setChanged(executeUpdateVal);
                
                res = s.executeQuery( SQLstatementSel );
                while (res.next())
                {
                    resWithErr.addtoRes(res.getString(1), res.getString(2), res.getString(3), res.getString(4));     
                } // while
                
                return resWithErr;
            } catch (SQLException e) {
                errString =  "\nProblem with deleting inventory:: " + e;
                resWithErr.setErrMsg(errString);
                return resWithErr;
            } // end try-catch
      }
}
