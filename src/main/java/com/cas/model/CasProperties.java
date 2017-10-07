package com.cas.model;

/**
 * Created by wangliucheng on 2017/9/19 0019.
 *  * CAS的配置参数
 */
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
@Data
public class CasProperties {
    @Value("${app.service.security}")
    private String appServiceSecurity;

    @Value("${app.service.home}")
    private String appServiceHome;

    @Value("${cas.service.login}")
    private String casServiceLogin;

    @Value("${cas.service.logout}")
    private String casServiceLogout;

    @Value("${cas.url.prefix}")
    private String casUrlPrefix;
}
