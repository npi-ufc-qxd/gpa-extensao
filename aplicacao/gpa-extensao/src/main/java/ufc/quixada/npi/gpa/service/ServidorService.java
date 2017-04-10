package ufc.quixada.npi.gpa.service;

import br.ufc.quixada.npi.ldap.model.Usuario;
import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.model.Servidor.Dedicacao;

import java.util.List;

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
	
	List<Servidor> findByPessoa_idNotIn(Integer idCoordenador);
}
