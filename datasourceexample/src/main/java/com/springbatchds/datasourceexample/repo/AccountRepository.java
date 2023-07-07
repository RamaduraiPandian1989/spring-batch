package com.springbatchds.datasourceexample.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springbatchds.datasourceexample.domain.Account;

public interface AccountRepository  extends PagingAndSortingRepository<Account, Long> {

	List<Account> findByAccountNumberOrCustomerId(String accountNumber, String customerId);
	
	List<Account> findByAccountNumberOrCustomerIdOrDescription(String accountNumber, String customerId,String description);
}