package ufc.quixada.npi.gpa.service.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.ParceriaExterna;
import ufc.quixada.npi.gpa.repository.ParceriaExternaRepository;
import ufc.quixada.npi.gpa.service.ParceriaExternaService;
@Named
public class ParceriaExternaServiceImpl implements ParceriaExternaService {

	@Autowired
	private ParceriaExternaRepository parceriaExternaRepository;
	@Override
	public List<ParceriaExterna> buscarTodas() {
		return parceriaExternaRepository.findAll();
	}

	@Override
	public void salvar(ParceriaExterna parceria) {
		parceriaExternaRepository.save(parceria);
	}

	@Override
	public ParceriaExterna buscarPorId(Integer id) {
		return parceriaExternaRepository.findById(id);
	}

	@Override
	public List<ParceriaExterna> buscarPorParceiro(Parceiro parceiro) {
		return parceriaExternaRepository.findByParceiro(parceiro);
	}

}
