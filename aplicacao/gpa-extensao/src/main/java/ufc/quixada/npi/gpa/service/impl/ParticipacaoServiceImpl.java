package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.ERROR_ADICIONAR_PARTICIPANTE_NAO_PERMITIDO;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_PESSOA_JA_PARTICIPANTE;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_QTD_HORAS_NAO_PERMITIDA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_DATA_INVALIDA;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_PERMISSAO_NEGADA;
import static ufc.quixada.npi.gpa.util.Constants.VALOR_INVALIDO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.generation.pdf.BuilderPDFReport;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Aluno;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Participacao.Funcao;
import ufc.quixada.npi.gpa.model.Participacao.Instituicao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.model.Servidor;
import ufc.quixada.npi.gpa.model.Servidor.Dedicacao;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.BolsaRepository;
import ufc.quixada.npi.gpa.repository.ParticipacaoRepository;
import ufc.quixada.npi.gpa.repository.ServidorRepository;
import ufc.quixada.npi.gpa.service.AlunoService;
import ufc.quixada.npi.gpa.service.ParticipacaoService;
import ufc.quixada.npi.gpa.service.PessoaService;

@Service
public class ParticipacaoServiceImpl implements ParticipacaoService {

	@Autowired
	private ServidorRepository servidorRepository;

	@Autowired
	private ParticipacaoRepository participacaoRepository;

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;
	
	@Autowired
	private BolsaRepository bolsaRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private AlunoService alunoService;

	@Override
	public Participacao participacaoCoordenador(AcaoExtensao acaoExtensao, Integer cargaHoraria) {
		Participacao participacao = new Participacao();
		participacao.setAcaoExtensao(acaoExtensao);
		participacao.setCoordenador(true);
		participacao.setCargaHoraria(cargaHoraria);
		participacao.setCpfParticipante("");
		participacao.setDataInicio(acaoExtensao.getInicio());
		participacao.setDataTermino(acaoExtensao.getTermino());
		participacao.setDescricaoFuncao("");
		participacao.setInstituicao(Instituicao.UFC);
		participacao.setNomeInstituicao("");
		participacao.setNomeParticipante("");
		participacao.setParticipante(acaoExtensao.getCoordenador());

		Servidor servidor = servidorRepository.findByPessoa_cpf(acaoExtensao.getCoordenador().getCpf());
		if (servidor.getFuncao().equals(Servidor.Funcao.DOCENTE)) {
			participacao.setFuncao(Funcao.DOCENTE);
		} else if (servidor.getFuncao().equals(Servidor.Funcao.STA)) {
			participacao.setFuncao(Funcao.STA);
		}

		participacaoRepository.save(participacao);
		return participacao;
	}

	@Override
	public void adicionarParticipanteEquipeTrabalho(AcaoExtensao acaoExtensao, Participacao participacao,
			Pessoa coordenador) throws GpaExtensaoException {

		AcaoExtensao old = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		if (old != null) {

			if (participacao.getParticipante() != null) {
				participacao.setCpfParticipante(participacao.getParticipante().getCpf());
				participacao.setNomeParticipante(participacao.getParticipante().getNome());
			}
			if (!old.getStatus().equals(Status.NOVO) && !old.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_PARECER)
					&& !old.getStatus().equals(Status.RESOLVENDO_PENDENCIAS_RELATO)
					&& !old.getStatus().equals(Status.APROVADO)) {
				throw new GpaExtensaoException(ERROR_ADICIONAR_PARTICIPANTE_NAO_PERMITIDO);
			}

			if (participacao.getDataInicio() == null || participacao.getDataTermino() == null
					|| participacao.getDataInicio().before(old.getInicio())
					|| participacao.getDataTermino().after(old.getTermino())
					|| participacao.getDataInicio().after(old.getTermino())
					|| participacao.getDataTermino().before(old.getInicio())
					|| participacao.getDataTermino().before(participacao.getDataInicio())) {
				throw new GpaExtensaoException(EXCEPTION_DATA_INVALIDA);
			}

			participacao.setCoordenador(false);
			participacao.setAcaoExtensao(acaoExtensao);

			if (!acaoExtensao.getCoordenador().getCpf().equalsIgnoreCase(coordenador.getCpf())) {
				throw new GpaExtensaoException(MENSAGEM_PERMISSAO_NEGADA);
			} else if (participacao.getFuncao().equals(Funcao.OUTRA)
					&& (participacao.getDescricaoFuncao().replaceAll(" ", "").isEmpty()
							|| participacao.getCpfParticipante().replaceAll(" ", "").isEmpty()
							|| participacao.getNomeParticipante().replaceAll(" ", "").isEmpty())) {

				throw new GpaExtensaoException(VALOR_INVALIDO);

			} else if (!participacao.getInstituicao().equals(Instituicao.UFC)) {
				if (participacao.getNomeInstituicao().replaceAll(" ", "").isEmpty()) {
					throw new GpaExtensaoException(VALOR_INVALIDO);
				}

			} else if (participacao.getCargaHoraria() < 4 || participacao.getCargaHoraria() > 16) {
				throw new GpaExtensaoException(ERROR_QTD_HORAS_NAO_PERMITIDA);
			} else if (participacao.getParticipante() != null) {
				Servidor servidor = servidorRepository.findByPessoa_cpf(participacao.getCpfParticipante());
				if ((servidor.getDedicacao().equals(Dedicacao.H20)) && participacao.getCargaHoraria() > 12) {
					throw new GpaExtensaoException(ERROR_QTD_HORAS_NAO_PERMITIDA);
				}

			}
			for (Participacao p : acaoExtensao.getEquipeDeTrabalho()) {
				if (p.getCpfParticipante().equalsIgnoreCase(participacao.getCpfParticipante())) {
					throw new GpaExtensaoException(ERROR_PESSOA_JA_PARTICIPANTE);
				}
			}
			acaoExtensao.getEquipeDeTrabalho().add(participacao);
			acaoExtensaoRepository.save(acaoExtensao);
		}

	}

	@Override
	public ByteArrayInputStream emitirDeclaracaoParticipanteEquipeTrabalho(Integer idAcaoExtensao
			, Integer idParticipante) throws DocumentException, MalformedURLException, IOException {
		BuilderPDFReport builderPDF = new BuilderPDFReport();
		
		Pessoa pessoa = pessoaService.buscarPorId(idParticipante);
	    AcaoExtensao acaoExtensao = acaoExtensaoRepository.findOne(idAcaoExtensao);
	    Aluno aluno = alunoService.findByPessoa(pessoa);
	    Bolsa bolsista = bolsaRepository.findByAcaoExtensaoAndBolsista(acaoExtensao, aluno);
	    
	    Participacao participante = participacaoRepository.findByParticipanteAndAcaoExtensao(pessoa, acaoExtensao);
	    
	    if(participante == null){
			ByteArrayInputStream bis = builderPDF.gerarPdfBolsista(acaoExtensao, bolsista);
			return bis;
	    }
	    		
		ByteArrayInputStream bis = builderPDF.gerarPdfParticipante(acaoExtensao, participante);
		
		return bis;
		
	}

}
