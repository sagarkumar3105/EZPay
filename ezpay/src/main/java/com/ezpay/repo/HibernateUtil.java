package com.ezpay.repo;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.ezpay.model.Customer;


public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    
    static {
        try {
        	 // Create the Configuration instance and configure it
            Configuration configuration = new Configuration().configure().addAnnotatedClass(Customer.class);
            
            // Create the ServiceRegistry from the Configuration
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
            
            // Create the SessionFactory from the ServiceRegistry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                } catch (Exception ex) {
                	ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
}
	

