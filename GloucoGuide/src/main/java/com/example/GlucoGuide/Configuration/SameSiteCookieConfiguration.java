package com.example.GlucoGuide.Configuration;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class SameSiteCookieConfiguration {

    @Bean
    public FilterRegistrationBean<Filter> sameSiteFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SameSiteFilter());
        return registrationBean;
    }

    public class SameSiteFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            chain.doFilter(request, response);
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("JSESSIONID".equals(cookie.getName())) {
                        String newHeader = String.format("%s=%s; Path=%s; %s", cookie.getName(), cookie.getValue(), cookie.getPath(), "SameSite=None; Secure");
                        res.setHeader("Set-Cookie", newHeader);
                    }
                }
            }
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {}

        @Override
        public void destroy() {}
    }


}