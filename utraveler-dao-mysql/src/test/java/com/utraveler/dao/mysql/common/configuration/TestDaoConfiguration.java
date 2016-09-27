package com.utraveler.dao.mysql.common.configuration;

import com.utraveler.dao.ResetPasswordRecordDao;
import com.utraveler.dao.TripPlanDao;
import com.utraveler.dao.mysql.ResetPasswordRecordDaoImpl;
import com.utraveler.dao.mysql.TripPlanDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.utraveler.dao.EventDao;
import com.utraveler.dao.MessageDao;
import com.utraveler.dao.MoneySpendingDao;
import com.utraveler.dao.PhotoDao;
import com.utraveler.dao.RouteDao;
import com.utraveler.dao.UserDao;
import com.utraveler.dao.mysql.EventDaoImpl;
import com.utraveler.dao.mysql.MessageDaoImpl;
import com.utraveler.dao.mysql.MoneySpendingDaoImpl;
import com.utraveler.dao.mysql.PhotoDaoImpl;
import com.utraveler.dao.mysql.RouteDaoImpl;
import com.utraveler.dao.mysql.UserDaoImpl;
import com.utraveler.dao.mysql.common.TestPersistenceDao;

@Configuration
public class TestDaoConfiguration {

    @Bean
    UserDao userDao() {
        return new UserDaoImpl();
    }


    @Bean
    EventDao eventDao() {
        return new EventDaoImpl();
    }


    @Bean
    MessageDao messageDao() {
        return new MessageDaoImpl();
    }


    @Bean
    MoneySpendingDao moneySpendingDao() {
        return new MoneySpendingDaoImpl();
    }


    @Bean
    PhotoDao photoDao() {
        return new PhotoDaoImpl();
    }


    @Bean
    RouteDao routeDao() {
        return new RouteDaoImpl();
    }


    @Bean
    ResetPasswordRecordDao resetPasswordRecordDao() {
        return new ResetPasswordRecordDaoImpl();
    }


    @Bean
    TripPlanDao tripPlanDao() {
        return new TripPlanDaoImpl();
    }


    @Bean
    TestPersistenceDao testPersistenceDao() {
        TestPersistenceDao testPersistenceDao = new TestPersistenceDao();
        testPersistenceDao.setUserDao(userDao());
        testPersistenceDao.setEventDao(eventDao());
        return testPersistenceDao;
    }
}
