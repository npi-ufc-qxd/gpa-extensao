package ufc.quixada.npi.gpa.service.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;

@Named
public class AcaoExtensaoServiceImpl implements AcaoExtensaoService {
	@Autowired
	AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Override
	public List<AcaoExtensao> getParecerRelator(Integer id) {
		return acaoExtensaoRepository.getParecerRelator(id);
	}
	@Override
	public List<AcaoExtensao> getParecerTecnico(Integer id) {
		return acaoExtensaoRepository.getParecerTecnico(id);
	}
	@Override
	public List<AcaoExtensao> getParticipacao(Integer id) {
		return acaoExtensaoRepository.getParticipacao(id);
	}
}
