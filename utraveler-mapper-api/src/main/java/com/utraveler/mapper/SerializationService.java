package com.utraveler.mapper;

public interface SerializationService {

    byte[] serialize(Object data);


    Object deSerialize(byte[] data);
}
