package ufc.quixada.npi.gpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.ParceriaExterna;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParceiroRepository;
import ufc.quixada.npi.gpa.repository.ParceriaExternaRepository;
import ufc.quixada.npi.gpa.service.ParceriaExternaService;

@Service
public class ParceriaExternaServiceImpl implements ParceriaExternaService {

	@Autowired
	private ParceriaExternaRepository parceriaExternaRepository;

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@Override
	public void adicionarParceriaExterna(ParceriaExterna parceriaExterna, Integer acaoExtensao, Integer parceiro) {
		AcaoExtensao acaoOld = acaoExtensaoRepository.findOne(acaoExtensao);
		
		Parceiro novoParceiro = null;
		if(parceiro != null){
			System.err.println("Id Parceiro não nulo");
			novoParceiro = parceiroRepository.findOne(parceiro);
		}else{
			System.err.println("Id Parceiro nulo");
		}		
		if (acaoOld != null) {
			if (novoParceiro != null) {
				parceriaExterna.setParceiro(novoParceiro);
			}
			acaoOld.getParceriasExternas().add(parceriaExterna);
			parceriaExterna.setAcaoExtensao(acaoOld);
			parceriaExternaRepository.save(parceriaExterna);
			acaoExtensaoRepository.save(acaoOld);
		}

	}

}
