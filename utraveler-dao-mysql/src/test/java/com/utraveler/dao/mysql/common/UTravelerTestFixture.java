package com.utraveler.dao.mysql.common;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.utraveler.dao.common.Dao;
import com.utraveler.dao.entity.BaseEntity;
import com.utraveler.dao.entity.UserEntity;
import com.utraveler.dao.mysql.common.configuration.TestConfiguration;
import com.utraveler.dao.mysql.entity.UserEntityImpl;

@ContextConfiguration(classes = TestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@TestExecutionListeners(
        {CommonTestExecutionListener.class}
)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UTravelerTestFixture {

    public static final String USER_TABLE = "user";
    public static final String EVENT_TABLE = "event";
    public static final String MESSAGE_TABLE = "message";
    public static final String MONEY_SPENDING_TABLE = "money_spending";
    public static final String PHOTO_TABLE = "photo";
    public static final String ROUTE_TABLE = "route";
    public static final String RESET_PASSWORD_RECORD_TABLE = "reset_password_record";
    public static final String TRIP_PLAN_TABLE = "trip_plan";

    protected ApplicationContext context;


    static {
        PropertyConfigurator.configure(UTravelerTestFixture.class.getResource("/properties/log-test.properties"));
    }


    protected <T extends BaseEntity> T getById(Dao dao, T message) {
        SessionFactory sessionFactory = context.getBean(LocalSessionFactoryBean.class).getObject();
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
        return (T)dao.getById(message.getId());
    }


    protected UserEntity createUser(String userName) {
        UserEntity userEntity = new UserEntityImpl();
        userEntity.setPassword(userName);
        userEntity.setEmail("user@mail.com");
        userEntity.setRegisterDate(new DateTime(2014, 1, 1, 0, 0));

        return userEntity;
    }

}
