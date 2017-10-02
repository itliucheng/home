package com.cas.Dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cas.model.UserInfo;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.LdapContext;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangliucheng on 2017/9/20 0020.
 */
@Repository
public class LdapDao {

    private static LdapContext ctx = LdapFactory.getInstance();

    public UserInfo getUserDN(String dn) {
        Map map = new HashMap();
        UserInfo userInfo = new UserInfo();
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
            NamingEnumeration answer = ctx.search("ou=system", filter, controls);
            while (answer.hasMore()) {
                SearchResult result = (SearchResult) answer.next();
                NamingEnumeration en = result.getAttributes().getAll();
                //输出查询到的结果
                while (en.hasMore()) {
                    Attribute attr = (Attribute) en.next();
                    map.put(attr.getID(),attr.get());
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        if(map.size()>0){
            userInfo = JSONObject.parseObject(JSON.toJSONString(map), UserInfo.class);
        }
        return userInfo;
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
        String uid = "zhangsan3";
        String userDN = "uid=" + uid + "," + "ou=system";
        attrs.put("cn", uid);
        attrs.put("sn", uid);
        attrs.put("displayName", "张三3");
        attrs.put("description", "not null3");
        attrs.put("mail", "abc3@126.com");
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
        String uid = "zhangsan";
        String userDN = "uid=" + uid + "," + "ou=system";
        Attributes attr = new BasicAttributes(true);
        attr.put("mail", "zhangsan@163.com");
        try {
            ctx.modifyAttributes(userDN, DirContext.REPLACE_ATTRIBUTE, attr);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
