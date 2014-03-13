import logic.LogicImplementation;
import user.UserImplementation;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class RMIServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
                    System.setProperty("java.security.policy", "policy.txt");
			System.setSecurityManager(new java.rmi.RMISecurityManager());
			LogicImplementation remote = new LogicImplementation();
			LocateRegistry.createRegistry(9999);
			Naming.bind("//localhost:9999/RMI", remote);
			System.out.println("Remote Order Class Bind Success");
		} catch (RemoteException e) {
			System.out.println("Errors on creating remote object");
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			System.out.println("Already Bound");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println("URL Error");
			e.printStackTrace();
		}
	}
}