This was made as Assignment 3 in Principles of Software Engineering. It utilizes the front controller pattern, command pattern, and the factory pattern,

How to run:

Server Setup:

You can either run with "make serverRun" and "make clientRun" "make run" or java -jar Assignment3.jar

    Guides for runing with make and jar

    Running with the Assignment3.jar and 'java -jar Assignment3.jar':

        If you choose this method there is an extra step

        First login to any rrpc machine.

        Transfer the Assignment3.jar to your current machine

        Type 'jar xf Assignment3.jar' to extract all the files this step is needed for rmi to work

        Once all files have been extracted type 'rmiregistry 2323 &' to open up a socket

        If the rmiregistry fails you will need to kill any other rmiregistry running with ps eaf and kill it

        Now type 'java -jar Assignment3.jar' to run the jar file

        The system should pop up asking if this is the server or client

        Select the server option using '1'

        The system will ask what rrpc machine you are on enter one number if you are on rrpc03 simply type '3'

        Next you must set up your admin account. Enter a username and password when prompted. Dont forget these you will need
        them to log in.

        Once everything has been entered a popup should appear saying 'server is ready'

        You are free to set up the client now

    Running with a makefile and 'make run':

        First login to any rrpc machine.

        You may transfer the jar file or just transfer the files to your current machine

        if you transfer the jar you will need to extract the files using 'jar xf Assignment3.jar'

        Once all files have been extracted type 'rmiregistry 2323 &' to open up a socket

        If the rmiregistry fails you will need to kill any other rmiregistry running with ps eaf and kill

        Now type 'make run' to compile and run all the files

        The system should pop up asking if this is the server or client

        Select the server option using '1'

        The system will ask what rrpc machine you are on enter one number if you are on rrpc03 simply type '3'

        Next you must set up your admin account. Enter a username and password when prompted. Dont forget these you will need
        them to log in.

        Once everything has been entered a popup should appear saying 'server is ready'

        You are free to set up the client
    
    Running with a makefile and 'make serverRun':

        First login to any rrpc machine.

        You may Transfer the jar file or just Transfer the files to your current machine

        If you transfer the jar you will need to extract the files using 'jar xf Assignment3.jar'

        Once all files have been extracted type 'rmiregistry 2323 &' to open up a socket

        If the rmiregistry fails you will need to kill any other rmiregistry running with ps eaf and kill

        Now type 'make serverRun' to compile and run the server

        The system will ask what rrpc machine you are on enter one number if you are on rrpc03 simply type '3'

        Next you must set up your admin account. Enter a username and password when prompted. Dont forget these you will need
        them to log in.

        Once everything has been entered a popup should appear saying 'server is ready'

        You are free to set up the cliet

Client Setup:

    Running with Assignment3.jar and 'java -jar Assignment3.jar':

        The sever will need to be set up first

        First login to any rrpc machine.

        You should transfer the jar file

        There is no need to extract the files for this method unless you wish to view them

        Type 'java -jar Assignment3.jar' to run the jar file

        It will ask if you are setting up the server or client

        Selete the client option '2'

        The system will ask what rrpc machine the server is on (Not what rrpc you are using for the client) if the server is on rrpc03 simply type '3'

        If everything works a login screen should appear

    Running with a makefile and 'make run'

        The sever will need to be set up first

        First login to any rrpc machine.

        You should transfer the Assignment3.jar or just the files

        If your transfer the jar file you will need to extract the files with 'jar xf Assignment3.jar'

        Type 'make run' to compile and run

        It will ask if you are setting up the server or client

        Selete the client option '2'

        The system will ask what rrpc machine the server is on (Not what rrpc you are using for the client) if the server is on rrpc03 simply type '3'

        If everything works a login screen should appear
    
    Running with a makefile and 'make clientRun'

        The sever will need to be set up first

        First login to any rrpc machine.

        You should transfer the Assignment3.jar or just the files

        If your transfer the jar file you will need to extract the files with 'jar xf Assignment3.jar'

        Type 'make clientRun' to compile and run

        The system will ask what rrpc machine the server is on (Not what rrpc you are using for the client) if the server is on rrpc03 simply type '3'

        If everything works a login screen should appear

How to login:

    Admin - Select the admin login option (The admin account your created on the server will always be there)

    Customer - Select the customer login option if you already have an account

            Select the create account option 

            The server should be able to create a new account if it fails it will tell you why

            If everything works an admin will have to approve your pending account


Files appear in no particular order
Files:

