package org.wangep.spi.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.wangep.spi.exception.ObjectSerializerException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/***
 * created by wange on 2020/4/16 10:46
 */
public class KryoSerializer implements ObjectSerializer {

    public KryoSerializer() {
        System.out.println(2222222);
    }
    @Override
    public byte[] serialize(Object obj) throws ObjectSerializerException {
        byte[] bytes;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            Kryo kryo = new Kryo();
            Output output = new Output(outputStream);
            kryo.writeObject(output, obj);
            bytes = output.toBytes();
            output.flush();
        } catch (Exception e) {
            throw new ObjectSerializerException("kryo serialize error" + e.getMessage());
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public <T> T deSerialize(byte[] param, Class<T> clazz) throws ObjectSerializerException {
        T object;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(param)) {
            Kryo kryo = new Kryo();
            Input input = new Input(inputStream);
            object = kryo.readObject(input, clazz);
            input.close();
        } catch (Exception e) {
            throw new ObjectSerializerException("kryo deSerialize error" + e.getMessage());
        }
        return object;
    }

    @Override
    public String getSchemeName() {
        return "kryoSerializer";
    }
}
