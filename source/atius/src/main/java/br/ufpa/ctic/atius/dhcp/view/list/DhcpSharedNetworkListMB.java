package br.ufpa.ctic.atius.dhcp.view.list;

import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.query.contrib.QueryConfig;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.contrib.AbstractListPageBean;
import br.gov.frameworkdemoiselle.util.Strings;
import br.ufpa.ctic.atius.dhcp.business.DhcpSharedNetworkBC;
import br.ufpa.ctic.atius.dhcp.domain.DhcpSharedNetwork;

@ViewController
public class DhcpSharedNetworkListMB extends AbstractListPageBean<DhcpSharedNetwork, String> {

	private static final long serialVersionUID = 1L;

	@Inject
	private DhcpSharedNetworkBC bc;

	@Override
	protected List<DhcpSharedNetwork> handleResultList(QueryConfig<DhcpSharedNetwork> queryConfig) {
		if (bc.getDhcpServiceDN() == null)
			return null;
		queryConfig.setGeneric(bc.getDhcpServiceDN());
		return bc.findAll();
	}

	public void init() {
		if (Strings.isEmpty(bc.getSelectedMenu()))
			if (getResultList() != null && getResultList().size() > 0)
				bc.selectDhcpSharedNetwork(getResultList().get(0));
			else
				bc.selectMenu("Configuração");
	}

	public void selectDhcpSharedNetwork(DhcpSharedNetwork dhcpSharedNetwork) {
		bc.selectMenu(dhcpSharedNetwork.getCn());
		bc.selectDhcpSharedNetwork(dhcpSharedNetwork);
	}

}
