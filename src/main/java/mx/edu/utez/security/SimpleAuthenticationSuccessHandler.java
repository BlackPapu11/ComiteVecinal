package mx.edu.utez.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		boolean hasAdministradorRole = false;
		boolean hasAdoptadorRole = false;
		boolean hasVoluntarioRole = false;

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROL_ADMINISTRADOR")) {
				hasAdministradorRole = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROL_ENLACE")) {
				hasAdoptadorRole = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROL_PRESIDENTE")) {
				hasVoluntarioRole = true;
				break;
			}
		}

		if (hasAdministradorRole) {
			redirectStrategy.sendRedirect(request, response, "/administrador/");
		} else if (hasAdoptadorRole) {
			redirectStrategy.sendRedirect(request, response, "/enlace/");
		} else if (hasVoluntarioRole) {
			redirectStrategy.sendRedirect(request, response, "/president/");
		} else {
			redirectStrategy.sendRedirect(request, response, "/login");
		}
	}

}