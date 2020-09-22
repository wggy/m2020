package org.wangep.spi.exception;

/***
 * created by wange on 2020/4/16 10:41
 */
public class ObjectSerializerException extends RuntimeException {
    public ObjectSerializerException() {
    }

    public ObjectSerializerException(String message) {
        super(message);
    }

    public ObjectSerializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectSerializerException(Throwable cause) {
        super(cause);
    }
}
