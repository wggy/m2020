package org.wangep.spi.serializer;

import org.wangep.spi.exception.ObjectSerializerException;

import java.io.*;

/***
 * created by wange on 2020/4/16 10:42
 */
public class JavaSerializer implements ObjectSerializer {

    public JavaSerializer() {
        System.out.println(1111111);
    }

    @Override
    public byte[] serialize(Object obj) throws ObjectSerializerException {
        ByteArrayOutputStream arrayOutputStream;
        try {
            arrayOutputStream = new ByteArrayOutputStream();
            ObjectOutput objectOutput = new ObjectOutputStream(arrayOutputStream);
            objectOutput.writeObject(obj);
            objectOutput.flush();
            objectOutput.close();
        } catch (IOException e) {
            throw new ObjectSerializerException("JAVA serialize error " + e.getMessage());
        }
        return arrayOutputStream.toByteArray();
    }

    @Override
    public <T> T deSerialize(byte[] param, Class<T> clazz) throws ObjectSerializerException {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(param);
        try {
            ObjectInput objectInput = new ObjectInputStream(arrayInputStream);
            return (T) objectInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ObjectSerializerException("JAVA deSerialize error " + e.getMessage());
        }
    }

    @Override
    public String getSchemeName() {
        return "javaSerializer";
    }
}
