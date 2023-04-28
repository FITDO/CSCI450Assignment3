package common.ControlPanelIFs;

import java.rmi.RemoteException;

import common.ItemIF;

/**
 * Class: AdminControlPanelIF
 * 
 * A interface for the AdminControlPanel that allows the client to interact
 * with the server. This prevents the user from getting access to systems they
 * shouldn't.
 */
public interface AdminControlPanelIF extends ControlPanelIF
{
    
    /**
     * The client requests an item from the store and can interact with it.
     * An index is used to retrieve an item an pass it to the user.
     * 
     * @param index
     * @return ItemIF
     * @throws RemoteException
     */
    public ItemIF getItemFromStore (int index) throws RemoteException;

    /**
     * A method to remove an item from the store. The user enters a index for
     * the system to remove an item. a boolean is returned to inform the id the
     * operation worked.
     * @param index
     * @return boolean
     * @throws RemoteException
     */
    public boolean removeItem (int index) throws RemoteException;

    /**
     * A method to add an item from the store. The user enters a index for
     * the system to remove an item. a boolean is returned to inform the id the
     * operation worked.
     * 
     * @param name
     * @param type
     * @param description
     * @param price
     * @param amount
     * @return boolean
     * @throws Exception
     * @throws RemoteException
     */
    public boolean addItem (String name , String type , String description , Double price , int amount) throws Exception , RemoteException;

    /**
     * This method sends back a complete list of the accounts that are labbeled as pending
     * @return
     * @throws RemoteException
     */
    public String viewPendingAccounts () throws RemoteException;

    /**
     * This method will verify a pending account with an index and return a string that
     * infroms the client what happened.
     * 
     * @param index
     * @return String
     * @throws RemoteException
     */
    public String verifyPendingAccount (int index) throws RemoteException;

    /**
     * This method will deny a pending account with an index and return a string that
     * infroms the client what happened.
     * 
     * @param index
     * @return
     * @throws RemoteException
     */
    public String denyPendingAccount (int index) throws RemoteException;

    /**
     * This method can remove a customer account using an index. A string will be returned 
     * informing the user of what happened.
     * 
     * @param index
     * @return
     * @throws RemoteException
     */
    public String removeCustomerAccount (int index) throws RemoteException;

    /**
     * This method will add a account to the accounts as long as the username isnt being used.
     * The string will infrom the user what happened.
     * 
     * @param username
     * @param password
     * @return String
     * @throws RemoteException
     */
    public String addCustomerAccount (String username , String password) throws RemoteException;

    /**
     * This method will add a account to the accounts as long as the username isnt being used.
     * The string will infrom the user what happened.
     * 
     * @param username
     * @param password
     * @return String
     * @throws RemoteException
     */
    public String addAdminAccount (String username , String password) throws RemoteException;

    /**
     * THis will produce a list of the Customer accounts and return it to the client
     * 
     * @return String
     * @throws RemoteException
     */
    public String viewCustomerAccounts () throws RemoteException;

    /**
     * THis will produce a list of the Admin accounts and return it to the client
     * 
     * @return String
     * @throws RemoteException
     */
    public String viewAdminAccounts () throws RemoteException;
}
