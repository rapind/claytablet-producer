package com.claytablet.service.event.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.claytablet.model.event.Account;
import com.claytablet.service.event.AccountManager;
import com.claytablet.service.event.EventServiceException;
import com.google.inject.Singleton;

/**
 * Copyright 2007 Clay Tablet Technologies Inc.
 * 
 * <p>
 * 
 * @author <a href="mailto:drapin@clay-tablet.com">Dave Rapin</a>
 * 
 * <p>
 * Default and required implementation for the account manager.
 */
@Singleton
public class AccountManagerImpl implements AccountManager {

	private static final String BASE_PATH = "accounts/";

	protected final Log log = LogFactory.getLog(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.AccountManager#findAccount(java.lang.String)
	 */
	public Account findAccount(String accountId) throws EventServiceException {

		String path = BASE_PATH + accountId + ".xml";

		log
				.debug("Load and deserialize the account from the class path under: "
						+ path);
		try {
			return Account.fromXml(IOUtils.toString(ClassLoader
					.getSystemClassLoader().getResourceAsStream(path)));
		} catch (IOException e) {
			throw new EventServiceException(
					"Unable to load the account file from class path under: "
							+ path + " due to IOException: " + e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.AccountManager#findAllAccounts()
	 */
	public List<Account> findAllAccounts() {

		log.error("Unsupported method.");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.AccountManager#removeAccount(java.lang.String)
	 */
	public void removeAccount(String accountId) {

		log.error("Unsupported method.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.claytablet.service.event.AccountManager#saveAccount(com.claytablet.model.event.Account)
	 */
	public Account saveAccount(Account account) {

		log.error("Unsupported method.");
		return null;
	}

}
