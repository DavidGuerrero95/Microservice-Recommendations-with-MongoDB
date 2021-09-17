package com.appcity.app.recomendaciones.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.appcity.app.recomendaciones.models.Muro;

@FeignClient(name = "app-muro")
public interface MuroFeignClient {

	@GetMapping("/muros/listar")
	public List<Muro> getMuros();

}
