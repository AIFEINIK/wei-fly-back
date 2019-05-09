package com.wei.fly.web.configuration;

import com.thetransactioncompany.cors.CORSConfiguration;
import com.thetransactioncompany.cors.CORSConfigurationException;
import com.thetransactioncompany.cors.CORSFilter;
import com.wei.fly.model.constant.CommonConstant;
import com.wei.fly.web.filter.LoginFilter;
import com.wei.fly.web.filter.WebCorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
@Configuration
public class ApplicationConfiguration {

    private static final String ALLOW_CORS = "http://localhost:9528 http://127.0.0.1:32685";

    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new LoginFilter());
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean corsFilterRegistrationBean() throws CORSConfigurationException {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        //CORSFilter filter = new CORSFilter();
        //if (CommonConstant.CORS_ALLOW_ORIGIN_ENABLE) {
        //    Properties props = new Properties();
        //    props.put(CommonConstant.CORS_ALLOW_ORIGIN, ALLOW_CORS);
        //    CORSConfiguration conf = new CORSConfiguration(props);
        //    filter = new WebCorsFilter(conf);
        //}

        registrationBean.setFilter(new CORSFilter());
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
