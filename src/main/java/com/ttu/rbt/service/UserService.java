package com.ttu.rbt.service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.annotation.processing.Filer;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ttu.rbt.entity.User;
import com.ttu.rbt.pojo.LoginPojo;
import com.ttu.rbt.pojo.UserPojo;
import com.ttu.rbt.pojo.UserResponsePojo;
import com.ttu.rbt.repo.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserService {

	private static String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";

	@Autowired
	public UserRepository userRepository;

	public UserResponsePojo addUser(UserPojo userData) {

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encoded = bCryptPasswordEncoder.encode(userData.getPassword());

		UUID uuid = UUID.randomUUID();
		//System.out.println(uuid);
		User user = new User(uuid.toString(), userData.getFullName(), userData.getMailId(), encoded,
				userData.getPermissions(), false);
		User u = userRepository.save(user);

		String jwt = createJWT(u.getId().toString(), "TTI", "admin", 300000, u.getUuid());

		UserResponsePojo userResponse = new UserResponsePojo(u, jwt);

		return userResponse;

	}

	public UserResponsePojo login(LoginPojo loginDetails) {
		User user = userRepository.findByMailId(loginDetails.getMailId());

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		Boolean match = bCryptPasswordEncoder.matches(loginDetails.getPassword(), user.getPassword());

		if (match.equals(true)) {
			String jwt = createJWT(user.getId().toString(), "TTI", "admin", 300000, user.getUuid());

			UserResponsePojo userResponse = new UserResponsePojo(user, jwt);

			return userResponse;
		}

		return null;
	}

	public User updateUser(UserPojo userData) {

		User user = userRepository.findByUuid(userData.getUuid());

		user.setFullName(userData.getFullName());
		user.setMailId(userData.getMailId());
		user.setPassword(user.getPassword());
		user.setPermissions(userData.getPermissions());
		user.setFirstLogin(false);

		return userRepository.save(user);
	}

	public void updateUserPassword(String uuid, String password) {
		User user = userRepository.findByUuid(uuid);

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encoded = bCryptPasswordEncoder.encode(password);
		user.setPassword(encoded);

		userRepository.save(user);

	}

	public static String createJWT(String id, String issuer, String subject, long ttlMillis, String key) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
				.signWith(signatureAlgorithm, signingKey);

		// if it has been specified, let's add the expiration
		if (ttlMillis > 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	public static Claims decodeJWT(String jwt, String key) {

		// This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(key)).parseClaimsJws(jwt)
				.getBody();
		return claims;
	}
}
