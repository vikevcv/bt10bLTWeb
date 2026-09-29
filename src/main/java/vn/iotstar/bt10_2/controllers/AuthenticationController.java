package vn.iotstar.bt10_2.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.bt10_2.entity.User;
import vn.iotstar.bt10_2.models.LoginResponse;
import vn.iotstar.bt10_2.models.LoginUserModel;
import vn.iotstar.bt10_2.models.RegisterUserModel;
import vn.iotstar.bt10_2.services.AuthenticationService;
import vn.iotstar.bt10_2.services.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<User> register(@RequestBody RegisterUserModel registerUser) {
        User registeredUser = authenticationService.signup(registerUser);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserModel loginUser) throws Exception {
        User authenticatedUser = authenticationService.authenticate(loginUser);
        
        // throws Exception (JOSEException) sẽ được ném ra từ đây nếu mã hóa lỗi
        String jwtToken = jwtService.generateToken(authenticatedUser);
        
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        
        return ResponseEntity.ok(loginResponse);
    }
}