package br.com.danielmota.services;

import br.com.danielmota.model.User;
import br.com.danielmota.repository.UserRepository;
import br.com.danielmota.data.dto.LoginDTO;
import br.com.danielmota.data.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public void register(RegisterDTO dto) {
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        userRepository.save(user);
    }

    public boolean login(LoginDTO dto) {
        var user = userRepository.findByEmail(dto.getEmail());
        if (user.isEmpty()) return false;
        return user.get().getPassword().equals(dto.getSenha());
    }
}
