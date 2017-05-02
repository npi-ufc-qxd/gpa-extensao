package ufc.quixada.npi.gpa.controller;

import static ufc.quixada.npi.gpa.util.RedirectConstants.R_MINHAS_ACOES;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExtensaoController {

    /**
     * Página inicial do sistema que redireciona para a listagem de ações do usuário
     */
    @RequestMapping(value = "/")
    public String index() {
        return R_MINHAS_ACOES;
    }
}
