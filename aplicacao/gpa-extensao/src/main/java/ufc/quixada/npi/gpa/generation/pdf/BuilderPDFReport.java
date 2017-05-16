package ufc.quixada.npi.gpa.generation.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;

public class BuilderPDFReport {
	public static ByteArrayInputStream gerarPdf(AcaoExtensao acao, Participacao participante) throws DocumentException{
		Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyy");
        String dataInicio = data.format(participante.getDataInicio());
        String dataFim = data.format(participante.getDataTermino());

        PdfWriter.getInstance(document, out);
            document.open();
            
            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(15);
            
            Paragraph titulo = new Paragraph("Declaração de Participação em Ação de Extensão",f);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            
            
            document.add(new Paragraph("Nome do Participante: " + participante.participante()));
    		document.add(new Paragraph("Ação: " + acao.getTitulo()));
    		document.add(new Paragraph("Período de Particição na Ação:"));
    		document.add(new Paragraph("Inicio: " + dataInicio));
    		document.add(new Paragraph("Fim: " + dataFim));
    		document.add(new Paragraph("Nome do coordenador: " + acao.getCoordenador().getNome()));
    		document.add(new Paragraph("                              "));
    		document.add(new Paragraph("_______________________________"));
    		document.add(new Paragraph("Assinatura do Coordenador Responsável"));
            
            document.close();
            
        

        return new ByteArrayInputStream(out.toByteArray());
	}
	
}

