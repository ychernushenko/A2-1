package logic;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LogicInterface extends Remote{
        // Usage: userLogin 
        public String authentication(String SQLServerIP,String username, String pw) throws RemoteException;
	public String recordUserActivity(String SQLServerIP, String username) throws RemoteException;
        public String getUserAccess (String SQLServerIP, String user )throws RemoteException;
}
