package com.codevalor.demo.freeads.util;

import com.codevalor.demo.freeads.model.User;
import com.codevalor.demo.freeads.model.UserRole;
import com.codevalor.demo.freeads.repository.UserRepository;
import com.codevalor.demo.freeads.service.UserDetailsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();

        String email = oidcUser.getEmail();
        String firstName = oidcUser.getGivenName();
        String lastName = oidcUser.getFullName().replace(firstName, "").trim();
        String externalId = oidcUser.getName();
        String picture = oidcUser.getPicture();

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;
        if (userOptional.isPresent()) {
                user = userOptional.get();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setProfileImg(picture);
                user.setExternalId(externalId);
            } else {
                user = User.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .profileImg(picture)
                        .role(UserRole.ROLE_USER)
                        .externalId(externalId).build();
        }
        userRepository.save(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.sendRedirect("http://localhost:3000/login/token/" + jwtUtil.generateToken(userDetailsService.loadUserByUsername(email)));
    }
}
