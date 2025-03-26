package hospital.restcontroller;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hospital.entidades.Cita;
import hospital.entidades.HistorialMedico;
import hospital.entidades.Receta;
import hospital.entidades.Usuario;
import hospital.modelo.service.CitaService;
import hospital.modelo.service.HistorialMedicoService;
import hospital.modelo.service.RecetaService;
import hospital.modelo.service.UsuarioService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/receta")
public class RecetaRestController {

	@Autowired
	private RecetaService rserv;
	
	@Autowired
	private UsuarioService userv;
	
	@Autowired
	private CitaService cserv;
	
	@Autowired HistorialMedicoService hmserv;
	
	@PostMapping("/alta/{idUsuario}/{idCita}/{idHistorialMedico}")
	public ResponseEntity<?> altaReceta(@PathVariable int idUsuario,@PathVariable int idCita,@PathVariable int idHistorialMedico,@RequestBody Receta receta ) {
		try {
			Cita cita =cserv.buscarPorId(idCita);
			Usuario usuario =userv.buscarPorId(idUsuario);
			HistorialMedico historialMedico=hmserv.buscarPorId(idHistorialMedico);
			receta.setCita(cita);
			receta.setUsuario(usuario);
			receta.setHistorialMedico(historialMedico);
			System.out.println("COMPROBANDO LA CITA "+cita);
			System.out.println("COMPROBANDO EL USUARIO"+usuario);
			System.out.println("COMPROBANDO EL HISTORIALMEDICO"+historialMedico);
			
			Receta nuevaReceta= rserv.alta(receta);
			
			
			return new ResponseEntity<>(nuevaReceta, HttpStatus.OK);
		} catch (Exception e) {
					return new ResponseEntity<>("No se pudo dar de alta  la reserva", HttpStatus.NOT_FOUND);

		}
		
	}
	
	
	@GetMapping("/miReceta/{idUsuario}/{idCita}")
	public ResponseEntity<?> verMisRecetas(@PathVariable int idUsuario,@PathVariable int idCita) {
		Usuario usuario= userv.buscarPorId(idUsuario);
		Cita cita = cserv.buscarPorId(idCita);
		if (usuario !=null) {
			
			 return new ResponseEntity<>(rserv.buscarRecetaCita(cita.getId()), HttpStatus.OK);
		}
		return new ResponseEntity<>("No se pudo encontrar la reserva", HttpStatus.NOT_FOUND);
	}
	
}
