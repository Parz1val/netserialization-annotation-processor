package com.ocdsoft.bacta.annotation;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;
import com.ocdsoft.bacta.engine.lang.UnicodeString;
import com.ocdsoft.bacta.engine.service.serialize.NetSerializerService;
import com.ocdsoft.bacta.engine.utils.BufferUtil;
import org.junit.Test;

import javax.tools.JavaFileObject;

import java.nio.ByteBuffer;

import static com.google.common.truth.Truth.assert_;
import static org.junit.Assert.*;

/**
 * Created by kyle on 4/29/2016.
 */
public class SerializationClassGeneratorTest {

    @Test
    public void TestFiles() {

        for(int i = 0; i < 5; ++i) {

            SerializationProcessor annotationProcessor = new SerializationProcessor();


            String fileName = "TestClass" + i + ".java";

            JavaFileObject fileObject = JavaFileObjects.forResource(
                    SerializationClassGeneratorTest.class.getResource("/com/ocdsoft/bacta/annotation/" + fileName)
            );
            assert_().about(JavaSourceSubjectFactory.javaSource())
                    .that(fileObject)
                    .processedWith(annotationProcessor)
                    .compilesWithoutError();
        }

    }

    @Test
    public void TestLoading() {

//        String value = "Hello World!";
//        ByteBuffer buffer = ByteBuffer.allocate(value.length() * 2 + 4);
//        BufferUtil.putUnicode(buffer, value);
//
//        NetSerializerService serializerService = new NetSerializerService();
//        UnicodeString deserialized = serializerService.read(UnicodeString.class, buffer);
//
//        assertTrue(deserialized.equals(value));
    }

}