package vn.fpt.courseservice.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import vn.fpt.courseservice.dto.request.LoginRequest;
import vn.fpt.courseservice.dto.request.RefreshTokenRequest;
import vn.fpt.courseservice.dto.request.VerifyTokenRequest;
import vn.fpt.courseservice.dto.response.LoginResponse;
import vn.fpt.courseservice.dto.response.RefreshTokenResponse;
import vn.fpt.courseservice.dto.response.VerifyTokenResponse;
import vn.fpt.courseservice.exception.AppException;
import vn.fpt.courseservice.model.TokenInValid;
import vn.fpt.courseservice.model.User;
import vn.fpt.courseservice.repository.TokenInvalidRepository;
import vn.fpt.courseservice.repository.UserRepository;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final JwtService jwtService;
    private final TokenInvalidRepository tokenInvalidRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) throws JOSEException {
        String email = request.getEmail();
        String password = request.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        User user = (User) authenticate.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

    public void logout(String accessToken) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(accessToken);
        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();

        tokenInvalidRepository.findById(jwtId).
                orElseGet(() -> {
                    TokenInValid tokenInValid = TokenInValid.builder()
                            .jwtId(jwtId)
                            .build();
                    tokenInvalidRepository.save(tokenInValid);
                    return tokenInValid;
                });

        log.info("logout successfully");
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        String refreshToken = request.getRefreshToken();
        SignedJWT signedJWT = SignedJWT.parse(refreshToken);
        Date date = signedJWT.getJWTClaimsSet().getExpirationTime();
        if(date.before(new Date())) {
            throw new AppException("Token expired");
        }

        Long userId = Long.parseLong(signedJWT.getJWTClaimsSet().getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User not found"));

        String accessToken = jwtService.generateAccessToken(user);

        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public VerifyTokenResponse verifyToken(VerifyTokenRequest request) throws ParseException, JOSEException {
        String accessToken = request.getAccessToken();
        SignedJWT signedJWT = SignedJWT.parse(accessToken);
        Date date = signedJWT.getJWTClaimsSet().getExpirationTime();
        if(date.before(new Date())) {
            throw new AppException("Token expired");
        }

        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        Optional<TokenInValid> byId = tokenInvalidRepository.findById(jwtId);
        if(byId.isPresent()) {
            throw new AppException("Token is logged");
        }
        Long userId = Long.parseLong(signedJWT.getJWTClaimsSet().getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User not found"));

        boolean isValid = signedJWT.verify(new MACVerifier(secretKey));
        return VerifyTokenResponse.builder()
                .isValid(isValid)
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

}
