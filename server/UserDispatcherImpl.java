package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;

import common.UserDispatcherIF;
import common.ControlPanelIFs.AdminControlPanelIF;
import common.ControlPanelIFs.CustomerControlPanelIF;
import server.Accounts.AdminAccount;
import server.Accounts.CustomerAccount;

/**
 * Class: UserDispatcherImpl
 * 
 * An Implementation of UserDispactherIF. Main methods deal with user's loging in and creating accounts.
 * Customers who want to login will first be placed inside pending until the account is approved by
 * an admin. This is the main Remote object and client will need to connect to this object to 
 * intereact with the store. Some methods will need an AdminControlPanel to work
 */
public final class UserDispatcherImpl extends UnicastRemoteObject implements UserDispatcherIF
{
    // a linkedlist of admin accounts
    private LinkedList <AdminAccount> adminAccounts_;
    // a linkedlist of customer accounts
    private LinkedList <CustomerAccount> customerAccounts_;
    // a linkedlist of pending accounts. No one can login to these accounts but
    // an admin can move them from pending to customers.
    private LinkedList <CustomerAccount> pendingCustomerAccounts_;
    // the store. this object dosen't interact with this object but passes it to the
    // controlpanels for the client to interact with.
    private OnlineStore store_;

    /**
     * A constructor for the userdispatcherImpl. The adminUsername and adminPassword is used to
     * generate a admin account for the user to use the system.
     * 
     * @param adminUsername
     * @param adminPassword
     * @throws RemoteException
     */
    public UserDispatcherImpl (String adminUsername , String adminPassword) throws RemoteException 
    {
        super ();
        this.adminAccounts_ = new LinkedList <AdminAccount> ();
        this.customerAccounts_ = new LinkedList <CustomerAccount> ();
        this.pendingCustomerAccounts_ = new LinkedList <CustomerAccount>();
        this.store_ = new OnlineStore();
        
        this.adminAccounts_.add(new AdminAccount (adminUsername, adminPassword));
    }

    /**
     * A login for the customers that takes a username and password. If the account is
     * found and the password works it will return a login for the client to use.
     */
    @Override
    public CustomerControlPanelIF customerLogin (String username, String password) throws RemoteException 
    {
        Iterator <CustomerAccount> iter = this.customerAccounts_.iterator ();
        CustomerAccount ptr;
        while (iter.hasNext()) // iterates till it finds the account
        {
            ptr = iter.next ();
            if (ptr.getUsername().equals(username))
            {
                if (ptr.checkPassword(password))
                {
                    System.out.println ("Customer: " + ptr.getUsername() + " logged in");
                    return ptr.getCustomerControlPanel (this.store_);
                }
                else // password was wronged
                {
                    System.out.println ("Login failed \nWrong Password");
                    return null;
                }
            }
        }

        System.out.println ("Login failed");
        return null;
    }

    /**
     * A login for the admin that takes a username and password. If the account is
     * found and the password works it will return a login for the client to use.
     */
    @Override
    public AdminControlPanelIF adminLogin (String username , String password) throws RemoteException
    {
        System.out.println ("Admin is trying to login");
        Iterator <AdminAccount> iter = this.adminAccounts_.iterator ();
        AdminAccount ptr;
        while (iter.hasNext()) // oterates through the list
        {
            ptr = iter.next ();
            if (ptr.getUsername().equals(username))
            {
                if (ptr.checkPassword(password))
                {
                    System.out.println (username + " logged in");
                    return ptr.getAdminControlPanel (this.store_ , this);
                }
                else // password was wrong
                {
                    System.out.println ("Login failed \nWrong Password");
                    return null;
                }
            }
        }

        System.out.println ("Login failed");
        return null;
    }

    /**
     * A way for customers to register for their accounts. The system will peform some minor error
     * checking. Next it will look for any account that shares the username, if any are found it 
     * will not generate the account. A string is returned to inform the user of what happened.
     */
    @Override
    public String registerCustomerAccount (String username, String password) throws RemoteException 
    {

        if (username.contains(" "))
        {
            System.out.println ("Account creation failed");
            return "Cannot create account with space in username";
        }

        if (password.contains(" "))
        {
            System.out.println ("Account creation failed");
            return "Cannot create account with space in password";
        }

        Iterator <CustomerAccount> iter = this.customerAccounts_.iterator ();
        CustomerAccount ptr;
        while (iter.hasNext()) // iterates through the list
        {
            ptr = iter.next ();
            if (ptr.getUsername().equals(username)) // an account was found to share the same name
            {
                System.out.println ("Account creation failed");
                return "Account username already exists try a new name";
            }
        }

        iter = this.pendingCustomerAccounts_.iterator ();
        while (iter.hasNext()) // checks pending customer too
        {
            ptr = iter.next ();
            if (ptr.getUsername().equals(username)) // an account was found to share the same name
            {
                System.out.println ("Account creation failed");
                return "Pending Account username already exists try a new name";
            }
        }

        // account is made then added to the list
        ptr = new CustomerAccount (username , password);
        this.pendingCustomerAccounts_.add (ptr);

        System.out.println ("Account creation successful");
        return "Account created and pending approval";
    }

    /**
     * The admin will be able to add another admin to the system. The method will need a username and
     * a password to work. The username shouldn't already be in use. The Control panel dosen't help
     * the server generate a list but checks the permissions of the user tying to 
     * view the list. If the user dosen't have appropriate permissions the method will fail.
     * The string passed back will inform the client what happened
     * 
     * @param admin
     * @param username
     * @param password
     * @return
     * @throws RemoteException
     */
    public String addAdminAccount (String username, String password) throws RemoteException 
    {
        if (username.contains(" ")) // the admin contains a space
        {
            System.out.println ("Account creation failed");
            return "Cannot create account with space in username";
        }

        Iterator <AdminAccount> iter = this.adminAccounts_.iterator ();
        AdminAccount ptr;
        while (iter.hasNext()) // iterators through ever item 
        {
            ptr = iter.next ();
            if (ptr.getUsername().equals(username))
            {
                System.out.println ("Account creation failed");
                return "Account username already exists try a new name";
            }
        }

        ptr = new AdminAccount (username, password);
        this.adminAccounts_.add (ptr);
        System.out.println ("Admin Account was created");
        return "Admin Account was added to the System";
    }

