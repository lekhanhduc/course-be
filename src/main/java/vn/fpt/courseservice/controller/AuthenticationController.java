package vn.fpt.courseservice.controller;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.courseservice.dto.request.LoginRequest;
import vn.fpt.courseservice.dto.request.RefreshTokenRequest;
import vn.fpt.courseservice.dto.request.VerifyTokenRequest;
import vn.fpt.courseservice.dto.response.LoginResponse;
import vn.fpt.courseservice.dto.response.RefreshTokenResponse;
import vn.fpt.courseservice.dto.response.VerifyTokenResponse;
import vn.fpt.courseservice.service.AuthenticationService;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws JOSEException {
        var result = authenticationService.login(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authToken) throws ParseException {
        String token = authToken.replace("Bearer ", "");
        authenticationService.logout(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws JOSEException, ParseException {
        var result = authenticationService.refreshToken(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/verify-token")
    public ResponseEntity<VerifyTokenResponse> verifyToken(@RequestBody VerifyTokenRequest request) throws JOSEException, ParseException {
        var result = authenticationService.verifyToken(request);
        return ResponseEntity.ok(result);
    }

}
