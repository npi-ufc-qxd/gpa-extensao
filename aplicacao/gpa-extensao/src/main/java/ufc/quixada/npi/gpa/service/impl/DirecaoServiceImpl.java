package ufc.quixada.npi.gpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.ParecerRepository;
import ufc.quixada.npi.gpa.service.DirecaoService;

@Service
@Transactional
public class DirecaoServiceImpl implements DirecaoService {
	
	@Autowired
	private ParecerRepository parecerRepository;
	
	@Override
	public List<Pessoa> getPossiveisPareceristas(Integer idAcaoExtensao) {
		return parecerRepository.getPossiveisPareceristas(idAcaoExtensao);
	}

}
