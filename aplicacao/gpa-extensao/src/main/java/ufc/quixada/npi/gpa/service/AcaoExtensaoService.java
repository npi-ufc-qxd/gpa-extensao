package ufc.quixada.npi.gpa.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.Pessoa;

public interface AcaoExtensaoService {

	Void salvarAcaoExtensao(AcaoExtensao acaoExtensao,MultipartFile arquivo, Pessoa coordenador) throws IOException;
}
