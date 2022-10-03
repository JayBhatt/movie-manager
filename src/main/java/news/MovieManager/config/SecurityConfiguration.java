package news.MovieManager.config;

import news.MovieManager.auth.AccessDeniedEntryPoint;
import news.MovieManager.auth.AuthEntryPoint;
import news.MovieManager.filter.JwtTokenFilter;
import news.MovieManager.model.Role;
import news.MovieManager.model.Status;
import news.MovieManager.persistence.entity.MovieUser;
import news.MovieManager.service.UserServiceImpl;
import news.MovieManager.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userService;
    private final JwtTokenFilter jwtTokenFilter;
    private final AuthEntryPoint authEntryPoint;
    private final AccessDeniedEntryPoint accessDeniedEntryPoint;

    @Autowired
    public SecurityConfiguration(UserServiceImpl userService, JwtTokenFilter jwtTokenFilter, AuthEntryPoint authEntryPoint, AccessDeniedEntryPoint accessDeniedEntryPoint) {
        this.userService = userService;
        this.jwtTokenFilter = jwtTokenFilter;
        this.authEntryPoint = authEntryPoint;
        this.accessDeniedEntryPoint = accessDeniedEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder encoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(username -> {
                Optional<MovieUser> user = this.userService.findDistinctByEmailAndActive(username, Status.ACTIVE.getStatus());
                if (user.isEmpty()) {
                    throw new UsernameNotFoundException("Specified user doesn't exists.");
                }
                MovieUser fetchedUser = user.get();
                return new User(fetchedUser.getEmail(), fetchedUser.getPassword(), new ArrayList<>());
            })
            .passwordEncoder(encoder)
            .and()
            .build();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // disable defaults
        http = http.csrf().disable().cors().and().httpBasic().disable().formLogin().disable();
        // State
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
        // Exceptions
        http = http
            .exceptionHandling()
            .authenticationEntryPoint(this.authEntryPoint)
            .and()
            .exceptionHandling().accessDeniedHandler(this.accessDeniedEntryPoint).and();
        // Endpoints
        http.authorizeHttpRequests(authorize -> authorize
                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .mvcMatchers("/api/auth/login").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/user").permitAll()
                .mvcMatchers(HttpMethod.PUT, "/api/config").hasRole(Role.ADMIN.getRole())
                .anyRequest().permitAll());
        // Filters
        http.addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setExposedHeaders(Arrays.asList("x-auth-token", "Authorization"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean
    public TokenUtil tokenUtils() {
        return new TokenUtil();
    }
}
