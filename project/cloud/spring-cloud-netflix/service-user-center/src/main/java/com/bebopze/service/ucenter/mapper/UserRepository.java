package com.bebopze.service.ucenter.mapper;

import com.bebopze.framework.model.ucenter.entity.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author bebopze
 * @date 2019/5/5
 */
public interface UserRepository extends JpaRepository<UserDO, Long> {

    UserDO findByName(String username);

    UserDO findByEmail(String Email);
}
