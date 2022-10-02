package news.MovieManager.filter;

import lombok.extern.slf4j.Slf4j;
import news.MovieManager.model.Token;
import news.MovieManager.service.AuthService;
import news.MovieManager.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;
    private final AuthService authService;

    private static final String AUTH_HEADER = "Bearer ";

    @Autowired
    public JwtTokenFilter(TokenUtil tokenUtil, AuthService authService) {
        this.tokenUtil = tokenUtil;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Validate Bearer header
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(header) || !header.startsWith(JwtTokenFilter.AUTH_HEADER)) {
            chain.doFilter(request, response);
            return;
        }
        String token = header.split(" ")[1].trim();
        if (StringUtils.isBlank(token)) {
            chain.doFilter(request, response);
            return;
        }
        // Validate token format
        Token parsedToken;
        try {
            parsedToken = tokenUtil.parse(token);
        } catch (Exception ex) {
            log.error("An error occurred while parsing the security token. Error: {}, Trace: {}", ex.getMessage(), ex.getStackTrace());
            chain.doFilter(request, response);
            return;
        }
        // Validate username
        String username = parsedToken.getSubject();
        if (StringUtils.isBlank(username)) {
            log.error("An error occurred while parsing username.");
            chain.doFilter(request, response);
            return;
        }
        // Validate user
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.authService.loadUserByUsername(username);
            if (tokenUtil.validate(parsedToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info(String.format("Successfully authenticated user %s", username));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

}
