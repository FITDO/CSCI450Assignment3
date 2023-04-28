import java.util.Scanner;

import client.ClientSetUp;
import server.ServerSetUp;

/**
 * Class: ClientandServerSetUp
 * 
 * A class that is used by a jar file since there can only be only be one main.
 * Provides a menu for choosing between setting up the server or client
 */
public class ClientandServerSetUp 
{

    // variables
    private ClientSetUp cl_;
    private ServerSetUp sl_;

    // default constructor
    public ClientandServerSetUp ()
    {
        this.cl_ = null;
        this.sl_ = null;
    }

    /**
     * Once called requests information needed from the user to run the client
     * Takes a scanner for input and the user must enter what rrpc machine the 
     * server is on for it to work
     * @param scan
     */
    public void runClient (Scanner scan)
    {
        System.out.println( "What rrpc machine is your server on? \nEnter one number:");
        char num = scan.nextLine().charAt(0);
        this.cl_ = new ClientSetUp (num);
        System.out.println ("Running Client");
        this.cl_.run (scan);
    }

    /**
     * Once called requests information needed from the user to run the server
     * Takes a scanner for input and the user must enter what rrpc machine you
     * are currently on for it to work. You will also need to enter a username
     * and password to use on the client.
     * @param scan
     */
    public void runServer (Scanner scan)
    {
        System.out.println ("What rrpc machine are you on? \nEnter one number:");
        char num = scan.nextLine().charAt(0);
        System.out.println ("Enter a password and username for your admin account \nYou will use this to log in on the client side");
        System.out.println ("What is the username of your main admin account");
        String username = scan.nextLine ();
        System.out.println ("What is the password of your main admin account");
        String password = scan.nextLine ();
        this.sl_ = new ServerSetUp (num , username , password);
        this.sl_.run ();
    }

    // main
    public static void main (String[] args)
    {
        ClientandServerSetUp cassu = new ClientandServerSetUp ();
        Scanner scan = new Scanner (System.in);

        System.out.println("Welcome to an online store");
        System.out.println("Are you about to run the \n0) Quit \n1) Server \n2) Client");
        int choice = Integer.valueOf (scan.nextLine());
        switch (choice)
        {
            case 0:
                System.out.println ("Quiting");
                break;
            case 1:
                System.out.println("Entering Client Setup");
                cassu.runServer (scan);
                break;
            case 2:
                System.out.println ("Entering Server SetUp");
                cassu.runClient (scan);
                break;
            default:
                System.out.println ("Stopping");
                break;

        }
    }
}
