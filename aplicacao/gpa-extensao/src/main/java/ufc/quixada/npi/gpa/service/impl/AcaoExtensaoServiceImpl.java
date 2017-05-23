package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ACAO_JA_ENCERRADA;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_STATUS_ACAO_NAO_PERMITE_ENCERRAMENTO;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_ACAO_EXTENSAO_INEXISTENTE;

import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_DATA_IGUAL_MAIOR;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_DATA_MENOR;

import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_PERMISSAO_NEGADA;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_TRANSFERENCIA_MESMO_COORDENADOR;

import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_DATA_HOMOLOGACAO_MAIOR;
import static ufc.quixada.npi.gpa.util.Constants.MENSAGEM_DATA_HOMOLOGACAO_MENOR;




import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.repository.BolsaRepository;
import ufc.quixada.npi.gpa.repository.ParecerRepository;
import ufc.quixada.npi.gpa.repository.ParticipacaoRepository;
import ufc.quixada.npi.gpa.repository.PessoaRepository;
import ufc.quixada.npi.gpa.service.AcaoExtensaoService;
import ufc.quixada.npi.gpa.service.DocumentoService;
import ufc.quixada.npi.gpa.service.NotificationService;
import ufc.quixada.npi.gpa.service.ParticipacaoService;

@Service
public class AcaoExtensaoServiceImpl implements AcaoExtensaoService {

	@Autowired
	private AcaoExtensaoRepository acaoExtensaoRepository;

	@Autowired
	private DocumentoService documentoService;

	@Autowired
	private ParticipacaoService participacaoService;

	@Autowired
	private BolsaRepository bolsaRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ParecerRepository parecerRepository;

	@Autowired
	private ParticipacaoRepository participacaoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public List<AcaoExtensao> findAcoesByPessoa(Pessoa pessoa) {
		return acaoExtensaoRepository.findByParticipacao(pessoa);
	}

	@Override
	public List<AcaoExtensao> findAcoesHomologadas() {
		return acaoExtensaoRepository.findByStatusIn(Arrays.asList(Status.APROVADO, Status.REPROVADO));
	}

	@Override
	public List<AcaoExtensao> findAcoesEmTramitacao() {
		return acaoExtensaoRepository.findByStatusNotIn(Arrays.asList(Status.APROVADO, Status.REPROVADO));
	}

	@Override
	public List<AcaoExtensao> findAcoesEmAndamento() {
		return acaoExtensaoRepository.findByAtivoAndStatus(true, Status.APROVADO);
	}

	@Override
	public List<AcaoExtensao> findAcoesEncerradas() {
		return acaoExtensaoRepository.findByAtivo(false);
	}

	@Override
	public void cadastrar(AcaoExtensao acaoExtensao, MultipartFile arquivo, Pessoa coordenador)
			throws GpaExtensaoException {
		acaoExtensao.setCoordenador(coordenador);
		acaoExtensao.setAtivo(true);
		acaoExtensao.setStatus(Status.NOVO);
		salvarAcao(acaoExtensao, arquivo);

	}

	@Override
	public void salvarAcaoRetroativa(AcaoExtensao acaoExtensao, MultipartFile arquivo, Integer cargaHorariaCoordenador)
			throws GpaExtensaoException {
		acaoExtensao.setStatus(Status.APROVADO);
		acaoExtensao.setAtivo(true);
		salvarAcao(acaoExtensao, arquivo);
		participacaoService.participacaoCoordenador(acaoExtensao, cargaHorariaCoordenador);
	}

	private void salvarAcao(AcaoExtensao acaoExtensao, MultipartFile arquivo) throws GpaExtensaoException {
		acaoExtensaoRepository.save(acaoExtensao);

		String idAcao = acaoExtensao.getId().toString();
		idAcao = completeToLeft(idAcao, '0', 4);
		acaoExtensao.setIdentificador("EXT-".concat(idAcao));

		if (!(arquivo.getOriginalFilename().toString().equals(""))) {
			Documento documento = documentoService.save(arquivo, acaoExtensao);
			if (documento != null) {
				acaoExtensao.setAnexo(documento);
			}
		}

		acaoExtensaoRepository.save(acaoExtensao);
	}
	
