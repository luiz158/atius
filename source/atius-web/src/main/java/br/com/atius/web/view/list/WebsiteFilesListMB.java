package br.com.atius.web.view.list;

import java.util.List;

import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;

import br.com.atius.web.business.WebsiteFilesBC;
import br.com.atius.web.domain.WebsiteDomain;
import br.com.atius.web.domain.WebsiteFiles;
import br.gov.frameworkdemoiselle.query.contrib.QueryConfig;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.contrib.AbstractListPageBean;

@ViewController
public class WebsiteFilesListMB extends AbstractListPageBean<WebsiteFiles, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private WebsiteFilesBC bc;

	private WebsiteDomain websiteDomain;

	private WebsiteFiles bean;

	@Override
	protected List<WebsiteFiles> handleResultList(QueryConfig<WebsiteFiles> queryConfig) {
		if (websiteDomain == null)
			return null;
		queryConfig.setSorting("serverName");
		return bc.findByServerName(websiteDomain.getServerName());
	}

	public void selectWebsite(WebsiteDomain websiteDomain) {
		list();
		this.websiteDomain = websiteDomain;
	}

	public void upload(FileUploadEvent event) {
		list();
		bc.insert(websiteDomain, event.getFile());
	}

	public void selectWebsiteFile(WebsiteFiles websiteFiles) {
		this.bean = websiteFiles;
	}

	public WebsiteFiles getWebsiteFiles() {
		return bean;
	}

	public void delete() {
		bc.delete(bean.getId());
	}

}
