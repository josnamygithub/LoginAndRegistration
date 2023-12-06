package com.example.demo.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.bean.Userdetails;
import com.example.demo.common.APIResponse;
import com.example.demo.controller.AddResponse;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.repositories.UserRepository;
import com.example.demo.util.jwtUtils;
import com.example.exception.DuplicateEntryException;
import com.example.exception.UserNotFoundException;

@Service
public class UserServiceImpl implements UserService {

		@Autowired
		UserRepository userRepo;
			
		@Autowired
		JavaMailSender javaMailSender;
	    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

		public List<Userdetails> getALLusers() {
			
			return userRepo.findAll();
		}


		 public Userdetails getById(int id) throws UserNotFoundException {
		        Optional<Userdetails> userOptional = userRepo.findById(id);
		        if (userOptional.isPresent()) {
		            return userOptional.get();
		        } else {
		            throw new UserNotFoundException("User Not Found with this id: " + id);
		        }
		    }
		 
		
		 public List<Userdetails> getUserId (Iterable<Integer> userid) throws UserNotFoundException {
				
			 List<Userdetails> users = userRepo.findAllById(userid);
				if (users.isEmpty()) {
			        throw new UserNotFoundException("No users found with the provided IDs");
			    }
				return users;
	
		}
	 
	public Userdetails getUser (int userid, String password) throws UserNotFoundException {
			
			List<Userdetails> Userdetail =userRepo.findAll();
			Userdetails Userdet = null;
			for(Userdetails usd :Userdetail) {
				if(usd.getUserid()==userid&&usd.getPassword().equals(password))
					Userdet =usd;
			}
		    if (Userdet != null) {
		        return Userdet;
		    } else {
		        throw new UserNotFoundException("User not found with the provided credentials");
		    }
				
		}
		 
	public APIResponse login(LoginRequestDto loginRequestDto) throws UserNotFoundException{
		APIResponse apiResponse = new APIResponse();
	try {
		Userdetails user = userRepo.findOneByEmailIgnoreCaseAndPassword(loginRequestDto.getEmail(),loginRequestDto.getPassword());
	
		 	if(user==null) {
		 		apiResponse.setData("failed");
		 		return apiResponse;
		 		}
			 
			 String token =jwtUtils.generateJwt(user);
			 Map<String,Object> data = new Hashtable<>();
			 data.put("accessToken ",token);
			 
			 apiResponse.setData(data);
			 apiResponse.setMessage("Login successful and Token Generated");
			 throw new RuntimeException("hello");
			}catch(Exception e) {
			logger.error("An error occurred during login:{} ", e.getMessage());
		}
			 return apiResponse;

	}

		public  int getMaxId() {
			return userRepo.findAll().size()+1;
		}
		

		public AddResponse addUser(Userdetails userdetails) {
			if(userRepo.existsByEmailAndPassword(userdetails.getEmail(), userdetails.getPassword())) {
				 throw new DuplicateEntryException("User with Password combination already exists");
			}
			userdetails.setId(getMaxId());
			userRepo.save(userdetails);
			AddResponse res =new AddResponse();
			res.setMsg("Successfully Registered ");	
			res.setId(userdetails.getId());
			return res;
			}
		
		
		
		public Userdetails updateuser(Userdetails userdetails) {
			if(userRepo.existsByEmailAndPassword(userdetails.getEmail(), userdetails.getPassword())) {
				 throw new DuplicateEntryException("User with Password combination already exists");
			}
			userRepo.save(userdetails);
			return userdetails;
		}
		
		public AddResponse  deleteuser(int id) {
			userRepo.deleteById(id);
			AddResponse res = new AddResponse();
			res.setId(id);
			res.setMsg("Country deleted");
			return res;
			
		}
		

		public String sendEmail(Userdetails addedUser) {
		    try {
		        SimpleMailMessage message = new SimpleMailMessage();
		        message.setFrom("josnajavaintern@gmail.com");
		        message.setTo(addedUser.getEmail()); 
		        message.setSubject("Testing purpose only");
		        message.setText("Hello, " + addedUser.getFirstname() + " Registration Successfully Done (This is a test email.)");

		        javaMailSender.send(message);

		        return "Email sent Successfully to " + addedUser.getEmail();
		    } catch (Exception e) {
		        return "Failed to send email: " + e.getMessage();
		    }
		}


		


		

		

}
