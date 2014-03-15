/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

import java.rmi.Remote;
import java.rmi.RemoteException;
import inventoryMgmt.Result;

/**
 *
 * @author Yury
 */
public interface LogicInterface extends Remote{
    
        //Usage: Inventory App
	public Result selectEntriesFromInventory(String serverIP, String dbName, String tableName) throws RemoteException;
        public Result insertEntriesToInventory(String serverIP, String dbName, String tableName, 
                                                String description, String productID, Integer quantity, Float perUnitCost) throws RemoteException;

        public Result deleteEntryFromInventory(String serverIP, String dbName, String tableName, String productID) throws RemoteException;
        public Result decrementEntryFromInventory(String serverIP, String dbName, String tableName, String productID) throws RemoteException;
}
