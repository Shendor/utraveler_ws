package com.utraveler.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import com.utraveler.dao.entity.impl.HiLoSequenceImpl;
import static org.mockito.Mockito.mock;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class HiLoKeyGeneratorImplTest {

    private static final String TABLE_NAME_1 = "table1";
    private static final String TABLE_NAME_2 = "table2";
    private static final String TABLE_NAME_3 = "table3";
    private static final int THREAD_COUNT = 10;
    private static final int GENERATION_COUNT = 10000;

    private HiLoKeyGeneratorImpl keyGenerator;
    private Map<String, HiLoSequenceImpl> sequenceIdMap;
    private Map<String, AtomicInteger> runnedTables = new HashMap<String, AtomicInteger>();
    private Map<String, Set<Long>> uniqueKeys = new HashMap<String, Set<Long>>();
    private List<String> tableNames = new ArrayList<String>();


    public HiLoKeyGeneratorImplTest() {
        tableNames.add(TABLE_NAME_1);
        tableNames.add(TABLE_NAME_2);

        sequenceIdMap = new HashMap<String, HiLoSequenceImpl>();
        sequenceIdMap.put(TABLE_NAME_1, new HiLoSequenceImpl(TABLE_NAME_1));
        sequenceIdMap.put(TABLE_NAME_2, new HiLoSequenceImpl(TABLE_NAME_2));
        sequenceIdMap.put(TABLE_NAME_3, new HiLoSequenceImpl(TABLE_NAME_3));

        MongoOperations mockMongoOperations = new MongoTemplate(mock(MongoDbFactory.class), mock(MongoConverter.class)) {

            @Override
            public <T> T findOne(Query query, Class<T> entityClass) {
                Object tableName = query.getQueryObject().get("tableName");
                return (T)sequenceIdMap.get(tableName.toString());
            }


            @Override
            public <T> T findAndModify(Query query, Update update, FindAndModifyOptions options, Class<T> entityClass) {
                Object tableName = query.getQueryObject().get("tableName");
                HiLoSequenceImpl sequenceId = sequenceIdMap.get(tableName.toString());
                sequenceId.setId(sequenceId.getId() + 1);
                return (T)sequenceId;
            }
        };
        keyGenerator = new HiLoKeyGeneratorImpl(mockMongoOperations);
    }


    @AfterTest
    public void afterTest() {
        if (runnedTables.isEmpty()) {
            assertEquals(uniqueKeys.get(TABLE_NAME_3).size(), THREAD_COUNT * GENERATION_COUNT);
        } else {
            int table1KeysSize = uniqueKeys.get(TABLE_NAME_1).size();
            int table2KeysSize = uniqueKeys.get(TABLE_NAME_2).size();
            assertEquals(table1KeysSize, GENERATION_COUNT * runnedTables.get(TABLE_NAME_1).get());
            assertEquals(table2KeysSize, GENERATION_COUNT * runnedTables.get(TABLE_NAME_2).get());
            assertEquals(table1KeysSize + table2KeysSize, THREAD_COUNT * GENERATION_COUNT);
        }

    }


    @Test(invocationCount = THREAD_COUNT, threadPoolSize = 12)
    public void testGetNextKey_ManyThreadsCallMethodForSingleTable_ReturnCorrectKeys() {
        List<Long> keys = new ArrayList<Long>();
        for (int i = 0; i < GENERATION_COUNT; i++) {
            keys.add(keyGenerator.getNextKey(TABLE_NAME_3));
        }
        addKeys(TABLE_NAME_3, keys);
    }


    @Test(invocationCount = THREAD_COUNT, threadPoolSize = 12)
    public void testGetNextKey_ManyThreadsCallMethodForTwoTables_ReturnCorrectKeys() {
        Random random = new Random();
        String tableName = tableNames.get(random.nextInt(2));
        List<Long> keys = new ArrayList<Long>();
        for (int i = 0; i < GENERATION_COUNT; i++) {
            keys.add(keyGenerator.getNextKey(tableName));
        }
        if (!runnedTables.containsKey(tableName)) {
            runnedTables.put(tableName, new AtomicInteger());
        }
        runnedTables.get(tableName).incrementAndGet();
        addKeys(tableName, keys);
    }


    private synchronized void addKeys(String tableName, List<Long> keys) {
        if (!uniqueKeys.containsKey(tableName)) {
            uniqueKeys.put(tableName, new TreeSet<Long>());
        }
        uniqueKeys.get(tableName).addAll(keys);
    }
}
