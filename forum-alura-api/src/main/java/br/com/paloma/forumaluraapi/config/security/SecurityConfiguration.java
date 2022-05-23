package br.com.paloma.forumaluraapi.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity //habilita o security
@Configuration //quando startar ele olha essa classe
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    //configuração de autorização == quem tem acesso ao que
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/topicos").permitAll() //permite acesso ao metodo do request
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()//mesma coisa /* qualquer coisa depois do barra com get
                .anyRequest().authenticated()//qualquer outra requisiçao precisa estar autenticado
                //.and().formLogin();//formulario padrao do string//foi retirado pra nao criar  sessão e sim gerar token
                .and().csrf().disable() //disablita pois nao tem mais chances de ataque
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //nao cria sessão e usoa token
    }

    //configuração de autenticação == quem ta logado
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }

//    public static void main(String[] args){
//        System.out.println(new BCryptPasswordEncoder().encode("123456"));
//    }
}
