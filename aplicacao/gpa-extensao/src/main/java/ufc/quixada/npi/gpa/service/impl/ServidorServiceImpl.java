package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_SERVIDOR_JA_CADASTRADO;
import static ufc.quixada.npi.gpa.util.Constants.USUARIO_NAO_ENCONTRADO_EXCEPTION;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;
import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Papel.Tipo;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.model.Servidor.Dedicacao;
import ufc.quixada.npi.gpa.model.Servidor.Funcao;
import ufc.quixada.npi.gpa.repository.PapelRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.ServidorService;

@Service
public class ServidorServiceImpl implements ServidorService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ServidorRepository servidorRepository;

	@Autowired
	private PapelRepository papelRepository;

	@Override
	public List<Servidor> findAllServidores() {
		return servidorRepository.findAll();
	}

	@Override
	public Usuario find(String cpf) throws GpaExtensaoException {
		Usuario usuario = usuarioService.getByCpf(cpf);

		if (usuario == null) {
			throw new GpaExtensaoException(USUARIO_NAO_ENCONTRADO_EXCEPTION);
		}

		return usuario;
	}

	@Override
	public Integer adicionar(Usuario usuario, Dedicacao dedicacao) throws GpaExtensaoException {
		if (servidorRepository.existsBySiape(usuario.getSiape())) {
			throw new GpaExtensaoException(EXCEPTION_SERVIDOR_JA_CADASTRADO);
		}

		Pessoa pessoa = new Pessoa();
		pessoa.setNome(usuario.getNome());
		pessoa.setEmail(usuario.getEmail());
		pessoa.setCpf(usuario.getCpf());

		List<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papelRepository.findByNome(Tipo.SERVIDOR));
		pessoa.setPapeis(papeis);

		Servidor servidor = new Servidor();
		servidor.setPessoa(pessoa);
		servidor.setDedicacao(dedicacao);
		servidor.setFuncao(Funcao.STA);
		servidor.setSiape(usuario.getSiape());

		servidorRepository.save(servidor);
		
		return servidor.getId();
	}

}
