package client.Views;

import java.util.Scanner;

import common.CardCommands.CardCommandIF;
import common.ControlPanelIFs.CustomerControlPanelIF;

/**
 * CLASS: CustomerView
 * 
 * The CustomerView is used by a client view to act as a customer. Here
 * the client will interact with a customerControlPanel that was retrieved from
 * the server. Here the menus will act as a way to issue commands to the 
 * server
 */
public class CustomerView 
{
    // variables

    // The CustomerControlPanel the client usses to issue commands
    private CustomerControlPanelIF ccp_;

    public CustomerView (CustomerControlPanelIF controlPanelIF)
    {
        this.ccp_ = controlPanelIF;
    }

    /**
     * The main menu for the customer view acts as a hub for all other
     * sub menus.
     * 
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
                System.out.println ("Welcome " + this.ccp_.getUsernameofAccount());
                System.out.println ("What would you like to do?");
                System.out.println ("0) Log out \n1) Interact with balance \n2) Interact with store \n3) Interact with your cart \n4) Interact with your account");
                choice = Integer.valueOf (scan.nextLine());
                switch (choice)
                {
                    case 0:
                        keepGoing = false;
                        break;
                    case 1:
                        System.out.println("Entering balance menu");
                        this.balanceMenu (scan);
                        break;
                    case 2:
                        System.out.println("Entering store menu");
                        this.storeMenu (scan);
                        break;
                    case 3:
                        System.out.println("Entering cart menu");
                        this.cartMenu (scan);
                        break;
                    case 4:
                        System.out.println("Entering account menu");
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
        System.out.println ("Exiting Customer View");
    }

    /**
     * This menu is for adding funds to the user account. Here the user can
     * adds funds by giftcard, giftcode, visa, or debit card.
     * @param scan
     */
    private void balanceMenu (Scanner scan)
    {
        int choice = -1;
        double amount = 0.0;
        String cardnum = "";
        String secCode = "";
        String expDate = "";
        CardCommandIF cmd = null;
        boolean keepGoing = true;
        while (keepGoing)
        {
            try
            {
                System.out.println ("What would you like to do?");
                System.out.println ("0) Quit \n1) View balance \n2) Add Funds by direct Input \n3) Add Funds by Visa \n4) Add Funds by Debit Card \n5) Add Funds by gift card \n6) Add Funds by gift code");
                choice = Integer.valueOf (scan.nextLine());
                switch (choice)
                {
                    case 0:
                        keepGoing = false;
                        break;
                    case 1:
                        // Direct input for ease of use
                        System.out.println ("Your balance is " + String.valueOf(this.ccp_.getBalance()));
                        break;
                    case 2:
                        System.out.println ("How much would you like to add? (0.00)");
                        amount = Double.valueOf(scan.nextLine());
                        amount = this.ccp_.addFunds (amount);
                        System.out.println("Your balance is " + String.valueOf(amount));
                        break;
                    case 3:
                        System.out.println ("What is your visa card Number ####-####-####-###");
                        cardnum = scan.nextLine ();
                        System.out.println ("What is the security code");
                        secCode = scan.nextLine ();
                        System.out.println ("What is the exp date");
                        expDate = scan.nextLine ();
                        System.out.println ("How much would you like to add? (0.00)");
                        amount = Double.valueOf(scan.nextLine());
                        cmd = this.ccp_.askForVisaCardCommand (cardnum, secCode, expDate, amount);
                        System.out.println (cmd.execute());
                        break;
                    case 4:
                        System.out.println ("What is your debit card Number ####-####-####-###");
                        cardnum = scan.nextLine ();
                        System.out.println ("What is the security code");
                        secCode = scan.nextLine ();
                        System.out.println ("What is the exp date");
                        expDate = scan.nextLine ();
                        System.out.println ("How much would you like to add? (0.00)");
                        amount = Double.valueOf(scan.nextLine());
                        cmd = this.ccp_.askForDebitCardCommand (cardnum, secCode, expDate, amount);
                        System.out.println (cmd.execute());
                        break;
                    case 5:
                        System.out.println ("What is the gift card number");
                        cardnum = scan.nextLine ();
                        cmd = this.ccp_.askForGiftCardCommand (cardnum);
                        System.out.println (cmd.execute());
                        break;
                    case 6:
                        System.out.println ("What is the gift code");
                        cardnum = scan.nextLine ();
                        cmd = this.ccp_.askForGiftCardCommand (cardnum);
                        System.out.println (cmd.execute());
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
        System.out.println ("Exiting balance View");
    }

    /**
     * The menu is for interacting with the store. Here the client can view
     * items, view specific items, add item to your cart, or just buy them.
     * @param scan
     */
    private void storeMenu (Scanner scan)
    {
        int choice = -1;
        int itemNum = 0;
        int amount = 0;
        boolean keepGoing = true;
        while (keepGoing)
        {
            try
            {
                System.out.println ("What would you like to do?");
                System.out.println ("0) Quit \n1) Get store cataloge \n2) View specific item \n3) Add Item to cart \n4) Purchase item from store");
                choice = Integer.valueOf (scan.nextLine());
                switch (choice)
                {
                    case 0:
                        keepGoing = false;
                        break;
                    case 1:
                        System.out.println ("Store Items: ");
                        System.out.println (this.ccp_.listAllStoreItems());
                        break;
                    case 2:
                        System.out.println ("Store Items: ");
                        System.out.println (this.ccp_.listAllStoreItems());
                        System.out.println ("What item are you looking for? Enter the index");
                        int index = Integer.valueOf (scan.nextLine());
                        System.out.println (this.ccp_.viewItemFromStore (index));
                        break;
                    case 3:
                        System.out.println ("Store Items: ");
                        System.out.println (this.ccp_.listAllStoreItems());
                        System.out.println ("What item are you looking for? Enter the index");
                        itemNum = Integer.valueOf (scan.nextLine());
                        System.out.println ("How much would you like to add to cart");
                        amount =  Integer.valueOf (scan.nextLine());
                        System.out.println(this.ccp_.addItemtoCart (itemNum, amount));
                        break;
                    case 4:
                        System.out.println ("Store Items: ");
                        System.out.println (this.ccp_.listAllStoreItems());
                        System.out.println ("What item are you looking for? Enter the index");
                        itemNum = Integer.valueOf (scan.nextLine());
                        System.out.println ("How much would you like to buy");
                        amount =  Integer.valueOf (scan.nextLine());
                        System.out.println(this.ccp_.purchaseItemFromStore (itemNum, amount));
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
        System.out.println ("Exiting Store menu");
    }

    /**
     * A menu for interacting with the cart. The user can view their cart
     * view a specific item from cart, remove items, purchase a single item, or
     * purchase the whole cart.
     * @param scan
     */
    private void cartMenu (Scanner scan)
    {
        int choice = -1;
        int itemnum = 0;
        boolean keepGoing = true;
        while (keepGoing)
        {
            try
            {
                System.out.println ("What would you like to do?");
                System.out.println ("0) Quit \n1) View cart \n2) View item from cart \n3) Remove Item from cart \n4) Purchase specific items from cart \n5) Purchase Whole cart");
                choice = Integer.valueOf (scan.nextLine());
                switch (choice)
                {
                    case 0:
                        keepGoing = false;
                        break;
                    case 1:
                        System.out.println ("Cart Items");
                        System.out.println (this.ccp_.getCartList());
                        break;
                    case 2:
                        System.out.println ("Cart Items");
                        System.out.println (this.ccp_.getCartList());
                        System.out.println ("What item would you like to view (Enter index)");
                        itemnum = Integer.valueOf (scan.nextLine());
                        System.out.println (this.ccp_.viewItemFromCart(itemnum));
                        break;
                    case 3:
                        System.out.println ("Cart Items");
                        System.out.println (this.ccp_.getCartList());
                        System.out.println ("What item would you like to remove (Enter index)");
                        itemnum = Integer.valueOf (scan.nextLine());
                        System.out.println (this.ccp_.removeItemFromCart(itemnum));
                        break;
                    case 4:
                        System.out.println ("Cart Items");
                        System.out.println (this.ccp_.getCartList());
                        System.out.println ("What item would you like to purchase (Enter index)");
                        itemnum = Integer.valueOf (scan.nextLine());

                        if (this.ccp_.getBalance() < this.ccp_.getTotalCostofItemInCart(itemnum))
                        {
                            System.out.println("Insufficent funds");
                            break;
                        }

                        System.out.println (this.ccp_.purchaseItemFromCart(itemnum));
                        break;
                    case 5:

                        if (this.ccp_.getBalance() < this.ccp_.getTotalCostofItemsInCart())
                        {
                            System.out.println("Insufficent funds");
                            break;
                        }

                        System.out.println(this.ccp_.purchaseAllItemsFromCart());

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
        System.out.println ("Exiting cart menu");
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
                        System.out.println ("Username: " + this.ccp_.getUsernameofAccount());
                        break;
                    case 2:
                        System.out.println ("What is your current password");
                        oldPassword = scan.nextLine();
                        System.out.println ("What is your new password");
                        newPassword = scan.nextLine();

                        if (this.ccp_.changePassword(oldPassword, newPassword))
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
