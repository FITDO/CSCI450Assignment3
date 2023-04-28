package server.Accounts;

import java.rmi.RemoteException;

import common.ControlPanelIFs.AdminControlPanelIF;
import server.OnlineStore;
import server.UserDispatcherImpl;
import server.Accounts.ControlPanelImpls.AdminControlPanelImpl;

/**
 * Class: AdminAccount
 * 
 * Holds info for an admin and returns a manager to the user
 */
public class AdminAccount extends ABSAccount
{
    // the control panel for the user
    private AdminControlPanelImpl acp_;

    /**
     * A constructor that builds an admin account
     * 
     * @param name
     * @param password
     * @throws RemoteException
     */
    public AdminAccount (String name, String password) throws RemoteException 
    {
        super (name, password);
        this.acp_ = null;
    }

    /**
     * Returns a true since this account is an admin
     */
    @Override
    public boolean isAdmin () 
    {
        return true;
    }

    /**
     * A method that is used to return a control panel to the client. This
     * way the client can interact with an account store, and userdispatcher
     * @param store
     * @param dispatcher
     * @return
     * @throws RemoteException
     */
    public AdminControlPanelIF getAdminControlPanel (OnlineStore store, UserDispatcherImpl dispatcher) throws RemoteException 
    {
        if (this.acp_ == null) // ControlPanel wasn't made yet
        {
            this.acp_ = new AdminControlPanelImpl (this , store , dispatcher);
        }

        return this.acp_;
    }
}
