package com.example.demoTwo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;



import org.springframework.beans.factory.annotation.Autowired;

@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//	    securedEnabled = true,
//	    jsr250Enabled = true,
//	    prePostEnabled = true
//	)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    
	@Autowired
	private UnAuthorizedUserAuthenticationEntryPoint authenticationEntryPoint;
	
    
  
	
	  @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(customUserDetailsService);
	    }

	    @Override
	    public void configure(HttpSecurity http) throws Exception {
	        http.csrf().disable()
	                .authorizeRequests()
	                .antMatchers("/signin", "/signup").permitAll()
	                .anyRequest().authenticated()
	                .and()
	    			.exceptionHandling()
	    			.authenticationEntryPoint(authenticationEntryPoint)
	    			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	    }
	    
	    @Bean
	    public PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }

	    @Override
	    @Bean
	    protected AuthenticationManager authenticationManager() throws Exception {
	        return super.authenticationManager();
	    }


	
	

}
