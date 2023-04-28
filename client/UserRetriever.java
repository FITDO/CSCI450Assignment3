package client;

import java.util.Scanner;

import client.Views.AdminView;
import client.Views.CustomerView;
import common.UserDispatcherIF;
import common.ControlPanelIFs.AdminControlPanelIF;
import common.ControlPanelIFs.CustomerControlPanelIF;

/**
 * CLASS: UserRetriever
 * 
 * The UserRetriever provides a easy wasy for clients to log in
 * to their customer or admin account. Here the user will be prompted
 * to log in to their admin or customer account. If the user fails to log
 * in the client will be unable to be move on.
 */
public class UserRetriever 
{

    // variables

    // UserDispatcher to retrieve accounts from the server
    private UserDispatcherIF dispatcher_;
    // adminview to provide a menu for the admin
    private AdminView admin_;
    // customerview to provide a menu for the customer
    private CustomerView customer_;

    // constructor
    public UserRetriever (UserDispatcherIF dispatcher)
    {
        this.dispatcher_ = dispatcher;
    }

    /**
     * A menu that provides a hub for the user loging in
     * The user can choose to login to their admin account,
     * login to their customer account, and create a customer
     * account.
     * @param scan
     */
    public void loginMenu (Scanner scan)
    {
        int choice = -1;
        boolean keepgoing = true;
        try
        {
            while (keepgoing)
            {
                System.out.println ("Hello User, please select your action \n0) Exit System \n1) Admin Login \n2) Customer Login \n3) Create Account");
                choice = scan.nextInt ();
                scan.nextLine();
                switch (choice)
                {
                    case 0:
                        keepgoing = false;
                        break;
                    case 1:
                        AdminControlPanelIF acp = this.startAdminLogin(scan);
                        if (acp != null)
                        {
                            this.startAdminView (scan , acp);
                            acp = null;
                        }
                        else // failed to log in
                        {
                            System.out.println("Failed to login");
                        }
                        break;
                    case 2:
                        CustomerControlPanelIF ccp = this.startCustomerLogin (scan);
                        if (ccp != null)
                        {
                            this.startCustomerView (scan , ccp);
                            ccp = null;
                        }
                        else // failed to log in
                        {
                            System.out.println("Failed to login");
                        }
                        break;
                    case 3:
                        this.createCustomerAccount (scan);
                        break;
                    default:
                        System.out.println ("Unknown Command");
                        break;
                }
            }
            System.out.println ("Exiting Login System");
        }
        catch (Exception e)
        {
            System.out.println( "Client err :" + e.getMessage ());
            e.printStackTrace ();
        }
    }

    /**
     * A menu for customer loging in. The user will enter a username, and
     * password. 
     * 
     * @param scan
     */
    private CustomerControlPanelIF startCustomerLogin (Scanner scan)
    {
        try
        {
            String username = "";
            String password = "";
            System.out.println ("Customer Login: \nEnter your username: ");
            username = scan.nextLine ();
            System.out.println ("Enter your password: ");
            password = scan.nextLine ();
            // request a customerControlPanel from the server.
            return this.dispatcher_.customerLogin (username, password);
        }
        catch (Exception e)
        {
            System.out.println ("There was an error logining in");
            System.out.println ("Err: " + e.getMessage());
            e.printStackTrace ();
        }

        System.out.println ("Couldn't login");
        return null;
    }

    /**
     * A menu for login in. The user will enter a username, and password.
     * @param scan
     * @return
     */
    private AdminControlPanelIF startAdminLogin (Scanner scan)
    {
        try
        {
            String username = "";
            String password = "";
            System.out.println ("Admin Login: \nEnter your username: ");
            username = scan.nextLine ();
            System.out.println ("Enter your password: ");
            password = scan.nextLine ();

            // request a AdminControlPanelIF from the server
            return this.dispatcher_.adminLogin (username, password);
        }
        catch (Exception e)
        {
            System.out.println ("There was an error logining in");
            System.out.println ("Err: " + e.getMessage());
            e.printStackTrace ();
        }
        
        System.out.println ("Couldn't login");
        return null;
    }

    /**
     * A menu for creating a customer account. The user will enter a username
     * and password. The client will recieve feedback from the server if the
     * server succsessfully made your account.
     * @param scan
     */
    private void createCustomerAccount (Scanner scan)
    {
        try
        {
            String username = "";
            String password = "";
            System.out.println("Customer Account Creator: \nEnter your username");
            username = scan.nextLine ();
            System.out.println ("Enter your password: ");
            password = scan.nextLine ();
            // request the server to made your account
            String accountMsg = this.dispatcher_.registerCustomerAccount (username, password);
            System.out.println (accountMsg);
        }
        catch (Exception e)
        {
            System.out.println ("There was an error creating the customer account");
            System.out.println ("Err: " + e.getMessage());
            e.printStackTrace ();
        }
    }

    /**
     * This method will take the client to the adminView.
     * 
     * @param scan
     * @param control
     */
    private void startAdminView (Scanner scan , AdminControlPanelIF control)
    {
        if (control == null)
        {
            return;
        }

        if (this.admin_ == null)
        {
            this.admin_ = new AdminView (control , this.dispatcher_);
        }

        System.out.println ("Starting admin view");
        this.admin_.show (scan);
        this.admin_ = null;
    }

    /**
     * This method will take the user to Customerview
     * @param scan
     * @param control
     */
    private void startCustomerView (Scanner scan , CustomerControlPanelIF control)
    {
        if (control == null)
        {
            return;
        }

        if (this.customer_ == null)
        {
            this.customer_ = new CustomerView (control);
        }

        System.out.println ("Starting customer view");
        this.customer_.show (scan);
        this.customer_ = null;
    }
}
