package server.Items;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.ItemIF;

/**
 * Class: CartItem
 * 
 * A implmentation of a Item that is stored in a cart. This acts as a proxy 
 * to the items in the cart and also stores an int as amount in the cart.
 * Most functions are empty since this is a proxy.
 */
public class CartItem extends UnicastRemoteObject implements ItemIF
{
    // Item this object acts as
    private ItemImpl item_;
    private int amountInCart_;


    public CartItem (ItemImpl item , int amountOrdered) throws Exception , RemoteException 
    {
        this.item_ = item;

        if (amountOrdered <= 0)
        {
            throw new Exception ("Negative amount entered");
        }

        this.amountInCart_ = amountOrdered;
    }

    /**
     * Does some minor error checking to set the amount ordered
     * 
     * @param amount
     * @throws Exception
     * @throws RemoteException
     */
    public void setAmountOrdered (int amount) throws Exception , RemoteException
    {
        if (amount > 0 && amount <= this.item_.getAmountInStock())
        {
            this.amountInCart_ = amount;
        }
        else if (amount >= this.item_.getAmountInStock())
        {
            this.amountInCart_ = this.item_.getAmountInStock ();
        }
        else // entered amount is less thatn 0
        {
            throw new Exception ("Can't order 0 or less items");
        }
    }

    /**
     * Returns amount ordered
     * @return
     * @throws RemoteException
     */
    public int getAmountOrdered () throws RemoteException 
    {
        return this.amountInCart_;
    }

    /**
     * 
     * @return
     * @throws RemoteException
     */
    public boolean purchase () throws RemoteException
    {
        if (this.amountInCart_ <= this.item_.getAmountInStock())
        {
            try
            {
                this.item_.decreaseAmountInStock (amountInCart_);
                return true;
            }
            catch (Exception e)
            {
                System.out.println("Server err: " + e.getMessage());
                return false;
            }
        }
        else
        {
            String msg =  "Order for " + String.valueOf(this.amountInCart_) + " " + this.item_.getName() + " failed due to low stock";
            System.out.println (msg);
            return false;
        }
    }

    /**
     * Generates a total price of the item
     * 
     * @return
     * @throws RemoteException
     */
    public double getTotal () throws RemoteException
    {
        return this.amountInCart_ * this.item_.getPrice();
    }

    @Override
    public void setName (String name) throws RemoteException 
    {
        // empty   
    }

    @Override
    public String getName () throws RemoteException 
    {
        return this.item_.getName ();
    }

    @Override
    public void setType (String type) throws RemoteException 
    {
        // empty
    }

    @Override
    public String getType () throws RemoteException
    {
        return this.item_.getType ();
    }

    @Override
    public void setDescription (String description) throws RemoteException
    {
        // empty
    }

    @Override
    public String getDescription () throws RemoteException 
    {
        return this.item_.getDescription ();
    }

    @Override
    public Double getPrice () throws RemoteException 
    {
        return this.item_.getPrice ();
    }

    @Override
    public void setPrice (Double price) throws Exception, RemoteException
    {
        // empty
    }

    @Override
    public void setAmountInStock (int stock) throws Exception, RemoteException 
    {
        // empty
    }

    @Override
    public void increaseAmountInStock (int amount) throws Exception, RemoteException 
    {
        // empty
    }

    @Override
    public void decreaseAmountInStock (int amount) throws Exception, RemoteException 
    {
        // empty
    }

    @Override
    public int getAmountInStock () throws RemoteException 
    {
        return this.item_.getAmountInStock ();
    }

    /**
     * Makes a short report of the item. Calls the ItemImpl's make shortReport function
     * but tacks on the amount in cart
     */
    @Override
    public String makeShortReport () throws RemoteException 
    {
        return this.item_.makeShortReport () + "\nAmount Ordered: " + String.valueOf(this.amountInCart_);

    }

    /**
     * Makes a short report of the item. Calls the ItemImpl's make makeFullReport function
     * but tacks on the amount in cart
     */
    @Override
    public String makeFullReport () throws RemoteException 
    {
        return this.item_.makeFullReport () + "\nAmount Ordered: " + String.valueOf(this.amountInCart_);
    }
}
