package com.springbatchds.datasourceexample.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbatchds.datasourceexample.domain.Account;
import com.springbatchds.datasourceexample.service.AccountService;

@RestController
@RequestMapping("/job")
public class BatchController {
	
	 	@Autowired
	    JobLauncher jobLauncher;

	    @Autowired
	    private Job importJob;
	    
	    @Autowired
	    private AccountService accountService;

	    @GetMapping("/importcsv")
	    public void runJob(){
	        try {
	            JobParametersBuilder builder = new JobParametersBuilder();
	            builder.addString("startDate", LocalDateTime.now().toString());

	            jobLauncher.run(importJob, builder.toJobParameters());
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	    }
	    
	    @GetMapping("/getAll")
	    public ResponseEntity<List<Account>> getAllAccounts()
	    {
			return new ResponseEntity<>(accountService.getAllAccounts(),HttpStatus.OK);
	    	
	    }
	    
	    @PostMapping("/search")
	    public ResponseEntity<List<Account>> search(@RequestParam(required=false) String accountNumber, 
				@RequestParam(required=false) String customerId,@RequestParam(required=false) String description)
	    {
			return new ResponseEntity<>(accountService.searchByAcctName(accountNumber,customerId,description),HttpStatus.OK);
	    	
	    }
	    
	    @GetMapping("/sortByAcc")
	    public ResponseEntity<List<Account>> search(@RequestParam String type)
	    {
			return new ResponseEntity<>(accountService.sort(type),HttpStatus.OK);
	    	
	    }
	    

}
