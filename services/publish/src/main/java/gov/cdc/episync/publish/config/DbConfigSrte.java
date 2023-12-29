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

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:persistence.properties")
@EnableJpaRepositories(
        basePackages = "gov.cdc.nbs.questionbank.repository.srte",
        entityManagerFactoryRef = "srteEntityManager",
        transactionManagerRef = "srteTransactionManager"
)
public class DbConfigSrte {
    private final Environment env;

    public DbConfigSrte(Environment env) {
        this.env = env;
    }

    @Bean
    @ConfigurationProperties(prefix="mssql.srte")
    public DataSource srteDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean srteEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(srteDataSource());
        em.setPackagesToScan("gov.cdc.nbs.questionbank.entity.srte");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(DbConfig.additionalProperties(env, "mssql"));
        return em;
    }

    @Bean
    public PlatformTransactionManager srteTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(srteEntityManager().getObject());
        return transactionManager;
    }
}
