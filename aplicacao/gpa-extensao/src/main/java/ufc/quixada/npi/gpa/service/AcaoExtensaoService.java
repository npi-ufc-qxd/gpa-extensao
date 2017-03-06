package ufc.quixada.npi.gpa.service;

import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;

import java.util.List;

public interface AcaoExtensaoService {

	List<AcaoExtensao> findAcoesByPessoa(Pessoa pessoa);

	List<AcaoExtensao> findAcoesHomologadas();

	List<AcaoExtensao> findAcoesEmTramitacao();





	void salvarAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException;

	void salvarAcaoRetroativa(AcaoExtensao acaoExtensao, MultipartFile arquivo, Integer cargaHorariaCoordenador)
			throws GpaExtensaoException;

	void submeterAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException;

	void editarAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException;

	void deletarAcaoExtensao(Integer idAcao, String cpfCoordenador) throws GpaExtensaoException;
	
	void encerrarAcao(Integer idAcao) throws GpaExtensaoException;
	
	void salvarRelatorioFinal(Integer acaoId, MultipartFile arquivo) throws GpaExtensaoException;

    List<AcaoExtensao> findAll(Pessoa pessoa);

	List<AcaoExtensao> findByParecer(Pessoa pessoa);

	List<AcaoExtensao> findProgramasAprovados();



}

