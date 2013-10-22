package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

public class DataRemote implements DBMainRmiConnector{

	private DBMain database = null;
	private Logger logger = Logger.getLogger("suncertify.db");
	
	public DataRemote(String dbLocation) throws FileNotFoundException, IOException{
		database = new Data(dbLocation);
	}
	@Override
	public String[] read(int recNo) throws RecordNotFoundException, RemoteException{
			return database.read(recNo);
	}

	@Override
	public void update(int recNo, String[] data) throws RecordNotFoundException, RemoteException {
		database.update(recNo, data);		
	}

	@Override
	public void delete(int recNo) throws RecordNotFoundException, RemoteException {
		database.delete(recNo);
	}

	@Override
	public int[] find(String[] criteria) throws RecordNotFoundException, RemoteException {
		return database.find(criteria);
	}

	@Override
	public int create(String[] data) throws DuplicateKeyException, RemoteException {
		int newRecord = 0;	
		newRecord = database.create(data);
		return newRecord;
	}

	@Override
	public void lock(int recNo) throws RecordNotFoundException, RemoteException {
		System.out.println("DatRemote locking");
		database.lock(recNo);
		
	}

	@Override
	public void unlock(int recNo) throws RecordNotFoundException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLocked(int recNo) throws RecordNotFoundException, RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
