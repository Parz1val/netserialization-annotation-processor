package com.ocdsoft.bacta.annotation;
import com.ocdsoft.bacta.engine.serialize.NetSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@NetSerialize
@AllArgsConstructor
@Getter
public class TestClass4 {
    private int field1;
    private int field2;
    private int field3;
}
