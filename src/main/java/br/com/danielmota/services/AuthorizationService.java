package br.com.danielmota.services;

import br.com.danielmota.infra.security.JwtUtil;
import br.com.danielmota.model.User;
import br.com.danielmota.repository.UserRepository;
import br.com.danielmota.data.dto.LoginDTO;
import br.com.danielmota.data.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public void register(RegisterDTO dto) {

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }

        if (dto.getEmail() == null || !dto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }

        if (dto.getSenha() == null || dto.getSenha().length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres.");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Este e-mail já está cadastrado.");
        }

        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());

        user.setSenha(passwordEncoder.encode(dto.getSenha()));

        userRepository.save(user);
    }

    public String login(LoginDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou senha incorretos"));

        if (!passwordEncoder.matches(dto.getSenha(), user.getPassword())) {
            throw new RuntimeException("Email ou senha incorretos");
        }

        return jwtUtil.generateToken(user, user.getId());
    }
}
