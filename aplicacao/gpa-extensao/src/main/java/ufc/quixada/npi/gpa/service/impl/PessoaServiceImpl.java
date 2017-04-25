package ufc.quixada.npi.gpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {

	@Autowired
	private ServidorRepository servidorRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public Servidor findServidor(String cpf) {
		return servidorRepository.findByPessoa_cpf(cpf);
	}

	@Override
	public Pessoa buscarPorCpf(String cpf) {
		return pessoaRepository.findByCpf(cpf);
	}

	@Override
	public Pessoa buscarPorId(Integer id) {
		return pessoaRepository.findOne(id);
	}

}
