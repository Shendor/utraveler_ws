package com.utraveler.mapper.mysql;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.utraveler.mapper.SerializationService;

public class SerializationServiceImpl implements SerializationService {

    @Override
    public byte[] serialize(Object data) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(data);
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Object deSerialize(byte[] data) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(is)) {
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
