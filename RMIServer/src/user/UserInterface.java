package user;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface UserInterface extends Remote{
	public String authentication(String SQLServerIP,String username, String pw) throws RemoteException;
	public String RecordUserActivity(String SQLServerIP, String username) throws RemoteException;
        public String getUserAccess (String SQLServerIP, String username)throws RemoteException;
}
