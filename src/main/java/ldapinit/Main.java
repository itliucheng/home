package ldapinit;

/**
 * Created by wangliucheng on 2017/11/15 0015.
 */
public class Main {
    public static void main(String[] args) {
        UserAuthenticate test = new UserAuthenticate();
        try {
            test.LDAP_connect();
           test.testAdd();
            // test.getUserDN();
            //test.testEdit();
            // test.testDelete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
