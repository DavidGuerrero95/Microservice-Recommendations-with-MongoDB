package com.appcity.app.recomendaciones.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.appcity.app.recomendaciones.models.Usuario;

@FeignClient(name = "app-usuarios")
public interface UsuarioFeignClient {

	@GetMapping("/users/encontrarUsuario/{usuario}")
	public Usuario encontrarUsuario(@PathVariable String usuario);
	
}
