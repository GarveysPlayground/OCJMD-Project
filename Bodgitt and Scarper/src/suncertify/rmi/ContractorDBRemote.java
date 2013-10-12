package suncertify.rmi;

import java.rmi.Remote;
import suncertify.db.DBMainRmiConnector;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface ContractorDBRemote extends Remote, DBMainRmiConnector {

}
