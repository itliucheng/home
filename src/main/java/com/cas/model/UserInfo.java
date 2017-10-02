package com.cas.model;

/**
 * Created by wangliucheng on 2017/9/19 0019.
 *用户信息这里我写了几个较为常用的字段，id，name，username，password，可以根据实际的情况自己增加
 */

import com.cas.config.AuthorityInfo;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;
@Data
public class UserInfo implements UserDetails {
    private static final long serialVersionUID = -1041327031937199938L;
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 登录名称
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    private boolean isEnabled = true;

    private Set<AuthorityInfo> authorities = new HashSet<AuthorityInfo>();

    //以下是ldap属性
    private String mail;
    private String uid;
    private String userPassword;
    private String displayName;
    private String description;
    private String sn;
    private String cn;
}
