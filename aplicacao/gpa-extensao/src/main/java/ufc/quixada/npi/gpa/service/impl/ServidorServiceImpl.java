package ufc.quixada.npi.gpa.service.impl;

import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Papel.Tipo;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.model.Servidor.Dedicacao;
import ufc.quixada.npi.gpa.model.Servidor.Funcao;
import ufc.quixada.npi.gpa.repository.PapelRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.ServidorService;

import java.util.ArrayList;
import java.util.List;

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
	public void adicionarServidor(Usuario usuario, Dedicacao dedicacao, Funcao funcao) {
		if (!servidorRepository.existsBySiape(usuario.getSiape())) {

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
			servidor.setFuncao(funcao);
			servidor.setSiape(usuario.getSiape());

			servidorRepository.save(servidor);
		}
	}

	@Override
	public void cadastrarServidores() {
		List<Usuario> tecnicos = usuarioService.getByAffiliation("sta");
		for (Usuario usuario : tecnicos) {
			adicionarServidor(usuario, Dedicacao.H40, Funcao.STA);
		}

		List<Usuario> docentes = usuarioService.getByAffiliation("docente");
		for (Usuario usuario : docentes) {
			adicionarServidor(usuario, Dedicacao.EXCLUSIVA, Funcao.DOCENTE);
		}

	}

	@Override
	public List<Servidor> findByPessoa_idNotIn(Integer idCoordenador) {
		return servidorRepository.findByPessoa_idNotIn(idCoordenador);
	}

}
