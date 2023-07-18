package com.codevalor.demo.freeads.service;

import com.codevalor.demo.freeads.dto.request.LoginRequest;
import com.codevalor.demo.freeads.dto.response.LoginResponse;
import com.codevalor.demo.freeads.model.User;
import com.codevalor.demo.freeads.repository.UserRepository;
import com.codevalor.demo.freeads.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(user.getExternalId() != null) {
            throw new RuntimeException("User is registered with social login, please login with social login");
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtil.generateToken(user, Map.of("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining())));
        return LoginResponse.builder().token(jwtToken).build();
    }

}
