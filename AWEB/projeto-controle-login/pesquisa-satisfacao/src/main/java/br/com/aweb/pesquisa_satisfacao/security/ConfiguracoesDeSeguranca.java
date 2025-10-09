package br.com.aweb.pesquisa_satisfacao.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracoesDeSeguranca {
    
    @Bean
    public UserDetailsService dadosUsuarios() {
        UserDetails usuario1 = User.builder()
            .username("jvitor@gmail.com")
            .password("{noop}senha")
            .build();
        
        UserDetails usuario2 = User.builder()
            .username("gabriel@gmail.com")
            .password("{noop}senha1")
            .build();
        
        UserDetails usuario3 = User.builder()
            .username("heloisa@gmail.com")
            .password("{noop}senha2")
            .build();

        return new InMemoryUserDetailsManager(usuario1, usuario2,usuario3);
        
    }

     @Bean
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
        // Configura a cadeia de filtros de segurança do Spring Security e retorna o SecurityFilterChain configurado.
        return http 
            // authorizeHttpRequests recebe um lambda para definir as regras de autorização
            .authorizeHttpRequests(req -> {
                // Permite acesso sem autenticação a recursos estáticos (CSS, JS, imagens)
                req.requestMatchers("/css/**", "/js/**", "/img/**").permitAll();
                // Exige autenticação para qualquer outra requisição
                req.anyRequest().authenticated();
            })
            .formLogin(form -> form.loginPage("/login")   // define a página de login personalizada em /login
                .defaultSuccessUrl("/")                   // após login bem-sucedido redireciona para /
                .permitAll()) 
                
                //incluindo um argumento de parametro para o logou                            
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll() // permite acesso à página de login sem autenticação
            )
            .build(); // constrói e retorna o SecurityFilterChain
    }

}
