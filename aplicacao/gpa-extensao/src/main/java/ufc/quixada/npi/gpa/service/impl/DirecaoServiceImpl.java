package ufc.quixada.npi.gpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParecerRepository;
import ufc.quixada.npi.gpa.service.DirecaoService;
import ufc.quixada.npi.gpa.service.NotificationService;

@Service
@Transactional
public class DirecaoServiceImpl implements DirecaoService {

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ParecerRepository parecerRepository;

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Override
	public List<Pessoa> getPossiveisPareceristas(Integer idAcaoExtensao) {
		return parecerRepository.getPossiveisPareceristas(idAcaoExtensao);
	}

	@Override
	public void homologarAcao(Integer idAcao, AcaoExtensao acaoExtensao) {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);

		acao.setStatus(acaoExtensao.getStatus());
		acao.setDataDeHomologacao(acaoExtensao.getDataDeHomologacao());
		acao.setNumeroProcesso(acaoExtensao.getNumeroProcesso());
		acao.setObservacaoHomologacao(acaoExtensao.getObservacaoHomologacao());

		acaoExtensaoRepository.save(acao);

		notificar(acao);
	}

	private void notificar(AcaoExtensao acaoExtensao) {
		this.notificationService.notificar(acaoExtensao);
	}
}
