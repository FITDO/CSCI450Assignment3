package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedList;

import server.Items.ItemImpl;

/**
 * Class: OnlineStore
 * 
 * A store with a linkedlist of items. The methods support adding, removing, and
 * getting information about items in the store.
 */
public class OnlineStore extends UnicastRemoteObject
{
    // items in the store
    private LinkedList <ItemImpl> items_;

    /**
     * default constructor
     * @throws RemoteException
     */
    public OnlineStore () throws RemoteException
    {
        this.items_ = new LinkedList <ItemImpl> ();
    }

    /**
     * A method for returning all information about items in the store. Indexes are generated
     * along side the items for easy retrievel.
     * 
     * @return
     * @throws RemoteException
     */
    public String getCataloge () throws RemoteException
    {
        String msg = "";

        Iterator <ItemImpl> iter = this.items_.iterator ();
        int count = 0;

        while (iter.hasNext())
        {
            msg += String.valueOf(count) + ") " + iter.next().makeShortReport() + "\n";
            count++;
        }

        return msg;
    }

    /**
     * This method adds an item to the store with some minor error checking.
     * Returns a boolean based on if the method was succsessful.
     * @param item
     * @return
     * @throws RemoteException
     */
    public boolean addItem (ItemImpl item) throws RemoteException
    {
        if (!this.items_.contains (item))
        {
            System.out.println ("Added item to store");
            this.items_.add (item);
            return true;
        }
        else // the item is already in the store
        {
            System.out.println ("Failed to add item to store");
            return false;
        }
    }

    /**
     * Gets the amount of items in the store
     * @return
     * @throws RemoteException
     */
    public int getAmountofItems () throws RemoteException
    {
        return this.items_.size ();
    }
    
    /**
     * Returns an item to the client.
     * Returns null if the item wasn't found
     * 
     * @param index
     * @return
     * @throws RemoteException
     */
    public ItemImpl getItem (int index) throws RemoteException
    {
        if (index < this.items_.size())
        {
            return this.items_.get (index);
        }
        else // item wasn't found
        {
            return null;
        }
    }

    /**
     * Returns information about an item. Uses an index to find
     * the item
     * 
     * @param index
     * @return
     * @throws RemoteException
     */
    public String getItemInfo (int index) throws RemoteException
    {
        if (index < this.items_.size())
        {
            return this.items_.get(index).makeFullReport ();
        }
        else // couldn't find an item
        {
            return "Item index out of range";
        }
    }

    /**
     * Tries to remove an item from the store using an index.
     * Returns a boolean if the operation worked
     * 
     * @param index
     * @return
     * @throws RemoteException
     */
    public boolean removeItem (int index) throws RemoteException
    {
        if (index < this.items_.size())
        {
            this.items_.remove (index);
            System.out.println ("An item was removed");
            return true;
        }
        else
        {
            return false;
        }
    }
}