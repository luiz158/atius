package br.ufpa.ctic.atius.panel.fingerprint.persistence;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import org.slf4j.Logger;

import br.ufpa.ctic.atius.panel.fingerprint.domain.Fingerprint;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.contrib.JPACrud;

@PersistenceController
public class FingerprintDAO extends JPACrud<Fingerprint, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	@SuppressWarnings("unused")
	private Logger logger;

	@SuppressWarnings("unchecked")
	public List<Fingerprint> findByCategory(String category, String search) {

		String q = "from Fingerprint as f where lower(f.category) = lower(:category) and"
				+ "(lower(f.serverName) like lower(:search) or lower(f.fingerprint) like lower(:search)) ";
		Query query = createQuery(q);
		query.setParameter("category", category);
		query.setParameter("search", "%" + search + "%");

		return query.getResultList();
	}

}
