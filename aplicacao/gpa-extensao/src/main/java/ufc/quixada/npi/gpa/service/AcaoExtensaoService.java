package ufc.quixada.npi.gpa.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Aluno.Curso;

public interface AcaoExtensaoService {

	void salvarAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException;

	void salvarAcaoRetroativa(AcaoExtensao acaoExtensao, MultipartFile arquivo, Integer cargaHorariaCoordenador)
			throws GpaExtensaoException;

	void submeterAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException;

	void editarAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException;

	void deletarAcaoExtensao(Integer idAcao, String cpfCoordenador) throws GpaExtensaoException;
	
	void encerrarAcao(Integer idAcao) throws GpaExtensaoException;
	
	List<AcaoExtensao> buscarAcoesCursoAno(Curso curso, Integer ano) throws GpaExtensaoException;
	
	List<AcaoExtensao> buscarTodasParticipacoes(List<AcaoExtensao> acoesEspecification, List<AcaoExtensao> acoesParticipacao) throws GpaExtensaoException;

	void salvarRelatorioFinal(Integer acaoId, MultipartFile arquivo) throws GpaExtensaoException;
}
