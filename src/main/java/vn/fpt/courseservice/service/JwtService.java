package vn.fpt.courseservice.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.fpt.courseservice.model.User;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String generateAccessToken(User user) throws JOSEException {
        // Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Payload (Nội dung token)
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(30, ChronoUnit.MINUTES).toEpochMilli()))
                .claim("roles", "USER")
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        // Sign
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(secretKey));

        return jwsObject.serialize();
    }

    public String generateRefreshToken(User user) throws JOSEException {
        // Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Payload (Nội dung token)
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(14, ChronoUnit.DAYS).toEpochMilli()))
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        // Sign
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(secretKey));

        return jwsObject.serialize();
    }

}