    /**
     * Sends a list of pending users to the admin. The Control panel dosen't help
     * the server generate a list but checks the permissions of the user tying to 
     * view the list. If the user dosen't have appropriate permissions the method will
     * fail.
     * 
     * @return
     * @throws RemoteException
     */
    public String getPendingAccounts () throws RemoteException 
    {
        String msg = "";
        int count = 0;

        Iterator <CustomerAccount> iter = this.pendingCustomerAccounts_.iterator ();
        CustomerAccount ptr;
        while (iter.hasNext())
        {
            ptr = iter.next ();
            
            msg += String.valueOf (count) + ") " + ptr.getUsername () + "\n";
            count++;
        }

        return msg;
    }

    /**
     * The admin will verify a pending account and add it to the system. This way a customer will
     * be able use their account. The account will be found with an index.
     * 
     * @param accountNum
     * @return
     * @throws RemoteException
     */
    public String verifyPendingAccount (int accountNum) throws RemoteException 
    {
        if (accountNum < this.pendingCustomerAccounts_.size())
        {
            this.addAccountFromPending (accountNum);
            return "Account added";
        }
        else
        {
            return "Invalid Account Number";
        }

    }

    /**
     * The admin will deny a pending account and add it to the system. The account 
     * will be found with an index and removed from the system. 
     * 
     * @param accountNum
     * @return
     * @throws RemoteException
     */
    public String denyPendingAccount (int accountNum) throws RemoteException 
    {
        if (accountNum < this.pendingCustomerAccounts_.size())
        {
           this.pendingCustomerAccounts_.remove (accountNum);
            return "Account Removed";
        }
        else
        {
            return "Invalid Account Number";
        }
    }

    /**
     * The admin will be able to add another admin to the system. The method will need a username and
     * a password to work. The username shouldn't already be in use. The string passed back will 
     * inform the client what happened
     * 
     * @param username
     * @param password
     * @return
     * @throws RemoteException
     */
    public String addCustomerAccount (String username , String password) throws RemoteException
    {
        Iterator <CustomerAccount> iter = this.customerAccounts_.iterator ();
        CustomerAccount ptr;
        while (iter.hasNext())
        {
            ptr = iter.next ();
            if (ptr.getUsername().equals(username))
            {
                System.out.println ("Account creation failed");
                return "Account username already exists try a new name";
            }
        }

        iter = this.pendingCustomerAccounts_.iterator ();
        while (iter.hasNext())
        {
            ptr = iter.next ();
            if (ptr.getUsername().equals(username))
            {
                System.out.println ("Account creation failed");
                return "Account username already exists in pending accounts \nDelete pending account or try a new name";
            }
        }

        String msg = "Customer Account was added";
        System.out.println (msg);
        ptr = new CustomerAccount (username, password);
        this.customerAccounts_.add (ptr);

        return msg;
    }

    /**
     * This method will remove a customer from the system. The method will find the user using a index 
     * Once found the customer will be removed from the system. The string passed back will inform the
     * client what happened
     * 
     * @param admin
     * @param index
     * @return
     * @throws RemoteException
     */
    public String removeCustomerAccount (int index) throws RemoteException
    {
        if (index > this.customerAccounts_.size())
        {
            return "Invalid Index";
        }

        this.customerAccounts_.remove (index);
        System.out.println("Customer Account was Removed");
        return "Customer Account was removed";
    }   

    /**
     * A method to simplify verifying an account
     * 
     * @param accountNum
     * @throws RemoteException
     */
    private void addAccountFromPending (int accountNum) throws RemoteException
    {
        CustomerAccount ptr = this.pendingCustomerAccounts_.get (accountNum);
        this.pendingCustomerAccounts_.remove (accountNum);
        this.customerAccounts_.add (ptr);
        System.out.println (ptr.getUsername() + " was moved from pending accounts to customer account");
    }

    /**
     * This method will return a list of all customer saved in the system. The Control 
     * panel dosen't help the server generate a list but checks the permissions of the 
     * user tying to view the list. The string passed back will inform the client what 
     * happened
     * 
     * @return
     * @throws RemoteException
     */
    public String getListOfCustomers () throws RemoteException
    {
        String msg = "";
        int count = 0;
        Iterator <CustomerAccount> iter = this.customerAccounts_.iterator ();
        CustomerAccount ptr;
        while (iter.hasNext())
        {
            ptr = iter.next ();
            msg +=  String.valueOf(count) + ") " + ptr.getUsername() + " Balance: $" + ptr.getBalance() + "\n";
            count++;
        }

        return msg;
    }

    /**
     * This method will return a list of all admins saved in the system. The Control panel dosen't help
     * the server generate a list but checks the permissions of the user tying to view the list. 
     * The string passed back will inform the client what happened
     * 
     * @return
     * @throws RemoteException
     */
    public String getListOfAdmins () throws RemoteException
    {
        String msg = "";
        int count = 0;
        Iterator <AdminAccount> iter = this.adminAccounts_.iterator ();
        AdminAccount ptr;
        while (iter.hasNext())
        {
            ptr = iter.next ();
            msg +=  String.valueOf(count) + ") " + ptr.getUsername() +"\n";
            count++;
        }

        return msg;
    }
}