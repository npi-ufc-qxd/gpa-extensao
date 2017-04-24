package ufc.quixada.npi.gpa.service;


import ufc.quixada.npi.gpa.model.Pessoa;

import ufc.quixada.npi.gpa.model.Servidor;

public interface PessoaService {
	
	Pessoa buscarPorId(Integer id);

	Servidor findServidor(String cpf);

	
	Pessoa buscarPorCpf(String cpf);
	
}