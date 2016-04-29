package com.ocdsoft.bacta.annotation;

import com.google.auto.service.AutoService;
import com.ocdsoft.bacta.engine.serialize.NetSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by kyle on 4/25/2016.
 */

@AutoService(Processor.class)
public class SerializationProcessor extends AbstractProcessor {

    private final Logger LOGGER = LoggerFactory.getLogger(SerializationProcessor.class);

    private final Set<String> supportedAnnotations;
    private final SerializationClassGenerator classGenerator;

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    public SerializationProcessor() {
        supportedAnnotations = new HashSet<>();
        supportedAnnotations.add(NetSerialize.class.getCanonicalName());
        classGenerator = new SerializationClassGenerator();
    }


    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // Itearate over all @Factory annotated elements
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(NetSerialize.class)) {

            // Check if a class has been annotated with @SerializationOrder
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                messager.printMessage(
                        Diagnostic.Kind.WARNING,
                        "The SerializationOrder interface is only applicable to classes",
                        annotatedElement);
                continue;
            }

            TypeElement typeElement = (TypeElement) annotatedElement;
            NetSerialize netSerializeAnnotation  = typeElement.getAnnotation(NetSerialize.class);

            if(!netSerializeAnnotation.serializer().isEmpty()) {
                LOGGER.info("Class: {} has a serializer already defined: {}",
                        typeElement.getQualifiedName().toString(),
                        netSerializeAnnotation.serializer()
                );
                continue;
            }

            try {

                List<VariableElement> variableElements =  typeElement
                        .getEnclosedElements()
                        .stream()
                        .filter(element -> element.getKind() == ElementKind.FIELD)
                        .map(element -> (VariableElement) element)
                        .collect(Collectors.toList());

                classGenerator.generateClass(typeElement.getQualifiedName().toString(), variableElements);

            } catch (Exception e) {
                messager.printMessage(
                        Diagnostic.Kind.ERROR,
                        "Unable to write serializer: " + typeElement.getSimpleName() + " " + e.getMessage(),
                        typeElement);

                LOGGER.error("Unable to write serializer", e);
                return false;
            }
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() { return supportedAnnotations; }

    @Override
    public SourceVersion getSupportedSourceVersion() { return SourceVersion.latestSupported(); }

}
