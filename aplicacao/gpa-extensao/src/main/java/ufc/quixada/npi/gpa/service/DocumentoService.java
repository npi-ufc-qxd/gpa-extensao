package ufc.quixada.npi.gpa.service;

import ufc.quixada.npi.gpa.model.Documento;

public interface DocumentoService {
	
	public Documento getDocumento(Integer idDocumento);
	
	public byte[] getArquivo(Documento documento);
}
