package com.egs.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;

@RedisHash("jwt_token")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtToken implements Serializable {

    @Id
    private Long id;

    @Indexed
    private String token;

    private String type;
}
