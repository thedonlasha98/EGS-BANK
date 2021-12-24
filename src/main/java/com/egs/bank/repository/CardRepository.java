package com.egs.bank.repository;

import com.egs.bank.domain.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card,Long> {

    Optional<Card> getByCardNo(String cardNo);
}
