package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ATRIBUIR_PARECERISTA;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.service.DirecaoService;

@Service
public class DirecaoServiceImpl implements DirecaoService {

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Override
	public void atribuirParecerista(Integer idAcaoExtensao, Parecer parecerTecnico) throws GpaExtensaoException {
		AcaoExtensao acaoExtensao = carregarAcaoExtensao(idAcaoExtensao);

		if (acaoExtensao.getStatus().equals(Status.AGUARDANDO_PARECERISTA)) {
			parecerTecnico.setDataAtribuicao(new Date());
			acaoExtensao.setParecerTecnico(parecerTecnico);

			acaoExtensao.setStatus(Status.AGUARDANDO_PARECER_TECNICO);
			acaoExtensaoRepository.save(acaoExtensao);
		} else {
			throw new GpaExtensaoException(EXCEPTION_ATRIBUIR_PARECERISTA);
		}

	}

	private AcaoExtensao carregarAcaoExtensao(Integer idAcaoExtensao) {
		return acaoExtensaoRepository.findOne(idAcaoExtensao);
	}
}
