package ufc.quixada.npi.gpa.service;

import java.util.Date;
import java.util.List;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.FrequenciaView;

public interface BolsaService {

	List<FrequenciaView> getBolsas(Integer ano);

	void setarFrequencia(Integer bolsaId, Integer mes, Integer ano, String acao) throws GpaExtensaoException;
	
	/*
	 * Salva uma bolsa na ação
	 */
	void salvarBolsa(Bolsa bolsa, AcaoExtensao acao);
	
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
}
