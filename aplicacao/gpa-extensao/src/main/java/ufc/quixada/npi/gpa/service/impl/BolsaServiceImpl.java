package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.CADASTRAR_FREQUENCIA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_FALHA_ATRIBUIR_FREQUENCIA;
import static ufc.quixada.npi.gpa.util.Constants.REMOVER_FREQUENCIA;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
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

				Integer anoInicio = inicio.get(Calendar.YEAR);
				Integer anoTermino = termino.get(Calendar.YEAR);
				Integer mesInicio = inicio.get(Calendar.MONTH) + 1;
				Integer mesTermino = termino.get(Calendar.MONTH) + 1;

				if ((ano >= anoInicio && mes >= mesInicio) && (ano <= anoTermino && mes <= mesTermino)) {

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
