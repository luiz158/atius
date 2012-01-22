package br.ufpa.ctic.atius.business;

import br.gov.frameworkdemoiselle.message.DefaultMessage;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.util.Faces;
import br.ufpa.ctic.atius.domain.DomainContainer;
import br.ufpa.ctic.atius.persistence.DomainContainerDAO;

@BusinessController
public class DomainContainerBC extends DelegateCrud<DomainContainer, String, DomainContainerDAO> {

	private static final long serialVersionUID = 1L;

	public DomainContainer getNextFreeUidNumber(String webserverName) {
		try {
			DomainContainer domainContainer = load(webserverName);
			domainContainer.setNextUidNumber(domainContainer.getNextUidNumber() + 1);
			update(domainContainer);
			domainContainer.setNextUidNumber(domainContainer.getNextUidNumber() - 1);
			return domainContainer;
		} catch (Exception e) {
			Faces.validationFailed();
			Faces.addMessage(new DefaultMessage("O webserver indicado não foi encontrado"));
			throw new RuntimeException();
		}
	}

}