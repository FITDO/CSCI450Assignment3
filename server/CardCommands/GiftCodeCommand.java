package server.CardCommands;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.CardCommands.CardCommandIF;
import server.Accounts.CustomerAccount;

/**
 * Class: DebitCardCommand
 * 
 * This method is for adding funds to a customer's account using a gift code. The gift codes
 * would be stored in the system and accessed during executions.
 */
public class GiftCodeCommand extends UnicastRemoteObject implements CardCommandIF
{
    // account to deposit funds in
    private CustomerAccount ca_;
    // the giftcards number
    private String code_;
    // the amount that will be deposited into the account
    private double amount_;

    public GiftCodeCommand (CustomerAccount ca , String cardNumber) throws RemoteException
    {
        super ();
        this.ca_ = ca;
        this.code_ = cardNumber;
        this.amount_ = 0;
    }

    /**
     * This method will connect to a database of gift codes and if found deposit the
     * funds into the account.
     */
    @Override
    public String execute () throws Exception , RemoteException
    {

        // connect to some database of giftcodes to add funds to user's account

        this.amount_ = 7;
        this.ca_.increaseBalance (this.amount_);
        System.out.println ("The gift code number " + this.code_ + " was used to add " + String.valueOf(this.amount_) + " to " + this.ca_.getUsername());
        return String.valueOf(this.amount_) + " was added to your account";
    }

    
}
