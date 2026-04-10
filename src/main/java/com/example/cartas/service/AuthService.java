package com.example.cartas.service;

import com.example.cartas.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.cartas.repository.SuperAdminRepository;
import com.example.cartas.repository.PartnerRepository;
import com.example.cartas.repository.AppUserRepository;
import com.example.cartas.repository.SpecialistRepository;
import com.example.cartas.repository.ArtTherapistRepository;
import com.example.cartas.entity.SuperAdmin;
import com.example.cartas.entity.AppUser;
import com.example.cartas.entity.Specialist;
import com.example.cartas.entity.ArtTherapist;
import com.example.cartas.dto.AuthResponse;
import com.example.cartas.dto.LoginRequest;
import com.example.cartas.dto.RegisterRequest;
import com.example.cartas.dto.MobileRegisterRequest;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

@Service
@Slf4j  // Pour les logs
public class AuthService {

    private final SuperAdminRepository saRepo;
    private final PartnerRepository partnerRepo;
    private final AppUserRepository appUserRepo;
    private final SpecialistRepository specialistRepo;
    private final ArtTherapistRepository artTherapistRepo;
    private final JwtService jwt;
    private final PasswordEncoder encoder;

    public AuthService(SuperAdminRepository saRepo,
                       PartnerRepository partnerRepo,
                       AppUserRepository appUserRepo,
                       SpecialistRepository specialistRepo,
                       ArtTherapistRepository artTherapistRepo,
                       JwtService jwt,
                       PasswordEncoder encoder) {
        this.saRepo = saRepo;
        this.partnerRepo = partnerRepo;
        this.appUserRepo = appUserRepo;
        this.specialistRepo = specialistRepo;
        this.artTherapistRepo = artTherapistRepo;
        this.jwt = jwt;
        this.encoder = encoder;
    }
    @Value("${cartas.activation-key}")
    private String activationKey;

    // ── Vérifier si SA déjà enregistré ──────────────
    public boolean isSuperAdminRegistered() {
        return saRepo.isSuperAdminRegistered();
    }

    // ── Inscription Super Admin (unique) ─────────────
    public AuthResponse register(RegisterRequest req) {
        // Vérifications
        if (saRepo.isSuperAdminRegistered()) {
            throw new RuntimeException("Un Super Admin est déjà enregistré.");
        }

        if (!activationKey.equals(req.getSecretKey())) {
            throw new RuntimeException("Clé d'activation incorrecte.");
        }

        if (saRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé.");
        }

        // Création du Super Admin
        var sa = SuperAdmin.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .activationKey(req.getSecretKey())
                .isActive(true)
                .isFirstLogin(true)
                .preferredTheme("dark")
                .build();
        
        saRepo.save(sa);
    

        return buildAuth(sa.getId(), sa.getEmail(), sa.getFirstName(),
                sa.getLastName(), "SUPER_ADMIN", "dark");
    }

    // ── Inscription Mobile (AppUser, Specialist, ArtTherapist) ──
    public void registerMobile(MobileRegisterRequest req, MultipartFile document) {
        // Validation d'email unique globale
        if (saRepo.findByEmail(req.getEmail()).isPresent() ||
            partnerRepo.findByEmail(req.getEmail()).isPresent() ||
            appUserRepo.findByEmail(req.getEmail()).isPresent() ||
            specialistRepo.findByEmail(req.getEmail()).isPresent() ||
            artTherapistRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé.");
        }

        String encodedPassword = encoder.encode(req.getPassword());
        String documentName = (document != null) ? document.getOriginalFilename() : null;

        switch (req.getRole().toUpperCase()) {
            case "APP_USER":
                AppUser user = AppUser.builder()
                        .firstName(req.getFirstName())
                        .lastName(req.getLastName())
                        .email(req.getEmail())
                        .password(encodedPassword)
                        .phone(req.getPhone())
                        .status(AppUser.UserStatus.ACTIVE)
                        .build();
                appUserRepo.save(user);
                break;

            case "SPECIALIST":
                Specialist specialist = Specialist.builder()
                        .firstName(req.getFirstName())
                        .lastName(req.getLastName())
                        .email(req.getEmail())
                        .password(encodedPassword)
                        .specialty(req.getSpecialty())
                        .licenseNumber(req.getLicenseNumber())
                        .bio(req.getBio())
                        .certificateUrl(documentName)
                        .status(Specialist.SpecialistStatus.PENDING)
                        .build();
                specialistRepo.save(specialist);
                break;

            case "ART_THERAPIST":
                ArtTherapist therapist = ArtTherapist.builder()
                        .firstName(req.getFirstName())
                        .lastName(req.getLastName())
                        .email(req.getEmail())
                        .password(encodedPassword)
                        .artDiscipline(req.getArtDiscipline())
                        .bio(req.getBio())
                        .certificateUrl(documentName)
                        .status(ArtTherapist.TherapistStatus.PENDING)
                        .build();
                artTherapistRepo.save(therapist);
                break;

            default:
                throw new RuntimeException("Rôle mobile non reconnu : " + req.getRole());
        }
    }

