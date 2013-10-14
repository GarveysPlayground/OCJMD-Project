package suncertify.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class RMIManager {
	
	private static DBFactoryImpl dbFactoryImpl;
	
	public static void startRegister(String dbLocation, int rmiPort) throws RemoteException{
		Registry register = java.rmi.registry.LocateRegistry.createRegistry(rmiPort);
		register.rebind("rmi://localhost:"+rmiPort+"/Bodgitt&Scarper", 
								new  DBFactoryImpl(dbLocation));

		
		System.out.println("RNI Registry Started");
		
	}

}
