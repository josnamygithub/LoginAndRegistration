package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bean.Userdetails;

public interface UserRepository extends JpaRepository<Userdetails,Integer>{


		boolean existsByEmailAndPassword(String email, String password);

		Userdetails findOneByEmailIgnoreCaseAndPassword(String email, String password);
	}




