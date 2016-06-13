package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.ParceriaExterna;

@Repository
public interface ParceriaExternaRepository extends CrudRepository<ParceriaExterna, Integer>{
	List<ParceriaExterna> findAll();
	ParceriaExterna findById(Integer id);
	List<ParceriaExterna> findByParceiro(Parceiro parceiro);
}
