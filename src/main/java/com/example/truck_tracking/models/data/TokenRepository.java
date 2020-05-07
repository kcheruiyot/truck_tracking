package com.example.truck_tracking.models.data;

import com.example.truck_tracking.models.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kipngetich
 */
@Repository
public interface TokenRepository extends CrudRepository<Token,Integer> {
    Token findByToken(String token);
}
