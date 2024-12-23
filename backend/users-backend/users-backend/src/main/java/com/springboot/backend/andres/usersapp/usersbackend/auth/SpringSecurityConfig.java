package com.springboot.backend.andres.usersapp.usersbackend.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.springboot.backend.andres.usersapp.usersbackend.auth.filter.JwtAuthenticationFilter;
import com.springboot.backend.andres.usersapp.usersbackend.auth.filter.JwtValidationFilter;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(authz -> authz
                // Rutas públicas (accesibles sin autenticación)
                .requestMatchers(HttpMethod.GET, "/api/ventas", "/api/ventas/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/compras", "/api/compras/page/{page}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/traspasos/page/{page}").permitAll()

                // USUARIOS
                .requestMatchers(HttpMethod.GET, "/api/users", "/api/users/page/{page}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
                
                // CLIENTES
                .requestMatchers(HttpMethod.GET, "/api/clientes", "/api/clientes/page/{page}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/clientes").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/clientes/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/clientes/{id}").hasAnyRole("USER", "ADMIN")

                // EMPLEADOS
                .requestMatchers(HttpMethod.GET, "/api/empleados").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/empleados/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/empleados").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/empleados/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/empleados/{id}").hasAnyRole("USER", "ADMIN")

                // LABORATORIOS
                .requestMatchers(HttpMethod.GET, "/api/laboratorios").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/laboratorios/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/laboratorios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/laboratorios/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/laboratorios/{id}").hasAnyRole("USER", "ADMIN")

                // PROVEEDORES
                .requestMatchers(HttpMethod.GET, "/api/proveedores").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/proveedores/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/proveedores").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/proveedores/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/proveedores/{id}").hasAnyRole("USER", "ADMIN")

                // MEDICAMENTOS
                .requestMatchers(HttpMethod.GET, "/api/medicamentos").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/medicamentos/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/medicamentos").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/medicamentos/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/medicamentos/{id}").hasAnyRole("USER", "ADMIN")


                // VENTAS
                .requestMatchers(HttpMethod.GET, "/api/ventas").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/ventas/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/ventas").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/ventas/{id}").hasAnyRole("USER", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/ventas/usuario/ventas").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/ventas/usuario/ventas/rango").hasAnyRole("USER", "ADMIN")


                // COMPRAS
                .requestMatchers(HttpMethod.GET, "/api/compras").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/compras/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/compras").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/compras/{id}").hasAnyRole("USER", "ADMIN")

                // ALMACENES
                .requestMatchers(HttpMethod.GET, "/api/almacenes").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/almacenes/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/almacenes").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/almacenes/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/almacenes/{id}").hasAnyRole("USER", "ADMIN")
                
                .requestMatchers(HttpMethod.DELETE, "/api/almacenes/listar-medicamentos/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/almacenes/traspasar").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/almacenes/medicamentos/almacen1").hasAnyRole("USER", "ADMIN")
                
                // TRASPASOS
                .requestMatchers(HttpMethod.GET, "/api/traspasos").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/traspasos/{id}").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/traspasos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/traspasos/{id}").hasAnyRole("USER", "ADMIN")

                // DASHBOARD
                .requestMatchers(HttpMethod.GET, "/api/dashboard/total-stock").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/stock-por-almacen/{almacenId}").hasAnyRole("USER", "ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/dashboard/ventas-diarias").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/dashboard/ventas-semanales").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/dashboard/ventas-mensuales").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/dashboard/ventas-especifica").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/dashboard/medicamentos-mas-vendidos-semana").hasAnyRole("USER", "ADMIN")

                // DETALLES DE VENTAS //no es necesario esta por si acaso
                
                  .requestMatchers(HttpMethod.GET, "/api/ventas").hasAnyRole("USER", "ADMIN")
                  .requestMatchers(HttpMethod.GET,
                  "/api/ventas/{ventaId}/{medicamentoId}").hasAnyRole("USER", "ADMIN")
                  .requestMatchers(HttpMethod.POST, "/api/ventas").hasAnyRole("USER", "ADMIN")
                  .requestMatchers(HttpMethod.DELETE,
                  "/api/venta/{ventaId}/{medicamentoId}").hasAnyRole("USER", "ADMIN")
                 

                // Cualquier otra solicitud debe estar autenticada
                .anyRequest().authenticated())
                .cors(cors -> cors.configurationSource(configurationSource()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    CorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedOrigins(Arrays.asList("http://localhost:4500"));
        config.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<CorsFilter>(
                new CorsFilter(this.configurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}
