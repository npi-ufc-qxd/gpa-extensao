package ufc.quixada.npi.gpa.model;

import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_SALVAR_ARQUIVO;
import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_BUSCAR_ARQUIVO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;;


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
			throw new GpaExtensaoException(EXCEPTION_SALVAR_ARQUIVO);
		}
	}
	
	@PreRemove
	public void deletarArquivo(Documento documento){
		context.getAutowireCapableBeanFactory().autowireBean(this);
		
		File file = new File(documento.getCaminho());
		
		removerArquivos(file);
	}
	
	private String getDiretorioDocumento(Documento documento) {
		String diretorio = documento.getCaminho();
		diretorio = diretorio.replace("/"+documento.getNome(), "");
		return diretorio;
	}
	
	private void removerArquivos(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				removerArquivos(f);
			}
		}
		file.delete();
	}
	
	@PostLoad
	public void carregarArquivo(Documento documento) throws GpaExtensaoException{
		FileInputStream fileInputStream = null;
		File file = new File(documento.getCaminho());
		byte[] bFile = new byte[(int) file.length()];

		try {
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (IOException e) {
			throw new GpaExtensaoException(documento.getCaminho() + EXCEPTION_BUSCAR_ARQUIVO + e.getMessage());
		}
		
		documento.setArquivo(bFile);
	}
}
