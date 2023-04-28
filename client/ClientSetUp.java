package client;

import java.rmi.Naming;
import java.util.Scanner;

import common.UserDispatcherIF;

/**
 * Class: ClientSetup
 * 
 * A class that the client uses to connect to a server with a
 * UserDispatcherFactoryIF
 */
public class ClientSetUp 
{
    
    private String serverURL_;
    private UserDispatcherIF loginSystem_;
    private UserRetriever retriever_;

    /**
     * Constructor with a char. Uses server_ to find a UserDispatcherIF.
     * @param num
     */
    public ClientSetUp (char num)
    {
        try
        {
            this.serverURL_ = "//in-csci-rrpc0" + num + ".cs.iupui.edu:2323/UserDispatcherIF";
            this.loginSystem_ = (UserDispatcherIF)Naming.lookup (this.serverURL_);
            this.retriever_ = new UserRetriever (loginSystem_);
            System.out.println ("Found server object. Beggining client");
        }
        catch (Exception e)
        {
            System.out.println ("Client err: " + e.getMessage());
            e.printStackTrace ();
        }
        
    }
    
    /**
     * Creats a client store and runs it with a scanner
     * @param scan
     */
    public void run (Scanner scan)
    {
        try
        {
            if (this.loginSystem_ == null)
            {
                return;
            }

            this.retriever_.loginMenu(scan) ;
            
        }
        catch (Exception e)
        {
            System.out.println( "Client err :" + e.getMessage ());
            e.printStackTrace ();
            System.out.println ("Stopping Client.");
        }
    }

    public static void main (String[] args)
    {
        /**
         * Get what rrpc the server is running on and create a
         * Accountclient object and run it.
         */
        Scanner scan = new Scanner(System.in);
        System.out.println ("What rrpc machine is your server on? \nEnter one number:");
        char num = scan.nextLine().charAt(0);
        try
        {
            ClientSetUp client = new ClientSetUp (num);
            client.run (scan);
        }
        catch (Exception e)
        {
            System.out.println ("Client err: " + e.getMessage());
            e.printStackTrace ();
            System.out.println ("Stopping Client.");
        }
        

        scan.close ();
    }
}

