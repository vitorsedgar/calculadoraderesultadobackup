package com.br.ages.calculadoraback.security;

import com.br.ages.calculadoraback.entity.UserEntity;
import com.br.ages.calculadoraback.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("userDetailsService")
public class UserDetailsImplementacao implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String document) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByDocument(document).orElseThrow(() -> new Exception("usuario nao encontrado"));

        List<GrantedAuthority> authorities = Stream
            .of(new SimpleGrantedAuthority(user.getRole() + "/" + user.getCodCoop().getCodCoop()))
                .collect(Collectors.toList());
        return new User(user.getDocument(), user.getPassword(), authorities);
    }
}

