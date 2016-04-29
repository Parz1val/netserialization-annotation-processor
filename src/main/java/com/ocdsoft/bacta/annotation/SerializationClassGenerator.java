package com.ocdsoft.bacta.annotation;

import com.ocdsoft.bacta.engine.serialize.NetSerialize;
import com.ocdsoft.bacta.engine.serialize.NetSerializer;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kyle on 4/25/2016.
 */
public class SerializationClassGenerator {

    private final static String BUFFER_NAME = "buffer";
    private final static String VALUE_HOLDER = "%value%";

    private final Map<Type, String> readMethods;
    private final Map<Type, String> writeMethods;

    private final String fs;

    public SerializationClassGenerator() {
        readMethods = new HashMap<>();
        writeMethods = new HashMap<>();
        fs = System.getProperty("file.separator");
        loadMethods();
    }

    private void loadMethods() {
        readMethods.put(Integer.TYPE, BUFFER_NAME + ".getInt()");
        writeMethods.put(Integer.TYPE, BUFFER_NAME + ".putInt(" + VALUE_HOLDER + ")");
        readMethods.put(Integer.class, BUFFER_NAME + ".getInt()");
        writeMethods.put(Integer.class, BUFFER_NAME + ".putInt(" + VALUE_HOLDER + ")");

        readMethods.put(Float.TYPE, BUFFER_NAME + ".getFloat()");
        writeMethods.put(Float.TYPE, BUFFER_NAME + ".putFloat(" + VALUE_HOLDER + ")");
        readMethods.put(Float.class, BUFFER_NAME + ".getFloat()");
        writeMethods.put(Float.class, BUFFER_NAME + ".putFloat(" + VALUE_HOLDER + ")");

        readMethods.put(Long.TYPE, BUFFER_NAME + ".getLong()");
        writeMethods.put(Long.TYPE, BUFFER_NAME + ".putLong(" + VALUE_HOLDER + ")");
        readMethods.put(Long.class, BUFFER_NAME + ".getLong()");
        writeMethods.put(Long.class, BUFFER_NAME + ".putLong(" + VALUE_HOLDER + ")");

        readMethods.put(Double.TYPE, BUFFER_NAME + ".getDouble()");
        writeMethods.put(Double.TYPE, BUFFER_NAME + ".putDouble(" + VALUE_HOLDER + ")");
        readMethods.put(Double.class, BUFFER_NAME + ".getDouble()");
        writeMethods.put(Double.class, BUFFER_NAME + ".putDouble(" + VALUE_HOLDER + ")");

        readMethods.put(String.class, "BufferUtil.getAscii(" + BUFFER_NAME + ")");
        writeMethods.put(String.class, "BufferUtil.putAscii(" + BUFFER_NAME + ", " + VALUE_HOLDER + ")");
    }

    public void generateClass(String fqClassName, List<VariableElement> variableElements) throws IOException, ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {

        Class<?> myClass = Class.forName(fqClassName);

        MethodSpec.Builder writeBuilder = MethodSpec.methodBuilder("write")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Override.class)
                .returns(void.class)
                .addParameter(myClass, "object")
                .addParameter(ByteBuffer.class, BUFFER_NAME);
        addWrites(myClass, writeBuilder, variableElements);

        MethodSpec write = writeBuilder.build();

        MethodSpec.Builder readBuilder = MethodSpec.methodBuilder("read")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Override.class)
                .returns(myClass)
                .addParameter(ParameterizedTypeName.get(Class.class, myClass), "clazz")
                .addParameter(ByteBuffer.class, BUFFER_NAME);

        addReads(myClass, readBuilder, variableElements);
        MethodSpec read = readBuilder.build();

        TypeSpec classSpec = TypeSpec.classBuilder(myClass.getSimpleName() + "Serializer")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(ParameterizedTypeName.get(NetSerializer.class, myClass))
                .addMethod(read)
                .addMethod(write)
                .build();

        JavaFile javaFile = JavaFile.builder(NetSerialize.class.getPackage().getName(), classSpec)
                .build();


        javaFile.writeTo(Paths.get("target" + fs + "generated-sources" + fs));
    }

    private void addReads(Class<?> myClass, MethodSpec.Builder readBuilder, List<VariableElement> variableElements) throws NoSuchFieldException {

        String paramString = "";

        for (VariableElement element : variableElements) {

            Field field = myClass.getDeclaredField(element.getSimpleName().toString());

            String methodCall = readMethods.get(field.getType());
            if (methodCall != null) {
                readBuilder.addStatement(element.asType() + " " + element.getSimpleName().toString() + " = " + methodCall);
            } else {
                readBuilder.addStatement(element.asType() + " " + element.getSimpleName().toString() + " = new " + methodCall);
            }

            paramString += element.getSimpleName().toString() + ", ";
        }

        paramString = paramString.substring(0, paramString.length() - 2);

        readBuilder.addStatement("return new " + myClass.getSimpleName() + "(" + paramString + ")");
    }

    private void addWrites(Class<?> myClass, MethodSpec.Builder readBuilder, List<VariableElement> variableElements) throws NoSuchFieldException, NoSuchMethodException {

        for (VariableElement element : variableElements) {

            String name = element.getSimpleName().toString();

            final Field field = myClass.getDeclaredField(name);

            String methodName = "get" + Character.toString(name.charAt(0)).toUpperCase() + name.substring(1);
            myClass.getDeclaredMethod(methodName);

            readBuilder.addStatement(writeMethods.get(field.getType()).replace(VALUE_HOLDER, "object." + methodName + "()"));

        }
    }

}
