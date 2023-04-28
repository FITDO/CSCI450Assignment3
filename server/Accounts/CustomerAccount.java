package server.Accounts;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;

import common.Money;
import server.OnlineStore;
import server.Accounts.ControlPanelImpls.CustomerControlPanelImpl;
import server.Items.CartItem;
import server.Items.ItemImpl;

/**
 * Class: CustomerAccount
 * 
 * Defines commands that will be used by the customer account manager
 * has a cart and balance to help with shopping
 */
public class CustomerAccount extends ABSAccount
{

    // balance of the user to store funds
    private Money balance_;
    // a list of cartItems
    public LinkedList <CartItem> cart_;
    // a controlpanel for the client to use
    private CustomerControlPanelImpl ccp_;

    /**
     * A constructor to build a new customer account
     * @param username
     * @param password
     * @throws RemoteException
     */
    public CustomerAccount (String username, String password) throws RemoteException 
    {
        super (username, password);
        this.balance_ = new Money ();
        this.cart_ = new LinkedList <CartItem> ();
        this.ccp_ = null;
    }

    /**
     * A method that returns the balacne of a user
     * @return
     * @throws RemoteException
     */
    public double getBalance () throws RemoteException
    {
        return this.balance_.getAmount ();
    }

    /**
     * A method to increase the user's balance. The entered double is added to the
     * current balance.
     * 
     * @param amount
     * @throws Exception
     * @throws RemoteException
     */
    public void increaseBalance (double amount) throws Exception , RemoteException
    {
        this.balance_.addAmount (amount);
    }

    /**
     * A method to increase the user's balance. The entered double is decreased to the
     * current balance.
     * 
     * @param amount
     * @throws Exception
     * @throws RemoteException
     */
    public void decreaseBalance (double amount) throws Exception , RemoteException
    {
        this.balance_.subtractAmount(amount);
    }

    /**
     * A method that returns a list of all the cart items as a string
     * 
     * @return
     * @throws RemoteException
     */
    public String getCart () throws RemoteException
    {
        String msg = "";
        int count = 0;
        CartItem ptr;
        Iterator<CartItem> iter = this.cart_.iterator ();
        while (iter.hasNext())
        {
            ptr = iter.next ();
            msg += String.valueOf(count) + ") " + ptr.makeShortReport() + "\n";
            count++;
        }
        return msg;
    }

    /**
     * This method returns the description of an item as a string
     * 
     * @param index
     * @return
     * @throws RemoteException
     */
    public String getItemDescriptionFromCart (int index) throws RemoteException
    {
        if (index < this.cart_.size()) // found item
        {
            return this.cart_.get(index).makeFullReport();
        }
        else // couldn't find item
        {
            return "Invaild Index";
        }
    }

    /**
     * A method to remove a item from the cart. An index is used to find the item
     * 
     * @param index
     * @return
     * @throws Exception
     * @throws RemoteException
     */
    public String removeItemFromCart (int index) throws Exception , RemoteException
    {

        if (index < this.cart_.size()) // found item
        {   
            CartItem ptr = this.cart_.remove (index);
            return ptr.getName() + " was removed from cart";
        }
        else // couldn't find the item
        {
            return "Invaild Index";
        }
       
    }
    
    /**
     * A method to add an item to the client's cart. It takes in an item and adds it to the users account
     * The item in the cart is a CartItem.
     * 
     * @param item
     * @param amountOrdered
     * @throws RemoteException
     */
    public void addItemtoCart (ItemImpl item , int amountOrdered) throws Exception , RemoteException
    {
        this.cart_.add (new CartItem(item, amountOrdered));
    }

    /**
     * A function that iterates through cart and gets the total of all items in the cart
     * 
     * @return
     * @throws RemoteException
     */
    public double getCartTotal () throws RemoteException
    {
        double total = 0.0;
        Iterator <CartItem> iter = this.cart_.iterator();
        while (iter.hasNext())
        {
            total += iter.next().getTotal();
        }
        return total;
    }

    /**
     * Returns false since this isnt an admin
     */
    @Override
    public boolean isAdmin () 
    {
        return false;
    }

    /**
     * Returns the customer's controlPanel. Generates a new one if one hasn't been
     * generated.
     * 
     * @param store
     * @return
     * @throws RemoteException
     */
    public CustomerControlPanelImpl getCustomerControlPanel (OnlineStore store) throws RemoteException
    {    
        if (this.ccp_ == null) // ccp is null
        {
            this.ccp_ = new CustomerControlPanelImpl (this , store);
        }

        return this.ccp_;
    }
}