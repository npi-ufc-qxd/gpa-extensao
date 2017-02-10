package ufc.quixada.npi.gpa.service;

import br.ufc.quixada.npi.ldap.model.Usuario;
import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.model.Servidor.Dedicacao;

import java.util.List;

public interface ServidorService {

	List<Servidor> findAllServidores();

	public Usuario find(String cpf) throws GpaExtensaoException;

	public Integer adicionar(Usuario usuario, Dedicacao dedicacao) throws GpaExtensaoException;

}
