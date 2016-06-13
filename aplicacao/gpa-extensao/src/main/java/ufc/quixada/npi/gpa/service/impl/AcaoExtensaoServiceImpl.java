package ufc.quixada.npi.gpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;

@Named
public class AcaoExtensaoServiceImpl implements AcaoExtensaoService{

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Override
	public AcaoExtensao getAcaoExtensao(AcaoExtensao acaoExtensao) {
		List<AcaoExtensao> acaoExtensaos = new ArrayList<AcaoExtensao>();
		acaoExtensaos =  (List<AcaoExtensao>) acaoExtensaoRepository.findAll();
		for(AcaoExtensao acao : acaoExtensaos){
			if(acao.equals(acaoExtensao)) return acao;
		}
		return null;
	}
	
	
}
