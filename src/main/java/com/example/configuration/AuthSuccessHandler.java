package com.example.configuration;

import com.example.model.User;
import com.example.service.AuthUserDetails;
import com.example.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;

    @Autowired
    public AuthSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        if (user.getFailureCounter() > 0) {
            userService.resetInvalidAttemptsCounter(user.getEmailAddress());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
