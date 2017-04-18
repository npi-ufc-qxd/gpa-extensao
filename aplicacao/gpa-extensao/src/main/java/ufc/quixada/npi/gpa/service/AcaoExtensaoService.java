package ufc.quixada.npi.gpa.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface AcaoExtensaoService {

	/**
	 * Retorna todas as ações que uma pessoa coordena ou participa
	 */
	List<AcaoExtensao> findAcoesByPessoa(Pessoa pessoa);

	/**
	 * Retorna todas as ações já homologadas (com status aprovado ou reprovado)
	 */
	List<AcaoExtensao> findAcoesHomologadas();

	/**
	 * Retorna todas as ações que estão em tramitação (que não tenham sido aprovadas ou reprovadas ainda)
	 */
	List<AcaoExtensao> findAcoesEmTramitacao();

	/**
	 * Retorna todas as acoes que já foram homologadas e estão em andamento
	 */
	List<AcaoExtensao> findAcoesEmAndamento();

	/**
	 * Retorna todas as ações que já estão encerradas
	 */
	List<AcaoExtensao> findAcoesEncerradas();

	/**
	 * Retorna todas as ações que estão aguardando parecer técnico ou do relator de um determinado parecerista ou relator
	 * e também as que estão aguardando as resoluções de pendências
	 */
	List<AcaoExtensao> findAcoesAguardandoParecer(Pessoa parecerista);

	/**
	 * Retorna todas as ações que cujos pareceres já foram emitidos de um determinado parecerista ou relator
	 */
	List<AcaoExtensao> findAcoesParecerEmitido(Pessoa parecerista);

	/**
	 * Retorna a quantidade de ações em tramitação
	 */
	int countAcoesEmTramitacao();

	/**
	 * Retorna a quantidade de ações em andamento
	 */
	int countAcoesEmAndamento();

	/**
	 * Retorna a quantidade de ações já encerradas
	 */
	int countAcoesEncerradas();
	
	/**
	 * Retorna a quantidade de minhas Ações
	 */
	int countMinhasAcoes(Pessoa pessoa);

	/**
	 * Retorna a quantidade de minhas ações aguardando parecer
	 */
	int countMinhasAcoesAguardandoParecer(Pessoa pessoa);

	/**
	 * Retorna a quantidade de minhas ações com pareceres emitidos
	 */
	int countMinhasAcoesPareceresEmitidos(Pessoa pessoa);

	/**
	 * Cadastra uma nova ação de extensão
	 */
	void cadastrar(AcaoExtensao acaoExtensao, MultipartFile arquivo, Pessoa coordenador) throws GpaExtensaoException;

	void salvarAcaoRetroativa(AcaoExtensao acaoExtensao, MultipartFile arquivo, Integer cargaHorariaCoordenador)
			throws GpaExtensaoException;

	void submeterAcaoExtensao(AcaoExtensao acaoExtensao, Pessoa pessoaLogada) throws GpaExtensaoException;

	void editarAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException;

	void deletarAcaoExtensao(Integer idAcao, String cpfCoordenador) throws GpaExtensaoException;
	
	void encerrarAcao(Integer idAcao) throws GpaExtensaoException;
	
	void salvarRelatorioFinal(Integer acaoId, MultipartFile arquivo) throws GpaExtensaoException;

    List<AcaoExtensao> findAll(Pessoa pessoa);

	List<AcaoExtensao> findProgramasAprovados();
}

