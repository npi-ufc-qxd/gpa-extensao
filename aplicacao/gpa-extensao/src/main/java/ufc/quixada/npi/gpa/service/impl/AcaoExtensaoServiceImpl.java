package ufc.quixada.npi.gpa.service.impl;



import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;

@Named
public class AcaoExtensaoServiceImpl implements AcaoExtensaoService{
	
	@Autowired
	AcaoExtensaoRepository acaoRepository;
	
	@Override
	public AcaoExtensao getById(int id) {
		return acaoRepository.getById(id);
	}

}
