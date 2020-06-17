package com.junzijian.service.ucenter.mapper;

import com.junzijian.framework.model.ucenter.entity.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author bebopze
 * @date 2019/5/5
 */
public interface UserRepository extends JpaRepository<UserDO, Long> {

    UserDO findByName(String username);

    UserDO findByEmail(String Email);
}
