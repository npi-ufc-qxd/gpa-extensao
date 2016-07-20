package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ufc.quixada.npi.gpa.model.Bolsa;

public interface BolsaRepository extends CrudRepository<Bolsa, Integer>{
	List<Bolsa> findAll();
	
	@Query("SELECT DISTINCT YEAR(b.inicio) FROM Bolsa b")
	List<Integer> findAnosInicio();
	
	@Query("SELECT b FROM Bolsa b where YEAR(b.inicio) = :year OR YEAR(b.termino) = :year")
	List<Bolsa> findByYear(@Param("year") Integer year);
}
