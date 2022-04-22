package com.example.musicBeat.security.jwt;


import com.example.musicBeat.security.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {
    public static final Logger log= LoggerFactory.getLogger(TokenFilter.class);
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt=parseJwt(request);
            if(jwt!=null&&jwtProvider.validateToken(jwt)){
                String email=jwtProvider.getUsernameFromToken(jwt);
                UserDetails userDetails =  userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e){
            log.error("Error"+e.getMessage());
        }
        filterChain.doFilter(request,response);
    }

    public String parseJwt(HttpServletRequest request){
        String header=request.getHeader("Authorization");
        if(!header.isEmpty()&&header.startsWith("Bearer ")){
            return header.substring(7,header.length());
        }
        return null;
    }


}
