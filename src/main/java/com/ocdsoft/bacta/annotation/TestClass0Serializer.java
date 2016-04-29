package com.ocdsoft.bacta.annotation;

import com.ocdsoft.bacta.engine.serialize.NetSerializer;

import java.nio.ByteBuffer;

/**
 * Created by kyle on 4/29/2016.
 */
public class TestClass0Serializer implements NetSerializer<TestClass4> {

    @Override
    public TestClass4 read(Class<TestClass4> clazz, ByteBuffer buffer) {
        return null;
    }

    @Override
    public void write(TestClass4 object, ByteBuffer buffer) {

    }
}
