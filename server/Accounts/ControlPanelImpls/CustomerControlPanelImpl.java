package server.Accounts.ControlPanelImpls;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

import common.CardCommands.CardCommandIF;
import common.ControlPanelIFs.CustomerControlPanelIF;
import server.OnlineStore;
import server.Accounts.CustomerAccount;
import server.CardCommands.CardFactoryImpl;
import server.Items.CartItem;
import server.Items.ItemImpl;

/**
 * Class: CustomerControlPanelImpl
 * 
 * A Implimented controlpanel for the customer to interact with the store an
 * their own account. 
 */
public class CustomerControlPanelImpl extends UnicastRemoteObject implements CustomerControlPanelIF
{
    //variables
    // The customers account 
    private CustomerAccount owner_;
    // The CardFactoru that a customer can use to deposit cash into their
    // account
    private CardFactoryImpl cardFac_;
    // The store 
    private OnlineStore store_;

    /**
     * Constructor that sets up a CustomerControlPanelImpl
     * 
     * @param owner
     * @param store
     * @throws RemoteException
     */
    public CustomerControlPanelImpl (CustomerAccount owner , OnlineStore store) throws RemoteException 
    {
        super ();
        this.owner_ = owner;
        this.store_ = store;
        this.cardFac_ = new CardFactoryImpl (owner);
    }

    /**
     * Returns a booelan that informs it is a admin. Always returns false
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean isAdmin () throws RemoteException 
    {
        return false;
    }


    /**
     * Returns a list of all items in the store. Calls the store to do this
     * 
     * @return
     * @throws RemoteException
     */
    @Override
    public String getUsernameofAccount () throws RemoteException 
    {
        return this.owner_.getUsername ();
    }

    /**
     * Allows the user to change their current password to a new one.
     * They will need their current password before they can change it
     * however. Calls the owner account to verify.
     * 
     * @param oldpass
     * @param newpass
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean changePassword (String oldpass, String newpass) throws RemoteException 
    {
        return this.owner_.resetPassword (oldpass, newpass);
    }

    /**
     * Returns infomration about a selected item.
     * Uses an index to find the item. Calls the store to do this
     * 
     * @param index
     * @return
     * @throws RemoteException
     */
    @Override
    public String getItemInfo (int index) throws RemoteException 
    {
        return this.store_.getItemInfo (index);
    }


    /**
     * Returns a list of all items in the store. Calls the store to do this
     * 
     * @return
     * @throws RemoteException
     */
    @Override
    public String listAllStoreItems () throws RemoteException 
    {
        return this.store_.getCataloge ();
    }

    /**
     * Returns the balance of the customer account as a double
     */
    @Override
    public double getBalance () throws RemoteException 
    {
        return this.owner_.getBalance();
    }

    /**
     * A method to add funds to the client account. Accepts a double and enters it into
     * the account
     */
    @Override
    public double addFunds (double amount) throws Exception  , RemoteException
    {
        System.out.println (this.owner_.getUsername() + " added $" + String.valueOf(amount) + " to their balance");
        this.owner_.increaseBalance (amount);
        return this.owner_.getBalance ();
    }

    /**
     * A method used to decrease the funds of a customer account. Accepts a double and
     * lowers the funds of the account.
     */
    @Override
    public double decreaseFunds (double amount) throws Exception  , RemoteException
    {
        this.owner_.decreaseBalance (amount);
        return this.owner_.getBalance ();
    }

    /**
     * A method to return the items in a cart as a string
     */
    @Override
    public String getCartList () throws RemoteException 
    {
        return this.owner_.getCart ();
    }

    /**
     * A method to view the description of an item in the cart
     */
    @Override
    public String viewItemFromCart (int index) throws RemoteException 
    {
        return this.owner_.getItemDescriptionFromCart(index);
    }

    /**
     * A method to view the description of an item in the store
     */
    @Override
    public String viewItemFromStore (int index) throws RemoteException 
    {
        return this.store_.getItemInfo (index);
    }

    /**
     * A method to add an item to the cart. It takes an index that is used to
     * find the item and an amount to add to the cart.
     * A String is returned to inform the user of what happened.
     */
    @Override
    public String addItemtoCart (int index , int amount) throws RemoteException
    {
        // The system tries to find the item
        ItemImpl item = this.store_.getItem(index);

        // an if else tries to determine the outcome
        if (item == null) // item wasnt found
        {
            return "Couldn't find item";
        } 
        else if (item.getAmountInStock() < amount) // not enough in stock
        {
            return "Item could not be added to cart due to insufficent stock";
        }
        else // item was added to cart
        {
            try
            {
                this.owner_.addItemtoCart (item, amount);
                return "Item was added to cart";
            }
            catch (Exception e)
            {
                return "Error adding item";
            }
            
        }
    }

    /**
     * A method to purchase an item from the store. The int is used to find
     * the item and the amount will be use to determine to determine how much the
     * client buys.
     */
    @Override
    public String purchaseItemFromStore (int index , int amount) throws RemoteException
    {
        // finds the item
        ItemImpl item = this.store_.getItem(index);
        if (item == null) // counldn't find the item
        {
            return "Couldn't find item";
        } 
        
        if (this.owner_.getBalance() < item.getPrice() * amount) // insufficent balance to buy items
        {
            return "Insufficent balance";
        }
        
        
        if (item.getAmountInStock() < amount) // no enough items in stock
        {
            return "Insufficent amount in stock";
        }
        else // can buy the item
        {
            // try and catch to suppress error
            try
            {
                item.decreaseAmountInStock (amount);
                String msg = String.valueOf(amount) + " " + item.getName() + " was bought";
                System.out.println (msg);
                return msg;
            }
            catch (Exception e)
            {
                String msg =  "Order for " + String.valueOf(amount) + " " + item.getName() + " failed";
                System.out.println (msg);
                return msg;
            } 
        }
    }

