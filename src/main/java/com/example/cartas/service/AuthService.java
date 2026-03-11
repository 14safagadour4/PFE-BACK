package com.example.cartas.service;

import com.example.cartas.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.cartas.repository.SuperAdminRepository;
import com.example.cartas.repository.PartnerRepository;
import com.example.cartas.entity.SuperAdmin;
import com.example.cartas.dto.AuthResponse;
import com.example.cartas.dto.LoginRequest;
import com.example.cartas.dto.RegisterRequest;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final SuperAdminRepository saRepo;
    private final PartnerRepository    partnerRepo;
    private final JwtService           jwt;
    private final PasswordEncoder      encoder;

    @Value("${cartas.activation-key}")
    private String activationKey;

    // ── Vérifier si SA déjà enregistré ──────────────
    public boolean isSuperAdminRegistered() {
        return saRepo.isSuperAdminRegistered();
    }

    // ── Inscription Super Admin (unique) ─────────────
    public AuthResponse register(RegisterRequest req) {
        if (saRepo.isSuperAdminRegistered())
            throw new RuntimeException("Un Super Admin est déjà enregistré.");

        if (!activationKey.equals(req.getSecretKey()))
            throw new RuntimeException("Clé d'activation incorrecte.");

        if (saRepo.findByEmail(req.getEmail()).isPresent())
            throw new RuntimeException("Cet email est déjà utilisé.");

        var sa = SuperAdmin.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .build();
        saRepo.save(sa);

        return buildAuth(sa.getId(), sa.getEmail(), sa.getFirstName(),
                         sa.getLastName(), "SUPER_ADMIN", "dark");
    }

    // ── Connexion ────────────────────────────────────
    public AuthResponse login(LoginRequest req) {
        // Tentative Super Admin
        var saOpt = saRepo.findByEmail(req.getEmail());
        if (saOpt.isPresent()) {
            var sa = saOpt.get();
            if (!encoder.matches(req.getPassword(), sa.getPassword()))
                throw new RuntimeException("Mot de passe incorrect.");
            return buildAuth(sa.getId(), sa.getEmail(), sa.getFirstName(),
                             sa.getLastName(), "SUPER_ADMIN", sa.getPreferredTheme());
        }

        // Tentative Partner
        var pOpt = partnerRepo.findByEmail(req.getEmail());
        if (pOpt.isPresent()) {
            var p = pOpt.get();
            if (!p.getInvitationAccepted() || !p.getIsActive())
                throw new RuntimeException("Compte inactif ou invitation non acceptée.");
            if (!encoder.matches(req.getPassword(), p.getPassword()))
                throw new RuntimeException("Mot de passe incorrect.");
            return buildAuth(p.getId(), p.getEmail(), p.getFirstName(),
                             p.getLastName(), p.getRole().getName(), "dark");
        }

        throw new RuntimeException("Email introuvable.");
    }

    // ── Construire la réponse auth ───────────────────
    private AuthResponse buildAuth(Long id, String email, String firstName,
                                   String lastName, String role, String theme) {
        return AuthResponse.builder()
                .accessToken(jwt.generateAccessToken(email, role, id))
                .refreshToken(jwt.generateRefreshToken(email))
                .tokenType("Bearer")
                .expiresIn(jwt.getAccessExpiration())
                .user(AuthResponse.UserInfo.builder()
                        .id(id).firstName(firstName).lastName(lastName)
                        .email(email).role(role).preferredTheme(theme)
                        .build())
                .build();
    }
}
