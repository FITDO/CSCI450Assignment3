package common;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;

/**
 * CLASS: Money
 * 
 * Provides a way to keep track and use the American Dollar as an amount.
 */
public class Money extends UnicastRemoteObject
{
    // variables
    private double amount_;
    private DecimalFormat df_;

    public Money () throws RemoteException
    {
        super ();
        this.df_ = new DecimalFormat ("0.00");
        this.amount_ = 0.00;
    }

    // constructor that takes defualt amount
    public Money (Double amount) throws RemoteException , Exception
    {
        super ();
        this.df_ = new DecimalFormat ("0.00");
        this.setAmount (amount);
    }

    public Money (Money money) throws RemoteException
    {
        super ();
        this.df_ = new DecimalFormat ("0.00");
        this.amount_ = money.amount_;
    }

    // returns amount
    public double getAmount () throws RemoteException
    {
        return this.amount_;
    }

    /**
     * Takes a double as amount. Shortens double to 2 decimal places
     * and checks double is less than 0. Once confirmed sets amount 
     * entered as amount_.
     * 
     * @param amount
     * @throws Exception
     */
    public void setAmount (Double amount) throws RemoteException , Exception
    {
        if (amount >= 0.0)
        {
            this.amount_ =  Double.valueOf (df_.format (amount));
        }
        else // amount < 0
        {
            throw new Exception ("Negative amount attempted");
        }
    }

    /**
     * Takes a double as amount. Shortens double to 2 decimal places
     * and checks double is less than 0. Once confirmed adds amount
     * to current amount_.
     * @param amount
     * @throws Exception
     */
    public void addAmount (Double amount) throws RemoteException , Exception
    {
        if (amount >= 0.0)
        {
            this.amount_ += Double.valueOf (df_.format (amount));
        }
        else // amount < 0
        {
            throw new Exception ("Negative amount attempted");
        }
    }

    /**
     * Takes a double as amount. Shortens double to 2 decimal places
     * and checks double is less than 0. Once confirmed subtracts 
     * amount to current amount_.
     * @param amount
     * @throws Exception
     */
    public void subtractAmount (Double amount) throws RemoteException , Exception
    {
        if (amount > 0.0)
        {
            // Makes sure new amount wont ever be negative
            if (this.amount_ >= amount)
            {
                this.amount_ -= Double.valueOf (df_.format (amount));
            }
            else // amount_ < amount
            {
                this.amount_ = 0.00;
            }
        }
        else // amount <= 0
        {
            throw new Exception ("Negative amount attempted");
        }
    }
}
