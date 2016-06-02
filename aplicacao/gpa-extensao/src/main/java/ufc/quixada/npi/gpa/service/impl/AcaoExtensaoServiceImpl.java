package ufc.quixada.npi.gpa.service.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;

@Named
public class AcaoExtensaoServiceImpl implements AcaoExtensaoService {

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	@Override
	public AcaoExtensao buscarPorId(Integer id) {
		return acaoExtensaoRepository.findById(id);
	}

	@Override
	public List<AcaoExtensao> buscarAcaoExtensaoPorCoordenador(Pessoa pessoa) {
		return acaoExtensaoRepository.findByCoordenador(pessoa);
	}

	@Override
	public void salvar(AcaoExtensao acaoExtensao) {
		acaoExtensaoRepository.save(acaoExtensao);
	}

}
