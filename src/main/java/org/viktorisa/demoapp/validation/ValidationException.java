package org.viktorisa.demoapp.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValidationException extends Exception {
	
	Map<String,String> message;
	
	public ValidationException(Map<String,String> message) {
		this.message = message;
	}
	
	public List<String> getValues() {
		return new ArrayList<String>(message.values());
	}

}
