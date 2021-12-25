package com.egs.bank.controller;

import com.egs.bank.annotations.AuthParam;
import com.egs.bank.domain.Card;
import com.egs.bank.domain.JwtToken;
import com.egs.bank.exception.EGSException;
import com.egs.bank.exception.ErrorKey;
import com.egs.bank.model.request.LoginRequest;
import com.egs.bank.model.response.EGSResponse;
import com.egs.bank.model.response.JwtResponse;
import com.egs.bank.repository.CardRepository;
import com.egs.bank.repository.JwtRepository;
import com.egs.bank.security.jwt.JwtUtils;
import com.egs.bank.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtRepository jwtRepository;

    @PostMapping("/signin")
    public EGSResponse<JwtResponse> authenticateCard(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getCardNo(), loginRequest.getFingerprint()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId());
        jwtRepository.save(new JwtToken(jwtResponse.getCardId(),jwtResponse.getToken(),jwtResponse.getType()));

        return new EGSResponse(jwtResponse);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/check-pin/{pin}")
    EGSResponse<Void> checkPinValidity(@AuthParam Long cardId, @PathVariable String pin) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new EGSException(ErrorKey.CARD_NOT_FOUND));
        boolean validPin = encoder.matches(pin, card.getPin());

        if (validPin) {
            card.setAttempts(0);
        } else {
            card.setAttempts(card.getAttempts() + 1);
            if (card.getAttempts() == 3) {
                card.setIsBlocked(1);
            }
            cardRepository.save(card);
            return new EGSResponse();
        }

        cardRepository.save(card);

        return new EGSResponse();
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/logout")
    public EGSResponse<Void> logout(@AuthParam Long cardId) {
        jwtRepository.deleteById(cardId);

        return new EGSResponse();
    }

    @GetMapping
    public EGSResponse<List<JwtToken>> getTokens() {

        return new EGSResponse(jwtRepository.findAll());
    }
}
