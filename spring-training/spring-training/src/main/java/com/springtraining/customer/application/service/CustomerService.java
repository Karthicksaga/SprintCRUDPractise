package com.springtraining.customer.application.service;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.springtraining.customer.application.repository.CustomerRepository;
//import com.sun.tools.javac.main.Main.Result;
import com.springtraining.customer.DTO.Result;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	public Map <String ,Object> fetchCustomer(Integer customerId) throws SQLException
	{
		System.out.println("Customer id in Service:" +customerId );
		Map<String,Object> result = customerRepository.fetchCustomer(customerId);;
		return result;
		
	}
	public List<Map <String ,Object>> fetchAllCustomer() throws SQLException
	{
		List<Map<String,Object>> resultAllList = customerRepository.fetchAllCustomer();
		return resultAllList;
		
	}
	
	
	public Result createCustomer(String customerFile) throws SQLException, JsonParseException, JsonMappingException, IOException
	{
		Result response = new Result();
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String ,Object> resultPassing = objectMapper.readValue(customerFile, new TypeReference<Map<String,Object>>() {});
		//Map<String, Object> resultCreate = new HashMap<String, Object>();
		try
		{
			System.out.println(resultPassing);
		 
		 response = customerRepository.createCustomer(resultPassing);
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return response;
	}
	public String updateCustomer(Integer customerId, String jsonUpdate) throws JsonParseException, JsonMappingException, IOException {
		String response = "";
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String , Object>  updateResult = objectMapper.readValue(jsonUpdate , new TypeReference<Map<String,Object>>(){});
		try 
		{
			response = customerRepository.updateCustomer(customerId , updateResult);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return response;
	}
	
	
	
	  
	  
	  @PostMapping("/api/cutomers")
	  public String createAllCustomer(String JsonMultipleCustomer) throws JsonParseException, JsonMappingException, IOException
	  {
		  String response = "";
		  ObjectMapper objectMapper = new ObjectMapper();
		  List<Map<String , Object>> resultCustomer = objectMapper.readValue(JsonMultipleCustomer , new TypeReference<List<Map<String ,Object>>>() {});
		  try
		  {
			  response = customerRepository.createAllCustomer(resultCustomer);
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  return response;
	  }
	 

}




