package com.hospitalityhub.config;



import com.hospitalityhub.entity.User;
import com.hospitalityhub.exception.UserNotFoundException;
import com.hospitalityhub.repository.UserRepository;
import com.hospitalityhub.shared.ResponseMessageConstant;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Loads a user by the given email address, typically used for authentication.
     *
     * @param email The email address of the user to load.
     * @return A UserDetails object representing the loaded user.
     * @throws UsernameNotFoundException If the user with the specified email is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user =userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException(ResponseMessageConstant.USER_NOT_FOUND);
        }
        return new CustomUserDetails(user.get().getEmail(), user.get().getPassword(), user.get().getRole());
    }

}
