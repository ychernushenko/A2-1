package orders;



import logic.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import orders.ResultWithErr;


public interface OrderInterface extends Remote{
	public ResultWithErr selectFromInventory(String serverIP, String dbName) throws RemoteException;
	public String SubmitOrder(String SQLServerIP, String firstName, String lastName, String phoneNumber, String customerAddress, String sTotalCost, String[] items) throws RemoteException;
}
