package vn.fpt.courseservice.controller;

import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.fpt.courseservice.dto.request.LoginRequest;
import vn.fpt.courseservice.dto.response.LoginResponse;
import vn.fpt.courseservice.service.AuthenticationService;

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

}