	@Override
	public void homologarAcaoExtensao(AcaoExtensao acao, String resultado, String dataHomologacao, String observacao)
			throws GpaExtensaoException, ParseException {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dataH = df.parse(dataHomologacao);
		
		if(acao == null) {
			throw new GpaExtensaoException(MENSAGEM_ACAO_EXTENSAO_INEXISTENTE);
		}
		
		if(dataH.before(acao.getInicio())) {
			throw new GpaExtensaoException(MENSAGEM_DATA_HOMOLOGACAO_MENOR);
		}
		
		if(dataH.after(acao.getTermino())) {
			throw new GpaExtensaoException(MENSAGEM_DATA_HOMOLOGACAO_MAIOR);
		}
		
		acao.setDataDeHomologacao(dataH);
		acao.setObservacaoHomologacao(observacao);
		
		if("APROVADO".equals(resultado)) {
			acao.setStatus(Status.APROVADO);
		}else {
			acao.setStatus(Status.REPROVADO);
		}
		
		acaoExtensaoRepository.save(acao);
	}
	
	@Override
	public void salvarAcaoBolsasRecebidas(AcaoExtensao acao, Integer numeroBolsas) throws GpaExtensaoException{
		if(acao == null) {
			throw new GpaExtensaoException("A ação não existe ou o código informado está vazio ");
		} else {
			if (acao.getBolsasSolicitadas() >= numeroBolsas) {
				acao.setBolsasRecebidas(numeroBolsas);
				acaoExtensaoRepository.save(acao);
			}
		}
	}

	@Override
	public void salvarCodigoAcao(AcaoExtensao acao, String codigo) throws GpaExtensaoException {
		String codigoUpper = codigo.toUpperCase();

		if (acao == null || codigoUpper.isEmpty()) {
			throw new GpaExtensaoException("A ação não existe ou o código informado está vazio ");
		}

		acao.setCodigo(codigoUpper);
		acaoExtensaoRepository.save(acao);
	}

	@Override
	public void transeferirCoordenacao(AcaoExtensao acao, Integer idNovoCoordenador, String dataInicio,
			Integer cargaHoraria) throws ParseException, GpaExtensaoException {

		if (acao == null) {
			throw new GpaExtensaoException(MENSAGEM_ACAO_EXTENSAO_INEXISTENTE);
		}

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dataI = df.parse(dataInicio);

		if (dataI.equals(acao.getTermino()) || dataI.after(acao.getTermino())) {
			throw new GpaExtensaoException(MENSAGEM_DATA_IGUAL_MAIOR);
		}

		if (dataI.before(acao.getInicio())) {
			throw new GpaExtensaoException(MENSAGEM_DATA_MENOR);
		}

		Pessoa antigoCoordenador = acao.getCoordenador();
		Pessoa novoCoordenador = pessoaRepository.findOne(idNovoCoordenador);

		if (!antigoCoordenador.equals(novoCoordenador)) {

			List<Participacao> participacoesAntigoCoordenador = participacaoRepository
					.findByAcaoExtensaoAndParticipante(acao, antigoCoordenador);

			for (Participacao p : participacoesAntigoCoordenador) {
				if (p.isCoordenador()) {
					p.setDataTermino(dataI);
					p.setCoordenador(false);
					participacaoRepository.save(p);
				}
			}

			List<Participacao> participacoesNovoCoordenador = participacaoRepository
					.findByAcaoExtensaoAndParticipante(acao, novoCoordenador);

			for (Participacao p : participacoesNovoCoordenador) {
				if (p.equals(novoCoordenador)) {
					p.setDataTermino(acao.getTermino());
					participacaoRepository.save(p);
				}
			}

			acao.setCoordenador(novoCoordenador);
			Participacao novaParticipacaoNovoCoordenador = participacaoService.participacaoCoordenador(acao,
					cargaHoraria);
			novaParticipacaoNovoCoordenador.setDataInicio(dataI);

			participacaoRepository.save(novaParticipacaoNovoCoordenador);
			acaoExtensaoRepository.save(acao);
		}else {
			throw new GpaExtensaoException(MENSAGEM_TRANSFERENCIA_MESMO_COORDENADOR);
		}
	}

