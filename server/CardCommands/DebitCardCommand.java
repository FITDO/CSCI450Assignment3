package server.CardCommands;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.CardCommands.CardCommandIF;
import server.Accounts.CustomerAccount;

/**
 * Class: DebitCardCommand
 * 
 * This method is for adding funds to a customer's account using a debit card
 */
public class DebitCardCommand extends UnicastRemoteObject implements CardCommandIF
{

    private CustomerAccount ca_;
    private String cardNumber_;
    private String securityCode_; // Would be needed to ask bank for funds but is uneeded here
    private String expDate_; // Would be needed to ask bank for funds but is uneeded here
    private double amount_;

    /**
     * A constructor for DebitCardCommand
     * 
     * @param cardNumber
     * @param securityCode
     * @param expDate
     * @param amount
     * @param ca
     * @throws RemoteException
     */
    public DebitCardCommand (String cardNumber, String securityCode, String expDate, double amount , CustomerAccount ca) throws RemoteException 
    {
        super ();
        this.cardNumber_ = cardNumber;
        this.securityCode_ = securityCode;
        this.expDate_ = expDate;
        this.amount_ = amount;
        this.ca_ = ca;
    }

    /**
     * The method will increase the balance of a customer's account.
     */
    @Override
    public String execute () throws Exception, RemoteException 
    {
        // Connect to a bank and request funds
        // Can't really do that thought since
        // you'd be wasting your money lol

        this.ca_.increaseBalance (this.amount_);
        System.out.println ("The Debit cart " + this.cardNumber_ + " was used to deposit " + String.valueOf(this.amount_) + " to " + this.ca_.getUsername());
        return String.valueOf(this.amount_) + " was deposited into your account";
    }
    
}
