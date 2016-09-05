package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ufc.quixada.npi.gpa.model.Aluno;

public interface AlunoRepository extends CrudRepository<Aluno, Integer> {

	List<Aluno> findAll();

	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN 'true' ELSE 'false' END FROM Aluno a WHERE a.matricula = :matricula")
	boolean existsByMatricula(@Param("matricula") String matricula);
}
