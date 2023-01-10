package com.book;

import com.book.domain.PersistentUserEntity;
import com.book.enumconstant.UserType;
import com.book.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Component
	class BootStrap implements CommandLineRunner {

		@Autowired
		private UserRepository userRepository;

		@Autowired
		private PasswordEncoder passwordEncoder;

		@Override
		public void run(String... args) {

			log.info("---inside bootstrap----");
			createDefaultUsers();
		}

		void createDefaultUsers(){
			if (userRepository.findByUsername("dhirajbadu50@gmail.com").isEmpty()){
				userRepository.saveAndFlush(new PersistentUserEntity("dhirajbadu50@gmail.com", passwordEncoder.encode("dhirajbadu50@gmail.com"), true, false, false, false, UserType.SUPER_ADMIN));
			}
		}

	}

	@Component
	@Profile("dev")
	class BootStrapDev implements CommandLineRunner {

		private final UserRepository userRepository;

		public BootStrapDev(UserRepository userRepository) {
			this.userRepository = userRepository;
		}

		@Override
		public void run(String... args) {

			log.info("---inside bootstrap dev----");


		}
	}

}
