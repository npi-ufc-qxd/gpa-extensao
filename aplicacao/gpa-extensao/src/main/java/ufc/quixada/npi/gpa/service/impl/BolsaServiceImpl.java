package ufc.quixada.npi.gpa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void adicionarFrequencia(Integer bolsaId, Integer mes, Integer ano) {

		Bolsa bolsa = bolsaRepository.findOne(bolsaId);
		bolsa.addFrequencia(new FrequenciaBolsista(bolsa, mes, ano));
		bolsaRepository.save(bolsa);

	}

}
