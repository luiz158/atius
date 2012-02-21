package br.ufpa.ctic.atius.dhcp.view.list;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.enumeration.contrib.Comparison;
import br.gov.frameworkdemoiselle.query.contrib.QueryConfig;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.contrib.AbstractListPageBean;
import br.gov.frameworkdemoiselle.util.contrib.Strings;
import br.ufpa.ctic.atius.dhcp.business.DhcpSubnetBC;
import br.ufpa.ctic.atius.dhcp.domain.DhcpSubnet;

@ViewController
public class DhcpSubnetListMB extends AbstractListPageBean<DhcpSubnet, String> {

	private static final long serialVersionUID = 1L;

	@Inject
	private DhcpSubnetBC bc;

	@Override
	protected List<DhcpSubnet> handleResultList(QueryConfig<DhcpSubnet> queryConfig) {
		if (bc.getDhcpSharedNetworkDN() == null)
			return null;
		
		queryConfig.setGeneric(bc.getDhcpSharedNetworkDN());

		List<DhcpSubnet> dhcpSubnets = new ArrayList<DhcpSubnet>();
		if (Strings.isNotBlank(getResultFilter())) {
			queryConfig.getFilter().put("dhcpComments", getResultFilter());
			dhcpSubnets.addAll(bc.findAll());
			
			queryConfig.getFilter().remove("dhcpComments");
			queryConfig.getFilter().put("cn", getResultFilter());
			queryConfig.setFilterComparison(Comparison.CONTAINS);
		}
		dhcpSubnets.addAll(bc.findAll());
		return dhcpSubnets;
	}

}