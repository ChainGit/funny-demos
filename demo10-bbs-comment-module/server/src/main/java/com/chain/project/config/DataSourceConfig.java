package com.chain.project.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.spring.stat.BeanTypeAutoProxyCreator;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.alibaba.druid.wall.WallFilter;
import com.chain.project.base.mapper.BaseMapper;
import com.chain.project.common.directory.Constant;
import com.chain.project.common.utils.ChainProjectUtils;
import com.chain.utils.crypto.CryptoFactoryBean;
import com.chain.utils.crypto.RSAUtils;
import com.github.pagehelper.PageInterceptor;
import net.sf.ehcache.CacheManager;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
//开启事务管理，也可以手动写txManager配置
@EnableTransactionManagement
@MapperScan("com.chain.project.**.mapper")
@ImportResource("classpath:datasource-config.xml")
public class DataSourceConfig {

    private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private CryptoFactoryBean cryptoFactoryBean;

    //destroy-method="close"的作用是当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用.
    @SuppressWarnings("all")
    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        logger.info("create datasource");
        RSAUtils rsaUtils = cryptoFactoryBean.getRsaUtils(true);
        String username = appConfig.getProperty("spring.datasource.username");
        String password = appConfig.getProperty("spring.datasource.password");
        if (appConfig.isEncrypt()) {
            username = rsaUtils.decryptByPrivateKey(username);
            password = rsaUtils.decryptByPrivateKey(password);
        }
        //使用Druid数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(appConfig.getProperty("spring.datasource.url"));
        dataSource.setUsername(username);//用户名
        dataSource.setPassword(password);//密码
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setInitialSize(1);//初始化时建立物理连接的个数
        dataSource.setMaxActive(20);//最大连接池数量
        dataSource.setMinIdle(1);//最小连接池数量
        dataSource.setMaxWait(60000);//获取连接时最大等待时间，单位毫秒。
        //dataSource.setFilters("slf4j,stat,wall");//配置监控统计拦截的filters
        dataSource.setValidationQuery("SELECT 'x'");//用来检测连接是否有效的sql
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTestOnBorrow(false);//申请连接时执行validationQuery检测连接是否有效
        dataSource.setTestWhileIdle(true);//建议配置为true，不影响性能，并且保证安全性。
        dataSource.setPoolPreparedStatements(true);//是否缓存preparedStatement，也就是PSCache
        dataSource.setTestOnReturn(true);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        dataSource.setLogAbandoned(false);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);
        List<Filter> filters = new ArrayList<>();
        filters.add(slf4jLogFilter());
        filters.add(statFilter());
        filters.add(wallFilter());
        dataSource.setProxyFilters(filters);
        return dataSource;
    }

    @Bean
    public Slf4jLogFilter slf4jLogFilter() {
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        slf4jLogFilter.setResultSetLogEnabled(true);
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
        slf4jLogFilter.setConnectionLogEnabled(true);
        slf4jLogFilter.setStatementLogEnabled(true);
        slf4jLogFilter.setConnectionLogErrorEnabled(true);
        return slf4jLogFilter;
    }

    @Bean
    public WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType("mysql");
        return wallFilter;
    }

    @Bean
    public StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setSlowSqlMillis(1000);
        statFilter.setDbType("mysql");
        return statFilter;
    }

    @Bean
    public BeanTypeAutoProxyCreator beanTypeAutoProxyCreator() {
        BeanTypeAutoProxyCreator beanTypeAutoProxyCreator = new BeanTypeAutoProxyCreator();
        beanTypeAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
        //没有采用AOP扫描方式，会造成问题，这里监控BaseMapper接口就行，以实现druid监控功能
        beanTypeAutoProxyCreator.setTargetBeanType(BaseMapper.class);
        return beanTypeAutoProxyCreator;
    }

    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }


    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws SQLException {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        String CLASS_PATH_STRING = "classpath:";
        String CLASS_PATH2_STRING = "classpath*:";
        /** 设置mybatis configuration 扫描路径 */
        String configLocation = "classpath:mybatis-config.xml";
        if (configLocation.startsWith(CLASS_PATH_STRING)) {
            configLocation = configLocation.replaceAll(CLASS_PATH_STRING, "");
        }
        bean.setConfigLocation(new ClassPathResource(configLocation));
        /** 添加mapper 扫描路径 */
        String CLASS_PATH_PREFIX = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX;
        String mybatisLocations = "classpath:mapper/**/*Mapper*.xml";
        //生产环境下不会加载test功能
        if (Constant.PROD_MODE.equals(appConfig.getProperty("spring.profiles.active")))
            mybatisLocations = "classpath:mapper/**/*Mapper.xml";
        if (mybatisLocations.startsWith(CLASS_PATH_STRING) || mybatisLocations.startsWith(CLASS_PATH2_STRING))
            mybatisLocations = CLASS_PATH_PREFIX + mybatisLocations.replaceAll(CLASS_PATH_STRING, "");
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(mybatisLocations));
        } catch (IOException e) {
            logger.error("io exception", e);
        }
        /** 设置datasource */
        bean.setDataSource(dataSource());
        /** 设置typeAlias 包扫描路径 */
        bean.setTypeAliasesPackage(getMybatisTypeAliasesPackage());
        //bean.setPlugins(getSqlSessionFactoryPlugins());
        return bean;
    }

    private Interceptor[] getSqlSessionFactoryPlugins() {
        logger.info("getSqlSessionFactoryPlugins");
        List<Interceptor> lst = new ArrayList<>();
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        //TODO 配置的不全，需要再完善一下，目前是启用默认自动配置
        properties.setProperty("autoRuntimeDialect", "true");
        pageInterceptor.setProperties(properties);
        lst.add(pageInterceptor);
        Interceptor[] interceptors = new Interceptor[lst.size()];
        for (int i = 0; i < interceptors.length; i++)
            interceptors[i] = lst.get(i);
        return interceptors;
    }

    //设置包的别名
    private String getMybatisTypeAliasesPackage() {
        logger.info("getMybatisTypeAliasesPackage");
        List<String> packages = new ArrayList<>();

        String alias = appConfig.getProperty("app.mybatis-alias.package");
        //为空则设置一个不存在entities的目录即可
        if (ChainProjectUtils.isEmpty(alias))
            alias = "common";

        String[] as = alias.split(",");

        String mode = appConfig.getProperty("spring.profiles.active");
        for (String s : as) {
            if ("test".equals(s) && Constant.PROD_MODE.equals(mode))
                continue;
            packages.add(getFull(s));
        }

        return String.join(",", packages);
    }

    private String getFull(String s) {
        String basePackage = "com.chain.project.";
        String tailPackage = ".entities";
        return basePackage + s + tailPackage;
    }

    @Bean
    public CacheManager cacheManager() {
        logger.info("create cacheManager");
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource resource = pathMatchingResourcePatternResolver.getResource("classpath:ehcache.xml");
        ehCacheManagerFactoryBean.setConfigLocation(resource);
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean.getObject();
    }

    @Bean("transactionManager")
    public DataSourceTransactionManager transactionManager() throws SQLException {
        logger.info("create transactionManager");
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }
}