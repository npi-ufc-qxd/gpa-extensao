package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.ASSUNTO_EMAIL;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_COORDENACAO_ATRIBUICAO_PARECERISTA;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_COORDENACAO_ATRIBUICAO_RELATOR;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_COORDENACAO_EMISSAO_PARECER;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_COORDENACAO_EMISSAO_RELATO;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_COORDENACAO_HOMOLOGACAO;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_COORDENACAO_SOLICITACAO_RESOLUCAO_PENDENCIAS;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_DIRECAO_EMISSAO_PARECER;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_DIRECAO_EMISSAO_RELATO;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_DIRECAO_SUBMISSAO;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_NOME_PESSOA;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_PARECERISTA_ATRIBUICAO_PARECERISTA;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_PARECERISTA_PRAZO;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_PARECERISTA_RESOLUCAO_PENDENCIAS;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_PRAZO;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_RELATOR_ATRIBUICAO_RELATOR;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_RELATOR_RESOLUCAO_PENDENCIAS;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_REMETENTE;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_STATUS;
import static ufc.quixada.npi.gpa.util.Constants.EMAIL_TITULO_ACAO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.repository.AcaoExtensaoRepository;
import ufc.quixada.npi.gpa.service.NotificationService;

/**
 * Implementa a notificação por e-mail para as ações de extensão
 */
@Service
public class EmailServiceImpl implements NotificationService {

	@Autowired
	private JavaMailSender mailSender;

