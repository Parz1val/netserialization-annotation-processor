package com.ocdsoft.bacta.engine.serialize;

import com.ocdsoft.bacta.annotation.TestClass3;
import java.lang.Class;
import java.lang.Override;
import java.nio.ByteBuffer;

public final class TestClass3Serializer implements NetSerializer<TestClass3> {
  @Override
  public final TestClass3 read(Class<TestClass3> clazz, ByteBuffer buffer) {
    int field1 = buffer.getInt();
    int field2 = buffer.getInt();
    int field3 = buffer.getInt();
    return new TestClass3(field1, field2, field3);
  }

  @Override
  public final void write(TestClass3 object, ByteBuffer buffer) {
    buffer.putInt(object.getField1());
    buffer.putInt(object.getField2());
    buffer.putInt(object.getField3());
  }
}
