package br.com.aweb.pesquisa_satisfacao.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ConfiguracoesDeSeguranca {
    
    @Bean
    public UserDetailsService dadosUsuarios() {
        UserDetails usuario1 = User.builder()
            .username("jvitor@gmail.com")
            .password("{noop}1234")
            .build();
        
        UserDetails usuario2 = User.builder()
            .username("gabriel@gmail.com")
            .password("{noop}senha1")
            .build();

        return new InMemoryUserDetailsManager(usuario1, usuario2);
        
    }

}
