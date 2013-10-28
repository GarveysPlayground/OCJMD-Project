package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Data implements DBMain{

	private static LockManager lockManager = LockManager.getInstance();
	
	public Data(String dbLocation) throws FileNotFoundException, IOException {
		new DataAccess(dbLocation);		
	}
	
	public Data(){
	}
	
	@Override
	public String[] read(int recNo) throws RecordNotFoundException{
			return DataAccess.read(recNo);
	}

	@Override
	public void update(int recNo, String[] data) throws RecordNotFoundException {
		DataAccess.update(recNo, data);		
	}

	@Override
	public void delete(int recNo) throws RecordNotFoundException {
		DataAccess.delete(recNo);
	}

	@Override
	public int[] find(String[] criteria) throws RecordNotFoundException {
		return DataAccess.find(criteria);
	}

	@Override
	public int create(String[] data) throws DuplicateKeyException {
		int newRecord = 0;	
		try {
			newRecord = DataAccess.create(data);
			return newRecord;
			} catch (IOException e) {
				System.err.println("Issue encountered while creating file");
				e.printStackTrace();
			}
		return newRecord;


	}

	@Override
	public void lock(int recNo) throws RecordNotFoundException {
		lockManager.lock(recNo);
	}

	@Override
	public void unlock(int recNo) throws RecordNotFoundException {
		lockManager.unlock(recNo);

	}

	@Override
	public boolean isLocked(int recNo) throws RecordNotFoundException {
		return lockManager.isLocked(recNo);

	}
}
