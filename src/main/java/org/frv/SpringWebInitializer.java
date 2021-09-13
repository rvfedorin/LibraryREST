package org.frv;

import org.frv.config.JpaTransactionManagerConfig;
import org.frv.config.MvcConfig;
import org.frv.config.RootConfig;
import org.frv.config.SecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public class SpringWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter("spring.profiles.active", "dev_compose");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class, JpaTransactionManagerConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
