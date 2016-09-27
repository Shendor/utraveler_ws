package com.utraveler.dao.impl;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.Maps;
import com.utraveler.dao.HiLoKeyGenerator;
import com.utraveler.dao.entity.impl.HiLoSequenceImpl;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public final class HiLoKeyGeneratorImpl implements HiLoKeyGenerator<Long> {

    private static final long LOW_KEY_SIZE = 1000000L;
    private static final String TABLE_NAME_FIELD = "tableName";

    private Map<String, AtomicLong> lowKeys;
    private Map<String, AtomicBoolean> isExpiredKeysOfTables;
    private MongoOperations mongoOperation;
    private long highKey;


    public HiLoKeyGeneratorImpl(MongoOperations mongoOperation) {
        this.mongoOperation = mongoOperation;
        lowKeys = Maps.newHashMap();
        isExpiredKeysOfTables = Maps.newHashMap();
    }


    @Override
    public synchronized long getNextKey(String tableName) {
        AtomicBoolean isExpired = isExpiredKeysOfTables.get(tableName);
        if (isExpired == null || !isExpired.get()) {
            init(tableName);
        }
        AtomicLong keyIncrementer = lowKeys.get(tableName);
        isExpiredKeysOfTables.get(tableName).set(isKeyExpired(keyIncrementer));
        return keyIncrementer.incrementAndGet();
    }


    private void init(String tableName) {
        highKey = getHighKeyForTable(tableName);
        lowKeys.put(tableName, new AtomicLong(highKey * LOW_KEY_SIZE));
        isExpiredKeysOfTables.put(tableName, new AtomicBoolean(true));
    }


    private long getHighKeyForTable(String tableName) {
        Query query = new Query(Criteria.where(TABLE_NAME_FIELD).is(tableName));

        if (mongoOperation.findOne(query, HiLoSequenceImpl.class) == null) {
            mongoOperation.insert(new HiLoSequenceImpl(tableName));
        }

        Update update = new Update();
        update.inc("id", 1);

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        HiLoSequenceImpl sequenceId = mongoOperation.findAndModify(query, update, options, HiLoSequenceImpl.class);

        return sequenceId.getId();
    }


    private boolean isKeyExpired(AtomicLong keyIncrementer) {
        return keyIncrementer.get() <= ((highKey + 1) * LOW_KEY_SIZE) - 10;
    }
}
