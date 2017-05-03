package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.CAMPO_OBRIGATORIO_VAZIO;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_PARCEIRO_JA_PARTICIPANTE;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ADICAO_PARCERIA_NAO_PERMITIDA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_EXCLUSAO_PARCERIA_NAO_PERMITIDA;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_PERMISSAO_NEGADA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.ParceriaExterna;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParceiroRepository;
import ufc.quixada.npi.gpa.repository.ParceriaExternaRepository;
import ufc.quixada.npi.gpa.service.ParceriaExternaService;

@Service
public class ParceriaExternaServiceImpl implements ParceriaExternaService {

	@Autowired
	private ParceriaExternaRepository parceriaExternaRepository;

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Autowired
	private ParceiroRepository parceiroRepository;

	@Override
	public void adicionarParceriaExterna(Pessoa coordenador, ParceriaExterna parceriaExterna, AcaoExtensao acaoExtensao,
			Parceiro parceiro) throws GpaExtensaoException {
		AcaoExtensao acaoOld = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		Parceiro parceiroExistente = null;

		if (acaoOld != null) {
			if (!acaoOld.getCoordenador().getCpf().equalsIgnoreCase(coordenador.getCpf())) {
				throw new GpaExtensaoException(MENSAGEM_PERMISSAO_NEGADA);
			}
			if (!acaoOld.getStatus().equals(Status.NOVO)
					&& !acaoOld.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_PARECER)
					&& !acaoOld.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_RELATO)
					&& !acaoOld.getStatus().equals(Status.APROVADO)) {
				throw new GpaExtensaoException(EXCEPTION_ADICAO_PARCERIA_NAO_PERMITIDA);
			}
			if (parceriaExterna.getParceiro().getNome().replaceAll(" ", "").trim().isEmpty()) {
				throw new GpaExtensaoException(CAMPO_OBRIGATORIO_VAZIO);
			}
			if (parceiro != null) {
				parceiroExistente = parceiroRepository.findOne(parceiro.getId());
				for (int i = 0; i < acaoOld.getParceriasExternas().size(); i++) {
					if (acaoOld.getParceriasExternas().get(i).getParceiro().getNome().trim()
							.equalsIgnoreCase(parceiroExistente.getNome().trim())) {
						throw new GpaExtensaoException(ERROR_PARCEIRO_JA_PARTICIPANTE);
					}
				}
				parceriaExterna.setParceiro(parceiroExistente);
			} else {
				for (int i = 0; i < acaoOld.getParceriasExternas().size(); i++) {
					if (parceriaExterna.getParceiro().getNome().trim()
							.equalsIgnoreCase(acaoOld.getParceriasExternas().get(i).getParceiro().getNome().trim())) {
						throw new GpaExtensaoException(ERROR_PARCEIRO_JA_PARTICIPANTE);
					}
				}
			}
			acaoOld.getParceriasExternas().add(parceriaExterna);
			parceriaExterna.setAcaoExtensao(acaoOld);
			parceriaExternaRepository.save(parceriaExterna);
			acaoExtensaoRepository.save(acaoOld);
		}

	}

	@Override
	public void excluirParceriaExterna(Pessoa coordenador, ParceriaExterna parceria) throws GpaExtensaoException {
		if (!parceria.getAcaoExtensao().getCoordenador().getCpf().equalsIgnoreCase(coordenador.getCpf())) {
			throw new GpaExtensaoException(MENSAGEM_PERMISSAO_NEGADA);
		} else if (!parceria.getAcaoExtensao().getStatus().equals(Status.NOVO)
				&& !parceria.getAcaoExtensao().getStatus().equals(Status.RESOLVENDO_PENDENCIAS_PARECER)
				&& !parceria.getAcaoExtensao().getStatus().equals(Status.RESOLVENDO_PENDENCIAS_RELATO)
				&& !parceria.getAcaoExtensao().getStatus().equals(Status.APROVADO)) {
			throw new GpaExtensaoException(EXCEPTION_EXCLUSAO_PARCERIA_NAO_PERMITIDA);
		}
		if (!parceria.getAcaoExtensao().isAtivo()) {
			throw new GpaExtensaoException(EXCEPTION_EXCLUSAO_PARCERIA_NAO_PERMITIDA);
		}
		parceriaExternaRepository.delete(parceria);
	}

}
