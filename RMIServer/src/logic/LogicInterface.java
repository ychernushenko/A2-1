package logic;



import java.rmi.Remote;
import java.rmi.RemoteException;
import orders.ResultWithErr;


public interface LogicInterface extends Remote{
    
        //Usage: order App
	public ResultWithErr selectFromInventory(String serverIP, String dbName) throws RemoteException;
	public String submitOrder(String SQLServerIP, String firstName, String lastName, String phoneNumber, String customerAddress, String sTotalCost, String[] items) throws RemoteException;


    
        
        
        
        
        // Usage: userLogin 
        public String authentication(String SQLServerIP,String username, String pw) throws RemoteException;
	public String recordUserActivity(String SQLServerIP, String username) throws RemoteException;
        public String getUserAccess (String SQLServerIP, String user )throws RemoteException;
}
