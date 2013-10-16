package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

public class Data implements DBMain{

	private static FileAccess database = null;
	private Logger logger = Logger.getLogger("suncertify.db");
	
	public Data(String dbLocation) throws FileNotFoundException, IOException {
		database = new FileAccess(dbLocation);
	}
	
	public Data(){
	}
	
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
		int newRecord = 0;	
		try {
			newRecord = FileAccess.create(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return newRecord;
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
