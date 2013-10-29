package suncertify.rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import suncertify.db.DBMainRmiConnector;
import suncertify.db.DataRemote;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

public class ContractorDBremoteImpl extends UnicastRemoteObject 
	implements ContractorDBRemote {

	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DBMainRmiConnector database = null;

	public ContractorDBremoteImpl(String dbLocation) throws RemoteException {
		
		try {
			database = new DataRemote(dbLocation);
		} catch (IOException e) {
			System.out.println("Issue connecting to DB");
			e.printStackTrace();
		}
	}
	
	/**
     * Logger instance to pass messages through
     */
    private Logger logger = Logger.getLogger("suncertify.rmi");

	@Override
	public String[] read(int recNo) 
			throws RecordNotFoundException,	RemoteException {
		// TODO Auto-generated method stub
		return database.read(recNo);
	}

	@Override
	public void update(int recNo, String[] data)
			throws RecordNotFoundException, RemoteException {
		database.update(recNo, data);
		
	}

	@Override
	public void delete(int recNo) 
			throws RecordNotFoundException,	RemoteException {
		database.delete(recNo);
		
	}

	@Override
	public int[] find(String[] criteria) 
			throws RecordNotFoundException,	RemoteException {
		System.out.println("Trying");
		return database.find(criteria);
	}

	@Override
	public int create(String[] data) 
			throws DuplicateKeyException, RemoteException {
		database.create(data);
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void lock(int recNo) 
			throws RecordNotFoundException, RemoteException {
		System.out.println("ContractorDBmain lock");
		database.lock(recNo);
		
	}

	@Override
	public void unlock(int recNo) 
			throws RecordNotFoundException,	RemoteException {
		System.out.println("ContractorDBmain lock");
		database.unlock(recNo);
		
	}

	@Override
	public boolean isLocked(int recNo) 
			throws RecordNotFoundException,	RemoteException {
		// TODO Auto-generated method stub
		return database.isLocked(recNo);
	}
}
