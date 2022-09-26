package edu.miu.cs544.gateway.web.auth;

import edu.miu.cs544.gateway.api.Security;
import edu.miu.cs544.gateway.api.auth.Users;
import edu.miu.cs544.gateway.configuration.auth.JwtTokenUtil;
import edu.miu.cs544.gateway.domain.auth.LocalUser;
import edu.miu.cs544.gateway.dto.auth.ClientDto;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final Security security;
    private final JwtTokenUtil jwtTokenUtil;

    private final Users users;

    private final LocalUserMapper userMapper;


    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@Valid @RequestBody JwtRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception e) {
            throw new AccessDeniedException("Can not authenticate", e);
        }

        UserDetails userDetails = security.loadUserByUsername(request.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(jwtTokenUtil.generateToken(userDetails)));
    }

    @PostMapping(value = "/auth/sign-up")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody SignUpRequest request) {
        LocalUser user = users.createUser(request);
        log.debug("user created");
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(user));
    }

    @GetMapping(value = "/profile/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ClientDto> getProfile(@PathVariable UUID id) {
        ClientDto clientDto = users.getClient(id);
        return ResponseEntity.status(HttpStatus.OK).body(clientDto);
    }

    @PutMapping(value = "/profile/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ClientDto> updateProfile(@PathVariable UUID id, @Valid @RequestBody ClientDto clientDto) {
        ClientDto updatedClient = users.updateClient(id, clientDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedClient);
    }

    @DeleteMapping(value = "/profile/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProfile(@PathVariable UUID id) {
        users.deleteClient(id);
        return ResponseEntity.status(HttpStatus.OK).body("Account with id: "+id+" is deleted successfully");
    }

    @GetMapping(value = "/profile/me")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    public ResponseEntity<ClientDto> getMyProfile() {
        ClientDto clientDto = users.getCurrentProfile();
        return ResponseEntity.status(HttpStatus.OK).body(clientDto);
    }

    @PutMapping(value = "/profile/me")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    public ResponseEntity<ClientDto> updateMyProfile(@Valid @RequestBody ClientDto clientDto) {
        ClientDto updatedClient = users.updateCurrentProfile(clientDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedClient);
    }

    @DeleteMapping(value = "/profile/me")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    public ResponseEntity<String> deleteMyProfile() {
        users.deleteCurrentProfile();
        return ResponseEntity.status(HttpStatus.OK).body("Your account is deleted successfully");
    }
}
