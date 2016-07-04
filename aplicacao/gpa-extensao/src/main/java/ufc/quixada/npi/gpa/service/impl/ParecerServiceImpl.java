package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ATRIBUIR_PARECERISTA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_RELATORIO;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Pendencia;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.service.DocumentoService;
import ufc.quixada.npi.gpa.service.ParecerService;

@Service
public class ParecerServiceImpl implements ParecerService {

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private DocumentoService documentoService;

	@Override
	public void atribuirParecerista(AcaoExtensao acaoExtensao) throws GpaExtensaoException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		acao.setParecerTecnico(acaoExtensao.getParecerTecnico());

		if (acao.getStatus().equals(Status.AGUARDANDO_PARECERISTA)
				|| acao.getStatus().equals(Status.AGUARDANDO_PARECER_TECNICO)) {
			acao.getParecerTecnico().setDataAtribuicao(new Date());

			acao.setStatus(Status.AGUARDANDO_PARECER_TECNICO);
			acaoExtensaoRepository.save(acao);
		} else {
			throw new GpaExtensaoException(EXCEPTION_ATRIBUIR_PARECERISTA);
		}

	}

	@Override
	public void atribuirRelator(AcaoExtensao acaoExtensao) throws GpaExtensaoException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		acao.setParecerRelator(acaoExtensao.getParecerRelator());

		if (acao.getStatus().equals(Status.AGUARDANDO_RELATOR)
				|| acao.getStatus().equals(Status.AGUARDANDO_PARECER_RELATOR)) {
			acao.getParecerRelator().setDataAtribuicao(new Date());

			acao.setStatus(Status.AGUARDANDO_PARECER_RELATOR);
			acaoExtensaoRepository.save(acao);
		} else {
			throw new GpaExtensaoException(EXCEPTION_ATRIBUIR_PARECERISTA);
		}
	}

	@Override
	public void solicitarResolucaoPendencias(Integer idAcao, Pendencia pendencia) {
		AcaoExtensao acaoExtensao = acaoExtensaoRepository.findOne(idAcao);

		pendencia.setDataDeSolicitacao(new Date());

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
	}

	@Override
	public void emitirParecer(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(acaoExtensao.getId());

		if (acao != null) {

			Documento documento = documentoService.save(arquivo, EXCEPTION_RELATORIO);

			switch (acao.getStatus()) {
			case AGUARDANDO_PARECER_TECNICO:

				acao.getParecerTecnico().setArquivo(documento);
				acao.getParecerTecnico().setDataRealizacao(new Date());
				acao.getParecerTecnico().setPosicionamento(acaoExtensao.getParecerTecnico().getPosicionamento());
				acao.getParecerTecnico().setParecer(acaoExtensao.getParecerTecnico().getParecer());
				acao.setStatus(Status.AGUARDANDO_RELATOR);
				break;

			case AGUARDANDO_PARECER_RELATOR:

				acao.getParecerRelator().setArquivo(documento);
				acao.getParecerRelator().setDataRealizacao(new Date());
				acao.getParecerRelator().setPosicionamento(acaoExtensao.getParecerRelator().getPosicionamento());
				acao.getParecerRelator().setParecer(acaoExtensao.getParecerRelator().getParecer());
				acao.setStatus(Status.AGUARDANDO_HOMOLOGACAO);
				break;

			default:
				break;
			}

			acaoExtensaoRepository.save(acao);

		} else {
			throw new GpaExtensaoException(EXCEPTION_RELATORIO);
		}
	}
}
