package ufc.quixada.npi.gpa.service;

import br.ufc.quixada.npi.ldap.model.Usuario;
import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Servidor.Dedicacao;

public interface ServidorService {

	public Usuario find(String cpf) throws GpaExtensaoException;

	public Integer adicionar(Usuario usuario, Dedicacao dedicacao) throws GpaExtensaoException;

}
