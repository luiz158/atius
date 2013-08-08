package br.com.atius.catalog.business;

import javax.inject.Inject;

import br.com.atius.catalog.domain.ServiceGroup;
import br.com.atius.catalog.domain.ServiceItem;
import br.com.atius.catalog.domain.ServiceSubgroup;
import br.com.atius.catalog.persistence.ServiceItemDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class ServiceItemBC extends DelegateCrud<ServiceItem, Integer, ServiceItemDAO> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceGroupBC serviceGroupBC;

	@Inject
	private ServiceSubgroupBC serviceSubgroupBC;

	public ServiceGroup loadGroup(Integer id) {
		return serviceGroupBC.load(id);
	}

	public ServiceSubgroup loadSubgroup(Integer id) {
		return serviceSubgroupBC.load(id);
	}

}