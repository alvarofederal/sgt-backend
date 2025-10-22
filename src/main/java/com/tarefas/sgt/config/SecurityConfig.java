package com.tarefas.sgt.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tarefas.sgt.security.JWTAuthenticationFilter;
import com.tarefas.sgt.security.JWTAuthorizationFilter;
import com.tarefas.sgt.security.JWTUtil;

@CrossOrigin(origins = { "${app.security.cors.origin}" })
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };

	@Autowired
	private Environment env;
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private UserDetailsService userDetailsService;
	
    @Autowired
    private com.tarefas.sgt.security.JwtAuthenticationEntryPoint unauthorizedHandler;
    
    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
    	JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager(), jwtUtil);
    	filter.setAuthenticationManager(authenticationManagerBean());
    	filter.setJwtUtil(jwtUtil);
    	return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable();
        http
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/usuarios/register").permitAll()
                .antMatchers("/error").permitAll()  // ← Libere /error pra erros não bloquearem
                .antMatchers("/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger-ui/index.html", "/webjars/**", "/webjars/springfox-swagger-ui/**").permitAll()  // ← Expanda pra index.html e springfox webjars
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
            .and()
            .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil))
            .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService))
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        



		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui/**", "/webjars/**", "/v2/api-docs", "/swagger-resources/**", "/error");  // ← Ignora /error também
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setExposedHeaders(Arrays.asList("Authorization"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
