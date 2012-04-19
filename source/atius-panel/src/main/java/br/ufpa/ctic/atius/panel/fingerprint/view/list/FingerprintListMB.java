package br.ufpa.ctic.atius.panel.fingerprint.view.list;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.ufpa.ctic.atius.panel.fingerprint.business.FingerprintBC;
import br.ufpa.ctic.atius.panel.fingerprint.domain.Fingerprint;
import br.gov.frameworkdemoiselle.query.contrib.QueryConfig;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.contrib.AbstractListPageBean;

@ViewController
public class FingerprintListMB extends AbstractListPageBean<Fingerprint, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private FingerprintBC bc;

	private List<String> categories;

	@Override
	protected List<Fingerprint> handleResultList(QueryConfig<Fingerprint> queryConfig) {
		return bc.find(getResultFilter(), getSelectedMenu());
	}

	public List<String> getCategories() {
		if (categories == null) {
			categories = new ArrayList<String>();
			categories.add("Internet");
			categories.add("DMZ");
			categories.add("Interno");
			categories.add("Desenvolvimento");
		}
		return categories;
	}

}
