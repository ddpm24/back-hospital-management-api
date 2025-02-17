package hospital.modelo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import hospital.entidades.Cita;
import hospital.entidades.Doctor;
import hospital.entidades.Usuario;

public interface CitaRepository extends JpaRepository<Cita, Integer>{
	
	//@Query("select c from Cita c where c.usuario.id = ?1")
	//List<Cita> findCitaByUsuario (int id);
	
	//List<Cita> findByEstado (String estado);
	List<Cita> findByUsuarioAndEstado (Usuario usuario, String estado);

	List<Cita> findByDoctorAndEstado (Doctor doctor, String estado);
	
	List<Cita> findByVisto(int visto);
	
	List<Cita> findByDoctor(Doctor doctor);

	
	
}
