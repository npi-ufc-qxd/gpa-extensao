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
import com.itextpdf.text.pdf.PdfWriter;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Participacao;

public class BuilderPDFReport {
	public static ByteArrayInputStream gerarPdf(AcaoExtensao acao, Participacao participante) throws DocumentException, MalformedURLException, IOException{
		Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyy");
        String dataInicio = data.format(participante.getDataInicio());
        String dataFim = data.format(participante.getDataTermino());
        String participanteDaAcao = participante.participante();
        String tituloAcao = acao.getTitulo();
        String coordenador = acao.getCoordenador().getNome();
        String dataHoje = data.format(new Date());

        PdfWriter.getInstance(document, out);
            document.open();
               
            Image imagemBrasao = Image.getInstance("src/main/resources/static/img/brasaoufc.png");
            imagemBrasao.scalePercent(20f);
            imagemBrasao.setAlignment(Image.ALIGN_CENTER);
            document.add(imagemBrasao);
            
            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(10);
            
            Paragraph titulo = new Paragraph("Diretoria do Campus de Quixadá",f);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(55f);
            document.add(titulo);
            
            Font fonteSublinhada = new Font();
            fonteSublinhada.setStyle(Font.UNDERLINE);
            fonteSublinhada.setSize(14);
            
            Paragraph declaracao = new Paragraph("DECLARAÇÃO",fonteSublinhada);
            declaracao.setAlignment(Element.ALIGN_CENTER);
            declaracao.setSpacingAfter(65f);
            document.add(declaracao);
            
            Paragraph textoDaDeclaracao = new Paragraph("Declaro, para os devidos fins, "
            		+ "que o(a) participante " + participanteDaAcao 
            		+ " participou da Ação de Extensão intitulada " + tituloAcao.toUpperCase()
            		+ " de autoria de " + coordenador
            		+ " nos períodos de " + dataInicio + " até " + dataFim + ".");
            
            textoDaDeclaracao.setAlignment(Element.ALIGN_JUSTIFIED);
            textoDaDeclaracao.setSpacingAfter(20f);
          
            document.add(textoDaDeclaracao);
            
            Paragraph dataEmissaoDocumento = new Paragraph("Quixadá, " + dataHoje);
            dataEmissaoDocumento.setAlignment(Element.ALIGN_RIGHT);
            dataEmissaoDocumento.setSpacingAfter(55f);
            document.add(dataEmissaoDocumento);
            
            
            
            Paragraph nomeResponsavel = new Paragraph("Profa. Andréia Andréia Libório Sampaio");
            Paragraph nomeDoCargo = new Paragraph("Vice-Diretora e Coordenadora de Programas Acadêmicos");
            Paragraph stringUFCQuixada = new Paragraph("Campus da UFC em Quixadá");
            nomeResponsavel.setAlignment(Element.ALIGN_CENTER);
            nomeDoCargo.setAlignment(Element.ALIGN_CENTER);
            stringUFCQuixada.setAlignment(Element.ALIGN_CENTER);
            stringUFCQuixada.setSpacingAfter(260f);
            document.add(nomeResponsavel);
            document.add(nomeDoCargo);
            document.add(stringUFCQuixada);
            		
            
            Font rodape = new Font();
            rodape.setStyle(Font.NORMAL);
            rodape.setSize(10);
            
            
    		Paragraph campoAssinatura = new Paragraph("______________________________________________________________________________");
    		Paragraph endereco = new Paragraph("Av. José de Freitas Queiroz, 5003 - Cedro – CEP: 63.902-580 – "
    			+ "Quixadá-Ceará", rodape);
    		Paragraph contato =  new Paragraph("Fone/Fax: (88) 34120919 / E-mail: secretaria@quixada.ufc.br", rodape);
    		campoAssinatura.setAlignment(Element.ALIGN_CENTER);
    		endereco.setAlignment(Element.ALIGN_RIGHT);
    		contato.setAlignment(Element.ALIGN_RIGHT);
    		
    		
    		document.add(campoAssinatura);
    		document.add(endereco);
    		document.add(contato);
    		
    		
            
            document.close();
            
        

        return new ByteArrayInputStream(out.toByteArray());
	}
	
}

