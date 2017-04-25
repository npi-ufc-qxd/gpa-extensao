package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, Integer> {

	@Query("SELECT pa FROM Pessoa pe INNER JOIN pe.papeis pa WHERE pe.cpf = :cpf")
	List<Papel> findPapeisByCpf(@Param("cpf") String cpf);
	
	Pessoa findByCpf(String cpf);

	List<Pessoa> findAll();

	List<Pessoa> findAllByPapeis(List<Papel> papeis);
	
	Pessoa findByNome(String nome);

}
