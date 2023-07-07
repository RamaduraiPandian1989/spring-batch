/**
 * 
 */
package com.springbatchds.datasourceexample.service;

import java.util.List;

import com.springbatchds.datasourceexample.domain.Account;

/**
 * @author Ramad
 *
 */
public interface AccountService {
	
	public List<Account> getAllAccounts();

	public List<Account> searchByAcctName(String accountNumber, String customerId, String description);

	public List<Account> sort(String sort);

}
