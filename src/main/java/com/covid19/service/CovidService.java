package com.covid19.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CovidService {

	public boolean validateUser(String name, String password) {
		// TODO Auto-generated method stub
		return name.equalsIgnoreCase("test")
                && password.equalsIgnoreCase("test");
	}
}
