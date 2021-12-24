package com.egs.bank.security.services;

import com.egs.bank.domain.Card;
import com.egs.bank.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    CardRepository cardRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String cardNo) throws UsernameNotFoundException {
        Card card = cardRepository.getByCardNo(cardNo)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + cardNo));

        return UserDetailsImpl.build(card);
    }

}
