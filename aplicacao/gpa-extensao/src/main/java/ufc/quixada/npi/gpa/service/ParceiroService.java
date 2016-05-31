package ufc.quixada.npi.gpa.service;

import java.util.List;

import ufc.quixada.npi.gpa.model.Parceiro;

public interface ParceiroService {
	List<Parceiro> buscarTodos();
	
	Parceiro salvar(Parceiro parceiro);
	
	Parceiro buscarPorId(Integer id);
}
