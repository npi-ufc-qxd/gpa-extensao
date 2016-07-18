package ufc.quixada.npi.gpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Participacao.Funcao;
import ufc.quixada.npi.gpa.model.Participacao.Instituicao;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.repository.ParticipacaoRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.ParticipacaoService;

@Service
public class ParticipacaoServiceImpl implements ParticipacaoService {

	@Autowired
	private ServidorRepository servidorRepository;

	@Autowired
	private ParticipacaoRepository participacaoRepository;

	@Override
	public Participacao participacaoCoordenador(AcaoExtensao acaoExtensao, Integer cargaHoraria) {
		Participacao participacao = new Participacao();
		participacao.setAcaoExtensao(acaoExtensao);
		participacao.setCoordenador(true);
		participacao.setCargaHoraria(cargaHoraria);
		participacao.setCpfParticipante("");
		participacao.setDataInicio(acaoExtensao.getInicio());
		participacao.setDataTermino(acaoExtensao.getTermino());
		participacao.setDescricaoFuncao("");
		participacao.setInstituicao(Instituicao.UFC);
		participacao.setNomeInstituicao("");
		participacao.setNomeParticipante("");
		participacao.setParticipante(acaoExtensao.getCoordenador());

		Servidor servidor = servidorRepository.findByPessoa_cpf(acaoExtensao.getCoordenador().getCpf());
		if (servidor.getFuncao().equals(Servidor.Funcao.DOCENTE)) {
			participacao.setFuncao(Funcao.DOCENTE);
		} else if (servidor.getFuncao().equals(Servidor.Funcao.STA)) {
			participacao.setFuncao(Funcao.STA);
		}

		participacaoRepository.save(participacao);
		return participacao;
	}

}