    /**
     * A method to buy an item from the cart. Uses an index to find the item.
     * The String returned tells the user what happened.
     */
    @Override
    public String purchaseItemFromCart (int index) throws RemoteException
    {
        // finds the item
        CartItem item = this.owner_.cart_.get(index);

        if (item == null) // couldn't find the item
        {
            return "Index not found";
        }

        if (this.owner_.getBalance() < item.getTotal()) // insufficentBalance
        {
            return "Insufficent balance";
        }

        // tries to purchase the item
        boolean worked = item.purchase ();

        

        if (worked) // the item was purchased
        {
            try
            {
                // lowres the balance of the account and removes the item from the cart
                this.owner_.decreaseBalance (item.getTotal());
                this.owner_.cart_.remove (index);
                System.out.println ("User " + this.getUsernameofAccount() + " bought " + item.getAmountOrdered() + " " + item.getName());
                return String.valueOf(item.getAmountOrdered()) + " " + item.getName() + " was bought";
            }
            catch (Exception e)
            {
                // will never trigger but needed a try catch
                return "";
            }   
            

            
        }
        else // the purchase didn't work
        {
            return "Failed to buy " + String.valueOf(item.getAmountOrdered()) + " " + item.getName();
        }
    }

    /**
     * A method to purchase all the items from a customer's cart. The method will
     * go through all the items and determing what happened
     */
    @Override
    public String purchaseAllItemsFromCart () throws RemoteException
    {

        if (this.getBalance() < this.owner_.getCartTotal()) // The user didn't have enough funds
        {
            return "Insufficent funds";
        }

        System.out.println("Trying to purchase all from " + this.owner_.getUsername() + "'s cart");
        
        String msg = "";
        // a LinkedList of boolean that keeps track of what happened to each item
        LinkedList <Boolean> checklist = new LinkedList<Boolean>();
        Boolean check = false;

        // loops through each item then tries to purchase them and adds a string to the msg that
        // will be returned to the user
        for (int i = 0 ; i < this.owner_.cart_.size() ; i++)
        {
            // item is purchased
            check = this.owner_.cart_.get(i).purchase();
            if (check) // The item was purchased
            {
                msg += String.valueOf(this.owner_.cart_.get(i).getAmountOrdered()) + " " + this.owner_.cart_.get(i).getName() + " were bought \n";
            }
            else // The item wasn't purchased
            {
                msg += String.valueOf("Unable to buy " + this.owner_.cart_.get(i).getAmountOrdered() + " " + this.owner_.cart_.get(i).getName()) + "\n";
            }

            checklist.add (check);
        }

        // Remove all items that were purchased
        int runtime = this.owner_.cart_.size() - 1;
        for (int i = runtime ; i > -1 ; i--)
        {
            if (checklist.get(i))
            {
                this.owner_.cart_.remove(i);
            }
        }

        return msg;
    }


    /**
     * A method that gets the total cost of all the items in a cart and returns it as a double
     */
    @Override
    public double getTotalCostofItemsInCart () throws RemoteException
    {
        return this.owner_.getCartTotal ();
    }

    /**
     * A method that takes an index an tries to remove an item from the cart.
     * Sends a string to inform the user what happened.
     */
    @Override
    public String removeItemFromCart (int index) throws RemoteException 
    {
        if (index < this.owner_.cart_.size()) // found item and removes it
        {
            this.owner_.cart_.remove (index);
            return "Item was removed";
        }
        else // couldn't find item
        {
            return "Invaild Index";
        }
    }

    /**
     * A method that gets the total of a single item in the customers cart
     */
    @Override
    public double getTotalCostofItemInCart (int index) throws RemoteException 
    {
        return this.owner_.cart_.get(index).getTotal();
    }

    // Methods for the client to interact with their CardCommandFactory

    /**
     * Generates a DebitCartCommand that the client can then use
     */
    @Override
    public CardCommandIF askForDebitCardCommand (String cardNumber, String securityCode, String expDate, double amount) throws Exception , RemoteException 
    {
        return this.cardFac_.createDebitCardCommand (cardNumber , securityCode , expDate , securityCode , expDate , amount);
    }

    /**
     * Generates a GiftCardCommand that the client can then use
     */
    @Override
    public CardCommandIF askForGiftCardCommand (String cardCode) throws Exception , RemoteException 
    {
        return this.cardFac_.createGiftCardCommand (cardCode);
    }

    /**
     * Generates a GiftCodeCommand that the client can then use
     */
    @Override
    public CardCommandIF askForGiftCodeCommand (String code) throws Exception , RemoteException
    {
        return this.cardFac_.createGiftCodeCommand (code);
    }

    /**
     * Generates a VisaCardCommand that the client can then use
     */
    @Override
    public CardCommandIF askForVisaCardCommand (String cardNumber, String securityCode, String expDate, double amount) throws Exception , RemoteException
    {
        return this.cardFac_.createVisaCardCommand (cardNumber , securityCode , expDate , amount);
    }
}