package server.CardCommands;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.CardCommands.CardCommandIF;
import server.Accounts.CustomerAccount;

/**
 * Class: GiftCardCommand
 * 
 * This method is for adding funds to a customer's account using a gift card
 */
public class GiftCardCommand extends UnicastRemoteObject implements CardCommandIF
{
    private CustomerAccount ca_; // customer account to add funds for
    private String cardNumber_; // a cardnumber to gain access to the funds
    private double amount_; // the amount to deposit into the account

    /**
     * A constructor for GiftCardCommand
     * 
     * @param cardNumber
     * @param ca
     * @throws RemoteException
     */
    public GiftCardCommand (String cardNumber , CustomerAccount ca) throws RemoteException
    {
        super ();
        this.ca_ = ca;
        this.cardNumber_ = cardNumber;
        this.amount_ = 25;
    }

    /**
     * When executed it connects to a gift card and transfers funds to a customer account
     */
    @Override
    public String execute () throws Exception , RemoteException
    {

        // would check for a gift card

        this.ca_.increaseBalance (this.amount_);
        System.out.println ("The giftcard with card number " + this.cardNumber_ + " was used to add " + String.valueOf(this.amount_) + " to " + this.ca_.getUsername());
        return String.valueOf(this.amount_) + " was added to your account";
    }

    
}
