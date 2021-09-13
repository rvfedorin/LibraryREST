package org.frv.controller;

import org.frv.dto.AuthenticationRequestDto;
import org.frv.exceptions.ItemNotFound;
import org.frv.exceptions.UserNotFoundException;
import org.frv.model.User;
import org.frv.security.jwt.JwtTokenProvider;
import org.frv.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roman V.F.
 * Date: 20.08.2021
 */
@RestController
@RequestMapping("/auth/")
public class AuthenticationRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequestDto requestDto) {
        Map<String, String> response = new HashMap<>();
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            try {
                User user = userService.findUserByLogin(username);
                String token = jwtTokenProvider.createToken(username, user.getRoles());
                response.put("username", username);
                response.put("token", token);
            } catch (ItemNotFound ex) {
                throw new UserNotFoundException("User with username: " + username + " not found.");
            }

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        SecurityContextLogoutHandler contextLogoutHandler = new SecurityContextLogoutHandler();
        contextLogoutHandler.logout(httpRequest, httpResponse, null);
    }
}
