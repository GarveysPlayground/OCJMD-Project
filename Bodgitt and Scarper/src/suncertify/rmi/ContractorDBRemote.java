/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 28th Oct 2013 
 * ContractorDBRemote.java
 */
package suncertify.rmi;

import java.rmi.Remote;

import suncertify.db.DBMainRmiConnector;

/**
 * The Interface ContractorDBRemote for the network client. This extents the 
 * DBMainRmiConnector interface which is a duplicate of the DBmain interface
 * provided by instructions.html but allows the use of extends remote. 
 * See choices.txt for more on this.
 */
public interface ContractorDBRemote extends Remote, DBMainRmiConnector {

}
