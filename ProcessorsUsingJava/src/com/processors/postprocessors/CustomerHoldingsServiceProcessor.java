package com.processors.postprocessors;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.konylabs.middleware.common.DataPostProcessor2;
import com.konylabs.middleware.controller.DataControllerRequest;
import com.konylabs.middleware.controller.DataControllerResponse;
import com.konylabs.middleware.dataobject.Dataset;
import com.konylabs.middleware.dataobject.Record;
import com.konylabs.middleware.dataobject.Param;
import com.konylabs.middleware.dataobject.Result;

public class CustomerHoldingsServiceProcessor implements DataPostProcessor2 {

	private static final Logger logger = Logger.getLogger(CustomerHoldingsServiceProcessor.class);
	
	@Override
	public Object execute(Result result, DataControllerRequest request, DataControllerResponse response) throws Exception {

	    logger.debug("Entering into processAccountsForCustomer");
	    
	    Dataset body = result.getDatasetById("body");
	    
	    Dataset savingsAccounts = new Dataset("savingsAccounts");
	    Dataset currentAccounts = new Dataset("currentAccounts");
	    Dataset personalLoanAccounts = new Dataset("personalLoanAccounts");
	    Dataset mortgageAccounts = new Dataset("mortgageAccounts");
	    Dataset currentAccountsWithOD = new Dataset("currentAccountsWithOD");
		
	    int numberofAccounts = body.getAllRecords().size();
	    logger.debug("Number of accounts: " + numberofAccounts);
	    
	    String tempProductName = "";
	    ArrayList<Record> savAccRecords = new ArrayList<>();
	    ArrayList<Record> currAccRecords = new ArrayList<>();
	    ArrayList<Record> plRecords = new ArrayList<>();
	    ArrayList<Record> mortgageRecords = new ArrayList<>();
	    ArrayList<Record> currAccODRecords = new ArrayList<>();
	    
	    for (int i = 0; i < numberofAccounts; i++) {
	        Record bodyDSRecord = new Record();
	        bodyDSRecord = body.getRecord(i);
	        tempProductName = bodyDSRecord.getParam("productName").getValue();
	        logger.debug("Product name in this record: " + tempProductName);
	        Param imgBankLogo = new Param();
	        imgBankLogo.setName("imgBankLogo");
	        imgBankLogo.setValue("bankofamerica.png");
	        bodyDSRecord.addParam(imgBankLogo);
	        Param lblBankName = new Param();
	        lblBankName.setName("lblBankName");
	        lblBankName.setValue("Infinity");
	        bodyDSRecord.addParam(lblBankName);
	        if ("Savings Account".equalsIgnoreCase(tempProductName)) {
	          savAccRecords.add(bodyDSRecord);
	        } else if ("Current Account".equalsIgnoreCase(tempProductName)) {
	          currAccRecords.add(bodyDSRecord);
	        } else if ("AA Pers Loan".equalsIgnoreCase(tempProductName)) {
	          plRecords.add(bodyDSRecord);
	        } else if ("AA Mortgage".equalsIgnoreCase(tempProductName)) {
	          mortgageRecords.add(bodyDSRecord);
	        } else if ("CurrAcc with OD".equalsIgnoreCase(tempProductName)) {
	          currAccODRecords.add(bodyDSRecord);
	        } 
	      }
	    
	    savingsAccounts.addAllRecords(savAccRecords);
	    result.addDataset(savingsAccounts);
	    
	    currentAccounts.addAllRecords(currAccRecords);
	    result.addDataset(currentAccounts);
	    
	    personalLoanAccounts.addAllRecords(plRecords);
	    result.addDataset(personalLoanAccounts);
	    
	    mortgageAccounts.addAllRecords(mortgageRecords);
	    result.addDataset(mortgageAccounts);
	    
	    currentAccountsWithOD.addAllRecords(currAccODRecords);
	    result.addDataset(currentAccountsWithOD);
	    logger.debug("Exiting out of processAccountsForCustomer");
	    
		return result;
	}

}
