/**
 * 
 */
package com.springbatchds.datasourceexample.domain;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author Ramad
 *
 */
@Entity
@Data
public class Account {
	
	//ACCOUNT_NUMBER|TRX_AMOUNT|DESCRIPTION|TRX_DATE|TRX_TIME|CUSTOMER_ID
	//8872838283|123.00|FUND TRANSFER|2019-09-12|11:11:11|222
	
	@Id
    @GeneratedValue
    private long Id;
	
	private String accountNumber;
	
	@Column
	private String trxAmount;
	
	@Column
	private String description;
	
	@Column
	private String trxDate;
	
	@Column
	private String trxTime;
	
	@Column
	private String customerId;

}
