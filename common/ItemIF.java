package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * CLASS: ItemIF
 * 
 * An interface that provides commen methods for Item Implementations.
 */
public interface ItemIF extends Remote
{
    
    /**
     * Sets the name of an Item
     * 
     * @param name
     * @throws RemoteException
     */
    public void setName (String name) throws RemoteException;

    /**
     * Gets the name of an item
     * 
     * @return
     * @throws RemoteException
     */
    public String getName () throws RemoteException;

    /**
     * Sets the type of an item
     * 
     * @param type
     * @throws RemoteException
     */
    public void setType (String type) throws RemoteException;

    /**
     * Gets the type of an item
     * 
     * @return
     * @throws RemoteException
     */
    public String getType () throws RemoteException;

    /**
     * Sets the description of an item
     * 
     * @param description
     * @throws RemoteException
     */
    public void setDescription (String description) throws RemoteException;

    /**
     * Gets the description of an item
     * 
     * @return
     * @throws RemoteException
     */
    public String getDescription () throws RemoteException;

    /**
     * Sets the price of an item
     * 
     * @param price
     * @throws Exception
     * @throws RemoteException
     */
    public void setPrice (Double price) throws Exception , RemoteException;

    /**
     * Gets the price of an item
     * 
     * @return
     * @throws RemoteException
     */
    public Double getPrice () throws RemoteException;

    /**
     * Sets the amountInStock of an item
     * 
     * @param stock
     * @throws Exception
     * @throws RemoteException
     */
    public void setAmountInStock (int stock) throws Exception , RemoteException;

    /**
     * Increases te amountInStock of an item
     * Dosen't reset the stock but adds to it
     * 
     * @param amount
     * @throws Exception
     * @throws RemoteException
     */
    public void increaseAmountInStock (int amount) throws Exception , RemoteException;

    /**
     * Decrease the AmountInStock of an Item
     * Dosent reset the stock but lowers it
     * 
     * @param amount
     * @throws Exception
     * @throws RemoteException
     */
    public void decreaseAmountInStock (int amount) throws Exception , RemoteException;

    /**
     * Gets the amount in stock
     * 
     * @return
     * @throws RemoteException
     */
    public int getAmountInStock () throws RemoteException;

    /**
     * Makes a short report of an item such as name price and stock
     * 
     * @return
     * @throws RemoteException
     */
    public String makeShortReport () throws RemoteException;

    /**
     * Makes a long report of an item. All fields are sent to the client
     * 
     * @return
     * @throws RemoteException
     */
    public String makeFullReport () throws RemoteException;
}
