package ufc.quixada.npi.gpa.generation.pdf;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pessoa;

public class PdfBuilder extends AbstractPdfView{

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//response.setHeader("Content-Disposition", "attachment; filename=\"my-pdf-file.pdf\"");
		
		
		Pessoa pessoa = (Pessoa) model.get("participante");
		AcaoExtensao acao = (AcaoExtensao) model.get("acao");
		
		document.add(new Paragraph("Declaração de participação de participante da equipe de trabalho"));
		document.add(new Paragraph("Pessoa:" + pessoa.getNome()));
		document.add(new Paragraph("Coordenador:" + acao.getCoordenador().getNome()));
		
		
		
		
	}

}
