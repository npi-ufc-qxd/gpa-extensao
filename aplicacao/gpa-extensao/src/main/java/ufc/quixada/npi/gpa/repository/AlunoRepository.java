package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ufc.quixada.npi.gpa.model.Aluno;

public interface AlunoRepository extends CrudRepository<Aluno, Integer>{

	List<Aluno> findAll();
	
	@Query("SELECT al FROM Aluno al WHERE al.curso =:curso")
	List<Aluno> findByCurso(@Param("curso") String curso);
}
