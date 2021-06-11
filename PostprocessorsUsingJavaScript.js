function processCustomerHoldings () {
 logger.debug("Entering into processCustomerHoldings");
 var body = result.findDataset("body");

 //Create seperate data sets based on the Product Types
 var savingsAccounts = new com.konylabs.middleware.dataobject.Dataset("SavingsAccounts");
 var currentAccounts = new com.konylabs.middleware.dataobject.Dataset("CurrentAccounts");
 var personalLoanAccounts = new com.konylabs.middleware.dataobject.Dataset("PersonalLoanAccounts");
 var mortgageAccounts = new com.konylabs.middleware.dataobject.Dataset("MortgageAccounts");
 var currentAccountsWithOD = new com.konylabs.middleware.dataobject.Dataset("CurrentAccountsWithOD");
 
 var numberofAccounts = body.getRecords().length;
 logger.debug("Number of accounts: "+numberofAccounts);
 
 var tempProductName = ""
 var bodyDSRecord = ""
 var savAccRecords = new java.util.ArrayList();
 var currAccRecords = new java.util.ArrayList();
 var plRecords = new java.util.ArrayList();
 var mortgageRecords = new java.util.ArrayList();
 var currAccODRecords = new java.util.ArrayList();
 
 for (i = 0; i < numberofAccounts; i++) {
  bodyDSRecord = new com.konylabs.middleware.dataobject.Record();
  bodyDSRecord = body.getRecord(i);
  logger.debug("Processing the resord: "+JSON.stringify(bodyDSRecord));
  tempProductName = bodyDSRecord.getParam("productName").getValue();
  logger.debug("Product name in this record: "+tempProductName);
  var imgBankLogo = new com.konylabs.middleware.dataobject.Param();
  imgBankLogo.setName("imgBankLogo");
  imgBankLogo.setValue("bankofamerica.png");
  bodyDSRecord.setParam(imgBankLogo);
  var lblBankName = new com.konylabs.middleware.dataobject.Param();
  lblBankName.setName("lblBankName");
  lblBankName.setValue("Infinity");
  bodyDSRecord.setParam(lblBankName);
  if (tempProductName == "Savings Account") {
   savAccRecords.add(bodyDSRecord);
  }else if(tempProductName === "Current Account"){
   currAccRecords.add(bodyDSRecord);
  }else if(tempProductName === "AA Pers Loan"){
   plRecords.add(bodyDSRecord);
  }else if(tempProductName === "AA Mortgage"){
   mortgageRecords.add(bodyDSRecord);
  }else if(tempProductName === "CurrAcc with OD"){
   currAccODRecords.add(bodyDSRecord);
  }
 }

 savingsAccounts.setRecords(savAccRecords);
 result.setDataSet(savingsAccounts);

 currentAccounts.setRecords(currAccRecords);
 result.setDataSet(currentAccounts);

 personalLoanAccounts.setRecords(plRecords);
 result.setDataSet(personalLoanAccounts);

 mortgageAccounts.setRecords(mortgageRecords);
 result.setDataSet(mortgageAccounts);

 currentAccountsWithOD.setRecords(currAccODRecords);
 result.setDataSet(currentAccountsWithOD);

 logger.debug("Exiting out of processCustomerHoldings"); 
}