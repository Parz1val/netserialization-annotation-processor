package com.ocdsoft.bacta.engine.serialize;

import com.ocdsoft.bacta.annotation.TestClass4;
import java.lang.Class;
import java.lang.Override;
import java.nio.ByteBuffer;

public final class TestClass4Serializer implements NetSerializer<TestClass4> {
  @Override
  public final TestClass4 read(Class<TestClass4> clazz, ByteBuffer buffer) {
    int field1 = buffer.getInt();
    int field2 = buffer.getInt();
    int field3 = buffer.getInt();
    return new TestClass4(field1, field2, field3);
  }

  @Override
  public final void write(TestClass4 object, ByteBuffer buffer) {
    buffer.putInt(object.getField1());
    buffer.putInt(object.getField2());
    buffer.putInt(object.getField3());
  }
}
