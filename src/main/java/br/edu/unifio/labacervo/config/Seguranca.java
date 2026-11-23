package br.edu.unifio.labacervo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Seguranca {
    @Bean
    SecurityFilterChain filtro(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a
                        .dispatcherTypeMatchers(jakarta.servlet.DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("CONSULTA", "TECNICO")
                        .requestMatchers("/api/**").hasRole("TECNICO")
                        .anyRequest().denyAll())
                .httpBasic(Customizer.withDefaults()).build();
    }

    @Bean
    UserDetailsService usuarios(@Value("${lab.senha.consulta}") String consulta,
                               @Value("${lab.senha.tecnico}") String tecnico) {
        var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new InMemoryUserDetailsManager(
                User.withUsername("consulta").password(encoder.encode(consulta)).roles("CONSULTA").build(),
                User.withUsername("tecnico").password(encoder.encode(tecnico)).roles("TECNICO").build());
    }
}
