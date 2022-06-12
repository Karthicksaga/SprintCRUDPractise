package com.springtraining.customer.application.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.sql.SQLException;
//import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.web.bind.annotation.PathVariable;
import com.springtraining.customer.application.service.CustomerService;
//import com.springtraining.customer.application.service.CustomerService;
import com.springtraining.customer.DTO.Result;
@Controller
@Component
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/api/customer")
	public ResponseEntity<String> createCustomer(@RequestBody  String customerFile) throws SQLException, JsonParseException, JsonMappingException, IOException
	{
		Result resultCreate = customerService.createCustomer(customerFile);
		
		HttpStatus status = resultCreate.getStatus();
		
		ObjectMapper objectaMapper = new ObjectMapper();
		String resultJson = objectaMapper.writeValueAsString(resultCreate);
		return new ResponseEntity<String>(resultJson,null ,  status);
		
	}
	
	@RequestMapping(value = "api/customer" , method = RequestMethod.GET)
	public ResponseEntity<String> fetchAllCustomer() throws SQLException
	{
		List<Map<String, Object>> resultAllList = customerService.fetchAllCustomer();
		return new ResponseEntity<String>(resultAllList.toString() ,null, HttpStatus.OK);
	}
	
	
//
	@RequestMapping(value  = "api/customer/{customerId}"  , method = RequestMethod.GET)
	public ResponseEntity<String> fetchCustomer(@PathVariable(value = "customerId") Integer customerId) throws SQLException
	{
		System.out.println(customerId);
		
		Map<String ,Object> result = customerService.fetchCustomer(customerId);
		return new ResponseEntity<String>(result.toString(), null, HttpStatus.OK);
	}
	
	@PutMapping("/api/customer/{customerId}")
	public ResponseEntity<String> updateCustomer(@PathVariable(value = "customerId") Integer customerId ,@RequestBody String jsonUpdate)
	{
		String result = "";
		try {
			result = customerService.updateCustomer(customerId ,jsonUpdate);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(result, null, HttpStatus.OK);
	}
	
	
	
	  @PostMapping("/api/customers") 
	  public ResponseEntity<String> createMultipleCustomer(@RequestBody String jsonMultipleCustomer) throws JsonParseException, JsonMappingException, IOException 
	  { 	
		  String resultCreateAll = "";
		   resultCreateAll = customerService.createAllCustomer(jsonMultipleCustomer);
		  return new ResponseEntity<String>(resultCreateAll ,null , HttpStatus.OK);
	  
	  
	  }
	 
	
	
}
