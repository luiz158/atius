package br.ufpa.ctic.atius.panel.fingerprint.view.edit;

import javax.inject.Inject;


import br.ufpa.ctic.atius.panel.fingerprint.business.FingerprintBC;
import br.ufpa.ctic.atius.panel.fingerprint.domain.Fingerprint;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.contrib.AbstractEditPageBean;

@ViewController
public class FingerprintEditMB extends AbstractEditPageBean<Fingerprint, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private FingerprintBC bc;

	@Override
	public String delete() {
		this.bc.delete(getBean().getId());
		return null;
	}

	@Override
	public String insert() {
		this.bc.insert(getBean());
		return getPreviousView();
	}

	@Override
	public String update() {
		this.bc.update(getBean());
		return null;
	}

	@Override
	protected Fingerprint load(Long id) {
		return this.bc.load(id);
	}

}
