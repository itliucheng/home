package com.cas.Dao;


import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * Created by wangliucheng on 2017/9/20 0020.
 */
public class LdapFactory {


    private LdapFactory(){}
    public static LdapContext getInstance(){
        return LdapHolder.ctx;
    }
    private static class  LdapHolder {
        private static LdapContext ctx = null;
        private static String URL = "ldap://114.67.133.83:10389";
        private static String BASEDN = "cn=admin,ou=users,ou=system";
        private static String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
        private static Hashtable env = new Hashtable();
        private static Control[] connCtls = null;
        static {
            env.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY);
            env.put(Context.PROVIDER_URL, URL);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            //用户名
            env.put(Context.SECURITY_PRINCIPAL, "uid=admin;ou=system");
            //密码
            env.put(Context.SECURITY_CREDENTIALS, "secret");
            //env.put(Context.SECURITY_CREDENTIALS, "123456");
            env.put("java.naming.referral", "follow");
            try {
                ctx = new InitialLdapContext(env, connCtls);
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }
}
