package com.codevalor.demo.freeads;

import com.codevalor.demo.freeads.model.User;
import com.codevalor.demo.freeads.model.UserRole;
import com.codevalor.demo.freeads.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoFreeadsApplication {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(DemoFreeadsApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return (args) -> {
			userRepository.save(User.builder().id(1L).email("poojitha@gmail.com").password(passwordEncoder.encode("123")).contact("0762873649").firstName("Poojitha").lastName("Irosha").role(UserRole.ROLE_USER).build());
		};
	}
}
