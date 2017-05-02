package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ufc.quixada.npi.gpa.model.Aluno;

public interface AlunoRepository extends CrudRepository<Aluno, Integer> {
	
	@Query("FROM Aluno a order by a.pessoa.nome asc")
 	List<Aluno> findAll();
	
	@Query("SELECT al FROM Aluno al WHERE al.curso =:curso")
	List<Aluno> findByCurso(@Param("curso") String curso);

	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN 'true' ELSE 'false' END FROM Aluno a WHERE a.matricula = :matricula")
	boolean existsByMatricula(@Param("matricula") String matricula);


}
