package ufc.quixada.npi.gpa.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.persistence.PrePersist;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_ARQUIVO;;


public class DocumentoEntityListener implements ApplicationContextAware{

	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		context = ctx;
	}
	
	public ApplicationContext getApplicationContext(){
		return context;
	}
	
	@PrePersist
	public void salvarArquivo(Documento documento) throws GpaExtensaoException{
		context.getAutowireCapableBeanFactory().autowireBean(this);
		
		String caminhoDiretorio = getDiretorioDocumento(documento);
		File diretorio = new File(caminhoDiretorio);
		diretorio.mkdirs();
		
		try {
			File arquivo = new File(diretorio, documento.getNome());
			FileOutputStream fop = new FileOutputStream(arquivo);
			arquivo.createNewFile();
			fop.write(documento.getArquivo());
			fop.flush();
			fop.close();
		} catch (IOException ex) {
			throw new GpaExtensaoException(EXCEPTION_ARQUIVO);
		}
	}
	
	private String getDiretorioDocumento(Documento documento) {
		String diretorio = documento.getCaminho();
		diretorio = diretorio.replace("/"+documento.getNome(), "");
		return diretorio;
	}
}