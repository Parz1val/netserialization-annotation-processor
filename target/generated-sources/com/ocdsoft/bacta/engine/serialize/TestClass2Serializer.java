package com.ocdsoft.bacta.engine.serialize;

import com.ocdsoft.bacta.annotation.TestClass2;
import java.lang.Class;
import java.lang.Override;
import java.nio.ByteBuffer;

public final class TestClass2Serializer implements NetSerializer<TestClass2> {
  @Override
  public final TestClass2 read(Class<TestClass2> clazz, ByteBuffer buffer) {
    int field1 = buffer.getInt();
    int field2 = buffer.getInt();
    int field3 = buffer.getInt();
    return new TestClass2(field1, field2, field3);
  }

  @Override
  public final void write(TestClass2 object, ByteBuffer buffer) {
    buffer.putInt(object.getField1());
    buffer.putInt(object.getField2());
    buffer.putInt(object.getField3());
  }
}
