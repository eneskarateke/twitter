package com.workintech.twitter;

import com.workintech.twitter.entity.Role;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.RoleRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TwitterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterApplication.class, args);
	}


	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository,
						  PasswordEncoder passwordEncoder) {
		return args -> {

			if(roleRepository.findByAuthority("ADMIN").isPresent()){
				return;
			}

			Role adminRole = new Role();
			adminRole.setAuthority("ADMIN");

			Role userRole = new Role();
			userRole.setAuthority("USER");

			roleRepository.save(adminRole);
			roleRepository.save(userRole);
			Set<Role> roleSet = new HashSet<>();
			roleSet.add(adminRole);

			User admin = new User();
			admin.setEmail("ek@test.com");
			admin.setPassword(passwordEncoder.encode("1234"));
			admin.setAuthorities(roleSet);
			userRepository.save(admin);

		};
	}

}
