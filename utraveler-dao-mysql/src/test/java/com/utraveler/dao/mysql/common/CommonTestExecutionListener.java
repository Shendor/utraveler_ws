package com.utraveler.dao.mysql.common;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import com.utraveler.dao.mysql.common.annotations.ClearTable;


public class CommonTestExecutionListener extends AbstractTestExecutionListener {

    private static final Logger LOGGER = Logger.getLogger(CommonTestExecutionListener.class);


    @Override
    public void beforeTestMethod(TestContext testContext)
            throws Exception {
        LOGGER.debug("Initialize application context");
        UTravelerTestFixture testInstance = (UTravelerTestFixture)testContext.getTestInstance();

        testInstance.context = testContext.getApplicationContext();
        getCurrentSession(testInstance).getTransaction().begin();
    }


    @Override
    public void afterTestMethod(TestContext testContext)
            throws Exception {
        super.afterTestMethod(testContext);
        UTravelerTestFixture testInstance = (UTravelerTestFixture)testContext.getTestInstance();

        getCurrentSession(testInstance).getTransaction().commit();

        ClearTable clearTableAnnotation = testInstance.getClass().getAnnotation(ClearTable.class);
        if (clearTableAnnotation == null) {
            Method method = testContext.getTestMethod();
            clearTableAnnotation = method.getAnnotation(ClearTable.class);
        }

        if (clearTableAnnotation != null) {
            LOGGER.debug("Start to remove table data");
            clearDataForTables(testInstance.context.getBean(DataSource.class), clearTableAnnotation.tables());
            LOGGER.debug("All table data were successfully deleted");
        }
    }


    private void clearDataForTables(DataSource dataSource, String[] tables)
            throws Exception {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            try {
                Statement stmt = connection.createStatement();
                try {
                    for (String table : tables) {
                        stmt.execute("DELETE FROM " + table);
                        LOGGER.debug("Delete table data for " + table);
                    }
                    connection.commit();
                } finally {
                    stmt.close();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new Exception(e);
            }
        } catch (SQLException e) {
            throw new Exception(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private Session getCurrentSession(UTravelerTestFixture testInstance) {
        SessionFactory sessionFactory = testInstance.context.getBean(LocalSessionFactoryBean.class).getObject();
        return sessionFactory.getCurrentSession();
    }
}