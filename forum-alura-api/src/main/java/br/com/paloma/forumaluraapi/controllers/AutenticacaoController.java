package br.com.paloma.forumaluraapi.controllers;

import br.com.paloma.forumaluraapi.controllers.form.LoginForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @PostMapping
    public ResponseEntity<?> auttenticar(@RequestBody @Valid LoginForm form){
        System.out.println(form.getEmail());
        System.out.println(form.getSenha());
    }
}
