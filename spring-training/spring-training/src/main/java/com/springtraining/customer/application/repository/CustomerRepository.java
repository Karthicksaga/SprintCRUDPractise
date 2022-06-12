package com.springtraining.customer.application.repository;
import java.sql.Connection;
//import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springtraining.customer.DTO.Result;


@Repository
public class CustomerRepository {
	
	
	private JdbcTemplate jdbcTemplate;
	
	
	public CustomerRepository(DataSource dataSource)
	{
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Map<String , Object> fetchCustomer(Integer customerId) throws SQLException
	{
		System.out.println(customerId);
		Map<String ,Object> result = new HashMap<String,Object>();
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try
		{
			connect = jdbcTemplate.getDataSource().getConnection();
			String query= "Select * from tbl_customer where ID = ?";
			preparedStatement = connect.prepareStatement(query);
			preparedStatement.setInt(1, customerId);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				Integer id = resultSet.getInt("id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String mobileNo = resultSet.getString("mobile_number");
				String city = resultSet.getString("city");
				
				result.put("id" ,id);
				result.put("firstName" , firstName);
				result.put("lastName" , lastName);
				result.put("mobileNo" , mobileNo);
				result.put("city",city);
				
				System.out.println("Id:"+ id + " " + "firstname :" + firstName +"" + "lastname" + lastName);
			}
			
		}
		catch(SQLException e)
		{
				e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if (connect != null)
			{
				connect.close();
				preparedStatement.close();
			}
						
		}
		
		return result;
	}


public List<Map<String , Object>> fetchAllCustomer() throws SQLException
	{
		
		List<Map<String, Object>> resultAllList = new ArrayList<Map<String, Object>>();
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		
		try
		{
			connect = jdbcTemplate.getDataSource().getConnection();
			String query= "Select * from tbl_customer";
			preparedStatement = connect.prepareStatement(query);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			
			while(resultSet.next())
			{
				Map<String, Object> result = new HashMap<String, Object>();
				Integer id = resultSet.getInt("id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String mobileNo = resultSet.getString("mobile_number");
				String city = resultSet.getString("city");
				
				result.put("id" ,id);
				result.put("firstName",firstName);
				result.put("lastName" , lastName);
				result.put("mobileNo" , mobileNo);
				result.put("city",city);
				System.out.println("Id:"+ id + " " + "firstname :" + firstName +"" + "lastname" + lastName);
				resultAllList.add(result);
			}
			
			
		}
		catch(SQLException e)
		{
				e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(connect != null)
			{
				connect.close();
				preparedStatement.close();
			}
		}
	
		return resultAllList;
	}



	
	public Result createCustomer(Map<String ,Object> customerFile) throws SQLException
	{
		//String response = "";
		
		Result result = new Result();
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try
		{
			Integer id = (Integer)customerFile.get("id");
			String firstName = customerFile.get("firstname").toString();
			String lastName =  customerFile.get("lastname").toString();
			String mobileNo = customerFile.get("mobileno").toString();
			String city = customerFile.get("city").toString();
			
			if(firstName != null && lastName != null && city !=null && mobileNo != null)
			{
			
				conn = jdbcTemplate.getDataSource().getConnection();	
				String query = "insert into tbl_customer values (?,?,?,?,?)";
				
				preparedStatement = conn.prepareStatement(query);
				preparedStatement.setInt(1,id);
				preparedStatement.setString(2, firstName);
				preparedStatement.setString(3 , lastName);
				preparedStatement.setString(4 , mobileNo);
				preparedStatement.setString(5 , city);
				
				int affectedRow = preparedStatement.executeUpdate();
				if (affectedRow == 1)
				{
					result.setCode(201);
					result.setStatus(HttpStatus.CREATED); //set proper status
					result.setSuccess(true);
					result.setMessage("Successfully created customer in Database");
					
				}
				else
				{
					result.setCode(0);
					result.setStatus(HttpStatus.BAD_REQUEST); //set proper status
					result.setSuccess(false);
					result.setMessage("Failed to create customer in Database");
				}
			}
			else
			{
				result.setCode(0);
				result.setStatus(HttpStatus.BAD_REQUEST);
				result.setSuccess(false);
				result.setMessage("Invalid request");
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			result.setCode(201);
			result.setStatus(HttpStatus.CREATED); //set proper status
			result.setSuccess(true);
			result.setMessage("Successfully created customer in Database");
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			result.setCode(201);
			result.setStatus(HttpStatus.CREATED); //set proper status
			result.setSuccess(true);
			result.setMessage("Successfully created customer in Database");
		}
		finally
		{
			if(conn!= null)
			{
				conn.close();
			
			}
			if (preparedStatement != null)
			{
				preparedStatement.close();
			}
		}
		return result;
	}

	
	public String updateCustomer(Integer customerId, Map<String, Object> updateResult) throws SQLException {
		String response = "";
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		int updated = 0;
		try
		{
			
			String mobileno = updateResult.get("mobileno").toString();
			String city = updateResult.get("city").toString();
			if (mobileno !=null && city != null)
			{
				conn = jdbcTemplate.getDataSource().getConnection();
				String query = "Update tbl_customer set mobile_number= ? , city = ? where Id = ?";
				preparedStatement = conn.prepareStatement(query);
				preparedStatement.setInt(3, customerId);
				preparedStatement.setString(1 , mobileno);
				preparedStatement.setString(2 , city);
				
				updated = preparedStatement.executeUpdate();
				if(updated == 1)
				{
					response = "Data Updated Successfully";
					
				}
				else
				{
					response = "Data Updation Failed";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (conn != null)
			{
				conn.close();
				
			}
			if(preparedStatement != null)
			{
				preparedStatement.close();
			}
		}
		System.out.println(response);
		return response;
	}
	
	
	public String createAllCustomer(List<Map<String , Object>> jsonMultipleCustomer) throws SQLException
	{
		//String setUpdate = "";
		String response = "";
		Connection connection = null;
		PreparedStatement prep = null;
		try
		{
			connection = jdbcTemplate.getDataSource().getConnection();
			
			for(Map<String,Object> currentCustomer : jsonMultipleCustomer)
			{
				Integer id = (Integer)currentCustomer.get("id");
				String firstName = currentCustomer.get("firstname").toString();
				String lastName = currentCustomer.get("lastname").toString();
				String mobileNo = currentCustomer.get("mobileo").toString();
				String city = currentCustomer.get("city").toString();	
				String query = "insert into tbl_customer values (?,?,?,?,?)";
				prep.setInt(1, id);
				prep.setString(2 , firstName);
				prep.setString(3 ,lastName);
				prep.setString(4 , mobileNo);
				prep.setString(5 ,city);
				int result = prep.executeUpdate();
				if (result == 1)
				{
					response = "Inserted Successfully";
				}
				else
				{
					response = "Not Inserted";
				}
			}
	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
		if (connection != null)
		{
			connection.close();
		}

			if (prep != null)
			{
			prep.close();
			}
		}
		
		return response;
	}
}
	
