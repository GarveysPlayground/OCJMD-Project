package suncertify.db;

import java.util.logging.Logger;

public class Data implements DBMain{

	private Logger logger = Logger.getLogger("suncertify.db");
	
	@Override
	public String[] read(int recNo) throws RecordNotFoundException{
			return FileAccess.read(recNo);
	}

	@Override
	public void update(int recNo, String[] data) throws RecordNotFoundException {
			FileAccess.update(recNo, data);		
	}

	@Override
	public void delete(int recNo) throws RecordNotFoundException {
			FileAccess.delete(recNo);
	}

	@Override
	public int[] find(String[] criteria) throws RecordNotFoundException {
		return FileAccess.find(criteria);
	}

	@Override
	public int create(String[] data) throws DuplicateKeyException {
			return FileAccess.create(data);
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
