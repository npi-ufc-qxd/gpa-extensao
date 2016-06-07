package ufc.quixada.npi.gpa.service.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;

@Named
public class AcaoExtensaoServiceImpl implements AcaoExtensaoService {
	@Autowired
	AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Override
	public List<AcaoExtensao> getTramitacao(Integer id) {
		return acaoExtensaoRepository.getTramitacao(id, Status.APROVADO, Status.REPROVADO, Status.NOVO);
	}
	@Override
	public List<AcaoExtensao> getNovos(Integer id) {
		return acaoExtensaoRepository.getNovos(id, Status.NOVO);
	}
	@Override
	public List<AcaoExtensao> getHomologados(Integer id) {
		return acaoExtensaoRepository.getHomologados(id, Status.APROVADO, Status.REPROVADO);
	}
}
