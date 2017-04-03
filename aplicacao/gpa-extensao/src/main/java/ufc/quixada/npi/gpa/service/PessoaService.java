package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;

public interface PessoaService {

	Servidor findServidor(String cpf);
	
}