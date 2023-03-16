package com.formacionbdi.springboot.app.oauth.services.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.formacionbdi.springboot.app.commons.usuarios.models.entity.Usuario;
import com.formacionbdi.springboot.app.oauth.services.IUsuarioService;

import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {

		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}

		UserDetails user = (UserDetails) authentication.getPrincipal();
		String mensaje = "Success login:" + user.getUsername();

		System.out.println(mensaje);
		log.info(mensaje);
		
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		
		if (usuario.getIntentosLogin() != null && usuario.getIntentosLogin() > 0) {
			usuario.setIntentosLogin(0);
			usuarioService.update(usuario, usuario.getId());
		}
		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje = "Error login:" + exception.getMessage();
		System.out.println(mensaje);
		log.error(mensaje);

		try {
			Usuario usuario = usuarioService.findByUsername(authentication.getName());

			if (usuario.getIntentosLogin() == null) {
				usuario.setIntentosLogin(0);
			}
			
			log.info("Intentos actual es de: "+ usuario.getIntentosLogin());
			usuario.setIntentosLogin(usuario.getIntentosLogin() + 1);
			log.info("Intentos despues es de: "+ usuario.getIntentosLogin());
			
			if (usuario.getIntentosLogin() >= 3) {
				log.error(String.format("El usuario %s deshabilitado por maximos intentos", usuario.getUsername()));
				usuario.setEnabled(false);
			}
			
			usuarioService.update(usuario, usuario.getId());

		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
	}

}
