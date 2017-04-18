package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.Constants.ERRO;
import static ufc.quixada.npi.gpa.util.Constants.FRAGMENTS_TABLE_PARCERIAS_EXTERNAS;
import static ufc.quixada.npi.gpa.util.RedirectConstants.R_ACAO;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Parceiro;
import ufc.quixada.npi.gpa.model.ParceriaExterna;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.ParceiroRepository;
import ufc.quixada.npi.gpa.repository.ParceriaExternaRepository;
import ufc.quixada.npi.gpa.service.ParceriaExternaService;

@Controller
@Transactional
@RequestMapping(value = "/parceria")
public class ParceriaExternaController {

	@Autowired
	private ParceriaExternaRepository parceriaExternaRepository;
	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	@Autowired
	private ParceiroRepository parceiroRepository;

	@Autowired
	private ParceriaExternaService parceriaExternaService;

	@RequestMapping(value = "/buscarParceriasExternas/{idAcao}", method = RequestMethod.GET)
	public String buscarParceriasExternas(@PathVariable("idAcao") Integer idAcao, Model model) {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);
		model.addAttribute("parceriasExternas", parceriaExternaRepository.findByAcaoExtensao(acao));
		model.addAttribute("cpfCoordenador", acao.getCoordenador().getCpf());
		return FRAGMENTS_TABLE_PARCERIAS_EXTERNAS;
	}

	@RequestMapping(value = "/excluir/{id}/{acao}")
	public String deleteParceriaExterna(@PathVariable("id") Integer idParceriaExterna, @PathVariable("acao") Integer acao) {
		parceriaExternaService.excluirParceriaExterna(idParceriaExterna);
		return R_ACAO + acao;
	}

	@RequestMapping(value = "/salvar/{idAcao}", method = RequestMethod.POST)
	public String novaParceriaExterna(@PathVariable("idAcao") Integer idAcao,
			@ModelAttribute @Valid ParceriaExterna parceria,
			@RequestParam(required = false) Integer parceiro, RedirectAttributes redirectAttribute) {

		try {
			parceriaExternaService.adicionarParceriaExterna(parceria, idAcao, parceiro);
		} catch (GpaExtensaoException e) {
			redirectAttribute.addAttribute(ERRO, e.getMessage());
		}

		return R_ACAO + idAcao;
	}

	@RequestMapping(value = "/buscarParceiros")
	public @ResponseBody List<Parceiro> buscarParceiros() {
		return parceiroRepository.findAll();
	}
}
