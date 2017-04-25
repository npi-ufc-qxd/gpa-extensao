package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.CAMPO_OBRIGATORIO_VAZIO;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_PARCEIRO_JA_PARTICIPANTE;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_PERMISSAO_NEGADA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
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

		Parceiro parceiroCadastrado = null;
		if (parceiro != null) {
			parceiroCadastrado = parceiroRepository.findOne(parceiro.getId());
		}
		if (acaoOld != null) {
			if (!acaoOld.getCoordenador().getCpf().equalsIgnoreCase(coordenador.getCpf())) {
				throw new GpaExtensaoException(MENSAGEM_PERMISSAO_NEGADA);
			}
			if (parceiroCadastrado != null) {
				for (int i = 0; i < acaoOld.getParceriasExternas().size(); i++) {
					if (acaoOld.getParceriasExternas().get(i).getParceiro().getNome().trim()
							.equalsIgnoreCase(parceiroCadastrado.getNome().trim())) {
						throw new GpaExtensaoException(ERROR_PARCEIRO_JA_PARTICIPANTE);
					}
				}
				parceriaExterna.setParceiro(parceiroCadastrado);
			}
			for (int i = 0; i < acaoOld.getParceriasExternas().size(); i++) {
				if (parceriaExterna.getParceiro().getNome().trim()
						.equalsIgnoreCase(acaoOld.getParceriasExternas().get(i).getParceiro().getNome().trim())) {
					throw new GpaExtensaoException(ERROR_PARCEIRO_JA_PARTICIPANTE);
				}
			}
			if (parceriaExterna.getParceiro().getNome().replaceAll(" ", "").trim().isEmpty()) {
				throw new GpaExtensaoException(CAMPO_OBRIGATORIO_VAZIO);
			}
			acaoOld.getParceriasExternas().add(parceriaExterna);
			parceriaExterna.setAcaoExtensao(acaoOld);
			parceriaExternaRepository.save(parceriaExterna);
			acaoExtensaoRepository.save(acaoOld);
		}

	}

	@Override
	public void excluirParceriaExterna(Integer idParceria) {
		parceriaExternaRepository.delete(idParceria);
	}

}
