/**
 * 
 */
package com.springbatchds.datasourceexample.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.springbatchds.datasourceexample.domain.Account;
import com.springbatchds.datasourceexample.repo.AccountRepository;

/**
 * @author Ramad
 *
 */
@Component
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accountRepo;

	@Override
	public List<Account> getAllAccounts() {
		List<Account> users = 
				  StreamSupport.stream(accountRepo.findAll().spliterator(), false)
				    .collect(Collectors.toList());
		return users;
	}

	@Override
	public List<Account> searchByAcctName(String accountNumber, String customerId,String description) {
		return accountRepo.findByAccountNumberOrCustomerIdOrDescription(accountNumber, customerId,description);
	}

	@Override
	public List<Account> sort(String sort) {
		Sort s = null;
		if(sort=="asc")
		{
			s  = Sort.by(Sort.Direction.ASC,"description");
		}
		else
		{
			s  = Sort.by(Sort.Direction.DESC,"description");
		}
		return  StreamSupport.stream(accountRepo.findAll(s).spliterator(), false)
			    .collect(Collectors.toList());
	}
	
	

}
