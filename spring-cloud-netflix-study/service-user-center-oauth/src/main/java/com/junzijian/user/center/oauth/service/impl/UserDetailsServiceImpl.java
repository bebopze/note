package com.junzijian.user.center.oauth.service.impl;

import com.google.common.collect.Lists;
import com.junzijian.user.center.oauth.client.UCenterClient;
import com.junzijian.user.center.oauth.config.UserJwt;
import com.junzijian.framework.model.ucenter.entity.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UCenterClient uCenterClient;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                // 密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        // TODO 调用用户中心根据账号查询用户信息
        UserDO userDO = uCenterClient.getUserByUsername(username);
//        EmployeeDO user = employeeDOMapper.selectByUsername();
        UserDO user = new UserDO();
        if (user == null) {
            // 返回空给spring security表示用户不存在
            return null;
        }
//        XcUserExt userext = new XcUserExt();
//        userext.setUsername("lz");
//        userext.setPassword(new BCryptPasswordEncoder().encode("123"));
//        userext.setPermissions(new ArrayList<XcMenu>());//权限暂时用静态的

        //取出正确密码（hash值）
//        String password = user.getPassword();
        // 这里暂时使用静态密码
        String password = "$2a$10$9bEpZ/hWRQxyr5hn5wHUj.jxFpIrnOmBcWlE/g/0Zp3uNxt9QTh/S";
        //用户权限，这里暂时使用静态数据，最终会从数据库读取

        // 从数据库获取权限
//        List<XcMenu> permissions = user.getPermissions();
        List<String> permissions = null;
        if (permissions == null) {
            permissions = Lists.newArrayList();
        }

        List<String> user_permission = new ArrayList<>();
//        permissions.forEach(item -> user_permission.add(item.getCode()));

        // 使用静态的权限表示用户所拥有的权限
        user_permission.add("get_baseinfo");
        user_permission.add("pic_list");
        String user_permission_string = StringUtils.join(user_permission.toArray(), ",");

        UserJwt userDetails = new UserJwt(
                username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));

        userDetails.setId(String.valueOf(user.getId()));
        userDetails.setName(user.getName());

       /* UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(""));*/
//                AuthorityUtils.createAuthorityList("course_get_baseinfo","course_get_list"));

        return userDetails;
    }
}
