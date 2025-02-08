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
	private DoctorService rserv;
	
	@GetMapping("recomendado/{cantidad}")
	public ResponseEntity<?> getMedicos(@PathVariable int cantidad) {
	    List<Doctor> medicos = rserv.buscarTodos();
	    if (medicos != null && !medicos.isEmpty()) {
	        List<Doctor> NMedicos = medicos.stream().limit(cantidad).collect(Collectors.toList());
	        return new ResponseEntity<>(NMedicos, HttpStatus.OK);
	    }
	    return new ResponseEntity<>("No hay medicos", HttpStatus.NOT_FOUND);
	}
}