    // ── Connexion ────────────────────────────────────
    public AuthResponse login(LoginRequest req) {
        // Tentative Super Admin
        var saOpt = saRepo.findByEmail(req.getEmail());
        if (saOpt.isPresent()) {
            var sa = saOpt.get();
            if (!encoder.matches(req.getPassword(), sa.getPassword())) {
                throw new RuntimeException("Mot de passe incorrect.");
            }
            return buildAuth(sa.getId(), sa.getEmail(), sa.getFirstName(),
                    sa.getLastName(), "SUPER_ADMIN", sa.getPreferredTheme());
        }

        // Tentative Partner
        var pOpt = partnerRepo.findByEmail(req.getEmail());
        if (pOpt.isPresent()) {
            var p = pOpt.get();
            
            // Vérifications Partner
            if (!p.getInvitationAccepted() || !p.getIsActive()) {
                throw new RuntimeException("Compte inactif ou invitation non acceptée.");
            }
            
            if (!encoder.matches(req.getPassword(), p.getPassword())) {
                throw new RuntimeException("Mot de passe incorrect.");
            }
            
            return buildAuth(p.getId(), p.getEmail(), p.getFirstName(),
                    p.getLastName(), p.getRole().getName(), "dark");
        }

        // Tentative AppUser
        var uOpt = appUserRepo.findByEmail(req.getEmail());
        if (uOpt.isPresent()) {
            var u = uOpt.get();
            if (u.getStatus() == AppUser.UserStatus.BLOCKED) {
                throw new RuntimeException("Votre compte est bloqué.");
            }
            if (!encoder.matches(req.getPassword(), u.getPassword())) {
                throw new RuntimeException("Mot de passe incorrect.");
            }
            return buildAuth(u.getId(), u.getEmail(), u.getFirstName(),
                    u.getLastName(), "APP_USER", "dark");
        }

        // Tentative Specialist
        var sOpt = specialistRepo.findByEmail(req.getEmail());
        if (sOpt.isPresent()) {
            var s = sOpt.get();
            if (s.getStatus() == Specialist.SpecialistStatus.PENDING) {
                throw new RuntimeException("Votre compte est en attente de validation par l'administrateur.");
            }
            if (s.getStatus() == Specialist.SpecialistStatus.BLOCKED) {
                throw new RuntimeException("Votre compte est bloqué.");
            }
            if (!encoder.matches(req.getPassword(), s.getPassword())) {
                throw new RuntimeException("Mot de passe incorrect.");
            }
            return buildAuth(s.getId(), s.getEmail(), s.getFirstName(),
                    s.getLastName(), "SPECIALIST", "dark");
        }

        // Tentative ArtTherapist
        var tOpt = artTherapistRepo.findByEmail(req.getEmail());
        if (tOpt.isPresent()) {
            var t = tOpt.get();
            if (t.getStatus() == ArtTherapist.TherapistStatus.PENDING) {
                throw new RuntimeException("Votre compte est en attente de validation par l'administrateur.");
            }
            if (t.getStatus() == ArtTherapist.TherapistStatus.BLOCKED) {
                throw new RuntimeException("Votre compte est bloqué.");
            }
            if (!encoder.matches(req.getPassword(), t.getPassword())) {
                throw new RuntimeException("Mot de passe incorrect.");
            }
            return buildAuth(t.getId(), t.getEmail(), t.getFirstName(),
                    t.getLastName(), "ART_THERAPIST", "dark");
        }

        throw new RuntimeException("Email introuvable.");
    }

    // ── Construire la réponse auth ───────────────────
    private AuthResponse buildAuth(Long id, String email, String firstName,
                                   String lastName, String role, String theme) {
        
        String accessToken = jwt.generateAccessToken(email, role, id);
        String refreshToken = jwt.generateRefreshToken(email);
        
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwt.getAccessExpiration())
                .user(AuthResponse.UserInfo.builder()
                        .id(id)
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .role(role)
                        .preferredTheme(theme)
                        .build())
                .build();
    }
}