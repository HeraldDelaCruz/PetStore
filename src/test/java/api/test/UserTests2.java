package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints2;
import api.payloads.User;
import io.restassured.response.Response;

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setupData()
	{
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logger
		
		logger = LogManager.getLogger(this.getClass());
	
		logger.debug("debugging..");
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("***** CREATE USER *****");
		Response response = UserEndPoints2.createUser(userPayload);
		response.then().log().all();
		logger.info(userPayload);
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("***** USER IS CREATED *****");
	}
	
	
	@Test(priority=2)
	public void testGetUserByName() 
	{
		logger.info("***** READING  USER *****");
		Response response = UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		System.out.println(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("***** USER INFO *****");
	}
	
	
	@Test(priority=3)
	public void testUpdateUserByName()
	{
		logger.info("***** UPDATING USER *****");
		//update data using payload
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		
		Response response = UserEndPoints2.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().body();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("***** USER UPDATED *****");
		//check data after update
		Response responseAfterUpdate = UserEndPoints2.readUser(this.userPayload.getUsername());
		System.out.println(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);	
	
	}
	
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		logger.info("***** DELETING USER *****");
		Response response = UserEndPoints2.deleteUser(this.userPayload.getUsername());
		System.out.println(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);	
	
		logger.info("***** USER DELETED *****");
	}
	
	
	
}
