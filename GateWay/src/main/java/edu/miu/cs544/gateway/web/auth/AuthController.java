package edu.miu.cs544.gateway.web.auth;

import edu.miu.cs544.gateway.api.Security;
import edu.miu.cs544.gateway.api.auth.Users;
import edu.miu.cs544.gateway.configuration.auth.JwtTokenUtil;
import edu.miu.cs544.gateway.domain.auth.LocalUser;
import edu.miu.cs544.gateway.dto.auth.UserDto;
import edu.miu.cs544.gateway.mapper.LocalUserMapper;
import edu.miu.cs544.gateway.rest.request.JwtRequest;
import edu.miu.cs544.gateway.rest.request.SignUpRequest;
import edu.miu.cs544.gateway.rest.response.JwtResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final Security security;
    private final JwtTokenUtil jwtTokenUtil;

    private final Users users;

    private final LocalUserMapper userMapper;

    @PostMapping
    public ResponseEntity<JwtResponse> createAuthenticationToken(@Valid @RequestBody JwtRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception e) {
            throw new AccessDeniedException("Can not authenticate", e);
        }

        UserDetails userDetails = security.loadUserByUsername(request.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(jwtTokenUtil.generateToken(userDetails)));
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody SignUpRequest request) {
        LocalUser user = users.createUser(request);
        log.debug("user created");
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(user));
    }
}
