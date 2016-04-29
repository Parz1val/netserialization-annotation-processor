package com.ocdsoft.bacta.engine.serialize;

import com.ocdsoft.bacta.annotation.TestClass1;
import java.lang.Class;
import java.lang.Override;
import java.nio.ByteBuffer;

public final class TestClass1Serializer implements NetSerializer<TestClass1> {
  @Override
  public final TestClass1 read(Class<TestClass1> clazz, ByteBuffer buffer) {
    int field1 = buffer.getInt();
    float field2 = buffer.getFloat();
    long field3 = buffer.getLong();
    java.lang.Long field4 = buffer.getLong();
    return new TestClass1(field1, field2, field3, field4);
  }

  @Override
  public final void write(TestClass1 object, ByteBuffer buffer) {
    buffer.putInt(object.getField1());
    buffer.putFloat(object.getField2());
    buffer.putLong(object.getField3());
    buffer.putLong(object.getField4());
  }
}
