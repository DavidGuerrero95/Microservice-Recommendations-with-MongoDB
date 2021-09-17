package com.appcity.app.recomendaciones.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.appcity.app.recomendaciones.clients.MuroFeignClient;
import com.appcity.app.recomendaciones.clients.ProyectoFeignClient;
import com.appcity.app.recomendaciones.clients.UsuarioFeignClient;
import com.appcity.app.recomendaciones.models.Muro;
import com.appcity.app.recomendaciones.models.Proyectos;
import com.appcity.app.recomendaciones.models.Recomendaciones;
import com.appcity.app.recomendaciones.models.Usuario;
import com.appcity.app.recomendaciones.repository.RecomendacionesRepository;
import com.appcity.app.recomendaciones.services.IRecomendacionMath;

@RestController
public class RecomendacionesController {

	@Autowired
	RecomendacionesRepository recomendaciones;
	
	@Autowired
	MuroFeignClient muroFeignClient;
	
	@Autowired
	ProyectoFeignClient proyectoFeignClient;
	
	@Autowired
	UsuarioFeignClient usuarioFeignClient;
	
	@Autowired
	IRecomendacionMath iRecomendacion;

	@GetMapping("/recomendaciones/listarRecomendaciones")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Recomendaciones> listarRecomendaciones(){
		return recomendaciones.findAll();
	}
	
	@PostMapping("/recomendaciones/crear")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void crearRecomendacion(@RequestBody Usuario usuario) {
		try {
			Recomendaciones recomen = new Recomendaciones();
			recomen.setNombre(usuario.getUsername());
			recomen.setBusquedas(new ArrayList<String>());
			recomen.setIntereses(usuario.getIntereses());
			recomen.setUbicacion(usuario.getUbicacion());
			recomendaciones.save(recomen);
		} catch (Exception e) {
			System.out.println("Error --> " + e.getLocalizedMessage() + "Error -->" + e.getMessage());
		}

	}

	@DeleteMapping("/recomendaciones/eliminar/{nombre}")
	@ResponseStatus(code = HttpStatus.OK)
	public void eliminarRecomendacion(@PathVariable String nombre) {
		try {
			Recomendaciones recomen = recomendaciones.findByNombre(nombre);
			String id = recomen.getId();
			recomendaciones.deleteById(id);
		} catch (Exception e) {
			System.out.println("Error --> " + e.getLocalizedMessage() + "Error -->" + e.getMessage());
		}

	}

	@PutMapping("/recomendaciones/editarUbicacion/{nombre}")
	@ResponseStatus(code = HttpStatus.OK)
	public void editarUbicacion(@PathVariable String nombre, @RequestBody Usuario usuario) {
		try {
			Recomendaciones recomen = recomendaciones.findByNombre(nombre);
			recomen.setUbicacion(usuario.getUbicacion());
		} catch (Exception e) {
			System.out.println("Error --> " + e.getLocalizedMessage() + "Error -->" + e.getMessage());
		}
	}
	
	@GetMapping("/recomendaciones/ubicacionMuro/{nombre}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Muro> ubicacionMuro(@PathVariable String nombre){
		List<Muro> muro = muroFeignClient.getMuros();
		Usuario usuario = usuarioFeignClient.encontrarUsuario(nombre);
		List<Double> posDistancia = new ArrayList<Double>();
		List<Integer> posMenor = new ArrayList<Integer>();
		List<Muro> retorno = new ArrayList<Muro>();
		if(usuario.getUbicacion().size()>1) {
			for(int i=0; i<muro.size(); i++) {
				posDistancia.add(iRecomendacion.distanciaCoord(usuario.getUbicacion(), muro.get(i).getLocalizacion()));
			}
			
			for(int i=0; i<posDistancia.size();i++) {
				Double Menor = posDistancia.stream().min(Comparator.naturalOrder()).get();
				posMenor.add(posDistancia.indexOf(Menor));
				posDistancia.set(posDistancia.indexOf(Menor), 100000.0);
			}
			
			for(int i=0;i<posMenor.size();i++) {
				retorno.add(muro.get(posMenor.get(i)));
			}
			return retorno;
		}
		else {
			return muro;
		}
	}
	
	@GetMapping("/recomendaciones/ubicacionProyectos/{nombre}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Proyectos> ubicacionProyectos(@PathVariable String nombre){
		List<Proyectos> proyectos = proyectoFeignClient.getProyectos();
		Usuario usuario = usuarioFeignClient.encontrarUsuario(nombre);
		List<Double> posDistancia = new ArrayList<Double>();
		List<Integer> posMenor = new ArrayList<Integer>();
		List<Proyectos> retorno = new ArrayList<Proyectos>();
		if(usuario.getUbicacion().size()>1) {
			for(int i=0; i<proyectos.size(); i++) {
				posDistancia.add(iRecomendacion.distanciaCoord(usuario.getUbicacion(), proyectos.get(i).getLocalizacion()));
			}
			
			for(int i=0; i<posDistancia.size();i++) {
				Double Menor = posDistancia.stream().min(Comparator.naturalOrder()).get();
				posMenor.add(posDistancia.indexOf(Menor));
				posDistancia.set(posDistancia.indexOf(Menor), 100000.0);
			}
			
			for(int i=0;i<posMenor.size();i++) {
				retorno.add(proyectos.get(posMenor.get(i)));
			}
			return retorno;
		}
		else {
			return proyectos;
		}
	}
	
	@PutMapping("/recomendaciones/editarBusqueda/{nombre}")
	@ResponseStatus(code = HttpStatus.OK)
	public void editarBusqueda(@PathVariable String nombre, @RequestParam String busqueda) {
		Recomendaciones recomen = recomendaciones.findByNombre(nombre);
		List<String> busquedas = recomen.getBusquedas();
		if(!busquedas.contains(busqueda)) {
			busquedas.add(busqueda);
			recomen.setBusquedas(busquedas);
			recomendaciones.save(recomen);
		}
	}
	
}
