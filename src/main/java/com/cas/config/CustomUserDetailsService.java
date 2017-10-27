package com.cas.config;

import com.cas.Dao.LdapDao;
import com.cas.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangliucheng on 2017/10/3 0003.
 */
public class CustomUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    @Autowired
    private LdapDao ldapDao;
    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        System.out.println("当前的用户名是："+token.getName());
        /*这里我为了方便，就直接返回一个用户信息，实际当中这里修改为查询数据库或者调用服务什么的来获取用户信息*/

        //token.getName()是唯一标识
        UserInfo userInfo = ldapDao.getUserDN(token.getName());
        Set<AuthorityInfo> authorities = new HashSet<AuthorityInfo>();
        //设置权限
        AuthorityInfo authorityInfo = new AuthorityInfo(userInfo.getDescription());
        authorities.add(authorityInfo);
        userInfo.setAuthorities(authorities);
        return userInfo;
    }
}