	@Override
	public void submeterAcaoExtensao(AcaoExtensao acaoExtensao, Pessoa pessoaLogada) throws GpaExtensaoException {

		if (!acaoExtensao.getCoordenador().getCpf().equals(pessoaLogada.getCpf())) {
			throw new GpaExtensaoException("Usuário logado não pode submeter a ação " + acaoExtensao.getCodigo()
					+ " pois não é o coordenador!");
		}

		AcaoExtensao old = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		old = checkAcaoExtensao(old, acaoExtensao);

		switch (old.getStatus()) {
		case RESOLVENDO_PENDENCIAS_PARECER:
			old.setStatus(Status.AGUARDANDO_PARECER_TECNICO);
			old.getParecerTecnico().setPendenciasResolvidas();
			break;

		case RESOLVENDO_PENDENCIAS_RELATO:
			old.setStatus(Status.AGUARDANDO_PARECER_RELATOR);
			old.getParecerRelator().setPendenciasResolvidas();
			break;

		default:
			old.setStatus(Status.AGUARDANDO_PARECERISTA);
			break;
		}

		acaoExtensaoRepository.save(old);

		notificar(old);
	}

	@Override
	public void editarAcaoExtensao(AcaoExtensao acaoExtensao, MultipartFile arquivo, boolean pendencia) throws GpaExtensaoException {
		AcaoExtensao old = acaoExtensaoRepository.findOne(acaoExtensao.getId());
		Documento documento = documentoService.save(arquivo, acaoExtensao);
		
		if (documento != null) {
			acaoExtensao.setAnexo(documento);
		}
		
		if(pendencia) {
			switch(old.getStatus()) {
				case RESOLVENDO_PENDENCIAS_PARECER:
					old.setStatus(Status.AGUARDANDO_PARECER_TECNICO);
					old.ultimaPendenciaParecer().setResolvida(true);
					notificationService.notificarResolucaoPendenciasParecer(old);
					break;
		
				case RESOLVENDO_PENDENCIAS_RELATO:
					old.setStatus(Status.AGUARDANDO_PARECER_RELATOR);
					old.ultimaPendenciaRelator().setResolvida(true);
					notificationService.notificarResolucaoPendenciasRelato(old);
					break;
		
				default:
					break; 
			}
		}
		
		old = checkAcaoExtensao(old, acaoExtensao);
		acaoExtensaoRepository.save(old);
	}

	@Override
	public void deletarAcaoExtensao(Integer idAcao, String cpfCoordenador) throws GpaExtensaoException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(idAcao);

