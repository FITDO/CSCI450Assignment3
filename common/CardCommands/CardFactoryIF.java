package common.CardCommands;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * CLASS: CardFactoryIF
 * 
 * A Interface for a Factory that produces CardCommands. The commands are created then returned to the
 * client.
 */
public interface CardFactoryIF extends Remote
{
    
    /**
     * A method that asks the server to produce a GiftcardCommand that the client can
     * then execute
     * 
     * @param CardNumber
     * @param ca
     * @return CardCommandIF
     * @throws Exception
     * @throws RemoteException
     */
    public CardCommandIF createGiftCardCommand (String CardNumber) throws Exception , RemoteException;

    /**
     * A method that asks the server to produce a VisaCardCommand that the client can
     * then execute
     * 
     * @param CardNumber
     * @param ca
     * @return CardCommandIF
     * @throws Exception
     * @throws RemoteException
     */
    public CardCommandIF createVisaCardCommand (String CardNumber , String securityCode , String expDate , double amount) throws Exception , RemoteException;

    /**
     * A method that asks the server to produce a DebitCardCommand that the client can
     * then execute
     * 
     * @param CardNumber
     * @param ca
     * @return CardCommandIF
     * @throws Exception
     * @throws RemoteException
     */
    public CardCommandIF createDebitCardCommand (String CardNumber , String securityCode , String expDate , String cardHolderName , String billingAdress , double amount) throws Exception , RemoteException;

    /**
     * A method that asks the server to produce a GiftCodeCommand that the client can
     * then execute
     * 
     * @param CardNumber
     * @param ca
     * @return CardCommandIF
     * @throws Exception
     * @throws RemoteException
     */
    public CardCommandIF createGiftCodeCommand (String CardNumber) throws Exception , RemoteException;

}
