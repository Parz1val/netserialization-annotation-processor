package com.ocdsoft.bacta.annotation;
import com.ocdsoft.bacta.engine.serialize.NetSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@NetSerialize
@AllArgsConstructor
@Getter
public class TestClass1 {
    private int field1;
    private float field2;
    private long field3;
    private Long field4;
}
