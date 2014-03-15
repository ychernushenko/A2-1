package logic;
import inventoryMgmt.InventoryImplementation;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import orders.OrderImplementation;
import orders.ResultWithErr;
import inventoryMgmt.Result;
import user.UserImplementation;

public class LogicImplementation extends UnicastRemoteObject implements LogicInterface{
    public LogicImplementation() throws RemoteException{	
    }
        
    @Override
    public ResultWithErr selectFromInventory(String serverIP, String dbName, String tableName)throws RemoteException{
        OrderImplementation order = new OrderImplementation();
        return order.selectInvetoryFromDatabase(serverIP, dbName, tableName);
    }

    @Override
    public String submitOrder(String SQLServerIP, String firstName, String lastName, String phoneNumber, String customerAddress, String sTotalCost, String[] items)throws RemoteException{
        OrderImplementation order = new OrderImplementation();
        return order.SubmitOrder(SQLServerIP, firstName, lastName, phoneNumber, customerAddress, sTotalCost, items);
    }

   @Override
    public String authentication(String SQLServerIP,String username, String pw ) throws RemoteException {
        UserImplementation user = new UserImplementation();
        return user.authentication(SQLServerIP, username, pw); 
    }

    @Override
    public String recordUserActivity(String SQLServerIP, String username) throws RemoteException {
       UserImplementation user = new UserImplementation();
       return user.RecordUserActivity(SQLServerIP, username);
    }

    @Override
    public String getUserAccess (String SQLServerIP, String username)throws RemoteException{
        UserImplementation user = new UserImplementation();
        return user.getUserAccess(SQLServerIP, username);
    }

    @Override
        public Result selectEntriesFromInventory(String serverIP, String dbName, String tableName) throws RemoteException {
        InventoryImplementation inventory = new InventoryImplementation();
        return inventory.selectInvetoryEntriesFromDatabase(serverIP, dbName, tableName);
    }
    
    @Override
    public Result insertEntriesToInventory(String serverIP, String dbName, String tableName, 
                                            String description, String productID, Integer quantity, Float perUnitCost) throws RemoteException {
        InventoryImplementation inventory = new InventoryImplementation();
        return inventory.isertInvetoryEntryToDatabase(serverIP, dbName, tableName, description, productID, quantity, perUnitCost);
    }
    
    @Override
    public Result deleteEntryFromInventory(String serverIP, String dbName, String tableName, String productID) throws RemoteException {
        InventoryImplementation inventory = new InventoryImplementation();
        return inventory.deleteInvetoryEntryFromDatabase(serverIP, dbName, tableName, productID);
    }
    
    @Override
    public Result decrementEntryFromInventory(String serverIP, String dbName, String tableName, String productID) throws RemoteException {
        InventoryImplementation inventory = new InventoryImplementation();
        return inventory.decrementInvetoryEntryFromDatabase(serverIP, dbName, tableName, productID);
    }
}
