package ufc.quixada.npi.gpa.service;

import java.util.Date;
import java.util.List;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.FrequenciaView;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface BolsaService {

	List<FrequenciaView> getBolsas(Integer ano);

	void setarFrequencia(Integer bolsaId, Integer mes, Integer ano, String acao) throws GpaExtensaoException;
	
	void adicionarBolsista(AcaoExtensao acao, Bolsa bolsista)throws GpaExtensaoException;
	
	void removerBolsista(AcaoExtensao acao, Bolsa bolsista, Pessoa coordenador)throws GpaExtensaoException;

	/*
	 * Salva uma bolsa na ação
	 */
	void salvarBolsa(Bolsa bolsa, AcaoExtensao acao) throws GpaExtensaoException;
	
	/*
	 * Encerra uma bolsa
	 */
	void encerrarBolsa(Bolsa bolsa, Date data);
	
	/*
	 * Buscar todas as bolsas de uma determinada ação
	 */
	List<Bolsa> listarBolsasAcao(Integer acaoId);
	
	/*
	 * Deleta uma bolsa
	 */
	void deletarBolsa(Integer bolsaId);
	
	/*
	 * Retorna uma bolsa
	 */
	Bolsa buscarBolsa(Integer bolsaId);
	
	/*
	 * Retorna as bolsas de um aluno que é bolsista
	 */
	List<Bolsa> listarBolsasAluno(Integer alunoId);
	
	void alterarDataParticipacao(AcaoExtensao acaoExtensao, Bolsa bolsa, Pessoa pessoa, Date dataInicio,
			Date dataTermino) throws GpaExtensaoException;
	
	Bolsa buscarBolsa(Bolsa bolsa);

}
