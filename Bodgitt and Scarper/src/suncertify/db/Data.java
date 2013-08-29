package suncertify.db;

import java.io.IOException;
import java.util.logging.Logger;

public class Data implements DBMain{

	private static FileAccess database = null;
	private Logger logger = Logger.getLogger("suncertify.db");
	
	@Override
	public String[] read(int recNo) throws RecordNotFoundException{
			return database.read(recNo);
	}

	@Override
	public void update(int recNo, String[] data) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int recNo) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] find(String[] criteria) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(String[] data) throws DuplicateKeyException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void lock(int recNo) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlock(int recNo) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLocked(int recNo) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

}
