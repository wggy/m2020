package org.wangep.spi.service;

import org.springframework.stereotype.Service;
import org.wangep.spi.serializer.JavaSerializer;
import org.wangep.spi.serializer.ObjectSerializer;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/***
 * created by wange on 2020/4/16 10:54
 */
@Service
public class SerializerService {

    public ObjectSerializer getObjectSerializer() {
        ServiceLoader<ObjectSerializer> serializers = ServiceLoader.load(ObjectSerializer.class);
        final Optional<ObjectSerializer> serializer = StreamSupport.stream(serializers.spliterator(), false).findFirst();
        return serializer.orElse(new JavaSerializer());
    }
}
