./client/Views/AdminView.class: ./client/Views/AdminView.java
	javac ./client/Views/AdminView.java

./client/Views/CustomerView.class: ./client/Views/CustomerView.java
	javac ./client/Views/CustomerView.java

./client/ClientSetUp.class: ./client/ClientSetUp.java
	javac ./client/ClientSetUp.java

./client/UserRetriever.class: ./client/UserRetriever.java
	javac ./client/UserRetriever.java

./common/CardCommands/CardCommandIF.class: ./common/CardCommands/CardCommandIF.java
	javac ./common/CardCommands/CardCommandIF.java

./common/CardCommands/CardFactoryIF.class: ./common/CardCommands/CardFactoryIF.java
	javac ./common/CardCommands/CardFactoryIF.java

./common/ControlPanelIFs/AdminControlPanelIF.class: ./common/ControlPanelIFs/AdminControlPanelIF.java
	javac ./common/ControlPanelIFs/AdminControlPanelIF.java

./common/ControlPanelIFs/ControlPanelIF.class: ./common/ControlPanelIFs/ControlPanelIF.java
	javac ./common/ControlPanelIFs/ControlPanelIF.java

./common/ControlPanelIFs/CustomerControlPanelIF.class: ./common/ControlPanelIFs/CustomerControlPanelIF.java
	javac ./common/ControlPanelIFs/CustomerControlPanelIF.java

./common/ItemIF.class: ./common/ItemIF.java
	javac ./common/ItemIF.java

./common/Money.class: ./common/Money.java
	javac ./common/Money.java
	
./common/UserDispatcherIF.class: ./common/UserDispatcherIF.java
	javac ./common/UserDispatcherIF.java

./server/Accounts/ControlPanelImpls/AdminControlPanelImpl.class: ./server/Accounts/ControlPanelImpls/AdminControlPanelImpl.java
	javac ./server/Accounts/ControlPanelImpls/AdminControlPanelImpl.java

./server/Accounts/ControlPanelImpls/CustomerControlPanelImpl.class: ./server/Accounts/ControlPanelImpls/AdminControlPanelImpl.java
	javac ./server/Accounts/ControlPanelImpls/AdminControlPanelImpl.java

./server/Accounts/ControlPanelImpls/ABSAccount.class: ./server/Accounts/ControlPanelImpls/ABSAccount.java
	javac ./server/Accounts/ControlPanelImpls/ABSAccount.java

./server/Accounts/AdminAccount.class: ./server/Accounts/AdminAccount.java
	javac ./server/Accounts/AdminAccount.java

./server/Accounts/CustomerAccount.class: ./server/Accounts/CustomerAccount.java
	javac ./server/Accounts/CustomerAccount.java

./server/CardCommands/CardFactoryImpl.class: ./server/CardCommands/CardFactoryImpl.java
	javac ./server/CardCommands/CardFactoryImpl.java

./server/CardCommands/DebitCardCommand.class: ./server/CardCommands/DebitCardCommand.java
	javac ./server/CardCommands/DebitCardCommand.java

./server/CardCommands/GiftCardCommand.class: ./server/CardCommands/GiftCardCommand.java
	javac ./server/CardCommands/GiftCardCommand.java

./server/CardCommands/GiftCodeCommand.class: ./server/CardCommands/GiftCodeCommand.java
	javac ./server/CardCommands/GiftCodeCommand.java

./server/CardCommands/VisaCardCommand.class: ./server/CardCommands/VisaCardCommand.java
	javac ./server/CardCommands/VisaCardCommand.java

./server/Items/CartItem.class: ./server/Items/CartItem.java
	javac ./server/Items/CartItem.java

./server/Items/ItemImpl.class: ./server/Items/ItemImpl.java
	javac ./server/Items/ItemImpl.java

./server/OnlineStore.class: ./server/OnlineStore.java
	javac ./server/OnlineStore.java

./server/ServerSetUp.class: ./server/ServerSetUp.java
	javac ./server/ServerSetUp.java

./server/UserDispatcherImpl.class: ./server/UserDispatcherImpl.java
	javac ./server/UserDispatcherImpl.java

ClientandServerSetUp.class: ClientandServerSetUp.java
	javac ClientandServerSetUp.java

client: ./client/Views/AdminView.class ./client/Views/CustomerView.class ./client/ClientSetUp.class ./client/UserRetriever.class common

clientRun: client common
	java client.ClientSetUp

common: ./common/CardCommands/CardCommandIF.class ./common/CardCommands/CardFactoryIF.class ./common/ControlPanelIFs/AdminControlPanelIF.class ./common/ControlPanelIFs/ControlPanelIF.class ./common/ControlPanelIFs/CustomerControlPanelIF.class ./common/ItemIF.class ./common/Money.class ./common/UserDispatcherIF.class

server: ./server/Accounts/ControlPanelImpls/AdminControlPanelImpl.class ./server/Accounts/ControlPanelImpls/CustomerControlPanelImpl.class ./server/Accounts/ABSAccount.class ./server/Accounts/AdminAccount.class ./server/Accounts/CustomerAccount.class ./server/CardCommands/CardFactoryImpl.class ./server/CardCommands/DebitCardCommand.class ./server/CardCommands/GiftCardCommand.class ./server/CardCommands/GiftCodeCommand.class ./server/CardCommands/VisaCardCommand.class ./server/Items/CartItem.class ./server/Items/ItemImpl.class ./server/OnlineStore.class ./server/ServerSetUp.class ./server/UserDispatcherImpl.class

serverRun: server common
	java server.ServerSetUp

all: ClientandServerSetUp.class client common server

run: all
	java ClientandServerSetUp

clean: 
	rm *.class
	rm ./client/*.class
	rm ./client/Views/*.class
	rm ./common/*.class
	rm ./common/CardCommands/*.class
	rm ./common/ControlPanelIFs/*.class
	rm ./server/*.class
	rm ./server/Accounts/*.class
	rm ./server/Accounts/ControlPanelImpls/*.class
	rm ./server/CardCommands/*.class
	rm ./server/Items/*.class

