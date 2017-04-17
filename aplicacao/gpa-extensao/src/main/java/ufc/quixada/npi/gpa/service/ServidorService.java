package ufc.quixada.npi.gpa.service;

import java.util.List;

import br.ufc.quixada.npi.ldap.model.Usuario;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.model.Servidor.Dedicacao;

public interface ServidorService {

	/**
	 * Busca todos os servidores cadastrados no sistema
	 */
	List<Servidor> findAllServidores();

	/**
	 * Cadastra um novo servidor no sistema
	 */
	void adicionarServidor(Usuario usuario, Dedicacao dedicacao, Servidor.Funcao funcao);

	/**
	 * Busca todos os servidores (docentes e técnicos) no LDAP e cadastra os que ainda não estão no sistema
	 */
	void cadastrarServidores();
	
	/*
	 * Busca todos os servidores que não são o coordenador atual
	 */
	List<Servidor> buscarServidorNaoCoordenador(Integer id);
}
