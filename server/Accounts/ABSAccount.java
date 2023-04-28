package server.Accounts;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class: ABSAccount
 * 
 * A abstract class that is used to define some general methods for accounts.
 * Sucha as those that involved usernames and passwords
 */
public abstract class ABSAccount extends UnicastRemoteObject
{
    protected String username_;
    protected String password_;

    /**
     * A constructor that builds a account using a username and password
     * @param name
     * @param password
     * @throws RemoteException
     */
    protected ABSAccount (String username , String password) throws RemoteException
    {
        super ();
        this.username_ = username;
        this.password_ = password;
    }

    // setters and getters

    /**
     * Returns a username to the client
     * @return
     * @throws RemoteException
     */
    public String getUsername () throws RemoteException
    {
        return this.username_;
    }

    /**
     * confirms a accounts password but needs the input to confirm
     * No return for password for security reasons
     * @param password
     * @return
     * @throws RemoteException
     */
    public boolean checkPassword (String password) throws RemoteException
    {
        if (this.password_.equals(password)) // password was right
        {
            return true;
        }
        else // password was wrong
        {
            return false;
        }
    }

    /**
     * A method for reseting a account's password. The currentpassword is needed before
     * the password can be changed. Returns a boolean that determines if the operation worked
     * 
     * @param oldpassword
     * @param newPassword
     * @return
     * @throws RemoteException
     */
    public boolean resetPassword (String currentpassword , String newPassword) throws RemoteException
    {
        if (this.checkPassword (currentpassword)) // the currentpassword was right
        {
            this.password_ = newPassword;
            System.out.println ("User " + this.username_ + "'s password has been changed.");
            return true;
        }
        else // the currentpassword was wrong
        {
            System.out.println ("An attempt to change " + this.username_ + "'s password failed.");
            return false;
        }
    }

    /**
     * This function returns a boolean dependent on if the account was
     * is an admin or not
     * 
     * @return
     * @throws RemoteException
     */
    public abstract boolean isAdmin () throws RemoteException;
}
