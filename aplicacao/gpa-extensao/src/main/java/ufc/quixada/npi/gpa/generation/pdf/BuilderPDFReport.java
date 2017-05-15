package ufc.quixada.npi.gpa.generation.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;

public class BuilderPDFReport {
	public static ByteArrayInputStream gerarPdf(AcaoExtensao acao, Participacao participante) throws DocumentException{
		Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("Nome do Participante: " + participante.participante()));
    		document.add(new Paragraph("Ação: " + acao.getTitulo()));
    		document.add(new Paragraph("Período de Particição na Ação:"));
    		document.add(new Paragraph("Inicio: " + participante.getDataInicio()));
    		document.add(new Paragraph("Fim: " + participante.getDataTermino()));
    		document.add(new Paragraph("Nome do coordenador: " + acao.getCoordenador().getNome()));
    		document.add(new Paragraph("_______________________________"));
    		document.add(new Paragraph("Assinatura do Coordenador Responsável"));
            
            document.close();
            
        

        return new ByteArrayInputStream(out.toByteArray());
	}
	
	//refatorar os service participacao
	//ajeitar a data
	// ajeitar a questao de aparecer o botao de emitir declaracao la no front
	// refatorar o código
}

