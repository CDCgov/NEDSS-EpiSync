package gov.cdc.episync.publish.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:persistence.properties")
@EnableJpaRepositories(
        basePackages = "gov.cdc.nbs.questionbank.repository.odse",
        entityManagerFactoryRef = "odseEntityManager",
        transactionManagerRef = "odseTransactionManager"
)
public class DbConfigOdse {
    private final Environment env;

    public DbConfigOdse(Environment env) {
        this.env = env;
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix="mssql.odse")
    public DataSource odseDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean odseEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(odseDataSource());
        em.setPackagesToScan("gov.cdc.nbs.questionbank.entity.odse");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(DbConfig.additionalProperties(env, "mssql"));
        return em;
    }

    @Bean
    public PlatformTransactionManager odseTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(odseEntityManager().getObject());
        return transactionManager;
    }
}
