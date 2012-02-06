package br.ufpa.ctic.atius.websites.view.list;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.contrib.AbstractListPageBean;
import br.gov.frameworkdemoiselle.util.contrib.Faces;
import br.ufpa.ctic.atius.websites.business.WebsiteDomainBC;
import br.ufpa.ctic.atius.websites.domain.WebsiteCategory;
import br.ufpa.ctic.atius.websites.domain.WebsiteDomain;

@ViewController
public class WebsiteDomainListMB extends AbstractListPageBean<WebsiteDomain, String> {

	private static final long serialVersionUID = 1L;

	@Inject
	private WebsiteDomainBC bc;

	private String searchDomain;

	List<WebsiteCategory> websiteCategories;

	List<String> websiteProfiles;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		bc.selectMenu(getFirstWebsiteCategory());
	}
	
	public String getSortAttribute() {
		return "serverName";
	}
	
	private String getFirstWebsiteCategory() {
		if (getWebsiteCategories().size() > 0)
			return getWebsiteCategories().get(0).getName();
		else
			return "";
	}

	@Override
	protected List<WebsiteDomain> handleResultList() {
		return bc.findByCategory(bc.getSelectedMenu(), searchDomain);
	}

	public String clearValidation() {
		Faces.resetValidation();
		clearResultList();
		return null;
	}

	public String getSearchDomain() {
		return searchDomain;
	}

	public void setSearchDomain(String searchDomain) {
		this.searchDomain = searchDomain;
		clearResultList();
	}

	public List<WebsiteCategory> getWebsiteCategories() {
		setWebsiteCategories();
		return websiteCategories;
	}

	public void setWebsiteCategories() {
		if (websiteCategories == null)
			websiteCategories = bc.getOrderedWebsiteCategories();
	}

	public List<String> getWebsiteProfiles() {
		setWebsiteProfiles();
		return websiteProfiles;
	}

	public void setWebsiteProfiles() {
		if (websiteProfiles == null)
			websiteProfiles = bc.getWebsiteProfiles();
	}

}
