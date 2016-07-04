package ufc.quixada.npi.gpa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.quixada.npi.gpa.model.Documento;
import ufc.quixada.npi.gpa.repository.DocumentoRepository;
import ufc.quixada.npi.gpa.service.DocumentoService;

@Service
public class DocumentoServiceImpl implements DocumentoService{

	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Override
	public Documento getDocumento(Integer idDocumento) {
		
		Documento documento = documentoRepository.findOne(idDocumento);
		return documento;
	}

	@Override
	public byte[] getArquivo(Documento documento) {
/*		FileInputStream fileInputStream = null;
		File file = new File(documento.getCaminho());
		byte[] bFile = new byte[(int) file.length()];

		try {
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/

		return documento.getArquivo();
	}
}
