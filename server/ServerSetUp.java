package server;

import java.rmi.Naming;
import java.util.Scanner;

import common.UserDispatcherIF;

/**
 * A class to set up the store and server
 */
public class ServerSetUp 
{
    
    // variables
    // server_ is where the rmi object is stored
    private String serverURL_;
    private UserDispatcherIF userLogin_;

    // Constructor
    public ServerSetUp (char num , String username , String password)
    {
        this.serverURL_ = "//in-csci-rrpc0" + num + ".cs.iupui.edu:2323/UserDispatcherIF";
        try
        {
            this.userLogin_ = new UserDispatcherImpl (username , password);
        }
        catch (Exception e)
        {
            System.out.println ("Server err: " + e.getMessage ());
            e.printStackTrace ();
        }
    }

    // main
    public static void main (String[] args)
    {
        /**
         * Prompts the user to enter the number of the rrpc machine they are on
         * Once entered create a RemoteAccount Object and run.
         */
        Scanner scan = new Scanner(System.in);
        System.out.println ("What rrpc machine are you on? \nEnter one number:");
        char num = scan.nextLine().charAt (0);
        System.out.println ("Enter a password and username for your admin account \nYou will use this to log in on the client side");
        System.out.println ("What is the username of your main admin account");
        String username = scan.nextLine ();
        System.out.println ("What is the password of your main admin account");
        String password = scan.nextLine ();
        ServerSetUp server = new ServerSetUp (num , username , password);
        server.run ();
        scan.close ();
    }

    /**
     * Creates an AccounntManagerInterface object and binds it to the 2323 port
     * Catchs any Exceptions that occur
     */
    public void run ()
    {
        try
        {
            Naming.rebind (this.serverURL_ , this.userLogin_);
            System.out.println (this.serverURL_);
            System.out.println ("Server is ready");
        }
        catch (Exception e)
        {
            System.out.println("Server err: " + e.getMessage ());
            e.printStackTrace ();
        }
    }
}
