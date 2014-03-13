package logic;



import java.rmi.Remote;
import java.rmi.RemoteException;
import orders.ResultWithErr;


public interface LogicInterface extends Remote{
    
        //Usage: order App
	public ResultWithErr selectFromInventory(String serverIP, String dbName, String tableName) throws RemoteException;
	public String submitOrder(String SQLServerIP, String firstName, String lastName, String phoneNumber, String customerAddress, String sTotalCost, String[] items) throws RemoteException;


}
