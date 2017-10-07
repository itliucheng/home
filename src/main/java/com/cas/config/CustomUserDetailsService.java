package com.cas.config;

import com.cas.Dao.LdapDao;
import com.cas.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wangliucheng on 2017/10/3 0003.
 */
public class CustomUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    @Autowired
    private LdapDao ldapDao;
    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        String login = token.getPrincipal().toString();
        String username = login.toLowerCase();

        UserInfo userInfo = ldapDao.getUserDN("zhangsan");
        Set<AuthorityInfo> authorities = new HashSet<AuthorityInfo>();
        //设置权限
        AuthorityInfo authorityInfo = new AuthorityInfo(userInfo.getDescription());
        authorities.add(authorityInfo);
        userInfo.setAuthorities(authorities);
        return userInfo;
    }
}
