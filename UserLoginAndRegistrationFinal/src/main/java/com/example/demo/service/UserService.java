package com.example.demo.service;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.bean.Userdetails;
import com.example.demo.common.APIResponse;
import com.example.demo.controller.AddResponse;
import com.example.demo.dto.LoginRequestDto;
import com.example.exception.UserNotFoundException;

public interface UserService {
	List<Userdetails> getALLusers();
    Userdetails getById(int id) throws UserNotFoundException;
    List<Userdetails> getUserId(Iterable<Integer> userid) throws UserNotFoundException;
    Userdetails getUser(int userId, String password) throws UserNotFoundException;

	public APIResponse login(LoginRequestDto loginRequestDto) throws UserNotFoundException;

    AddResponse addUser(Userdetails userdetails);
    Userdetails updateuser(Userdetails userdetails);
    AddResponse deleteuser(int id);
//	String sendEmail(AddResponse addedUser);
	String sendEmail(@Valid Userdetails userdetails);
	
}
