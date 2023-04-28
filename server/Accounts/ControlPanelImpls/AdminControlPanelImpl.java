package server.Accounts.ControlPanelImpls;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.ControlPanelIFs.AdminControlPanelIF;
import server.OnlineStore;
import server.UserDispatcherImpl;
import server.Accounts.AdminAccount;
import server.Items.ItemImpl;

/**
 * Class: AdminControlPanelImpl
 * 
 * An Implimentation of AdminControlPanel. This acts as a way for Admins of the online store
 * to interact with it. Here they can issue commands to view, create, and delete accounts.
 * There also ways for a admin to interact with the online store such as add, editing, and
 * removing items. 
 */
public class AdminControlPanelImpl extends UnicastRemoteObject implements AdminControlPanelIF
{

    // variables


    // This is the account of the user operating the control panel
    private AdminAccount owner_;
    // The client can add, edit, or remove items with this but can't buy them
    private OnlineStore store_;
    // The item that holds all the users
    private UserDispatcherImpl userDatabase_;

    /**
     * A construtor that sets up the AdminAccount, OnlineStore, and
     * UserDispatcherIF.
     * 
     * @param owner
     * @param store
     * @throws RemoteException
     */
    public AdminControlPanelImpl (AdminAccount owner , OnlineStore store , UserDispatcherImpl userDispatcher) throws RemoteException 
    {
        super ();
        this.owner_ = owner;
        this.store_ = store;
        this.userDatabase_ = userDispatcher;
    }

    /**
     * A method to return the username of the AdminAccount
     * 
     * @return String
     * @throws RemoteException
     */
    @Override
    public String getUsernameofAccount () throws RemoteException 
    {
        return this.owner_.getUsername ();
    }

    /**
     * Allows the user to change their current password to a new one.
     * They will need their current password before they can change it
     * however. Calls the owner account to verify.
     * 
     * @param oldpass
     * @param newpass
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean changePassword (String curpass , String newpass) throws RemoteException 
    {
        return this.owner_.resetPassword (curpass, newpass);
    }   

    /**
     * Returns a booelan that informs it is a admin. Always returns true
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean isAdmin () throws RemoteException 
    {
        return true;
    }

    /**
     * Returns a list of all items in the store. Calls the store to do this
     * 
     * @return
     * @throws RemoteException
     */
    @Override
    public String listAllStoreItems () throws RemoteException 
    {
        return this.store_.getCataloge ();
    }

    /**
     * Returns infomration about a selected item.
     * Uses an index to find the item. Calls the store to do this
     * 
     * @param index
     * @return
     * @throws RemoteException
     */
    @Override
    public String getItemInfo (int index) throws RemoteException 
    {
        return this.store_.getItemInfo (index);
    }

    /**
     * The client requests an item from the store and can interact with it.
     * An index is used to retrieve an item an pass it to the user.
     * Calls the store to do this
     * 
     * @param index
     * @return ItemIF
     * @throws RemoteException
     */
    @Override
    public ItemImpl getItemFromStore (int index) throws RemoteException 
    {
        System.out.println ("Requesting Item from store for admin");
        return this.store_.getItem (index);
    }

    /**
     * A method to add an item from the store. The user enters a index for
     * the system to remove an item. a boolean is returned to inform the id the
     * operation worked. Calls the store to do this
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
    @Override
    public boolean addItem (String name , String type , String description , Double price , int amount) throws Exception , RemoteException 
    {
        System.out.println ("Trying to add item");
        return this.store_.addItem (new ItemImpl(name , type , description , price , amount));
    }

    /**
     * This method can remove a customer account using an index. A string will be returned 
     * informing the user of what happened. Calls the store to do this
     * 
     * @param index
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean removeItem (int index) throws RemoteException 
    {
        System.out.println ("trying to remove an item");
        return this.store_.removeItem (index);
    }

    /**
     * This method sends back a complete list of the accounts that are labbeled as pending.
     * Calls the userdispatcher
     * @return
     * @throws RemoteException
     */
    @Override
    public String viewPendingAccounts () throws RemoteException 
    {
        return this.userDatabase_.getPendingAccounts ();
    }

    /**
     * This method will verify a pending account with an index and return a string that
     * infroms the client what happened.
     * 
     * @param index
     * @return String
     * @throws RemoteException
     */
    @Override
    public String verifyPendingAccount (int index) throws RemoteException 
    {
        return this.userDatabase_.verifyPendingAccount (index);
    }

    /**
     * This method will deny a pending account with an index and return a string that
     * infroms the client what happened.
     * 
     * @param index
     * @return
     * @throws RemoteException
     */
    @Override
    public String denyPendingAccount (int index) throws RemoteException
    {
        return this.userDatabase_.denyPendingAccount (index);
    }

    /**
     * A method to remove an item from the store. The user enters a index for
     * the system to remove an item. a boolean is returned to inform the id the
     * operation worked.
     * @param index
     * @return boolean
     * @throws RemoteException
     */
    @Override
    public String removeCustomerAccount (int index) throws RemoteException 
    {
        return this.userDatabase_.removeCustomerAccount (index);
    }

    /**
     * This method will add a account to the accounts as long as the username isnt being used.
     * The string will infrom the user what happened.
     * 
     * @param username
     * @param password
     * @return String
     * @throws RemoteException
     */
    @Override
    public String addCustomerAccount (String username, String password) throws RemoteException 
    {
        return this.userDatabase_.addCustomerAccount (username , password);
    }

    /**
     * This method will add a account to the accounts as long as the username isnt being used.
     * The string will infrom the user what happened.
     * 
     * @param username
     * @param password
     * @return String
     * @throws RemoteException
     */
    @Override
    public String addAdminAccount (String username, String password) throws RemoteException 
    {
        return this.userDatabase_.addAdminAccount (username , password);
    }

    /**
     * This method can remove a customer account using an index. A string will be returned 
     * informing the user of what happened.
     * 
     * @param index
     * @return
     * @throws RemoteException
     */
    @Override
    public String viewCustomerAccounts () throws RemoteException
    {
        return this.userDatabase_.getListOfCustomers ();
    }

    /**
     * THis will produce a list of the Admin accounts and return it to the client
     * 
     * @return String
     * @throws RemoteException
     */
    @Override
    public String viewAdminAccounts () throws RemoteException
    {
        return this.userDatabase_.getListOfAdmins ();
    }
}
