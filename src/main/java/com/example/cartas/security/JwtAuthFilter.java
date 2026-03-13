package com.example.cartas.security;
import com.example.cartas.repository.PartnerRepository;
import com.example.cartas.repository.SuperAdminRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final SuperAdminRepository superAdminRepo;
    private final PartnerRepository partnerRepo;

    public JwtAuthFilter(JwtService jwtService, SuperAdminRepository superAdminRepo, PartnerRepository partnerRepo) {
        this.jwtService = jwtService;
        this.superAdminRepo = superAdminRepo;
        this.partnerRepo = partnerRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws IOException, jakarta.servlet.ServletException {

        final String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(req, res); return;
        }

        final String token = authHeader.substring(7);
        if (!jwtService.isValid(token)) {
            chain.doFilter(req, res); return;
        }

        final String email = jwtService.extractEmail(token);
        final String role  = jwtService.extractRole(token);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            boolean valid = false;

            if ("SUPER_ADMIN".equals(role)) {
                valid = superAdminRepo.findByEmail(email).isPresent();
            } else {
                valid = partnerRepo.findByEmail(email)
                        .map(p -> p.getIsActive() && p.getInvitationAccepted())
                        .orElse(false);
            }

            if (valid) {
                var auth = new UsernamePasswordAuthenticationToken(
                        email, null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(req, res);
    }
}
