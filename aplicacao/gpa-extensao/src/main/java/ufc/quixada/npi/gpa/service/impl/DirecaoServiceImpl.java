package ufc.quixada.npi.gpa.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.service.DirecaoService;

@Service
public class DirecaoServiceImpl implements DirecaoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Override
	public void atribuirParecerista(AcaoExtensao acaoExtensao, Pessoa parecerista) {
		acaoExtensao = carregarAcaoExtensao(acaoExtensao.getId());

		if (acaoExtensao.getStatus().equals(Status.AGUARDANDO_PARECERISTA)) {
			parecerista = carregarPessoa(parecerista.getId());

			Parecer parecerTecnico = new Parecer();
			parecerTecnico.setDataAtribuicao(new Date());
			parecerTecnico.setResponsavel(parecerista);

			acaoExtensao.setParecerTecnico(parecerTecnico);

			acaoExtensaoRepository.save(acaoExtensao);
		}
	}

	private AcaoExtensao carregarAcaoExtensao(Integer idAcaoExtensao) {
		return acaoExtensaoRepository.findOne(idAcaoExtensao);
	}

	private Pessoa carregarPessoa(Integer idPessoa) {
		return pessoaRepository.findOne(idPessoa);
	}
}
