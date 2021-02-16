package com.service;

import com.config.UserCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final static String GET_AUTH_USER_URL = "http://user-service/users?identifier=";

    private final RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ResponseEntity<UserCredentials> entity = getAuthUserEntityByUsername(username);

        UserCredentials authUser = entity.getBody();

        if(isNull(authUser)) {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }

        if(authUser.getIdentifier().equals(username)) {

            List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList(authUser.getRoles());

            /*
               The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
               And used by auth manager to verify and check user authentication.
             */
            return new User(authUser.getIdentifier(), authUser.getPassword(), grantedAuthorities);
        }

        throw new UsernameNotFoundException("Username: " + username + " not found");
    }

    public ResponseEntity<UserCredentials> getAuthUserEntityByUsername(String username) {
        return restTemplate.getForEntity(GET_AUTH_USER_URL + username, UserCredentials.class);
    }
}
