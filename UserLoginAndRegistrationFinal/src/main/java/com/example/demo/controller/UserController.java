package com.example.demo.controller;

import java.util.*;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.bean.Userdetails;
import com.example.demo.common.APIResponse;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.service.UserService;
import com.example.demo.util.jwtUtils;
import com.example.exception.DuplicateEntryException;
import com.example.exception.UserNotFoundException;

@RestController
public class UserController {
    
    @Autowired
    UserService userService;
    @Autowired
	private jwtUtils jwtUtils;
    
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    
    @GetMapping("/getusers")
    public List<Userdetails> getusers() {
        logger.debug("Fetching all users");

        return userService.getALLusers();
    }
    
    @GetMapping("/getusers/{id}")
    public ResponseEntity<Userdetails> getuserById(@PathVariable(value = "id") int id) throws UserNotFoundException {
      
    	logger.debug("Fetching user by ID: {}", id);
    	
   	return  ResponseEntity.ok(userService.getById(id));
    }
   
   
    
    @GetMapping("/getuser")
    public ResponseEntity<Userdetails> getUserfilter (
            @RequestParam(value = "userid") int userid,
            @RequestParam(value = "password") String password) throws UserNotFoundException{
        logger.debug("Fetching user with userid: {} and password: {}", userid, password);

        try {
            Userdetails userdetails = userService.getUser(userid, password);
            return new ResponseEntity<>(userdetails, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            logger.debug("User not found for userid: {} and password: {}", userid, password);

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/login") 
	public ResponseEntity<APIResponse> login(@RequestBody LoginRequestDto loginRequestDto) throws UserNotFoundException{
    	
        logger.debug("Attempting login for user with email: {}", loginRequestDto.getEmail());

		APIResponse apiResponse = userService.login(loginRequestDto);
        //logger.debug("Login response status: ", apiResponse.getStatus());

		return ResponseEntity
				.status(apiResponse.getStatus())
				.body(apiResponse);
		
	}
    
    @GetMapping("/privateapi")
	public ResponseEntity<APIResponse> privateApi(@RequestHeader(value ="authorization",defaultValue = "") String auth) throws Exception{
        logger.debug("Received request for private API with auth header: {}", auth);

		APIResponse apiResponse = new APIResponse();
		
		jwtUtils.verify(auth);
		apiResponse.setData("this is private api");
        logger.debug("Returning response for private API");

		return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
		
	}


    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@Valid @RequestBody Userdetails userdetails) {
       try {
    	AddResponse response = userService.addUser(userdetails);
        userService.sendEmail(userdetails); // Ensure you're sending the email with userdetails, not response
        logger.debug("User added successfully: {}", userdetails);

        return new ResponseEntity<>(response, HttpStatus.OK);
       }catch (DuplicateEntryException e) {
           logger.debug("Duplicate entry exception: {}", e.getMessage());
    	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }

    }

    
    @PutMapping("/updateuser/{id}")
    public ResponseEntity<?> updateCountry(
            @PathVariable(value = "id") int id,@Valid 
            @RequestBody Userdetails userdetails) {
        logger.debug("Received request to update user with ID: {}", id);

        try {
            Userdetails existUserdetails = userService.getById(id);
            existUserdetails.setUserid(userdetails.getUserid());
            existUserdetails.setPassword(userdetails.getPassword());
            existUserdetails.setEmail(userdetails.getEmail());
            existUserdetails.setFirstname(userdetails.getFirstname());
            existUserdetails.setLastname(userdetails.getLastname());
            Userdetails updatedUser = userService.updateuser(existUserdetails);
            logger.debug("User updated successfully: {}", updatedUser);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
       
        } catch (Exception e) {
        	
            logger.debug("Failed to update user: {}", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
//    @DeleteMapping("/deleteuser/{id}")
//    public ResponseEntity<AddResponse> deleteCountry(@PathVariable(value = "id") int id) throws UserNotFoundException{
//        AddResponse response = userService.deleteuser(id);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable(value = "id") int id) throws UserNotFoundException{ 
        logger.debug("Received request to delete user with ID: {}", id);

		AddResponse response = userService.deleteuser(id);

		logger.debug("User deleted successfully with ID: {}", id);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
}