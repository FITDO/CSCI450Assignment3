package common.ControlPanelIFs;

import java.rmi.RemoteException;

import common.CardCommands.CardCommandIF;

public interface CustomerControlPanelIF extends ControlPanelIF
{
    
    public double getBalance () throws RemoteException;

    public double addFunds (double amount) throws Exception  , RemoteException;

    public double decreaseFunds (double amount) throws Exception  , RemoteException;

    public String getCartList () throws RemoteException;

    public String viewItemFromCart (int index) throws RemoteException;

    public String viewItemFromStore (int index) throws RemoteException;

    public String removeItemFromCart (int index) throws RemoteException;

    public String addItemtoCart (int index , int amount) throws RemoteException;

    public String purchaseItemFromStore (int index , int amount) throws RemoteException;

    public String purchaseItemFromCart (int index) throws RemoteException;

    public String purchaseAllItemsFromCart () throws RemoteException;

    public double getTotalCostofItemsInCart () throws RemoteException;

    public double getTotalCostofItemInCart (int index) throws RemoteException;

    public CardCommandIF askForDebitCardCommand (String cardNumber , String securityCode , String expDate , double amount) throws Exception , RemoteException;

    public CardCommandIF askForGiftCardCommand (String cardCode) throws Exception , RemoteException;

    public CardCommandIF askForGiftCodeCommand (String code) throws Exception , RemoteException;

    public CardCommandIF askForVisaCardCommand (String cardNumber , String securityCode , String expDate , double amount) throws Exception , RemoteException;
    
}
