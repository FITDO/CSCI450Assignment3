package server.Items;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.ItemIF;
import common.Money;

/**
 * CLASS: ItemImpl
 * 
 * A class that contians information about an Item. 
 */
public class ItemImpl extends UnicastRemoteObject implements ItemIF 
{
    // variables
    private String name_;
    private String type_;
    private String description_;
    private Money price_;
    private int amountInStock_;

    // default constructor
    public ItemImpl () throws RemoteException
    {
        this.name_ = "NULL";
        this.type_ = "NULL";
        this.description_ = "NULL";
        this.price_ = new Money();
        this.amountInStock_ = 0;
    }

    // Constructor
    public ItemImpl (String name , String type , String description , double price , int amountInStock) throws Exception , RemoteException
    {
        this.name_ = name;
        this.type_ = type;
        this.description_ = description;
        this.price_ = new Money(price);
        this.amountInStock_ = amountInStock;
    }

    // Copy Constructor
    public ItemImpl (ItemImpl item) throws RemoteException
    {
        this.name_ = item.name_;
        this.type_ = item.type_;
        this.description_ = item.description_;
        try
        {
            this.price_ = new Money(item.getPrice());
        }
        catch (Exception e)
        {
            // Exception will never trigger but java requires a try catch
        }
        this.amountInStock_ = item.getAmountInStock ();
    }

    // set and getters

    public void setName (String name) throws RemoteException
    {
        this.name_ = name;
    }

    public String getName () throws RemoteException
    {
        return this.name_;
    }

    public void setType (String type) throws RemoteException
    {
        this.type_ = type;
    }

    public String getType () throws RemoteException
    {
        return this.type_;
    }

    public void setDescription (String description) throws RemoteException
    {
        this.description_ = description;
    }

    public String getDescription () throws RemoteException
    {
        return this.description_;
    }

    public Double getPrice () throws RemoteException
    {
        return this.price_.getAmount ();
    }

    public void setPrice (Double price) throws Exception , RemoteException
    {
        this.price_.setAmount (price);
    }

    public void setAmountInStock (int stock) throws Exception , RemoteException
    {
        // check to make sure stock isn't set below zero
        if (stock < 0)
        {
            throw new Exception("Amount can't be less than 0");
        }

        this.amountInStock_ = stock;
    }

    public void increaseAmountInStock (int amount) throws Exception , RemoteException
    {
        // check to make a negative amount isn't added
        if (amount < 0)
        {
            throw new Exception("Amount can't be less than 0");
        }

        this.amountInStock_ += amount;
    }

    public void decreaseAmountInStock (int amount) throws Exception , RemoteException
    {
        // check to make a negative amount isn't subtracted
        if (amount < 0)
        {
            throw new Exception("Amount can't be less than 0");
        }

        this.amountInStock_ -= amount;

        // check to make sure stock isn't accidently set below zero
        if (this.amountInStock_ < 0)
        {
            this.amountInStock_ = 0;
        }
    }

    public int getAmountInStock () throws RemoteException
    {
        return this.amountInStock_;
    }

    // Makes a short report about the item for a user to view
    public String makeShortReport () throws RemoteException
    {
        String msg = "";
        msg = this.name_ + " Price: " + String.valueOf(this.getPrice()) + " Stock: " + String.valueOf(this.amountInStock_);
        return msg;
    }

    // makes a full report of an item for the user to view
    public String makeFullReport () throws RemoteException
    {
        String msg = "";

        msg = this.name_ + "\nType: " + this.type_ + "\nPrice: " + String.valueOf(this.getPrice()) + "\nDescription:\n  " + this.description_ + "\nStock: " + String.valueOf(this.amountInStock_);

        return msg;
    }
}