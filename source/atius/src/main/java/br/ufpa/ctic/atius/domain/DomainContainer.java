package br.ufpa.ctic.atius.domain;

import br.gov.frameworkdemoiselle.ldap.template.Entry;

public class DomainContainer extends Entry {

	private String cn;

	private Integer nextUidNumber;

	protected String[] objectClass() {
		return new String[] { "domainContainer" };
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public Integer getNextUidNumber() {
		return nextUidNumber;
	}

	public void setNextUidNumber(Integer nextUidNumber) {
		this.nextUidNumber = nextUidNumber;
	}

}