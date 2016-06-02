package ufc.quixada.npi.gpa.service.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.repository.ParceiroRepository;
import ufc.quixada.npi.gpa.service.ParceiroService;
@Named
public class ParceiroServiceImpl implements ParceiroService {

	@Autowired
	private ParceiroRepository parceiroRepository;
	@Override
	public List<Parceiro> buscarTodos() {
		return parceiroRepository.findAll();
	}

	@Override
	public void salvar(Parceiro parceiro) {
		parceiroRepository.save(parceiro);
	}

	@Override
	public Parceiro buscarPorId(Integer id) {
		return parceiroRepository.findOne(id);
	}

}
