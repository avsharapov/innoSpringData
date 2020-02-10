package ru.inno;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan({"ru.inno"})
@PropertySource({"classpath:persistence.properties"})
@EnableJpaRepositories(basePackages = "ru.inno.repository")
public class PersistenceConfig {

  @Autowired
  private Environment env;

  public PersistenceConfig() {
    super();
  }


  @Bean
  public DataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(env.getProperty("jdbc.driverClassName"));
    hikariConfig.setJdbcUrl(env.getProperty("jdbc.url"));
    hikariConfig.setUsername(env.getProperty("jdbc.user"));
    hikariConfig.setPassword(env.getProperty("jdbc.pass"));

    hikariConfig.setMaximumPoolSize(Integer.parseInt(env.getProperty("hikari.maximumPoolSize")));
    hikariConfig.setConnectionTestQuery(env.getProperty("hikari.connectionTestQuery"));
    hikariConfig.setPoolName(env.getProperty("hikari.poolName"));

    hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts",
        env.getProperty("hikari.dataSource.cachePrepStmts"));
    hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize",
        env.getProperty("hikari.dataSource.prepStmtCacheSize"));
    hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit",
        env.getProperty("hikari.dataSource.prepStmtCacheSqlLimit"));

    return new HikariDataSource(hikariConfig);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan("ru.inno.model");
    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(additionalProperties());
    return em;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  final Properties additionalProperties() {

    final Properties hibernateProperties = new Properties();
    hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
    hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
    hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
    hibernateProperties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
    return hibernateProperties;
  }

}