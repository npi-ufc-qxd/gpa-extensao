package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ATRIBUIR_PARECERISTA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_PARECERISTA_DA_EQUIPE;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_RELATORIO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Pendencia;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.service.NotificationService;
import ufc.quixada.npi.gpa.service.ParecerService;

@Service
public class ParecerServiceImpl implements ParecerService {

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Autowired
	private NotificationService notificationService;

	@Override
	public void atribuirParecerista(AcaoExtensao acaoExtensaoForm, String prazo) throws GpaExtensaoException, ParseException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(acaoExtensaoForm.getId());
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = (Date)formatter.parse(prazo);
		
		acaoExtensaoForm.getParecerTecnico().setPrazo(date);
		
		acao.setParecerTecnico(acaoExtensaoForm.getParecerTecnico());
		

		if (acao.getEquipeDeTrabalho().contains(acaoExtensaoForm.getParecerTecnico().getResponsavel())) {
			throw new GpaExtensaoException(EXCEPTION_PARECERISTA_DA_EQUIPE);

		} else if (acao.getStatus().equals(Status.AGUARDANDO_PARECERISTA)
				|| acao.getStatus().equals(Status.AGUARDANDO_PARECER_TECNICO)) {
			acao.getParecerTecnico().setDataAtribuicao(new Date());

		
			acao.getParecerTecnico().setResponsavel(acaoExtensaoForm.getParecerTecnico().getResponsavel());
			acao.getParecerTecnico().setPrazo(acaoExtensaoForm.getParecerTecnico().getPrazo()); 
			acao.getParecerTecnico().setDataAtribuicao(acaoExtensaoForm.getParecerTecnico().getDataAtribuicao());
			acao.setStatus(Status.AGUARDANDO_PARECER_TECNICO);
			acaoExtensaoRepository.save(acao);

			notificar(acao);

		} else {
			throw new GpaExtensaoException(EXCEPTION_ATRIBUIR_PARECERISTA);
		}

	}

	@Override
	public void atribuirRelator(AcaoExtensao acaoExtensao, String prazo) throws GpaExtensaoException, ParseException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = (Date)formatter.parse(prazo);
		
		acaoExtensao.getParecerRelator().setPrazo(date);
		
		acao.setParecerRelator(acaoExtensao.getParecerRelator());

		if (acao.getEquipeDeTrabalho().contains(acaoExtensao.getParecerRelator().getResponsavel())) {
			throw new GpaExtensaoException(EXCEPTION_PARECERISTA_DA_EQUIPE);

		} else if (acao.getStatus().equals(Status.AGUARDANDO_RELATOR)
				|| acao.getStatus().equals(Status.AGUARDANDO_PARECER_RELATOR)) {
			acao.getParecerRelator().setDataAtribuicao(new Date());

			acao.setStatus(Status.AGUARDANDO_PARECER_RELATOR);
			acaoExtensaoRepository.save(acao);

			notificar(acao);

		} else {
			throw new GpaExtensaoException(EXCEPTION_ATRIBUIR_PARECERISTA);
		}
	}

	@Override
	public void solicitarResolucaoPendencias(Integer idAcao, Pendencia pendencia) throws GpaExtensaoException {
			AcaoExtensao acaoExtensao = acaoExtensaoRepository.findOne(idAcao);

			pendencia.setDataDeSolicitacao(new Date());
			pendencia.setResolvida(false);
			
			switch (acaoExtensao.getStatus()) {
			case AGUARDANDO_PARECER_TECNICO:
				acaoExtensao.getParecerTecnico().addPendencia(pendencia);
				acaoExtensao.setStatus(Status.RESOLVENDO_PENDENCIAS_PARECER);
				
				break;

			case AGUARDANDO_PARECER_RELATOR:
				acaoExtensao.getParecerRelator().addPendencia(pendencia);
				acaoExtensao.setStatus(Status.RESOLVENDO_PENDENCIAS_RELATO);
				break;

			default:
				break;
			}

			acaoExtensaoRepository.save(acaoExtensao);
			notificationService.notificarSolicitacaoResolucaoPendenciasParecer(acaoExtensao, pendencia);
	}
	
	@Override
	public void emitirParecer(AcaoExtensao acaoExtensao) throws GpaExtensaoException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		
		if (acao != null) {

			switch (acao.getStatus()) {
			case AGUARDANDO_PARECER_TECNICO:

				acao.getParecerTecnico().setDataRealizacao(new Date());
				acao.getParecerTecnico().setPosicionamento(acaoExtensao.getParecerTecnico().getPosicionamento());
				acao.getParecerTecnico().setParecer(acaoExtensao.getParecerTecnico().getParecer());
				acao.getParecerTecnico().setObservacoes(acaoExtensao.getParecerTecnico().getObservacoes());
				acao.atribuirParecerRelator(new Parecer(), acao);
				acao.setStatus(Status.AGUARDANDO_RELATOR);
				break;

			case AGUARDANDO_PARECER_RELATOR:
				acao.getParecerRelator().setDataRealizacao(new Date());
				acao.getParecerRelator().setPosicionamento(acaoExtensao.getParecerRelator().getPosicionamento());
				acao.getParecerRelator().setParecer(acaoExtensao.getParecerRelator().getParecer());
				acao.getParecerRelator().setObservacoes(acaoExtensao.getParecerRelator().getObservacoes());
				acao.setStatus(Status.AGUARDANDO_HOMOLOGACAO);
				break;

			default:
				break;
			}

			acaoExtensaoRepository.save(acao);

			notificar(acao);

		} else {
			throw new GpaExtensaoException(EXCEPTION_RELATORIO);
		}
	}

	private void notificar(AcaoExtensao acaoExtensao) {
		this.notificationService.notificar(acaoExtensao);
	}
}
