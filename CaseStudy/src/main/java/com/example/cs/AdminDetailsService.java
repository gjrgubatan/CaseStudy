package com.example.cs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepo;
	
	@Override
	public AdminDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminRepo.findByEmail(username);
		if (admin == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new AdminDetails(admin);
	}

}
