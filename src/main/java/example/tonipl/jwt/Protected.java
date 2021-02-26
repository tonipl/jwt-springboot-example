package example.tonipl.jwt;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Controller
public class Protected {

    @GetMapping(value = "/protected")
    @ResponseBody
    public String getProtectedResource(HttpServletRequest request) {

	if (!validateToken(request)) {
	    throw new ForbiddenException();
	}
	return "<h2>Welcome to the protected resource</h2><br/><hr/><p>Here is your token: " + retrieveJWT(request)
		+ "</p>";
    }

    private boolean validateToken(final HttpServletRequest request) {
	final String jwt = retrieveJWT(request);
	if (jwt == null) {
	    return false;
	}
	Claims claims = Jwts.parser().setSigningKey(Secret.getKey()).parseClaimsJws(jwt).getBody();

	return "EXTERNAL_ID".equals(claims.get("externalServiceId")) && "demo".equals(claims.getSubject());
    }

    private String retrieveJWT(HttpServletRequest request) {
	String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
	return StringUtils.substringAfter(authHeader, "Bearer ");
    }
}