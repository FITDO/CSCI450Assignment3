package common.CardCommands;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * CLASS: CardCommandIF
 * 
 * A interface that is used cardCommands 
 */
public interface CardCommandIF extends Remote 
{
    /**
     * Used to execute a command. The function returns a String
     * that details what happened.
     * 
     * @return String
     * @throws Exception
     * @throws RemoteException
     */
    public String execute () throws Exception , RemoteException;
}