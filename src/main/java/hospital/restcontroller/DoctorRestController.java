package hospital.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hospital.entidades.Doctor;
import hospital.modelo.service.DoctorService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/doctor")
public class DoctorRestController {
	
		@Autowired
		private DoctorService dserv;
		
		@GetMapping("recomendado/{cantidad}")
		public ResponseEntity<?> getMedicos(@PathVariable int cantidad) {
		    List<Doctor> medicos = dserv.buscarTodos();
		    if (medicos != null && !medicos.isEmpty()) {
		        List<Doctor> NMedicos = medicos.stream()
		            .sorted((d1, d2) -> Integer.compare(d2.getVotos(), d1.getVotos())) // Ordenar por votos en orden descendente
		            .limit(cantidad)
		            .collect(Collectors.toList());
		        return new ResponseEntity<>(NMedicos, HttpStatus.OK);
		    }
		    return new ResponseEntity<>("No hay medicos", HttpStatus.NOT_FOUND);
		}
	
		
		@GetMapping("one/{id}")
		public ResponseEntity<?> getMedicoById(@PathVariable int id) {
		    if (dserv.buscarPorId(id) != null) {
		    	return new ResponseEntity<>(dserv.buscarPorId(id), HttpStatus.OK);
		    }
		    return new ResponseEntity<>("Ese medico no existe", HttpStatus.NOT_FOUND);
		}
		
		@GetMapping("oneByUsername/{username}")
		public ResponseEntity<?> getOne(@PathVariable String username) {
			if (dserv.buscarPorUsername(username) != null) {
				return new ResponseEntity<>(dserv.buscarPorUsername(username), HttpStatus.OK);
			}
			return new ResponseEntity<>("Medico no existe", HttpStatus.NOT_FOUND);
		}
		
		@GetMapping("/allDoctors")
	    public ResponseEntity<?> todos() {
	        List<Doctor> doctores = dserv.buscarTodos();
	        if (doctores.isEmpty()) {
	            return new ResponseEntity<>("No se encontraron doctores.", HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(doctores, HttpStatus.OK);
	    }
		
		
		 @GetMapping("/porNombreApellidoLocalidadYEspecialidad/{nombre}/{apellidos}/{localidad}/{especialidad}")
		    public ResponseEntity<?> buscarPorNombreApellidoLocalidadYEspecialidad(@PathVariable String nombre,
		                                                                           @PathVariable String apellidos,
		                                                                           @PathVariable String localidad,
		                                                                           @PathVariable String especialidad) {
		        List<Doctor> doctores = dserv.buscarPorNombreApellidoLocalidadYEspecialidad(nombre, apellidos, localidad, especialidad);
		        if (doctores.isEmpty()) {
		            return new ResponseEntity<>("No se encontraron doctores", HttpStatus.NOT_FOUND);
		        }
		        return new ResponseEntity<>(doctores, HttpStatus.OK);
		}
	
	 	@GetMapping("/localidad/{localidad}")
	    public ResponseEntity<?> porLocalidad(@PathVariable String localidad) {
	        List<Doctor> doctores = dserv.buscarPorLocalidad(localidad);
	        if (doctores.isEmpty()) {
	            return new ResponseEntity<>("No se encontraron doctores.", HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(doctores, HttpStatus.OK);
	    }
	 
	 
	 
}
	
	
	
	

