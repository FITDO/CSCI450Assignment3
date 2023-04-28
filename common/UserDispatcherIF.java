package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.ControlPanelIFs.AdminControlPanelIF;
import common.ControlPanelIFs.CustomerControlPanelIF;

/**
 * Class: UserDispatcherIF
 * 
 * A Interface that provides common methods for Userdispatcher
 * classes. Theses classes are a part of the front controller pattern
 * Control Panels will be sent to the user as views. Since this class
 * is the remote object it will have permission checking. Every user will
 * be able to log in or create an account. The more advanced functions will
 * need a AdminControlpanel to run. This is to prevent hackers from gaining access
 * to systems they shouldn't be able to.
 */
public interface UserDispatcherIF extends Remote
{
    /**
     * A way for users to add their accounts to the system
     * The user dispatcher should take a username and password then 
     * create a customer account and send back a string informing the
     * user if the task worked.
     * @param username
     * @param password
     * @return
     * @throws RemoteException
     */
    public String registerCustomerAccount (String username , String password) throws RemoteException;

    /**
     * A method for customers to login. The username will be used to find the correct account
     * then the password will be used to unlock it. if either are wrong the login will fail.
     * 
     * @param username
     * @param password
     * @return
     * @throws RemoteException
     */
    public CustomerControlPanelIF customerLogin (String username , String password) throws RemoteException;

    /**
     * A method for customers to login. The username will be used to find the correct account
     * then the password will be used to unlock it. if either are wrong the login will fail.
     * 
     * @param username
     * @param password
     * @return
     * @throws RemoteException
     */
    public AdminControlPanelIF adminLogin (String username , String password) throws RemoteException;
}
