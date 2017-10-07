package com.cas.controller;

import com.cas.config.AuthorityInfo;
import com.cas.model.UserInfo;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Set;


@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(HttpServletRequest request) {

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        String name = userInfo.getUid();
        String role = null;
        Set<AuthorityInfo> authorities = userInfo.getAuthorities();
        if(authorities.iterator().hasNext()){
            AuthorityInfo next = authorities.iterator().next();
            role = next.getAuthority();
        }
        request.setAttribute("role",role);
        request.setAttribute("name",name);
        return "index";
    }
}
