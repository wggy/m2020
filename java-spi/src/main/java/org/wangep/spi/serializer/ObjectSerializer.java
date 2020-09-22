package org.wangep.spi.serializer;

import org.wangep.spi.exception.ObjectSerializerException;

/***
 * created by wange on 2020/4/16 10:37
 */
public interface ObjectSerializer {
    byte[] serialize(Object obj) throws ObjectSerializerException;

    <T> T deSerialize(byte[] param, Class<T> clazz) throws ObjectSerializerException;

    String getSchemeName();

}
