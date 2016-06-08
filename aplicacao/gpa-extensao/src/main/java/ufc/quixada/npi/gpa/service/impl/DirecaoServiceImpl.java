package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ATRIBUIR_PARECERISTA;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParecerRepository;
import ufc.quixada.npi.gpa.service.DirecaoService;

@Service
public class DirecaoServiceImpl implements DirecaoService {

	@Autowired
	private ParecerRepository parecerRepository;

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Override
	public List<Pessoa> getPossiveisPareceristas(Integer idAcaoExtensao) {
		return parecerRepository.getPossiveisPareceristas(idAcaoExtensao);
	}

	@Override
	public void atribuirParecerista(Integer idAcaoExtensao, Parecer parecerTecnico) throws GpaExtensaoException {
		AcaoExtensao acaoExtensao = carregarAcaoExtensao(idAcaoExtensao);

		if (acaoExtensao.getStatus().equals(Status.AGUARDANDO_PARECERISTA)) {
			acaoExtensao.setParecerTecnico(parecerTecnico);

			acaoExtensaoRepository.save(acaoExtensao);
		} else {
			throw new GpaExtensaoException(EXCEPTION_ATRIBUIR_PARECERISTA);
		}

	}

	private AcaoExtensao carregarAcaoExtensao(Integer idAcaoExtensao) {
		return acaoExtensaoRepository.findOne(idAcaoExtensao);
	}
}
