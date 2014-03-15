/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventoryMgmt;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Yury
 */
public interface InventoryInterface extends Remote{
	public Result selectInvetoryEntriesFromDatabase(String serverIP, String dbName, String tableName) throws RemoteException;
        public Result isertInvetoryEntryToDatabase(String serverIP, String dbName, String tableName, 
                                                                String description, String productID, Integer quantity, Float perUnitCost) throws RemoteException;
        public Result deleteInvetoryEntryFromDatabase(String serverIP, String dbName, String tableName, String productID) throws RemoteException;
        public Result decrementInvetoryEntryFromDatabase(String serverIP, String dbName, String tableName, String productID) throws RemoteException;
}