package example.tonipl.jwt;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
public class Authentication {

    @PostMapping(value = "/auth")
    @ResponseBody
    public void authorize(HttpServletResponse response, @RequestParam(name = "user") String userId,
	    @RequestParam(name = "password") String password) {

	if (isUserAuthenticated(userId, password)) {
	    String thirdPartyId = getDataFromThirdPartyService(userId);
	    setJWTCookie(userId, thirdPartyId, response);
	}
    }

    private String getDataFromThirdPartyService(String userId) {
	// TODO
	return "EXTERNAL_ID";
    }

    private boolean isUserAuthenticated(final String userId, final String password) {
	// TODO Authentication logic
	return true;
    }

    private void setJWTCookie(final String userId, final String thirdPartyId, final HttpServletResponse response) {
	final String jwt = generateToken(userId, thirdPartyId);
	response.addCookie(new Cookie("access_token", jwt));
    }

    private String generateToken(final String userId, final String thirdPartyId) {
	return Jwts.builder().setSubject(userId).claim("externalServiceId", thirdPartyId).setIssuedAt(new Date())
		.signWith(SignatureAlgorithm.HS512, Secret.getKey()).compact();

    }
}