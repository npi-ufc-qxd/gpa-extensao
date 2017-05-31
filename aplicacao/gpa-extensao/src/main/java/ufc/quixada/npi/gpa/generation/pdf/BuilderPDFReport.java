package ufc.quixada.npi.gpa.generation.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.Participacao;

public class BuilderPDFReport extends PdfPageEventHelper {

	public ByteArrayInputStream gerarPdfParticipante(AcaoExtensao acao, Participacao participante)
			throws DocumentException, MalformedURLException, IOException {
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		boolean isCoordenador = false;

		SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyy");
		String dataInicio = data.format(participante.getDataInicio());
		String dataFim = data.format(participante.getDataTermino());
		String participanteDaAcao = participante.participante();
		String tituloAcao = acao.getTitulo();
		String coordenador = acao.getCoordenador().getNome();
		String dataHoje = data.format(new Date());

		if (participante.isCoordenador() == true) {
			isCoordenador = true;
		}

		writer = desenharTemplatePdf(dataInicio, dataFim, participanteDaAcao, isCoordenador, tituloAcao, coordenador,
				dataHoje);

		ByteArrayInputStream in = new ByteArrayInputStream(writer.toByteArray());
		return in;
	}

	public ByteArrayInputStream gerarPdfBolsista(AcaoExtensao acao, Bolsa bolsista)
			throws DocumentException, MalformedURLException, IOException {
		ByteArrayOutputStream writer = new ByteArrayOutputStream();

		SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyy");
		String dataInicio = data.format(bolsista.getInicio());
		String dataFim = data.format(bolsista.getTermino());
		String participanteDaAcao = bolsista.getBolsista().getPessoa().getNome();
		String tituloAcao = acao.getTitulo();
		String coordenador = acao.getCoordenador().getNome();
		String dataHoje = data.format(new Date());

		writer = desenharTemplatePdf(dataInicio, dataFim, participanteDaAcao, false, tituloAcao, coordenador, dataHoje);

		ByteArrayInputStream in = new ByteArrayInputStream(writer.toByteArray());
		return in;

	}
	

	private ByteArrayOutputStream desenharTemplatePdf(String dataInicio, String dataFim, String nomePessoa,
			boolean isCoordenador, String tituloAcao, String coordenador, String dataHoje)
			throws DocumentException, MalformedURLException, IOException {

		String assinatura;

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, out);
		document.open();

		Image imagemBrasao = Image.getInstance("src/main/resources/static/img/brasaoufc.png");
		imagemBrasao.scalePercent(15f);
		imagemBrasao.setAlignment(Image.ALIGN_CENTER);
		document.add(imagemBrasao);

		Font f = new Font();
		f.setStyle(Font.BOLD);
		f.setSize(10);

		Paragraph titulo = new Paragraph("Diretoria do Campus de Quixadá", f);
		titulo.setAlignment(Element.ALIGN_CENTER);
		titulo.setSpacingAfter(50f);
		document.add(titulo);

		Font fonteSublinhada = new Font();
		fonteSublinhada.setStyle(Font.UNDERLINE);
		fonteSublinhada.setSize(14);

		Paragraph declaracao = new Paragraph("DECLARAÇÃO", fonteSublinhada);
		declaracao.setAlignment(Element.ALIGN_CENTER);
		declaracao.setSpacingAfter(60f);
		document.add(declaracao);
		
		if (isCoordenador == true) {
			assinatura = "Assinatura do Diretor - UFC Campus de Quixadá";
			
			Paragraph textoDaDeclaracao = new Paragraph("Declaro, para os devidos fins, " + "que o(a) participante "
					+ nomePessoa + " participou da Ação de Extensão intitulada " + tituloAcao.toUpperCase()
					+ " como coordenador " +  "nos períodos de " + dataInicio + " a " + dataFim + ".");
			
			textoDaDeclaracao.setAlignment(Element.ALIGN_JUSTIFIED);
			textoDaDeclaracao.setSpacingAfter(15f);

			document.add(textoDaDeclaracao);
		}

		else {
			assinatura = "Assinatura do Coordenador da Ação";
			
			Paragraph textoDaDeclaracao = new Paragraph("Declaro, para os devidos fins, " + "que o(a) participante "
					+ nomePessoa + " participou da Ação de Extensão intitulada " + tituloAcao.toUpperCase()
					+ " de autoria de " + coordenador + " nos períodos de " + dataInicio + " a " + dataFim + ".");
			
			textoDaDeclaracao.setAlignment(Element.ALIGN_JUSTIFIED);
			textoDaDeclaracao.setSpacingAfter(15f);

			document.add(textoDaDeclaracao);

		}
		
		

		

		Paragraph dataEmissaoDocumento = new Paragraph("Quixadá, " + dataHoje);
		dataEmissaoDocumento.setAlignment(Element.ALIGN_RIGHT);
		dataEmissaoDocumento.setSpacingAfter(90f);
		document.add(dataEmissaoDocumento);

		

		Paragraph campoAssinatura = new Paragraph(
				"_________________________________________________________");
		Paragraph donoAssinatura = new Paragraph(assinatura);
		campoAssinatura.setAlignment(Element.ALIGN_CENTER);
		donoAssinatura.setAlignment(Element.ALIGN_CENTER);
		donoAssinatura.setSpacingAfter(240f);
		document.add(campoAssinatura);
		document.add(donoAssinatura);

		Font fonteRodape = new Font();
		fonteRodape.setStyle(Font.NORMAL);
		fonteRodape.setSize(10);

		Paragraph linhaRodape = new Paragraph(
				"______________________________________________________________________________");

		Paragraph rodape = new Paragraph("Av. José de Freitas Queiroz, 5003 - Cedro – CEP: 63.902-580 – "
				+ "Quixadá-Ceará" + "\n" + "Fone/Fax: (88) 34120919 / E-mail: secretaria@quixada.ufc.br", fonteRodape);
		linhaRodape.setAlignment(Element.ALIGN_CENTER);
		rodape.setAlignment(Element.ALIGN_RIGHT);
		
		linhaRodape.setSpacingBefore(30f);
		rodape.setSpacingBefore(40f);

		document.add(linhaRodape);
		document.add(rodape);

		document.close();

		return out;
	}

}
