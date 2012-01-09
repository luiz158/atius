/*
 * Demoiselle Framework
 * Copyright (C) 2010 SERPRO
 * ----------------------------------------------------------------------------
 * This file is part of Demoiselle Framework.
 * 
 * Demoiselle Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 * ----------------------------------------------------------------------------
 * Este arquivo é parte do Framework Demoiselle.
 * 
 * O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação
 * do Software Livre (FSF).
 * 
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA
 * GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou
 * APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português
 * para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título
 * "LICENCA.txt", junto com esse programa. Se não, acesse <http://www.gnu.org/licenses/>
 * ou escreva para a Fundação do Software Livre (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package br.gov.frameworkdemoiselle.internal.implementation;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.internal.configuration.SecurityConfig;
import br.gov.frameworkdemoiselle.security.AfterLoginSuccessful;
import br.gov.frameworkdemoiselle.security.AfterLogoutSuccessful;
import br.gov.frameworkdemoiselle.security.Authenticator;
import br.gov.frameworkdemoiselle.security.Authorizer;
import br.gov.frameworkdemoiselle.security.NotLoggedInException;
import br.gov.frameworkdemoiselle.security.PublicResource;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.security.User;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.ResourceBundle;

/**
 * This is the default implementation of {@link SecurityContext} interface.
 * 
 * @author SERPRO
 */
@SessionScoped
@Named("securityContext")
public class SecurityContextImpl implements SecurityContext {

	private static final long serialVersionUID = 1L;

	@Inject
	@Name("demoiselle-core-bundle")
	private ResourceBundle bundle;

	@Inject
	private Authenticator authenticator;

	@Inject
	private Authorizer authorizer;

	@Inject
	private SecurityConfig config;

	@Inject
	private PublicResource publicResource;

	/**
	 * @see br.gov.frameworkdemoiselle.security.SecurityContext#hasPermission(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean hasPermission(String resource, String operation) throws NotLoggedInException {
		if (config.isEnabled()) {
			checkLoggedIn();
			return authorizer.hasPermission(resource, operation);

		} else {
			return true;
		}
	}

	/**
	 * @see br.gov.frameworkdemoiselle.security.SecurityContext#hasRole(java.lang.String)
	 */
	@Override
	public boolean hasRole(String role) throws NotLoggedInException {
		if (config.isEnabled()) {
			checkLoggedIn();
			return authorizer.hasRole(role);

		} else {
			return true;
		}
	}

	/**
	 * @see br.gov.frameworkdemoiselle.security.SecurityContext#isLoggedIn()
	 */
	@Override
	public boolean isLoggedIn() {
		if (config.isEnabled()) {
			return getUser() != null;
		} else {
			return true;
		}
	}

	/**
	 * @see br.gov.frameworkdemoiselle.security.SecurityContext#login()
	 */
	@Override
	public void login() {
		if (config.isEnabled() && authenticator.authenticate()) {
			Beans.getBeanManager().fireEvent(new AfterLoginSuccessful() {

				private static final long serialVersionUID = 1L;

			});
		}
	}

	/**
	 * @see br.gov.frameworkdemoiselle.security.SecurityContext#logout()
	 */
	@Override
	public void logout() throws NotLoggedInException {
		if (config.isEnabled()) {
			checkLoggedIn();
			authenticator.unAuthenticate();

			Beans.getBeanManager().fireEvent(new AfterLogoutSuccessful() {

				private static final long serialVersionUID = 1L;
			});
		}
	}

	/**
	 * @see br.gov.frameworkdemoiselle.security.SecurityContext#getUser()
	 */
	@Override
	public User getUser() {
		User user = authenticator.getUser();

		if (!config.isEnabled() && user == null) {
			user = new User() {

				private static final long serialVersionUID = 1L;

				@Override
				public void setAttribute(Object key, Object value) {
				}

				@Override
				public String getId() {
					return "demoiselle";
				}

				@Override
				public Object getAttribute(Object key) {
					return null;
				}
			};
		}

		return user;
	}

	private void checkLoggedIn() throws NotLoggedInException {
		if (!isLoggedIn()) {
			throw new NotLoggedInException(bundle.getString("user-not-authenticated"));
		}
	}

	@Override
	public List<String> getPublicResources(String resourceName) {
		return publicResource.getResources(resourceName);
	}

}