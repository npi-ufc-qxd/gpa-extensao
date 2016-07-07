package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ERROR_UPPERCASE;
import static ufc.quixada.npi.gpa.util.Constants.FRAGMENTS_TABLE_PARCERIAS_EXTERNAS;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_CADASTRO_SUCESSO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_STATUS_RESPONSE;
import static ufc.quixada.npi.gpa.util.Constants.OK_UPPERCASE;
import static ufc.quixada.npi.gpa.util.Constants.RESPONSE_DATA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.ParceriaExterna;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParceiroRepository;
import ufc.quixada.npi.gpa.repository.ParceriaExternaRepository;
@Controller
@Transactional
@RequestMapping(value="/parceria")
public class ParceriaExternaController {
	
	@Autowired
	private ParceriaExternaRepository parceriaExternaRepository;
	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	@Autowired
	private ParceiroRepository parceiroRepository;
	
	@RequestMapping(value="/buscarParceriasExternas/{idAcao}", method=RequestMethod.GET)
	public String buscarParceriasExternas(@PathVariable("idAcao") Integer idAcao, Model model){
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		model.addAttribute("parceriasExternas", parceriaExternaRepository.findByAcaoExtensao(acao));
		return FRAGMENTS_TABLE_PARCERIAS_EXTERNAS;
	}
	
	@RequestMapping(value="/excluir/{idParceria}")
	public void deleteParceriaExterna(@PathVariable("idParceria") Integer idParceriaExterna){
		parceriaExternaRepository.delete(idParceriaExterna);
	}
	
	@RequestMapping(value="/salvar/{id}", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> novaParceriaExterna(@PathVariable("id") Integer id, @ModelAttribute @Valid ParceriaExterna parceria,
			Model model, BindingResult binding){
		Map<String, Object> map = new HashMap<String, Object>();
		if(binding.hasErrors()){
			map.put(MESSAGE_STATUS_RESPONSE, ERROR_UPPERCASE);
			map.put(RESPONSE_DATA, binding.getFieldErrors());
			return map;
		}
		AcaoExtensao acaoExtensao = acaoExtensaoRepository.findOne(id);
		parceria.setAcaoExtensao(acaoExtensao);
		parceriaExternaRepository.save(parceria);
		map.put(MESSAGE_STATUS_RESPONSE, OK_UPPERCASE);
		map.put(RESPONSE_DATA, MESSAGE_CADASTRO_SUCESSO);
		return map;
	}
	
	@RequestMapping(value="/buscarParceiros")
	public @ResponseBody List<Parceiro> buscarParceiros(){
		return parceiroRepository.findAll();
	}
}
