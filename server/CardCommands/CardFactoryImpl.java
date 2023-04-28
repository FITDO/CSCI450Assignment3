package server.CardCommands;

import java.rmi.RemoteException;

import common.CardCommands.CardCommandIF;
import common.CardCommands.CardFactoryIF;
import server.Accounts.CustomerAccount;

/**
 * CLASS: CardFactoryImpl
 * 
 * This is an Implmentation of the card factory. It creates new CardCommands to the client when
 * requested.
 */
public class CardFactoryImpl implements CardFactoryIF
{

    private CustomerAccount ca_;
    /**
     * A constructor to build a new cardFactory.
     * @throws RemoteException
     */
    public CardFactoryImpl (CustomerAccount ca) throws RemoteException
    {
        super ();
        this.ca_ = ca;
    }

    /**
     * A method to create and return a GiftCardCommand
     */
    @Override
    public CardCommandIF createGiftCardCommand (String CardNumber) throws Exception, RemoteException 
    {
        System.out.println ("GiftCardCommand requested");
        return new GiftCardCommand (CardNumber , this.ca_);
    }

    /**
     * A method to create and return a VisaCardCommand
     */
    @Override
    public CardCommandIF createVisaCardCommand (String CardNumber, String securityCode, String expDate, double amount) throws Exception, RemoteException 
    {
        System.out.println ("VisaCardCommand requested");
        return new VisaCardCommand (CardNumber, securityCode, expDate, amount, this.ca_);
    }

    /**
     * A method to create and return a DebitCardCommand
     */
    @Override
    public CardCommandIF createDebitCardCommand (String CardNumber, String securityCode, String expDate, String cardHolderName, String billingAdress, double amount) throws Exception, RemoteException
    {
        System.out.println ("DebitCardCommand requested");
        return new DebitCardCommand (CardNumber, securityCode, expDate, amount , this.ca_);
    }

    /**
     * A method to create and return a GiftCodeCommand
     */
    @Override
    public CardCommandIF createGiftCodeCommand (String CardNumber) throws Exception, RemoteException 
    {
        System.out.println ("GiftCodeCommand requested");
        return new GiftCodeCommand (ca_, CardNumber);
    }
    
}
