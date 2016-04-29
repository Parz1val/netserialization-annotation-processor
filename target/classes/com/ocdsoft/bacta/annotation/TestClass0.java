package com.ocdsoft.bacta.annotation;
import com.ocdsoft.bacta.engine.serialize.NetSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@NetSerialize(serializer = "com.ocdsoft.bacta.annotation.TestClass0Serializer")
@AllArgsConstructor
@Getter
public class TestClass0 {
    private int field1;
}
