package common.ControlPanelIFs;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * CLASS: ControlPanelIF
 * 
 * Provides some commen methods from ControlPanels
 */
public interface ControlPanelIF extends Remote
{

    /**
     * Returns a booelan if the user who is controlling the controlpanel
     * is an admin or not
     * @return
     * @throws RemoteException
     */
    public boolean isAdmin () throws RemoteException;

    /**
     * Returns a list of all items in the store
     * 
     * @return
     * @throws RemoteException
     */
    public String listAllStoreItems () throws RemoteException;

    /**
     * Returns infomration about a selected item.
     * Uses an index to find the item.
     * 
     * @param index
     * @return
     * @throws RemoteException
     */
    public String getItemInfo (int index) throws RemoteException;

    /**
     * Returns the username of the account opperating the controlPanel
     * @return
     * @throws RemoteException
     */ 
    public String getUsernameofAccount () throws RemoteException;

    /**
     * Allows the user to change their current password to a new one.
     * They will need their current password before they can change it
     * however.
     * 
     * @param oldpass
     * @param newpass
     * @return
     * @throws RemoteException
     */
    public boolean changePassword (String oldpass , String newpass) throws RemoteException;
}