Client:

    AdminView - This class acts as a menu for interacting with an AdminControlPanel. The object mainly uses scanners, prints, and
    switch cases. This object works by triggering methods from the AdminControlPanel to prompt the server to perform an action.
    This version of View is for the admin class so the options here will mainly deal with browsing the store, editing items, and
    interacting with accounts

    CustomerView - This class acts as a menu for interacting with a CustomerControlPanel. The object mainly uses scanners, prints, and
    switch cases. This object works by triggering methods from the CustomerControlPanel to prompt the server to perform an action.
    This version of View is for the Customer class so the options here will mainly deal with browsing the store, and adding funds to
    your account

    ClientSetUp - This class is responsible for connecting to the remote object on the server. It takes a char as the rrpc number and
    searches for the remote object. If found the server will then open up the UserRetriever.

    UserRetriever - This is the retriever of the front pattern. It asks the server Object (UserDispatcherIf) for Controlpanels for the 
    two views (Admin and Customer) to use. The client can enter a username and password to find their account. If the account is found
    the UserRetriever will decide which view to run.

Common:

    CardCommandIF - This is an interface used to define an execute method. The client will receive the commands and use the execute
    method to trigger any object. Returns a String to detail what happened. Defines the command pattern.

    CardFactoryIF - An interface that provides common methods for a factory to produce CardCommands. Part of the Abstract Factory
    Pattern.

    AdminControlPanelIF - An interface for the AdminControlPanel that provides methods that an admin will need to complete. The server
    will return an implementation of this interface. Calling the methods will trigger them on the server.

    Controlpanel - An interface that defines common methods for control panels such as isAdmin()/

    CustomerControlPanelIF - An interface for the CustomerControlPanel provides methods that an admin will need to complete. The server
    will return an implementation of this interface. Calling the methods will trigger them on the server.

    ItemIF - An interface for items on the server. Isn't used by a customer but by an admin when they need to edit an item

    Money - A wrapper around a double that provides a string format of (0.00) used by the balance of a customer and the price of an item

    UserDispatcherIf - An interface for the UserDispatcher defines part of the Front Controller Pattern. The client will
    connect to an implementation of this class using RMI. The user will then ask for control panels of classes. Also defines some
    methods for the admin to use when editing accounts.

Server:

    AdminControlPanelImpl - An implementation of the AdminControlPanelIF stored on the server given to the client from the UserDispatcher
    defines all the methods from AdminControlPanelIF. All methods called will be run on the server. Methods for interacting with
    stores, and accounts.

    CustomerControlPanelImpl - An implementation of the CustomerControlPanelIF stored on the server given to the client from the 
    UserDispatcher defines all the methods from CustomerControlPanelIF. All methods called will be run on the server. Methods for
    interacting with the cart, store, and balance.

    ASBAccount - An Abstract class that defines common methods mainly dealing with usernames and passwords for the customer account and
    admin account.

    AdminAccount - Am account that defines common methods for generating an AdminControlPanelImpl. Most methods are already defined in
    ASBAccount which it extends.

    CustomerAccount - An account that acts as a balance and cart of cart items. Also used for generating a CustomerControlPanelImpl.
    Most methods are already defined as ASBAccount which it extends.

    CardFactoryImpl - An implementation of the CardFactoryIF which defines all of its methods. This creates CardCommands on the server
    that can then be called on the client.

    DebitCardCommand - A command that would connect to a bank and request funds to add to an account. It won't actually do this
    since you probably don't wanna spend money on this. A command pattern.

    GiftCardCommand - A command that would access a database of gift cards. The funds on the gift card would then be added to an
    account.

    GiftCodeCommand - A command that would access a database of gift codes. The finds on the gift codes would then be added to 
    an account.

    VisaCardCommand - A command that would connect to a visa and request funds to add to an account. It won't actually do this
    since you probably don't wanna spend money on this. A command pattern.

    CartItem - This item is the exact same as ItemImpl. The only difference is that it can be placed in a cart with the int 
    "amountInCart" attached. Defines some other small methods. Also a mini proxy pattern.

    ItemImpl - This item holds information about an item such as name, type, description, price, and amountInStock. Stored in
    the store object. Defines the ItemIF interface.

    OnlineStore - This object is responsible for holding items. Provides some common methods for interacting with items

    ServerSetUp - This object sets up the server. It stores the UserDispatcherImpl as a remote object that clients
    can then request access to using RMI.

    UserDispatcherImpl - A piece of the Front Controller Pattern and the Remote object. This method holds all the
    user accounts and stores. The client can connect to this object and request a Controlpanel. The Controlpanel is
    used to make a way for clients to interact with the server safely. This stops malicious activities and
    tightens security. Acts as an intermediate between the client and control panels since someone who has a 
    AdminControlPanel who isn't supposed to can easily ruin the store.

    ClientandServerSetUp - This object is only for generating a jar file since there can only be one main. This object
    wouldn't work normally. You can ignore it. Provides a menu for setting up the server or client.