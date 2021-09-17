package com.appcity.app.recomendaciones.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.appcity.app.recomendaciones.models.Proyectos;

@FeignClient(name = "app-proyectos")
public interface ProyectoFeignClient {

	@GetMapping("/proyectos/listar")
	public List<Proyectos> getProyectos();

	@GetMapping("/proyectos/listarByMuro/{codigo}")
	public List<Proyectos> getProyecyosByMuro(@PathVariable("codigo") Integer codigo);

}
