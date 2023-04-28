package client.Views;

import java.util.Scanner;

import common.ItemIF;
import common.UserDispatcherIF;
import common.ControlPanelIFs.AdminControlPanelIF;

/**
 * CLASS: AdminView
 * 
 * The AdminView object is used by the client to interact with an 
 * AdminControlPanelIF that was retrieved from the server. The AdminView
 * is entered into this view by the show() method. Each field is divided into
 * diffrent methods that act as sub menus.
 */
public class AdminView 
{
    // Variable


    // The adminControlPanel the client uses to issue commands
    private AdminControlPanelIF acp_;

    /**
     * Constructor
     * 
     * The AdminView is constructed from an controlPanelIF and userdispatch.
     * The AdminControlPanel needs the userdispatcher to interact with the accounts
     * in the system.
     */
    public AdminView (AdminControlPanelIF controlPanelIF , UserDispatcherIF ud)
    {
        try
        {
            this.acp_ = controlPanelIF;
        }
        catch (Exception e)
        {
            System.out.println ("Client err: " + e.getMessage());
            e.printStackTrace ();
        }
    }

    /**
     * The main method of the AdminView class. The client will enter a menu that is divided into
     * many smaller menus. This acts as a hub for all those menus.
     * @param scan
     */
    public void show (Scanner scan)
    {

        int choice = -1;
        boolean keepGoing = true;
        while (keepGoing)
        {
            try
            {
                System.out.println ("Welcome " + this.acp_.getUsernameofAccount());
                System.out.println ("What would you like to do?");
                System.out.println ("0) Log out \n1) Interact with account database \n2) Interact with store items \n3) Edit your account");
                choice = Integer.valueOf (scan.nextLine());
                switch (choice)
                {
                    case 0:
                        keepGoing = false;
                        break;
                    case 1:
                        System.out.println("Entering account database menu");
                        this.accountDatabaseMenu (scan);
                        break;
                    case 2:
                        System.out.println("Enetering item menu");
                        this.storeMenu (scan);
                        break;
                    case 3:
                        System.out.println ("Entering account options");
                        this.accountMenu (scan);
                        break;
                    default:
                        System.out.println ("Unknown command");
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println("Client err: " + e.getMessage());
                e.printStackTrace ();
            }
        }
        System.out.println ("Exiting Admin View");
    }

    /**
     * Here the client will interact with Items in the store. They can get a list of the
     * stores items. View a specific item in more depth, edit an exisiting item, add new items,
     * and remove items
     * @param scan
     */
    private void storeMenu (Scanner scan)
    {
        int choice = -1;
        int itemNum = -1;
        boolean keepGoing = true;
        while (keepGoing)
        {
            try
            {
                System.out.println ("What would you like to do?");
                System.out.println ("0) Quit \n1) Generate list of store items \n2) View specific item \n3) Edit existing item info \n4) Add new item to store \n5) Remove an item");
                choice = Integer.valueOf (scan.nextLine());

                switch (choice)
                {
                    case 0:
                        keepGoing = false;
                        break;
                    case 1:
                        System.out.println ("Store Items: ");
                        System.out.println (this.acp_.listAllStoreItems());
                        break;
                    case 2:
                        System.out.println ("Store Items: ");
                        System.out.println (this.acp_.listAllStoreItems());
                        System.out.println ("What item are you looking for enter the index");
                        itemNum = Integer.valueOf (scan.nextLine());
                        System.out.println (this.acp_.getItemInfo(itemNum));
                        break;
                    case 3:
                        System.out.println ("Entering edit Item menu");
                        this.editItemMenu (scan);
                        break;
                    case 4:
                        System.out.println ("Entering add item menu");
                        this.addItemMenu (scan);
                        break;
                    case 5:
                        System.out.println ("Store Items: ");
                        System.out.println (this.acp_.listAllStoreItems());
                        System.out.println ("What item would you like to remove");
                        itemNum = Integer.valueOf (scan.nextLine());
                        if (this.acp_.removeItem (itemNum))
                        {
                            System.out.println ("The item was removed");
                        }
                        else
                        {
                            System.out.println ("The item wasn't removed");
                        }
                        break;
                    default:
                        System.out.println ("Unknown command");
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println("Client err: " + e.getMessage());
                e.printStackTrace ();
            }
        }
        System.out.println ("Exiting item menu");
    }

    /**
     * This menu helps the user find a item to edit.
     * @param scan
     */
    private void editItemMenu (Scanner scan)
    {
        int choice = -2;
        ItemIF proxy = null;
        boolean keepLooking = true;
        while (keepLooking)
        {
            try 
            {
                System.out.println ("Store Items: ");
                System.out.println (this.acp_.listAllStoreItems());
                System.out.println ("What item would you like to edit (enter -1 to quit)");
                choice = Integer.valueOf (scan.nextLine());
                switch (choice)
                {
                    case -1:
                        keepLooking = false;
                        break;
                    default:
                        proxy = this.acp_.getItemFromStore (choice);
                        if (proxy != null)
                        {
                            System.out.println ("Found item");
                            this.editMenu (scan, proxy);
                        }
                        else
                        {
                            System.out.println ("Couldn't find item");
                        }
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println("Client err: " + e.getMessage());
                e.printStackTrace ();
            }
        }
        System.out.println ("Exiting edit item menu");
    }

    /**
     * This menu is pretty specialized. It allows the client to edit a single item.
     * The reason is that there is so much to change about every item and it could easily
     * get out of control so by making it its own menu we can cut out a lot of unndeeded
     * complexity.
     *  @param scan
     */
    private void editMenu (Scanner scan , ItemIF eItem)
    {   
        int choice = -1;
        String word = "";
        while (eItem != null)
        {
            try
            {
                System.out.println (eItem.makeFullReport());
                System.out.println ("0) Quit \nWhat would you like to edit \n1) Name \n2) Type \n3) Description \n4) Price \n5) AmountInStock");
                choice = Integer.valueOf (scan.nextLine ());
                switch (choice)
                {
                    case 0:
                        eItem = null;
                        break;
                    case 1:
                        System.out.println ("Current name: " + eItem.getName());
                        System.out.println ("What is the new name? (Enter nothing to keep current name)");
                        word = scan.nextLine ();
                        if (word != "")
                        {
                            eItem.setName (word);
                            System.out.println ("Name set");
                        }
                        break;
                    case 2:
                        System.out.println ("Current type: " + eItem.getType());
                        System.out.println ("What is the new type? (Enter nothing to keep current type)");
                        word = scan.nextLine ();
                        if (word != "")
                        {
                            eItem.setType (word);
                            System.out.println ("Type set");
                        }
                        break;
                    case 3:
                        System.out.println ("Current Description: " + eItem.getDescription());
                        System.out.println ("What is the new Description? (Enter nothing to keep current Description)");
                        word = scan.nextLine ();
                        if (word != "")
                        {
                            eItem.setDescription (word);
                            System.out.println ("Description set");
                        }
                        break;
                    case 4:
                        System.out.println ("Current Price : $" + String.valueOf (eItem.getPrice()));
                        System.out.println ("What is the new price? (Enter nothing to keep current price)");
                        word = scan.nextLine ();
                        if (word != "")
                        {
                            eItem.setPrice (Double.valueOf(word));
                            System.out.println ("Price set");
                        }
                        break;
                    case 5:
                        System.out.println ("Current Stock : " + String.valueOf (eItem.getAmountInStock()));
                        System.out.println ("What is the new Stock? (Enter nothing to keep current Stock)");
                        word = scan.nextLine ();
                        if (word != "")
                        {
                            eItem.setAmountInStock (Integer.valueOf(word));
                            System.out.println ("Stock set");
                        }
                        break;
                    default:
                        System.out.println ("Unknown command");
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println("Client err: " + e.getMessage());
                e.printStackTrace ();
            }
        }
    }

    /**
     * This menu is for adding an item to the store. Here the client will enter a 
     * name, type, description, and price.
     * @param scan
     */
    private void addItemMenu (Scanner scan)
    {
        try
        {
            String name = "";
            String type = "";
            String description = "";
            Double price = 0.0;
            int stock = 0;
            System.out.println ("What is the name of the item");
            name = scan.nextLine ();
            System.out.println ("What is the type");
            type = scan.nextLine ();
            System.out.println ("What is the description");
            description = scan.nextLine ();
            System.out.println ("What is the price");
            price = Double.valueOf (scan.nextLine ());
            System.out.println ("What is the stock");
            stock = Integer.valueOf(scan.nextLine ());
            this.acp_.addItem (name, type, description, price, stock);
        }
        catch (Exception e)
        {
            System.out.println("Client err: " + e.getMessage());
            e.printStackTrace ();
        }
    }

    /**
     * This menu is a hub for messing with pending accounts, customer accounts, and admin accounts.
     * @param scan
     */
    private void accountDatabaseMenu (Scanner scan)
    {
        int choice = -1;

        boolean keepgoing = true;
        while (keepgoing)
        {
            try
            {
                System.out.println ("What would you like to do");
                System.out.println ("0) Quit \n1) Interact with pending customer accounts \n2) Interact with customer accounts \n3) Interact with admin accounts");
                choice = Integer.valueOf(scan.nextLine());
                switch (choice)
                {
                    case 0:
                        keepgoing = false;
                        break;
                    case 1:
                        System.out.println ("Entering Pending customer account menu");
                        this.pendingAccountMenu (scan);
                        break;
                    case 2:
                        System.out.println ("Entering Customer Menu");
                        this.customerAccountMenu (scan);
                        break;
                    case 3:
                        System.out.println ("Enter Admin Menu");
                        this.adminAccountMenu (scan);
                        break;
                    default:
                        System.out.println ("Unknown Command");
                }

            }
            catch (Exception e)
            {
                System.out.println("Client err: " + e.getMessage());
                e.printStackTrace ();
            }
        }
        System.out.println ("Exiting account databse menu");
    }

    /**
     * This menu is for interacting with pending accounts. Here the client
     * can view pending accounts, verify pending accounts, or deny them.
     * @param scan
     */
    private void pendingAccountMenu (Scanner scan)
    {
        int choice = -1;
        int index = -1;
        boolean keepgoing = true;
        while (keepgoing)
        {
            try
            {
                System.out.println ("What would you like to do");
                System.out.println ("0) Quit \n1) View customer pending accounts \n2) Verify pending customer account \n3) Deny pending customer account");
                choice = Integer.valueOf (scan.nextLine());
                switch (choice)
                {
                    case 0:
                        keepgoing = false;
                        break;
                    case 1:
                        System.out.println ("Pending Accounts: ");
                        System.out.println (this.acp_.viewPendingAccounts());
                        break;
                    case 2:
                        System.out.println ("Pending Accounts: ");
                        System.out.println (this.acp_.viewPendingAccounts());
                        index = Integer.valueOf (scan.nextLine());
                        System.out.println (this.acp_.verifyPendingAccount(index));
                        break;
                    case 3:
                        System.out.println ("Pending Accounts: ");
                        System.out.println (this.acp_.viewPendingAccounts());
                        index = Integer.valueOf (scan.nextLine());
                        System.out.println (this.acp_.denyPendingAccount(index));
                        break;
                    default:
                        System.out.println("Unknown Command");
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println("Client err: " + e.getMessage());
                e.printStackTrace ();
            }
        }
        System.out.println ("Exiting pending account menu");
    }

    /**
     * This menu is for interacting with customer accounts. Here the client
     * can view customer accounts, add them, or delete.
     * @param scan
     */
    private void customerAccountMenu (Scanner scan)
    {
        int choice = -1;
        int index = -1;
        boolean keepgoing = true;
        while (keepgoing)
        {
            try
            {
                System.out.println ("What would you like to do");
                System.out.println ("0) Quit \n1) View customer accounts \n2) Add customer account \n3) Delete customer account");
                choice = Integer.valueOf (scan.nextLine());
                switch (choice)
                {
                    case 0:
                        keepgoing = false;
                        break;
                    case 1:
                        System.out.println ("Customer accounts: ");
                        System.out.println (this.acp_.viewCustomerAccounts());
                        break;
                    case 2:
                        System.out.println ("Enter a customer username");
                        String username = scan.nextLine();
                        System.out.println ("Enter a password");
                        String password = scan.nextLine();
                        System.out.println (this.acp_.addCustomerAccount(username, password));
                        break;
                    case 3:
                        System.out.println ("Customer accounts: ");
                        System.out.println (this.acp_.viewCustomerAccounts());
                        System.out.println ("What account do you want to delete (enter index)");
                        index = Integer.valueOf (scan.nextLine());
                        this.acp_.removeCustomerAccount (index);
                        break;
                    default:
                        System.out.println("Unknown Command");
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println("Client err: " + e.getMessage());
                e.printStackTrace ();
            }
        }
        System.out.println ("Exiting customer account menu");
    }

    /**
     * This menu is for interacting with admin accounts. Here the client can
     * add other admins, or view them.
     * @param scan
     */
    private void adminAccountMenu (Scanner scan)
    {
        int choice = -1;
        boolean keepgoing = true;
        while (keepgoing)
        {
            try
            {
                System.out.println ("What would you like to do");
                System.out.println ("0) Quit \n1) View admin accounts \n2) Add admin account");
                choice = Integer.valueOf (scan.nextLine());
                switch (choice)
                {
                    case 0:
                        keepgoing = false;
                        break;
                    case 1:
                        System.out.println ("Admin accounts: ");
                        System.out.println (this.acp_.viewAdminAccounts());
                        break;
                    case 2:
                        System.out.println ("Enter a Admin username");
                        String username = scan.nextLine();
                        System.out.println ("Enter a password");
                        String password = scan.nextLine();
                        System.out.println (this.acp_.addAdminAccount(username, password));
                        break;
                    default:
                        System.out.println("Unknown Command");
                        break;
                }
            
            }
            catch (Exception e)
            {
                System.out.println("Client err: " + e.getMessage());
                e.printStackTrace ();
            }
        }
        System.out.println ("Exiting admin account menu");

    }

    /**
     * This is for interacting with the clients account. You can view your
     * username or change your password.
     * @param scan
     */
    private void accountMenu (Scanner scan)
    {
        int choice = -1;
        String oldPassword = "";
        String newPassword = "";
        boolean keepgoing = true;

        while (keepgoing)
        {
            try
            {
                System.out.println ("What would you like to do");
                System.out.println ("0) Quit \n1) Get Username \n2) Change Password");
                choice = Integer.valueOf (scan.nextLine());
                switch (choice)
                {
                    case 0:
                        keepgoing = false;
                        break;
                    case 1:
                        System.out.println ("Username: " + this.acp_.getUsernameofAccount());
                        break;
                    case 2:
                        System.out.println ("What is your current password");
                        oldPassword = scan.nextLine();
                        System.out.println ("What is your new password");
                        newPassword = scan.nextLine();

                        if (this.acp_.changePassword(oldPassword, newPassword))
                        {
                            System.out.println("Password was changed");
                        }
                        else
                        {
                            System.out.println("Failed to change password");
                        }
                        break;
                    default:
                        System.out.println ("Unknown Command");
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println("Client err: " + e.getMessage());
                e.printStackTrace ();
            }
        }
        System.out.println ("Exiting account menu");
    }
}