package suncertify.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

public class ContractorDBremoteImpl extends UnicastRemoteObject implements ContractorDBRemote {

	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContractorDBremoteImpl(String dbLocation) throws RemoteException{
		
		
	}
	
	/**
     * Logger instance to pass messages through
     */
    private Logger logger = Logger.getLogger("suncertify.rmi");

	@Override
	public String[] read(int recNo) throws RecordNotFoundException,
			RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(int recNo, String[] data)
			throws RecordNotFoundException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int recNo) throws RecordNotFoundException,
			RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] find(String[] criteria) throws RecordNotFoundException,
			RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(String[] data) throws DuplicateKeyException,
			RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void lock(int recNo) throws RecordNotFoundException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlock(int recNo) throws RecordNotFoundException,
			RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLocked(int recNo) throws RecordNotFoundException,
			RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
}
