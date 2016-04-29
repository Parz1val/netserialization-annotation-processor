package com.ocdsoft.bacta.annotation;

import com.ocdsoft.bacta.engine.serialize.NetSerialize;
import com.ocdsoft.bacta.engine.serialize.Unicode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by kyle on 4/29/2016.
 */
@NetSerialize
@AllArgsConstructor
@Getter
public class TestObject {

    private final String ascii;
    @Unicode
    private final String unicode;
    private final int primitiveInt;
    private final Integer boxedInt;
}
