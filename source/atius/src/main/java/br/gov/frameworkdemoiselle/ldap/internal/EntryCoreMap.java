package br.gov.frameworkdemoiselle.ldap.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.internal.producer.LoggerProducer;
import br.gov.frameworkdemoiselle.ldap.core.EntryManager;
import br.gov.frameworkdemoiselle.ldap.core.EntryQuery;
import br.gov.frameworkdemoiselle.ldap.exception.EntryException;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;

public class EntryCoreMap implements Serializable {

	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerProducer.create(EntryManager.class);

	@Inject
	private ConnectionManager conn;

	@Inject
	private EntryQuery query;
	
	private boolean verbose = false;

	private LDAPConnection getConnection() {
		return this.conn.initialized();
	}

	public void persist(Map<String, String[]> entry, String dn) {
		try {
			LDAPAttributeSet attributeSet = new LDAPAttributeSet();
			for (Map.Entry<String, String[]> attrMap : entry.entrySet()) {
				attributeSet.add(new LDAPAttribute(attrMap.getKey(), attrMap.getValue()));
			}
			LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);
			getConnection().add(newEntry);
		} catch (LDAPException e) {
			logger.error("Error persisting entry " + dn);
			throw new EntryException();
		}
	}

	public void merge(Map<String, String[]> entry, String dn) {
		try {
			List<LDAPModification> modList = new ArrayList<LDAPModification>();
			for (Map.Entry<String, String[]> attrMap : entry.entrySet()) {
				LDAPAttribute attribute = new LDAPAttribute(attrMap.getKey(), attrMap.getValue());
				modList.add(new LDAPModification(LDAPModification.REPLACE, attribute));
			}

			LDAPModification[] modsList = modList.toArray(new LDAPModification[0]);
			getConnection().modify(dn, modsList);
		} catch (LDAPException e) {
			logger.error("Error merging entry " + dn);
			throw new EntryException();
		}
	}

	public void update(Map<String, String[]> entry, String dn) {
		try {
			throw new LDAPException();
		} catch (LDAPException e) {
			logger.error("Error updating entry " + dn);
			throw new EntryException();
		}
	}

	public void remove(String dn) {
		try {
			getConnection().delete(dn);
		} catch (LDAPException e) {
			logger.error("Error deleting entry " + dn);
			throw new EntryException();
		}
	}

	public Map<String, String[]> find(String searchFilter) {
		query.setFilter(searchFilter);
		return query.getSingleResult();
	}

	public Map<String, String[]> getReference(String dn) {
		query.setBaseDn(dn);
		query.setScope(LDAPConnection.SCOPE_BASE);
		query.setFilter("objectClass=*");
		return query.getSingleResult();
	}

	public String findReference(String searchFilter) {
		query.setFilter(searchFilter);
		return query.getSingleDn();
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

}