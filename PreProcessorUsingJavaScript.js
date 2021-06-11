function loadAPIKeyFromServerProperties(){
 logger.debug("Entering into loadAPIKeyFromServerProperties");

 //Get ServicesManager Object
 var servicesManager = request.getServicesManager();
 //Get ConfigurableParametersHelper Object
 var configurableParametersHelper = servicesManager.getConfigurableParametersHelper();
 //Get all server properties
 var allServerProperties = configurableParametersHelper.getAllServerProperties();
 //Get the value of T24_API_KEY server property
 var apiKey = allServerProperties.get("T24_API_KEY");
 logger.debug("APIKey from server properties: " + apiKey);

 if (apiKey == null || apiKey == "" ) 
 {
  //return false, while returning false set the result object with opstatus as non-zero value and an error message to indicate the error to client application
  logger.debug("APIKey not foung in the server properties, returning false");
  var opstatus = new com.konylabs.middleware.dataobject.Param();
  opstatus.setName("opstatus");
  opstatus.setValue("9000");
  result.setParam(opstatus);
  var errorMessage = new com.konylabs.middleware.dataobject.Param();
  errorMessage.setName("errorMessage");
  errorMessage.setValue("APIKey not found in server properties");
  result.setParam(errorMessage);
  return false;
 } 
 else 
 {
  //set the apiKey retrived from server properties into request header parameters
  request.addRequestParam_("apikey", apiKey);
  request.addRequestParam_("Accept", "application/json");
  request.addRequestParam_("Content-Type", "application/json"); 
 }   
 logger.debug("Exiting out of loadAPIKeyFromServerProperties");

 return true;
}
