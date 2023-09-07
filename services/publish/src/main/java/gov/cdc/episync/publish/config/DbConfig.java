package gov.cdc.episync.publish.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;
import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:persistence.properties"})
@EnableJpaRepositories(
        basePackages = "gov.cdc.episync.publish.entity",
        entityManagerFactoryRef = "mmgManagerFactory",
        transactionManagerRef = "mmgTransactionManager"
    )
public class DbConfig {
    private final Environment env;

    public DbConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @ConfigurationProperties(prefix="postgres.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mmgManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("gov.cdc.episync.publish.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties(env, "postgres"));
        return em;
    }

    @Bean
    public PlatformTransactionManager mmgTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(mmgManagerFactory().getObject());
        return transactionManager;
    }

    static Properties additionalProperties(Environment env, String prefix) {
        final Properties hibernateProperties = new Properties();
        if (env.getProperty("hibernate.hbm2ddl.auto") != null) {
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        }
        final String dialectProp = prefix + "." + "hibernate.dialect";
        if (env.getProperty(dialectProp) != null) {
            hibernateProperties.setProperty("hibernate.dialect", env.getProperty(dialectProp));
        }
        if (env.getProperty("hibernate.show_sql") != null) {
            hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        }
        return hibernateProperties;
    }
}

@Configuration
@PropertySource("classpath:query.properties")
class QueryConfiguration {
}
