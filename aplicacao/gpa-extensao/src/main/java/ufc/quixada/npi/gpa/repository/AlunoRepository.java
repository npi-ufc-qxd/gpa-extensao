package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ufc.quixada.npi.gpa.model.Aluno;

public interface AlunoRepository extends CrudRepository<Aluno, Integer>{

	List<Aluno> findAll();
}
