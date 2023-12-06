package com.example.demo;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.bean.Userdetails;
import com.example.demo.controller.AddResponse;
import com.example.demo.repositories.UserRepository;
//import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import com.example.exception.UserNotFoundException;

@SpringBootTest(classes= {ServerMockitoTest.class})
public class ServerMockitoTest {

	@Mock
	UserRepository userRepo;
	
	@InjectMocks
	 UserServiceImpl userService;
	
	
	@Test
	@Order(1)
	public void test_getAllUsers() {
		List<Userdetails> userdetails = new ArrayList<Userdetails>();
		userdetails.add(new Userdetails(1,101,"jojo","josnajosna00123@gmail.com","josna","jo", null, null, null, null));
		userdetails.add(new Userdetails(2,120,"jojo","nidhunkumar@gmail.com","nidhun","josna", null, null, null, null));
		when(userRepo.findAll()).thenReturn(userdetails);
		assertEquals(2,userService.getALLusers().size());
	}
	
	
		
	@Test
	@Order(2)
	
		public void test_getUser() throws UserNotFoundException {
		    List<Userdetails> userdetails = new ArrayList<>();
		    userdetails.add(new Userdetails(1, 101, "jojo", "josnajosna00123@gmail.com", "josna", "jo", null, null, null, null));
		    userdetails.add(new Userdetails(2, 120, "jo1234@jo", "nidhunkumar@gmail.com", "nidhun", "josna", null, null, null, null));

		    int userId = 120;
		    String userPassword = "jo1234@jo";

		    when(userRepo.findAll()).thenReturn(userdetails);

		    Userdetails result = userService.getUser(userId, userPassword);

		    assertNotNull(result);
		    assertEquals(userId, result.getUserid());
		    assertEquals(userPassword, result.getPassword());
		}
	@Test
	@Order(3)
	public void test_addUser() {
		Userdetails userDetails = new Userdetails(3, 452, "Saleem@123", "josnasaleem00123@gmail.com", "josna", "saleem", null, null, null, null);
	
        when(userRepo.save(userDetails)).thenReturn(userDetails);

        AddResponse expectedResponse = new AddResponse();
        	expectedResponse.setMsg("Successfully Registered ");
        	expectedResponse.setId(1); // Assuming an ID is generated and set after saving
    
        	AddResponse actualResponse = userService.addUser(userDetails);
    
        	assertEquals(expectedResponse.getId(), actualResponse.getId());
        	assertEquals(expectedResponse.getMsg(), actualResponse.getMsg());
}
	
	
	
	
}