	private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);

	private String[] emailDirecao;
	
	@Autowired
	private AcaoExtensaoRepository acaoRepository;
	
	/**
	 * Recebe uma ação de extensão e verifica seu estado para saber qual tipo de
	 * notificação deve ser enviado.
	 * 
	 * @param acaoExtensao
	 */
	@Override
	public void notificar(AcaoExtensao acaoExtensao) {
		/*List<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papelRepository.findByNome(Tipo.DIRECAO));
		List<Pessoa> direcao = pessoaRepository.findAllByPapeis(papeis);
		Integer direcaoSize = direcao.size();

		this.emailDirecao = new String[direcaoSize];

		for (int i = 0; i < direcaoSize; i++) {
			this.emailDirecao[i] = direcao.get(i).getEmail();
		}
		Runnable notificacao = new Runnable() {

			@Override
			public void run() {
				switch (acaoExtensao.getStatus()) {

				case AGUARDANDO_PARECERISTA:

					notificarSubmissao(acaoExtensao);
					break;

				case AGUARDANDO_PARECER_TECNICO:

					if (acaoExtensao.getParecerTecnico().getPendencias() == null) {
						notificarAtribuicaoParecerista(acaoExtensao);

					} else {
						notificarResolucaoPendenciasParecer(acaoExtensao);
					}

					break;

				case RESOLVENDO_PENDENCIAS_PARECER:

					notificarSolicitacaoResolucaoPendenciasParecer(acaoExtensao);
					break;

				case AGUARDANDO_RELATOR:

					notificarEmissaoParecer(acaoExtensao);
					break;

				case AGUARDANDO_PARECER_RELATOR:

					if (acaoExtensao.getParecerRelator().getPendencias() == null) {
						notificarAtribuicaoRelator(acaoExtensao);
					} else {
						notificarResolucaoPendenciasRelato(acaoExtensao);
					}

					break;

				case RESOLVENDO_PENDENCIAS_RELATO:

					notificarSolicitacaoResolucaoPendenciasRelato(acaoExtensao);
					break;

				case AGUARDANDO_HOMOLOGACAO:

					notificarEmissaoRelato(acaoExtensao);
					break;

				case APROVADO:

					notificarHomologacao(acaoExtensao);
					break;

				default:
					break;
				}
			}
		};

		Thread notificar = new Thread(notificacao);
		notificar.start();*/
	}

	/**
	 * Encapsula o envio de email simples
	 * 
	 * @param email
	 */
	private void enviarEmail(SimpleMailMessage email) {
		this.mailSender.send(email);
	}
	
	private void enviarEmail(MimeMessage email) {
		this.mailSender.send(email);
}

	/**
	 * Encapsula o envio de multiplos emails
	 * 
	 * @param emails
	 */
	private void enviarEmails(List<SimpleMailMessage> emails) {
		for (SimpleMailMessage email : emails) {
			this.enviarEmail(email);
		}
	}

	/**
	 * Encapsula a formatação de datas
	 * 
	 * @param date
	 * @return string
	 */
	private String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * Notifica a submissão da ação à direção
	 * 
	 * @param acaoExtensao
	 */
	private void notificarSubmissao(AcaoExtensao acaoExtensao) {
		SimpleMailMessage email = new SimpleMailMessage();

		email.setTo(emailDirecao);
		email.setFrom(EMAIL_REMETENTE);

		String assunto = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		email.setSubject(assunto);

		String texto = EMAIL_DIRECAO_SUBMISSAO.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo()).replaceAll(EMAIL_NOME_PESSOA,
				acaoExtensao.getCoordenador().getNome());
		email.setText(texto);

		enviarEmail(email);
	}

	/**
	 * Notifica a atribuição do parecerista ao coordenador da ação, bem como ao
	 * parecerista.
	 * 
	 * @param acaoExtensao
	 */
	private void notificarAtribuicaoParecerista(AcaoExtensao acaoExtensao) throws MailException {
		SimpleMailMessage emailCoordenador = new SimpleMailMessage();

		emailCoordenador.setTo(acaoExtensao.getCoordenador().getEmail());
		emailCoordenador.setFrom(EMAIL_REMETENTE);

		String assuntoCoordenador = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailCoordenador.setSubject(assuntoCoordenador);

		String textoCoordenador = EMAIL_COORDENACAO_ATRIBUICAO_PARECERISTA
				.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo())
				.replaceAll(EMAIL_NOME_PESSOA, acaoExtensao.getParecerTecnico().getResponsavel().getNome());
		emailCoordenador.setText(textoCoordenador);

		SimpleMailMessage emailParecerista = new SimpleMailMessage();

		emailParecerista.setTo(acaoExtensao.getParecerTecnico().getResponsavel().getEmail());
		emailParecerista.setFrom(EMAIL_REMETENTE);

		String assuntoParecerista = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailParecerista.setSubject(assuntoParecerista);

		String textoParecerista = EMAIL_PARECERISTA_ATRIBUICAO_PARECERISTA
				.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo())
				.replaceAll(EMAIL_PRAZO, formatDate(acaoExtensao.getParecerTecnico().getPrazo()));
		emailParecerista.setText(textoParecerista);

		List<SimpleMailMessage> emails = new ArrayList<SimpleMailMessage>();
		emails.add(emailCoordenador);
		emails.add(emailParecerista);
		enviarEmails(emails);
	}

	/**
	 * Notifica a emissão do parecer da ação ao coordenador e à direção.
	 * 
	 * @param acaoExtensao
	 */
	private void notificarEmissaoParecer(AcaoExtensao acaoExtensao) {
		SimpleMailMessage emailCoordenador = new SimpleMailMessage();

		emailCoordenador.setTo(acaoExtensao.getCoordenador().getEmail());
		emailCoordenador.setFrom(EMAIL_REMETENTE);

		String assuntoCoordenador = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailCoordenador.setSubject(assuntoCoordenador);

		String textoCoordenador = EMAIL_COORDENACAO_EMISSAO_PARECER.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo())
				.replaceAll(EMAIL_NOME_PESSOA, acaoExtensao.getParecerTecnico().getResponsavel().getNome());
		emailCoordenador.setText(textoCoordenador);

		SimpleMailMessage emailDirecao = new SimpleMailMessage();

		emailDirecao.setTo(this.emailDirecao);
		emailDirecao.setFrom(EMAIL_REMETENTE);

		String assuntoDirecao = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailDirecao.setSubject(assuntoDirecao);

		String textoDirecao = EMAIL_DIRECAO_EMISSAO_PARECER.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo())
				.replaceAll(EMAIL_NOME_PESSOA, acaoExtensao.getParecerTecnico().getResponsavel().getNome());
		emailDirecao.setText(textoDirecao);

		List<SimpleMailMessage> emails = new ArrayList<SimpleMailMessage>();
		emails.add(emailCoordenador);
		emails.add(emailDirecao);
		enviarEmails(emails);
	}

	/**
	 * Notifica ao coordenador da ação, a solicitação de resolução de pendências
	 * feita pelo parecerista.
	 * 
	 * @param acaoExtensao
	 */
	private void notificarSolicitacaoResolucaoPendenciasParecer(AcaoExtensao acaoExtensao) {
		SimpleMailMessage emailCoordenador = new SimpleMailMessage();

		emailCoordenador.setTo(acaoExtensao.getCoordenador().getEmail());
		emailCoordenador.setFrom(EMAIL_REMETENTE);

		String assuntoCoordenador = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailCoordenador.setSubject(assuntoCoordenador);

		String textoCoordenador = EMAIL_COORDENACAO_SOLICITACAO_RESOLUCAO_PENDENCIAS.replaceAll(EMAIL_TITULO_ACAO,
				acaoExtensao.getTitulo());
		emailCoordenador.setText(textoCoordenador);

		enviarEmail(emailCoordenador);
	}

	/**
	 * Notifica ao parecerista a resolução de pendências da ação pelo
	 * coordenador da ação
	 * 
	 * @param acaoExtensao
	 */
	private void notificarResolucaoPendenciasParecer(AcaoExtensao acaoExtensao) {
		SimpleMailMessage emailParecerista = new SimpleMailMessage();

		emailParecerista.setTo(acaoExtensao.getParecerTecnico().getResponsavel().getEmail());
		emailParecerista.setFrom(EMAIL_REMETENTE);

		String assuntoParecerista = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailParecerista.setSubject(assuntoParecerista);

		String textoParecerista = EMAIL_PARECERISTA_RESOLUCAO_PENDENCIAS
				.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo())
				.replaceAll(EMAIL_NOME_PESSOA, acaoExtensao.getCoordenador().getNome());
		emailParecerista.setText(textoParecerista);

		enviarEmail(emailParecerista);
	}

	/**
	 * Notifica a atribuição do relator ao coordenador da ação, bem como ao
	 * relator.
	 * 
	 * @param acaoExtensao
	 */
	private void notificarAtribuicaoRelator(AcaoExtensao acaoExtensao) {
		SimpleMailMessage emailCoordenador = new SimpleMailMessage();

		emailCoordenador.setTo(acaoExtensao.getCoordenador().getEmail());
		emailCoordenador.setFrom(EMAIL_REMETENTE);

		String assuntoCoordenador = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailCoordenador.setSubject(assuntoCoordenador);

		String textoCoordenador = EMAIL_COORDENACAO_ATRIBUICAO_RELATOR.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo())
				.replaceAll(EMAIL_NOME_PESSOA, acaoExtensao.getParecerRelator().getResponsavel().getNome());
		emailCoordenador.setText(textoCoordenador);

		SimpleMailMessage emailRelator = new SimpleMailMessage();

		emailRelator.setTo(acaoExtensao.getParecerRelator().getResponsavel().getEmail());
		emailRelator.setFrom(EMAIL_REMETENTE);

		String assuntoRelator = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailRelator.setSubject(assuntoRelator);

		String textoRelator = EMAIL_RELATOR_ATRIBUICAO_RELATOR.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo())
				.replaceAll(EMAIL_PRAZO, formatDate(acaoExtensao.getParecerRelator().getPrazo()));
		emailRelator.setText(textoRelator);

		List<SimpleMailMessage> emails = new ArrayList<SimpleMailMessage>();
		emails.add(emailCoordenador);
		emails.add(emailRelator);
		enviarEmails(emails);
	}

	/**
	 * Notifica a emissão do relato da ação ao coordenador e à direção.
	 * 
	 * @param acaoExtensao
	 */
	private void notificarEmissaoRelato(AcaoExtensao acaoExtensao) {
		SimpleMailMessage emailCoordenador = new SimpleMailMessage();

		emailCoordenador.setTo(acaoExtensao.getCoordenador().getEmail());
		emailCoordenador.setFrom(EMAIL_REMETENTE);

		String assuntoCoordenador = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailCoordenador.setSubject(assuntoCoordenador);

		String textoCoordenador = EMAIL_COORDENACAO_EMISSAO_RELATO.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo())
				.replaceAll(EMAIL_NOME_PESSOA, acaoExtensao.getParecerRelator().getResponsavel().getNome());
		emailCoordenador.setText(textoCoordenador);

		SimpleMailMessage emailDirecao = new SimpleMailMessage();

		emailDirecao.setTo(this.emailDirecao);
		emailDirecao.setFrom(EMAIL_REMETENTE);

		String assuntoDirecao = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailDirecao.setSubject(assuntoDirecao);

		String textoDirecao = EMAIL_DIRECAO_EMISSAO_RELATO.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo())
				.replaceAll(EMAIL_NOME_PESSOA, acaoExtensao.getParecerRelator().getResponsavel().getNome());
		emailDirecao.setText(textoDirecao);

		List<SimpleMailMessage> emails = new ArrayList<SimpleMailMessage>();
		emails.add(emailCoordenador);
		emails.add(emailDirecao);
		enviarEmails(emails);
	}

	/**
	 * Notifica ao coordenador da ação, a solicitação de resolução de pendências
	 * feita pelo relator.
	 * 
	 * @param acaoExtensao
	 */
	private void notificarSolicitacaoResolucaoPendenciasRelato(AcaoExtensao acaoExtensao) {
		SimpleMailMessage emailCoordenador = new SimpleMailMessage();

		emailCoordenador.setTo(acaoExtensao.getCoordenador().getEmail());
		emailCoordenador.setFrom(EMAIL_REMETENTE);

		String assuntoCoordenador = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailCoordenador.setSubject(assuntoCoordenador);

		String textoCoordenador = EMAIL_COORDENACAO_SOLICITACAO_RESOLUCAO_PENDENCIAS.replaceAll(EMAIL_TITULO_ACAO,
				acaoExtensao.getTitulo());
		emailCoordenador.setText(textoCoordenador);

		enviarEmail(emailCoordenador);
	}

	/**
	 * Notifica a resolução de pendências da ação pelo coordenador da ação ao
	 * relator.
	 * 
	 * @param acaoExtensao
	 */
	private void notificarResolucaoPendenciasRelato(AcaoExtensao acaoExtensao) {
		SimpleMailMessage emailRelator = new SimpleMailMessage();

		emailRelator.setTo(acaoExtensao.getParecerRelator().getResponsavel().getEmail());
		emailRelator.setFrom(EMAIL_REMETENTE);

		String assuntoRelator = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailRelator.setSubject(assuntoRelator);

		String textoRelator = EMAIL_RELATOR_RESOLUCAO_PENDENCIAS.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo())
				.replaceAll(EMAIL_NOME_PESSOA, acaoExtensao.getCoordenador().getNome());
		emailRelator.setText(textoRelator);

		enviarEmail(emailRelator);
	}

	/**
	 * Notifica ao coordenador a homologação da ação.
	 * 
	 * @param acaoExtensao
	 */
	private void notificarHomologacao(AcaoExtensao acaoExtensao) {
		SimpleMailMessage emailCoordenador = new SimpleMailMessage();

		emailCoordenador.setTo(acaoExtensao.getCoordenador().getEmail());
		emailCoordenador.setFrom(EMAIL_REMETENTE);

		String assuntoCoordenador = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo());
		emailCoordenador.setSubject(assuntoCoordenador);

		String textoCoordenador = EMAIL_COORDENACAO_HOMOLOGACAO.replaceAll(EMAIL_TITULO_ACAO, acaoExtensao.getTitulo())
				.replaceAll(EMAIL_STATUS, acaoExtensao.getStatus().getDescricao());
		emailCoordenador.setText(textoCoordenador);

		enviarEmail(emailCoordenador);
	}

	/**
	 * Envia um e-mail informando ao parecerista/relator 
	 * que o prazo para emissão do parecer se encerra em um dia.
	 */
	@Override
	public void notificarPareceristaRelatorPrazo(Date dataHoje) {
		dataHoje = formataData(dataHoje);
		List<AcaoExtensao> acoesAguardandoParecer =
				acaoRepository.findByStatusAndParecerTecnico_prazo(Status.AGUARDANDO_PARECER_TECNICO, dataHoje);
		acoesAguardandoParecer.addAll(
				acaoRepository.findByStatusAndParecerRelator_prazo(Status.AGUARDANDO_PARECER_RELATOR, dataHoje));
		
		if (!acoesAguardandoParecer.isEmpty()) {
			MimeMessage mimeMessage;
			MimeMessageHelper mensagem;
			String destinatario = null;
			String assuntoEmail = null;
			String texto = null;
			for (AcaoExtensao acao : acoesAguardandoParecer) {
				mimeMessage = mailSender.createMimeMessage();
				mensagem = new MimeMessageHelper(mimeMessage, "UTF-8");
				
				// Set destinatario
				if (acao.getStatus().equals(Status.AGUARDANDO_PARECER_TECNICO)) {
					destinatario = acao.getParecerTecnico().getResponsavel().getEmail();
				} else {
					destinatario = acao.getParecerRelator().getResponsavel().getEmail();
				}
				
				// Set assunto do email
				assuntoEmail = ASSUNTO_EMAIL.replaceAll(EMAIL_TITULO_ACAO, acao.getTitulo());
				
				// Set corpo do email
				texto = EMAIL_PARECERISTA_PRAZO.replaceAll(EMAIL_PRAZO,
						formatDate(Status.AGUARDANDO_PARECER_TECNICO.equals(acao.getStatus()) ? 
								acao.getParecerTecnico().getPrazo() :
									acao.getParecerRelator().getPrazo()));
				
				try {
					mensagem.setTo(destinatario);
					mensagem.setFrom(EMAIL_REMETENTE);
					mensagem.setSubject(assuntoEmail);
					mensagem.setText(texto, true);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				
				enviarEmail(mimeMessage);
			}
		}
	}
	
	private Date formataData(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = sdf.parse(sdf.format(date));
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
