package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.CADASTRAR_FREQUENCIA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_FALHA_ATRIBUIR_FREQUENCIA;
import static ufc.quixada.npi.gpa.util.Constants.REMOVER_FREQUENCIA;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.FrequenciaBolsista;
import ufc.quixada.npi.gpa.model.FrequenciaView;
import ufc.quixada.npi.gpa.repository.BolsaRepository;
import ufc.quixada.npi.gpa.service.BolsaService;

@Service
public class BolsaServiceImpl implements BolsaService {

	@Autowired
	private BolsaRepository bolsaRepository;

	@Override
	public void salvarBolsa(Bolsa bolsa, AcaoExtensao acao) {
		if(acao != null) {
			if(bolsa != null) {
				bolsa.setAcaoExtensao(acao);
				bolsa.setAtivo(true);
				
				bolsaRepository.save(bolsa);
			}
		}
	}
	
	@Override
	public void encerrarBolsa(Bolsa bolsa, Date data) {
		if(bolsa != null) {
			bolsa.setAtivo(false);
			bolsa.setTermino(data);
			bolsaRepository.save(bolsa);
		}
	}
	
	@Override
	public List<Bolsa> listarBolsasAcao(Integer acaoId) {
		return bolsaRepository.findByAcaoExtensao_id(acaoId);
	}
	
	@Override
	public void deletarBolsa(Integer bolsaId) {
		bolsaRepository.delete(bolsaId);
	}
	
	@Override
	public Bolsa buscarBolsa(Integer bolsaId) {
		return bolsaRepository.findOne(bolsaId);
	}
	
	@Override
	public List<Bolsa> listarBolsasAluno(Integer idAluno) {
		return bolsaRepository.findByBolsista_id(idAluno);
	}
	
	@Override
	public List<FrequenciaView> getBolsas(Integer ano) {
		List<Bolsa> bolsas = bolsaRepository.findByYear(ano);

		List<FrequenciaView> frequenciasView = new ArrayList<FrequenciaView>();
		for (Bolsa bolsa : bolsas) {
			FrequenciaView frequenciaView = new FrequenciaView(bolsa);

			for (FrequenciaBolsista frequenciaBolsista : bolsa.getFrequencias()) {
				frequenciaView.addFrequencia(frequenciaBolsista.getMes(), frequenciaBolsista);
			}

			frequenciasView.add(frequenciaView);

		}

		return frequenciasView;
	}

	@Override
	public void setarFrequencia(Integer bolsaId, Integer mes, Integer ano, String acao) throws GpaExtensaoException {

		Bolsa bolsa = bolsaRepository.findOne(bolsaId);

		if (bolsa != null) {
			switch (acao) {
			case CADASTRAR_FREQUENCIA:

				Calendar inicio = Calendar.getInstance();
				inicio.setTime(bolsa.getInicio());

				Calendar termino = Calendar.getInstance();
				termino.setTime(bolsa.getTermino());

				Integer mesInicio = inicio.get(Calendar.MONTH) + 1;
				Integer mesTermino = termino.get(Calendar.MONTH) + 1;
				
				if ((mes >= mesInicio) && (mes <= mesTermino)) {

					bolsa.addFrequencia(new FrequenciaBolsista(bolsa, mes, ano));
					bolsaRepository.save(bolsa);

				} else {
					throw new GpaExtensaoException(EXCEPTION_FALHA_ATRIBUIR_FREQUENCIA);
				}

				break;

			case REMOVER_FREQUENCIA:

				bolsa.removeFrequencia(mes, ano);
				bolsaRepository.save(bolsa);

				break;

			default:
				throw new GpaExtensaoException(EXCEPTION_FALHA_ATRIBUIR_FREQUENCIA);
			}

		} else {
			throw new GpaExtensaoException(EXCEPTION_FALHA_ATRIBUIR_FREQUENCIA);
		}

	}

}
