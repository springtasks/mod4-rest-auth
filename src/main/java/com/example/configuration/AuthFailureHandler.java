package com.example.configuration;

import com.example.model.User;
import com.example.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public static final int MAX_ATTEMPTS = 3;

    UserService userService;
    @Autowired
    public AuthFailureHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String inputEmail = request.getParameter("email");
        Optional<User> userFromDb = userService.findByEmailAddress(inputEmail);
        if (userFromDb.isPresent()) {
            User user = userFromDb.get();
            if (!user.isLocked() && user.getFailureCounter() < MAX_ATTEMPTS) {
                user.setFailureCounter(user.getFailureCounter() + 1);
                userService.save(user);
                exception = new LockedException("3 Invalid Attempts ");
            }
            else if (!user.isLocked() && user.getFailureCounter() == MAX_ATTEMPTS) {
                user.setLocked(true);
                user.setLockDate(LocalDateTime.now());
                userService.save(user);
            }
            else if (user.isLocked()) {
                userService.resetCounterIfExpired(user); // Reset counter only if locked time has expired 10 sec before
            }
        }
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
