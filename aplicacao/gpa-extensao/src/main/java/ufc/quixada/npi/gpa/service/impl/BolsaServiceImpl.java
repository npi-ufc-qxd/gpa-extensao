package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.CADASTRAR_FREQUENCIA;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_ALUNO_JA_BOLSISTA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ACAO_MAXIMO_BOLSISTAS;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ACAO_NAO_ENCONTRADA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_FALHA_ATRIBUIR_FREQUENCIA;
import static ufc.quixada.npi.gpa.util.Constants.REMOVER_FREQUENCIA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ACAO_SEM_BOLSAS_RECEBIDAS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.FrequenciaBolsista;
import ufc.quixada.npi.gpa.model.FrequenciaView;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.BolsaRepository;
import ufc.quixada.npi.gpa.service.BolsaService;

@Service
public class BolsaServiceImpl implements BolsaService {

	@Autowired
	private BolsaRepository bolsaRepository;

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

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

	@Override
	public void adicionarBolsista(Integer acao, Bolsa bolsa) throws GpaExtensaoException {
		AcaoExtensao old = acaoExtensaoRepository.findOne(acao);
		if (old != null) {
			if(old.getBolsasRecebidas() == null){
				throw new GpaExtensaoException(EXCEPTION_ACAO_SEM_BOLSAS_RECEBIDAS);
			}
			if (old.getBolsistas().size() == old.getBolsasRecebidas()) {
				throw new GpaExtensaoException(EXCEPTION_ACAO_MAXIMO_BOLSISTAS);
			}
			for (int i = 0; i < old.getBolsistas().size(); i++) {
				if (old.getBolsistas().get(i).getBolsista().getPessoa().getCpf()
						.equalsIgnoreCase(bolsa.getBolsista().getPessoa().getCpf())) {
					throw new GpaExtensaoException(ERROR_ALUNO_JA_BOLSISTA);
				}
			}
			old.getBolsistas().add(bolsa);
			bolsa.setAcaoExtensao(old);
			bolsa.setAtivo(true);
			bolsa.setInicio(old.getInicio());
			bolsa.setTermino(old.getTermino());
			bolsa.setCargaHoraria(12);
			bolsaRepository.save(bolsa);
			acaoExtensaoRepository.save(old);

		} else {
			throw new GpaExtensaoException(EXCEPTION_ACAO_NAO_ENCONTRADA);
		}
	}

}
