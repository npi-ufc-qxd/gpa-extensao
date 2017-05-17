package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.model.Servidor.Funcao;

public interface ServidorRepository extends CrudRepository<Servidor, Integer>{
	
	@Query("FROM Servidor ORDER BY pessoa.nome")
	List<Servidor> findAll();
	
	@Query("FROM Servidor s WHERE s.funcao in :funcoes ORDER BY pessoa.nome")
	List<Servidor> findServidoresByFuncao(@Param("funcoes") List<Funcao> funcoes);
	
	Servidor findByPessoa_id(Integer id);
	
	Servidor findByPessoa_cpf(String cpf);
	
	List<Servidor> findByPessoa_idNotIn(Integer id);
	
	List<Servidor> findByFuncao(Funcao funcao);
	
	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN 'true' ELSE 'false' END FROM Servidor s WHERE s.siape = :siape")
	boolean existsBySiape(@Param("siape") String siape);
	
	
	
}