		if (acao.getStatus().equals(Status.NOVO) && acao.getCoordenador().getCpf().equals(cpfCoordenador)) {
			acaoExtensaoRepository.delete(acao);

		} else {
			throw new GpaExtensaoException(MENSAGEM_PERMISSAO_NEGADA);
		}
	}

	private String completeToLeft(String value, char c, int size) {
		String result = value;
		while (result.length() < size) {
			result = c + result;
		}
		return result;
	}

	private AcaoExtensao checkAcaoExtensao(AcaoExtensao old, AcaoExtensao nova) {
		old.setTitulo(nova.getTitulo());
		old.setResumo(nova.getResumo());
		old.setInicio(nova.getInicio());
		old.setTermino(nova.getTermino());
		old.setModalidade(nova.getModalidade());
		old.setHorasPraticas(nova.getHorasPraticas());
		old.setHorasTeoricas(nova.getHorasTeoricas());
		old.setBolsasSolicitadas(nova.getBolsasSolicitadas());
		old.setProgramacao(nova.getProgramacao());
		old.setAnexo(nova.getAnexo());
		old.setBolsasSolicitadas(nova.getBolsasSolicitadas());
		old.setVinculo(nova.getVinculo());
		return old;
	}

	private void notificar(AcaoExtensao acaoExtensao) {
		this.notificationService.notificar(acaoExtensao);
	}

	@Override
	public void salvarRelatorioFinal(Integer acaoId, MultipartFile arquivo) throws GpaExtensaoException {
		AcaoExtensao acao = acaoExtensaoRepository.findOne(acaoId);

		Documento documento = null;

		if (acao != null) {
			documento = documentoService.save(arquivo, acao);
		}

		if (documento != null) {
			acao.setRelatorioFinal(documento);
		}

		acaoExtensaoRepository.save(acao);
	}

	@Override
	public List<AcaoExtensao> findAll(Pessoa pessoa) {
		return acaoExtensaoRepository.findByParticipacao(pessoa);
	}

	@Override
	public List<AcaoExtensao> findAcoesAguardandoParecer(Pessoa parecerista) {
		List<AcaoExtensao> acoesParecerista = acaoExtensaoRepository.findByPareceristaAndStatus(parecerista,
				Arrays.asList(Status.AGUARDANDO_PARECER_TECNICO, Status.RESOLVENDO_PENDENCIAS_PARECER));
		List<AcaoExtensao> acoesRelator = acaoExtensaoRepository.findByRelatorAndStatus(parecerista,
				Arrays.asList(Status.AGUARDANDO_PARECER_RELATOR, Status.RESOLVENDO_PENDENCIAS_RELATO));
		acoesParecerista.removeAll(acoesRelator);
		acoesParecerista.addAll(acoesRelator);
		return acoesParecerista;
	}

	@Override
	public List<AcaoExtensao> findAcoesParecerEmitido(Pessoa parecerista) {
		List<AcaoExtensao> acoesParecerista = acaoExtensaoRepository.findByPareceristaAndStatus(parecerista,
				Arrays.asList(Status.AGUARDANDO_RELATOR, Status.RESOLVENDO_PENDENCIAS_RELATO,
						Status.AGUARDANDO_PARECER_RELATOR, Status.AGUARDANDO_HOMOLOGACAO, Status.APROVADO,
						Status.REPROVADO));
		List<AcaoExtensao> acoesRelator = acaoExtensaoRepository.findByRelatorAndStatus(parecerista,
				Arrays.asList(Status.AGUARDANDO_HOMOLOGACAO, Status.APROVADO, Status.REPROVADO));
		acoesParecerista.removeAll(acoesRelator);
		acoesParecerista.addAll(acoesRelator);
		return acoesParecerista;
	}

	@Override
	public int countAcoesEmTramitacao() {
		return acaoExtensaoRepository.countByStatusNotIn(Arrays.asList(Status.APROVADO, Status.REPROVADO));
	}

	@Override
	public int countAcoesEmAndamento() {
		return acaoExtensaoRepository.countByAtivoAndStatus(true, Status.APROVADO);
	}

	@Override
	public int countAcoesEncerradas() {
		return acaoExtensaoRepository.countByAtivo(false);
	}

	@Override
	public List<AcaoExtensao> findProgramasAprovados() {
		return acaoExtensaoRepository.findByModalidadeAndStatus(AcaoExtensao.Modalidade.PROGRAMA, Status.APROVADO);
	}

	public AcaoExtensao findById(Integer idAcao) {
		return acaoExtensaoRepository.findOne(idAcao);
	}

	@Override
	public int countAcoesAguardandoPareceristaRelator() {
		return acaoExtensaoRepository
				.countByStatusIn(Arrays.asList(Status.AGUARDANDO_PARECERISTA, Status.AGUARDANDO_RELATOR));
	}

	@Override
	public int countAcoesAguardandoHomologacao() {
		return acaoExtensaoRepository.countByStatus(Status.AGUARDANDO_HOMOLOGACAO);
	}

	@Override
	public int countAcoesPendenciasParecer(Pessoa coordenador) {
		return acaoExtensaoRepository.countByCoordenadorAndStatus(coordenador, Status.RESOLVENDO_PENDENCIAS_PARECER);
	}

	@Override
	public int countAcoesPendenciasRelato(Pessoa coordenador) {
		return acaoExtensaoRepository.countByCoordenadorAndStatus(coordenador, Status.RESOLVENDO_PENDENCIAS_RELATO);
	}

	@Override
	public int countAcoesAguardandoParecer(Pessoa responsavel) {
		List<Parecer> pareceres = parecerRepository.findByResponsavel(responsavel);
		int qtdPareceresTecnico = acaoExtensaoRepository.countByParecerTecnicoInAndStatus(pareceres,
				Status.AGUARDANDO_PARECER_TECNICO);
		int qtdPareceresRelato = acaoExtensaoRepository.countByParecerRelatorInAndStatus(pareceres,
				Status.AGUARDANDO_PARECER_RELATOR);
		return qtdPareceresRelato + qtdPareceresTecnico;
	}

	@Override
	public String buscarCpfCoordenador(Integer acaoId) {
		return acaoExtensaoRepository.findCoordenadorById(acaoId);
	}

	@Override
	public int countMinhasAcoes(Pessoa pessoa) {
		return acaoExtensaoRepository.countByParticipacao(pessoa);
	}

	@Override
	public int countMinhasAcoesAguardandoParecer(Pessoa pessoa) {
		int acoesParecerista = acaoExtensaoRepository.countByPareceristaAndStatus(pessoa,
				Arrays.asList(Status.AGUARDANDO_PARECER_TECNICO, Status.RESOLVENDO_PENDENCIAS_PARECER));
		int acoesRelator = acaoExtensaoRepository.countByRelatorAndStatus(pessoa,
				Arrays.asList(Status.AGUARDANDO_PARECER_RELATOR, Status.RESOLVENDO_PENDENCIAS_RELATO));
		return acoesParecerista + acoesRelator;
	}

	@Override
	public int countMinhasAcoesPareceresEmitidos(Pessoa pessoa) {
		int acoesParecerista = acaoExtensaoRepository.countByPareceristaAndStatus(pessoa,
				Arrays.asList(Status.AGUARDANDO_RELATOR, Status.RESOLVENDO_PENDENCIAS_RELATO,
						Status.AGUARDANDO_PARECER_RELATOR, Status.AGUARDANDO_HOMOLOGACAO, Status.APROVADO,
						Status.REPROVADO));
		int acoesRelator = acaoExtensaoRepository.countByRelatorAndStatus(pessoa,
				Arrays.asList(Status.AGUARDANDO_HOMOLOGACAO, Status.APROVADO, Status.REPROVADO));
		return acoesParecerista + acoesRelator;

	}

	@Override
	public void encerrarAcao(AcaoExtensao acaoExtensao, Pessoa pessoa) throws GpaExtensaoException {
		AcaoExtensao old = acaoExtensaoRepository.findOne(acaoExtensao.getId());

		if (old != null) {
			if (!old.getStatus().equals(Status.APROVADO)) {
				throw new GpaExtensaoException(EXCEPTION_STATUS_ACAO_NAO_PERMITE_ENCERRAMENTO);
			}
			if (!pessoa.isDirecao()) {
				throw new GpaExtensaoException(MENSAGEM_PERMISSAO_NEGADA);
			}
			if (old.isAtivo()) {
				old.setAtivo(false);
			} else {
				throw new GpaExtensaoException(EXCEPTION_ACAO_JA_ENCERRADA);
			}
			bolsaRepository.inativarBolsas(acaoExtensao.getId());
			acaoExtensaoRepository.save(old);
		}
	}

}
