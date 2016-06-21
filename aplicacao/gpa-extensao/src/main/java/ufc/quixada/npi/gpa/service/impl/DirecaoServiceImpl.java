package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.ATRIBUIR_PARECERISTA_EXCEPTION;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParecerRepository;
import ufc.quixada.npi.gpa.service.DirecaoService;

@Service
@Transactional
public class DirecaoServiceImpl implements DirecaoService {

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private ParecerRepository parecerRepository;
	
	@Override
	public List<Pessoa> getPossiveisPareceristas(Integer idAcaoExtensao) {
		return parecerRepository.getPossiveisPareceristas(idAcaoExtensao);
	}

	@Override
	public void atribuirParecerista(AcaoExtensao acaoExtensao) throws GpaExtensaoException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		acao.setParecerTecnico(acaoExtensao.getParecerTecnico());

		if (acao.getStatus().equals(Status.AGUARDANDO_PARECERISTA) || acao.getStatus().equals(Status.AGUARDANDO_PARECER_TECNICO)) {
			acao.getParecerTecnico().setDataAtribuicao(new Date());

			acao.setStatus(Status.AGUARDANDO_PARECER_TECNICO);
			acaoExtensaoRepository.save(acao);
		} else {
			throw new GpaExtensaoException(ATRIBUIR_PARECERISTA_EXCEPTION);
		}

	}
	
	@Override
	public void atribuirRelator(AcaoExtensao acaoExtensao) throws GpaExtensaoException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		acao.setParecerRelator(acaoExtensao.getParecerRelator());
		
		if (acao.getStatus().equals(Status.AGUARDANDO_RELATOR) || acao.getStatus().equals(Status.AGUARDANDO_PARECER_RELATOR)) {
			acao.getParecerRelator().setDataAtribuicao(new Date());

			acao.setStatus(Status.AGUARDANDO_PARECER_RELATOR);
			acaoExtensaoRepository.save(acao);
		} else {
			throw new GpaExtensaoException(ATRIBUIR_PARECERISTA_EXCEPTION);
		}
	}

}
