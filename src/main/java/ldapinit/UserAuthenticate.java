package ldapinit;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * Created by wangliucheng on 2017/11/15 0015.
 */
public class UserAuthenticate {
    private String URL = "ldap://114.67.133.83:10389";
    //private String BASEDN = "cn=zhangsan,ou=users,ou=system";
    private String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private LdapContext ctx = null;
    private Hashtable env = null;
    private Control[] connCtls = null;

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void LDAP_connect() {
        env = new Hashtable();
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

    public void getUserDN() {
        String dn = "zhangsan";
        SearchControls controls = new SearchControls();
        //限制要查询的字段内容
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        //设置过滤条件
        String filter = "(&(objectClass=top)(objectClass=person)(cn=" + dn
                + "))";
        //设置被返回的attribute
        controls.setReturningAttributes(new String[]{"uid", "userPassword",
                "displayName", "cn", "sn", "mail", "description"});
        try {
            //控制搜索的搜索条件，如果为null则使用默认的搜索控件，要搜索的属性如果为null则返回目标上下文中的所有对象
            NamingEnumeration answer = ctx
                    .search("ou=system", filter, controls);
            while (answer.hasMore()) {
                SearchResult result = (SearchResult) answer.next();
                NamingEnumeration en = result.getAttributes().getAll();
                if (en == null) {
                    System.out.println("Have no NamingEnumeration");
                }
                if (!en.hasMoreElements()) {
                    System.out.println("Have no element");
                }
                //输出查询到的结果
                while (en.hasMore()) {
                    Attribute attr = (Attribute) en.next();
                    System.out.println(attr.getID() + "=" + attr.get());
                }
            }

        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    public void testAdd() throws Exception {
        Attributes attrs = new BasicAttributes(true);
        Attribute objclass = new BasicAttribute("objectclass");
        String[] attrObjectClassPerson = {"inetOrgPerson",
                "organizationalPerson", "person", "top"};

        Arrays.sort(attrObjectClassPerson);
        for (String ocp : attrObjectClassPerson) {
            objclass.add(ocp);
        }
        attrs.put(objclass);
        String uid = "shiguangadmin";
        String userDN = "uid=" + uid + "," + "ou=system";
        attrs.put("cn", uid);
        attrs.put("sn", uid);
        attrs.put("displayName", "shiguang");
        attrs.put("description", "admin");
        attrs.put("mail", "shiguang@126.com");
        attrs.put("userPassword", "111111".getBytes("UTF-8"));
        ctx.createSubcontext(userDN, attrs);
    }

    public void testDelete() {
        String uid = "zhangsan";
        String userDN = "uid=" + uid + "," + "ou=system";
        try {
            ctx.destroySubcontext(userDN);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public boolean testEdit() {
        boolean result = true;
        String uid = "admin";
        String userDN = "uid=" + uid + "," + "ou=system";
        Attributes attr = new BasicAttributes(true);
        attr.put("description", "admin");
        try {
            ctx.modifyAttributes(userDN, DirContext.REPLACE_ATTRIBUTE, attr);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
