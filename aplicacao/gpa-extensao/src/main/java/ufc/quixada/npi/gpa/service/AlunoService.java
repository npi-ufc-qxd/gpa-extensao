package ufc.quixada.npi.gpa.service;

import java.util.List;

import br.ufc.quixada.npi.ldap.model.Usuario;
import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.Aluno;

public interface AlunoService {

    /**
     * Busca um aluno pelo cpf
     */
    Usuario find(String cpf) throws GpaExtensaoException;

    /**
     * Cadastra um novo aluno no sistema
     */
	void adicionarAluno(Usuario usuario);

    /**
     * Busca todos os alunos cadastrados
     */
	List<Aluno> findAllAlunos();

    /**
     * Busca todos os alunos no LDAP e cadastra aqueles que ainda não estão cadastrados no sistema
     */
	void cadastrarAlunos();
	
	/**
     * Busca um aluno cadastrado
     */
	Aluno buscarAluno(Integer idAluno);
}
