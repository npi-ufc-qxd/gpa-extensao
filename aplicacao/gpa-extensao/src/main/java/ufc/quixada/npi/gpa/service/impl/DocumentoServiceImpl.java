package ufc.quixada.npi.gpa.service.impl;

import static ufc.quixada.npi.gpa.util.Constants.EXCEPTION_BUSCAR_ARQUIVO;
import static ufc.quixada.npi.gpa.util.Constants.MESSAGE_SALVAR_ARQUIVO_ERROR;
import static ufc.quixada.npi.gpa.util.Constants.PASTA_DOCUMENTOS_GPA;
import static ufc.quixada.npi.gpa.util.Constants.PDF;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.exception.GpaExtensaoException;
import ufc.quixada.npi.gpa.model.AcaoExtensao;
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
	public byte[] getArquivo(Documento documento) throws GpaExtensaoException {
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
		
		return bFile;
	}
	
	@Override
	public Documento save(MultipartFile arquivo, AcaoExtensao acaoExtensao) throws GpaExtensaoException {
		if (arquivo != null && !(arquivo.getOriginalFilename().toString().equals(""))) {
			if (arquivo.getContentType().equals(PDF)) {
				try {
					Documento documento = new Documento();
					documento.setArquivo(arquivo.getBytes());
					documento.setNome(acaoExtensao.getIdentificador() + "_" + arquivo.getOriginalFilename().toString());
					documento.setCaminho(
							PASTA_DOCUMENTOS_GPA + "/" + acaoExtensao.getIdentificador() + "/" + documento.getNome());
					documentoRepository.save(documento);
					
					return documento;
				} catch (IOException e) {
					throw new GpaExtensaoException(MESSAGE_SALVAR_ARQUIVO_ERROR);
				}
			} else {
				throw new GpaExtensaoException(MESSAGE_SALVAR_ARQUIVO_ERROR);
			}
		}
		return null;
	}

	@Override
	public AcaoExtensao deletarDocumento(AcaoExtensao acao) throws GpaExtensaoException {
		Documento documento = null;
		
		documento = acao.getAnexo();
		if (documento != null) {
			File file = new File(documento.getCaminho());
			file.delete();
			acao.setAnexo(null);
			documentoRepository.delete(documento);
		}
		
		return acao;
	}
}

