package com.egs.bank.security.services;

import com.egs.bank.domain.Card;
import com.egs.bank.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String cardNo) throws UsernameNotFoundException {
        String eCardNo = Base64.getEncoder().encodeToString(cardNo.getBytes());
        Card card = cardRepository.getByCardNo(eCardNo)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + cardNo));

        return UserDetailsImpl.build(card);
    }

}
