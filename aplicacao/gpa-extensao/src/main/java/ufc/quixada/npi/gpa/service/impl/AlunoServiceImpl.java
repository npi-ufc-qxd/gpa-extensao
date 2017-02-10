package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ALUNO_JA_CADASTRADO;
import static ufc.quixada.npi.gpa.util.Constants.USUARIO_NAO_ENCONTRADO_EXCEPTION;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;
import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.model.Papel;
import ufc.quixada.npi.gpa.model.Papel.Tipo;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AlunoRepository;
import ufc.quixada.npi.gpa.repository.PapelRepository;
import ufc.quixada.npi.gpa.service.AlunoService;

@Service
public class AlunoServiceImpl implements AlunoService {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private PapelRepository papelRepository;

	@Override
	public Usuario find(String cpf) throws GpaExtensaoException {
		Usuario usuario = usuarioService.getByCpf(cpf);

		if (usuario == null) {
			throw new GpaExtensaoException(USUARIO_NAO_ENCONTRADO_EXCEPTION);
		}

		return usuario;
	}

	@Override
	public Integer adicionar(Usuario usuario) throws GpaExtensaoException {
		if (alunoRepository.existsByMatricula(usuario.getMatricula())) {
			throw new GpaExtensaoException(EXCEPTION_ALUNO_JA_CADASTRADO);
		}

		Pessoa pessoa = new Pessoa();
		pessoa.setNome(usuario.getNome());
		pessoa.setEmail(usuario.getEmail());
		pessoa.setCpf(usuario.getCpf());

		List<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papelRepository.findByNome(Tipo.ALUNO));
		pessoa.setPapeis(papeis);

		Aluno aluno = new Aluno();
		aluno.setPessoa(pessoa);
		aluno.setMatricula(usuario.getMatricula());
		aluno.setCurso(usuario.getCurso());

		alunoRepository.save(aluno);
		
		return aluno.getId();
	}

	@Override
	public List<Aluno> findAllAlunos() {
		return alunoRepository.findAll();
	}

}
