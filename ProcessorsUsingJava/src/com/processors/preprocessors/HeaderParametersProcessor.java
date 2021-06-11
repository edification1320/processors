package com.processors.preprocessors;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.konylabs.middleware.api.ConfigurableParametersHelper;
import com.konylabs.middleware.api.ServicesManager;
import com.konylabs.middleware.common.DataPreProcessor2;
import com.konylabs.middleware.controller.DataControllerRequest;
import com.konylabs.middleware.controller.DataControllerResponse;
import com.konylabs.middleware.dataobject.Param;
import com.konylabs.middleware.dataobject.Result;

public class HeaderParametersProcessor implements DataPreProcessor2 {

	private static final Logger logger = Logger.getLogger(HeaderParametersProcessor.class);

	@Override
	public boolean execute(HashMap serviceInputParams, DataControllerRequest request, DataControllerResponse response, Result result) throws Exception {

		logger.debug("Entering into execute method in HeaderParametersProcessor");
	    
		boolean headerParametersProcessorResponse = true;
		
	    ServicesManager servicesManager = request.getServicesManager();
	    ConfigurableParametersHelper configurableParametersHelper = servicesManager.getConfigurableParametersHelper();
	    Map<String, String> allServerProperties = configurableParametersHelper.getAllServerProperties();
	    String apiKey = allServerProperties.get("T24_API_KEY");
	    logger.debug("APIKey from server properties: " + apiKey);
		
	    if (apiKey == null || apiKey == "" || apiKey.trim().equalsIgnoreCase("")) {
	        logger.debug("APIKey not foung in the server properties, returning false");
	        
	        Param opstatus = new Param();
	        opstatus.setName("opstatus");
	        opstatus.setValue("9000");
	        result.addParam(opstatus);
	        
	        Param errorMessage = new Param();
	        errorMessage.setName("errorMessage");
	        errorMessage.setValue("APIKey not found in server properties");
	        result.addParam(errorMessage);
	        
	        logger.debug("Exiting out of execute method in HeaderParametersProcessor");
	        
	        headerParametersProcessorResponse = false;
	      }
	    
	    request.addRequestParam_("apikey", apiKey);
	    request.addRequestParam_("Accept", "application/json");
	    request.addRequestParam_("Content-Type", "application/json");

	    logger.debug("Exiting out of execute method in HeaderParametersProcessor");
	    
		return headerParametersProcessorResponse;
	}

}
