package ufc.quixada.npi.gpa.service;

import br.ufc.quixada.npi.ldap.model.Usuario;
import ufc.quixada.npi.gpa.exception.GpaExtensaoException;

public interface AlunoService {

	public Usuario find(String matricula) throws GpaExtensaoException;

	public void adicionar(Usuario usuario) throws GpaExtensaoException;

}
