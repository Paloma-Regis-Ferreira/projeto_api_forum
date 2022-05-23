package br.com.paloma.forumaluraapi.config.security;

import br.com.paloma.forumaluraapi.model.Usuario;
import br.com.paloma.forumaluraapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {
    //quando tentar fazer o login pelo form padrao do spring ele acessa essa classe
    //por essa classe implementar a interface UserDetailsService ele sabe que é a classe de regra de acesso
    //o username vai enviar pra ca como um parametro string
    //ele checa a senha em memoria

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
        if (usuario.isPresent()){
            return usuario.get();
        }
        throw new UsernameNotFoundException("Dados invalidos");
    }
}
