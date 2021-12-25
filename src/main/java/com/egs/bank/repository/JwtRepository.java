package com.egs.bank.repository;

import com.egs.bank.domain.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepository extends CrudRepository<JwtToken,Long> {

    JwtToken getJwtTokenByToken(String token);

    void deleteJwtTokenByToken(String token);

//    @Qualifier("redisTemplate")
//    @Autowired
//    private RedisTemplate template;
//
//    public JwtToken save(JwtToken jwtToken){
//        template.opsForHash().put("jwt_token",jwtToken.getId(), jwtToken);
//        return jwtToken;
//    }
//
//    public JwtToken getById(Long id) {
//        return (JwtToken) template.opsForHash().get("jwt_token", id);
//    }
//
//    public void deleteById(Long id){
//        template.opsForHash().delete("jwt_token",id);
//    }
//
//    public void deleteByToken(String token){
//        template.opsForHash().delete("jwt_token",token);
//    }

}
