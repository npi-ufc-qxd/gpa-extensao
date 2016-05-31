package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.ParceriaExterna;

public interface ParceriaExternaService {
	List<ParceriaExterna> buscarTodas();
	
	ParceriaExterna salvar(ParceriaExterna parceria);
	
	ParceriaExterna buscarPorId(Integer id);
	
	List<ParceriaExterna> buscarPorParceiro(Parceiro parceiro);
}
