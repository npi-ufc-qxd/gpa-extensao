package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.Parceiro;

@Repository
public interface ParceiroRepository extends JpaRepository<Parceiro, Integer>{
	List<Parceiro> findAll();
}
