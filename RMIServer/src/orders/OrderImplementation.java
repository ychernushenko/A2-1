package orders;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;


public class OrderImplementation extends UnicastRemoteObject implements OrderInterface{
	public OrderImplementation() throws RemoteException{	
	}
        
	public ResultWithErr selectFromInventory(String serverIP,String param)throws RemoteException{
            // jButton1 is responsible for querying the inventory database and
        // getting the tree inventory. Once retieved, the tree inventory is
        // displayed in jTextArea1. From here the user can select an inventory
        // item by triple clicking the item.

            String tableName = "";
            if(param.equals("tree"))
                tableName = "trees";
            else if(param.equals("seed"))
                tableName = "seeds";
            else if(param.equals("shrub"))
                tableName = "shrubs";
            // Database parameters
            Connection DBConn = null;           // MySQL connection handle
            String errString = null;            // String for displaying errors
            ResultSet res = null;               // SQL query result set pointer
            Statement s = null;                 // SQL statement pointer

            ResultWithErr resWithErr = new ResultWithErr();

            // Connect to the inventory database

            String sourceURL = "jdbc:mysql://" + serverIP + ":3306/inventory";
            try {
                DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
            }catch (SQLException e) {
                errString =  "\nProblem connecting to database:: " + e;
                resWithErr.setErrMsg(errString);
                return resWithErr;
            } // end try-catch
            try{
                s = DBConn.createStatement();
                res = s.executeQuery( "Select * from "+ tableName );
                while (res.next())
                {
                    resWithErr.addtoRes(res.getString(1), res.getString(2), res.getString(3), res.getString(4));     
                } // while
                return resWithErr;
            } catch (SQLException e) {
                errString =  "\nProblem getting tree inventory:: " + e;
                resWithErr.setErrMsg(errString);
                return resWithErr;
            } // end try-catch
        }

        public String SubmitOrder(String SQLServerIP, String firstName, String lastName, String phoneNumber, String customerAddress, String sTotalCost, String[] items)throws RemoteException{
            int beginIndex;                 // String parsing index
        int endIndex;                   // String paring index
        Connection DBConn = null;       // MySQL connection handle
        float fCost;                    // Total order cost
        String description;             // Tree, seed, or shrub description
        String errString = null;        // String for displaying errors
        String msgString = null;        // String for displaying non-error messages
        String orderTableName = null;   // This is the name of the table that lists the items
        String sPerUnitCost = null;     // String representation of per unit cost
        String orderItem = null;        // Order line item from jTextArea2
        Float perUnitCost;              // Cost per tree, seed, or shrub unit
        String productID = null;        // Product id of tree, seed, or shrub
        Statement s = null;             // SQL statement pointer
        String SQLstatement = null;     // String for building SQL queries

        String result = "";
            try
            {
                //define the data source
                String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/orderinfo";
                DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");

            } catch (SQLException e) {
                errString =  "\nError connecting to orderinfo database\n" + e;
                return errString;
            } // end try-catch
        //If there is not a connection error, then we form the SQL statement
        //to submit the order to the orders table and then execute it.

            Calendar rightNow = Calendar.getInstance();

            int TheHour = rightNow.get(rightNow.HOUR_OF_DAY);
            int TheMinute = rightNow.get(rightNow.MINUTE);
            int TheSecond = rightNow.get(rightNow.SECOND);
            int TheDay = rightNow.get(rightNow.DAY_OF_WEEK);
            int TheMonth = rightNow.get(rightNow.MONTH);
            int TheYear = rightNow.get(rightNow.YEAR);
            orderTableName = "order" + String.valueOf(rightNow.getTimeInMillis());

            String dateTimeStamp = TheMonth + "/" + TheDay + "/" + TheYear + " "
                    + TheHour + ":" + TheMinute  + ":" + TheSecond;

            // Get the order data
            beginIndex = 0;
            beginIndex = sTotalCost.indexOf("$",beginIndex)+1;
            sTotalCost = sTotalCost.substring(beginIndex, sTotalCost.length());
            fCost = Float.parseFloat(sTotalCost);
                
            try
            {
                s = DBConn.createStatement();

                SQLstatement = ( "CREATE TABLE " + orderTableName +
                            "(item_id int unsigned not null auto_increment primary key, " +
                            "product_id varchar(20), description varchar(80), " +
                            "item_price float(7,2) );");
                s.executeUpdate(SQLstatement);

            } catch (Exception e) {
                errString =  "\nProblem creating order table " + orderTableName +":: " + e;
                return errString;
            } // try

                try
                {
                    SQLstatement = ( "INSERT INTO orders (order_date, " + "first_name, " +
                        "last_name, address, phone, total_cost, shipped, " +
                        "ordertable) VALUES ( '" + dateTimeStamp + "', " +
                        "'" + firstName + "', " + "'" + lastName + "', " +
                        "'" + customerAddress + "', " + "'" + phoneNumber + "', " +
                        fCost + ", " + false + ", '" + orderTableName +"' );");

                    s.executeUpdate(SQLstatement);
                    
                } catch (SQLException e1) {

                    errString =  "\nProblem with inserting into table orders:: " + e1;
                    result += errString;                    
                    try
                    {
                        SQLstatement = ( "DROP TABLE " + orderTableName + ";" );
                        s.executeUpdate(SQLstatement);
                    } catch (Exception e2) {
                        errString =  "\nProblem deleting unused order table:: " +
                                orderTableName + ":: " + e2;
                        result += errString;
                        return result;
                    } // try

                } // try

        // Now, if there is no connect or SQL execution errors at this point, 
        // then we have an order added to the orderinfo::orders table, and a 
        // new ordersXXXX table created. Here we insert the list of items in
        // jTextArea2 into the ordersXXXX table.



            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < items.length; i++ )
            {
                orderItem = items[i];
                // Check just to make sure that a blank line was not stuck in
                // there... just in case.
                if (orderItem.length() > 0 )
                {
                    // Parse out the product id
                    beginIndex = 0;
                    endIndex = orderItem.indexOf(" : ",beginIndex);
                    productID = orderItem.substring(beginIndex,endIndex);

                    // Parse out the description text
                    beginIndex = endIndex + 3; //skip over " : "
                    endIndex = orderItem.indexOf(" : ",beginIndex);
                    description = orderItem.substring(beginIndex,endIndex);

                    // Parse out the item cost
                    beginIndex = endIndex + 4; //skip over " : $"
                    //endIndex = orderItem.indexOf(" : ",orderItem.length());
                    //sPerUnitCost = orderItem.substring(beginIndex,endIndex);
                    sPerUnitCost = orderItem.substring(beginIndex,orderItem.length());
                    perUnitCost = Float.parseFloat(sPerUnitCost);

                    SQLstatement = ( "INSERT INTO " + orderTableName +
                        " (product_id, description, item_price) " +
                        "VALUES ( '" + productID + "', " + "'" +
                        description + "', " + perUnitCost + " );");
                    try
                    {
                        s.executeUpdate(SQLstatement);
                    } catch (Exception e) {
                        errString =  "\nProblem with inserting into table " + orderTableName +
                            ":: " + e;
                       result += errString;
                        return result;
                    } // try

                } // line length check

            } //for each line of text in order table
            return result;
        }

}
