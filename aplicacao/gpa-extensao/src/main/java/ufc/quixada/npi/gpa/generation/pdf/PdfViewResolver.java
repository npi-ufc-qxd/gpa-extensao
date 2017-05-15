package ufc.quixada.npi.gpa.generation.pdf;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class PdfViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String s, Locale locale) throws Exception {
		PdfBuilder view = new PdfBuilder();
		return view;
	}
	

}
