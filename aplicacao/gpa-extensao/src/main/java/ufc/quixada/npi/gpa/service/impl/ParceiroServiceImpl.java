package ufc.quixada.npi.gpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.repository.ParceiroRepository;
import ufc.quixada.npi.gpa.service.ParceiroService;

@Service
public class ParceiroServiceImpl implements ParceiroService{
	@Autowired
	private ParceiroRepository parceiroRepository;

	@Override
	public List<Parceiro> listarParceiros() {
		return parceiroRepository.findAll();
	}
	
	
}
