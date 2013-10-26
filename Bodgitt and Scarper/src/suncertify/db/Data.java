package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

public class Data implements DBMain{

	private FileAccess database = null;
	private Logger logger = Logger.getLogger("suncertify.db");
	private static LockManager lockManager = LockManager.getInstance();
	// private LockManager lockManager = null;
	
	public Data(String dbLocation) throws FileNotFoundException, IOException {
		database = new FileAccess(dbLocation);
		//lockManager = new LockManager();
		
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
			return newRecord;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return newRecord;


	}

	@Override
	public void lock(int recNo) throws RecordNotFoundException {
		try {
			if(recNo < 0 || recNo >= database.getValidRecords().length){
				System.err.println("No Such rec No " + recNo);
			}else{
				lockManager.lock(recNo);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void unlock(int recNo) throws RecordNotFoundException {
		try {
			if(recNo < 0 || recNo >= database.getValidRecords().length){
				System.err.println("No Such rec No " + recNo);
			}else{
			lockManager.unlock(recNo);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isLocked(int recNo) throws RecordNotFoundException {
		try {
			if(recNo < 0 || recNo >= database.getValidRecords().length){
				System.err.println("No Such rec No " + recNo);
				return lockManager.isLocked(recNo);
			}else{
			return lockManager.isLocked(recNo);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lockManager.isLocked(recNo);
	}
}
