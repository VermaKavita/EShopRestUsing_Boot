package com.ecom.shopping_cart.jwtAuth;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

        private Logger logger= LoggerFactory.getLogger ( OncePerRequestFilter.class );
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestTokenHeader = request.getHeader ( "Authorization" );
        
        String username=null;
        String token=null;
  
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
            token=requestTokenHeader.substring(7);
        	try {
                username = this.jwtHelper.extractUsername ( token );

            } catch (IllegalArgumentException e) {
                logger.info ( "Illegal argument while fetching username  " );
                e.printStackTrace ();
            } catch (ExpiredJwtException e) {
                logger.info ( "Given jwt Is expired" );
                e.printStackTrace ();
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }else {
        	logger.warn("JWT doesn't start with Bearer");
        }
            //security purpose
            if (username != null && SecurityContextHolder.getContext ().getAuthentication () == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername ( username );
                Boolean validateToken = this.jwtHelper.validateToken ( token, userDetails );
                if (validateToken) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken ( username, null, userDetails.getAuthorities () );
                    usernamePasswordAuthenticationToken.setDetails ( new WebAuthenticationDetailsSource ().buildDetails ( request ) );
                    SecurityContextHolder.getContext ().setAuthentication ( usernamePasswordAuthenticationToken );

                } else {
                    logger.info ( "validation fails" );
                }
            }
        filterChain.doFilter ( request, response );
    }
}
