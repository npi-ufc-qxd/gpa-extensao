package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.model.Servidor.Funcao;

public interface ServidorRepository extends CrudRepository<Servidor, Integer>{

	List<Servidor> findAll();
	
	Servidor findByPessoa_id(Integer id);
	
	Servidor findByPessoa_cpf(String cpf);
	
	List<Servidor> findByPessoa_idNotIn(Integer id);
	
	List<Servidor> findByFuncao(Funcao funcao);
}
