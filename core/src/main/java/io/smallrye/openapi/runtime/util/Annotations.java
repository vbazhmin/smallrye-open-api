package io.smallrye.openapi.runtime.util;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationTarget;
import org.jboss.jandex.AnnotationValue;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.MethodInfo;
import org.jboss.jandex.MethodParameterInfo;
import org.jboss.jandex.Type;

public final class Annotations {

    private static final String VALUE = "value";

    private Annotations() {
    }

    @SuppressWarnings("deprecation")
    static Collection<AnnotationInstance> declaredAnnotation(ClassInfo target) {
        return target.classAnnotations();
    }

    @SuppressWarnings("deprecation")
    static AnnotationInstance declaredAnnotation(ClassInfo target, DotName name) {
        return target.classAnnotation(name);
    }

    public static Collection<AnnotationInstance> getAnnotations(AnnotationTarget target) {
        if (target == null) {
            return Collections.emptyList();
        }

        switch (target.kind()) {
            case CLASS:
                return declaredAnnotation(target.asClass());
            case FIELD:
                return target.asField().annotations();
            case METHOD:
                return target.asMethod().annotations();
            case METHOD_PARAMETER:
                MethodParameterInfo parameter = target.asMethodParameter();
                return parameter
                        .method()
                        .annotations()
                        .stream()
                        .filter(a -> targetsMethodParameter(a, parameter.position()))
                        .collect(Collectors.toList());
            case TYPE:
            case RECORD_COMPONENT:
                break;
        }

        return Collections.emptyList();
    }

    public static AnnotationInstance getDeclaredAnnotation(AnnotationTarget target, DotName name) {
        if (target == null) {
            return null;
        }

        AnnotationInstance annotation;

        switch (target.kind()) {
            case CLASS:
                annotation = declaredAnnotation(target.asClass(), name);
                break;
            case FIELD:
                annotation = target.asField()
                        .annotations()
                        .stream()
                        .filter(a -> name.equals(a.name()))
                        .filter(a -> target.equals(a.target())).findFirst()
                        .orElse(null);
                break;
            case METHOD:
                annotation = target.asMethod()
                        .annotations(name)
                        .stream()
                        .filter(a -> target.equals(a.target())).findFirst()
                        .orElse(null);
                break;
            case METHOD_PARAMETER:
                MethodParameterInfo parameter = target.asMethodParameter();
                annotation = parameter
                        .method()
                        .annotations(name)
                        .stream()
                        .filter(a -> targetsMethodParameter(a, parameter.position()))
                        .findFirst()
                        .orElse(null);
                break;
            case RECORD_COMPONENT:
                annotation = target.asRecordComponent().annotation(name);
                break;
            case TYPE:
            default:
                annotation = null;
                break;
        }

        return annotation;
    }

    public static <T> T value(AnnotationInstance annotation) {
        return annotation != null ? value(annotation, VALUE) : null;
    }

    /**
     * Convenience method to retrieve the named parameter from an annotation.
     * The value will be unwrapped from its containing {@link AnnotationValue}.
     *
     * @param <T> the type of the parameter being retrieved
     * @param annotation the annotation from which to fetch the parameter
     * @param name the name of the parameter
     * @return an unwrapped annotation parameter value
     */
    @SuppressWarnings({ "unchecked", "squid:S3776" })
    public static <T> T value(AnnotationInstance annotation, String name) {
        final AnnotationValue value = annotation.value(name);

        if (value == null) {
            return null;
        }

        final boolean isArray = (AnnotationValue.Kind.ARRAY == value.kind());

        switch (isArray ? value.componentKind() : value.kind()) {
            case BOOLEAN:
                return (T) (isArray ? value.asBooleanArray() : value.asBoolean());
            case BYTE:
                return (T) (isArray ? value.asByteArray() : value.asByte());
            case CHARACTER:
                return (T) (isArray ? value.asCharArray() : value.asChar());
            case CLASS:
                return (T) (isArray ? value.asClassArray() : value.asClass());
            case DOUBLE:
                return (T) (isArray ? value.asDoubleArray() : value.asDouble());
            case ENUM:
                return (T) (isArray ? value.asEnumArray() : value.asEnum());
            case FLOAT:
                return (T) (isArray ? value.asFloatArray() : value.asFloat());
            case INTEGER:
                return (T) (isArray ? value.asIntArray() : value.asInt());
            case LONG:
                return (T) (isArray ? value.asLongArray() : value.asLong());
            case NESTED:
                return (T) (isArray ? value.asNestedArray() : value.asNested());
            case SHORT:
                return (T) (isArray ? value.asShortArray() : value.asShort());
            case STRING:
                return (T) (isArray ? value.asStringArray() : value.asString());
            case UNKNOWN:
            default:
                return null;
        }
    }

    /**
     * Convenience method to retrieve the named parameter from an annotation.
     * The value will be unwrapped from its containing {@link AnnotationValue}.
     *
     * @param <T> the type of the parameter being retrieved
     * @param annotation the annotation from which to fetch the parameter
     * @param name the name of the parameter
     * @param defaultValue a default value to return if the parameter is not defined
     * @return an unwrapped annotation parameter value
     */
    public static <T> T value(AnnotationInstance annotation, String name, T defaultValue) {
        T value = value(annotation, name);
        return value != null ? value : defaultValue;
    }

    /**
     * Reads a String property value from the given annotation instance. If no value is found
     * this will return null.
     *
     * @param annotation AnnotationInstance
     * @param propertyName String
     * @param clazz Class type of the Enum
     * @param <T> Type parameter
     * @return Value of property
     */
    public static <T extends Enum<T>> T enumValue(AnnotationInstance annotation, String propertyName, Class<T> clazz) {
        String value = annotation != null ? value(annotation, propertyName) : null;

        if (value == null) {
            return null;
        }

        return Stream.of(clazz.getEnumConstants())
                .filter(c -> c.name().equals(value))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns all annotations configured for a single parameter of a method.
     *
     * @param method MethodInfo
     * @param paramPosition parameter position
     * @return List of AnnotationInstance's
     */
    public static List<AnnotationInstance> getParameterAnnotations(MethodInfo method, short paramPosition) {
        return method.annotations()
                .stream()
                .filter(a -> targetsMethodParameter(a, paramPosition))
                .collect(toList());
    }

    /**
     * Many OAI annotations can either be found singly or as a wrapped array. This method will
     * look for both and return a list of all found. Both the single and wrapper annotation names
     * must be provided.
     *
     * @param target the annotated target (e.g. ClassInfo, MethodInfo)
     * @param singleAnnotationName DotName
     * @param repeatableAnnotationName DotName
     * @return List of AnnotationInstance's
     */
    public static List<AnnotationInstance> getRepeatableAnnotation(AnnotationTarget target,
            DotName singleAnnotationName,
            DotName repeatableAnnotationName) {

        List<AnnotationInstance> annotations = new ArrayList<>();

        AnnotationInstance annotation = getAnnotation(target, singleAnnotationName);

        if (annotation != null) {
            annotations.add(annotation);
        }

        if (repeatableAnnotationName != null) {
            AnnotationInstance[] nestedArray = getAnnotationValue(target,
                    repeatableAnnotationName,
                    VALUE);

            if (nestedArray != null) {
                Arrays.stream(nestedArray)
                        .map(a -> AnnotationInstance.create(a.name(), target, a.values()))
                        .forEach(annotations::add);
            }
        }

        return annotations;
    }

    /**
     * Finds an annotation (if present) with the given name, on a particular parameter of a
     * method.Returns null if not found.
     *
     * @param method the method
     * @param parameterIndex the parameter index
     * @param annotationName name of annotation we are looking for
     * @return the Annotation instance
     */
    public static AnnotationInstance getMethodParameterAnnotation(MethodInfo method, int parameterIndex,
            DotName annotationName) {
        return method.annotations(annotationName)
                .stream()
                .filter(a -> targetsMethodParameter(a, parameterIndex))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds an annotation (if present) with the given name, on a particular parameter of a
     * method based on the identity of the parameterType. Returns null if not found.
     *
     * @param method the method
     * @param parameterType the parameter type
     * @param annotationName name of annotation we are looking for
     * @return the Annotation instance
     */
    public static AnnotationInstance getMethodParameterAnnotation(MethodInfo method, Type parameterType,
            DotName annotationName) {
        // parameterType must be the same object as in the method's parameter type array
        int parameterIndex = method.parameterTypes().indexOf(parameterType);
        return getMethodParameterAnnotation(method, parameterIndex, annotationName);
    }

    /**
     * Finds all annotations on a particular parameter of a method based on the identity of the parameterType.
     *
     * @param method the method
     * @param parameterType the parameter type
     * @return the list of annotations, never null
     */
    public static List<AnnotationInstance> getMethodParameterAnnotations(MethodInfo method, Type parameterType) {
        // parameterType must be the same object as in the method's parameter type array
        int parameterIndex = method.parameterTypes().indexOf(parameterType);
        return method.annotations()
                .stream()
                .filter(a -> targetsMethodParameter(a, parameterIndex))
                .collect(Collectors.toList());
    }

    public static boolean hasAnnotation(AnnotationTarget target, Collection<DotName> annotationNames) {
        for (DotName dn : annotationNames) {
            if (hasAnnotation(target, dn)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAnnotation(AnnotationTarget target, DotName annotationName) {
        if (target == null) {
            return false;
        }
        switch (target.kind()) {
            case CLASS:
                return declaredAnnotation(target.asClass(), annotationName) != null;
            case FIELD:
                return target.asField().hasAnnotation(annotationName);
            case METHOD:
                return target.asMethod().hasAnnotation(annotationName);
            case METHOD_PARAMETER:
                MethodParameterInfo parameter = target.asMethodParameter();
                return parameter.method()
                        .annotations()
                        .stream()
                        .filter(a -> targetsMethodParameter(a, parameter.position()))
                        .anyMatch(a -> a.name().equals(annotationName));
            case TYPE:
            case RECORD_COMPONENT:
                break;
        }

        return false;
    }

    public static AnnotationInstance getAnnotation(AnnotationTarget annotationTarget, DotName annotationName) {
        return getAnnotations(annotationTarget)
                .stream()
                .filter(annotation -> annotation.name().equals(annotationName))
                .findFirst()
                .orElse(null);
    }

    public static AnnotationInstance getAnnotation(AnnotationTarget annotationTarget, Collection<DotName> annotationNames) {
        return getAnnotations(annotationTarget)
                .stream()
                .filter(annotation -> annotationNames.contains(annotation.name()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Convenience method to retrieve the "value" parameter from an annotation bound to the target.
     * The value will be unwrapped from its containing {@link AnnotationValue}.
     *
     * @param <T> the type of the parameter being retrieved
     * @param target the target object annotated with the annotation named by annotationName
     * @param annotationNames names of annotations from which to retrieve the value
     * @return an unwrapped annotation parameter value
     */
    public static <T> T getAnnotationValue(AnnotationTarget target, List<DotName> annotationNames) {
        return getAnnotationValue(target, annotationNames, VALUE, null);
    }

    /**
     * Convenience method to retrieve the named parameter from an annotation bound to the target.
     * The value will be unwrapped from its containing {@link AnnotationValue}.
     *
     * @param <T> the type of the parameter being retrieved
     * @param target the target object annotated with the annotation named by annotationName
     * @param annotationName name of the annotation from which to retrieve the value
     * @param propertyName the name of the parameter/property in the annotation
     * @return an unwrapped annotation parameter value
     */
    public static <T> T getAnnotationValue(AnnotationTarget target, DotName annotationName, String propertyName) {
        return getAnnotationValue(target, Arrays.asList(annotationName), propertyName);
    }

    public static <T> T getAnnotationValue(AnnotationTarget target, List<DotName> annotationNames, String propertyName) {
        return getAnnotationValue(target, annotationNames, propertyName, null);
    }

    /**
     * Convenience method to retrieve the named parameter from an annotation bound to the target.
     * The value will be unwrapped from its containing {@link AnnotationValue}.
     *
     * @param <T> the type of the parameter being retrieved
     * @param target the target object annotated with the annotation named by annotationName
     * @param annotationNames names of the annotations from which to retrieve the value
     * @param propertyName the name of the parameter/property in the annotation
     * @param defaultValue a default value to return if either the annotation or the value are missing
     * @return an unwrapped annotation parameter value
     */
    public static <T> T getAnnotationValue(AnnotationTarget target,
            List<DotName> annotationNames,
            String propertyName,
            T defaultValue) {

        AnnotationInstance annotation = getAnnotation(target, annotationNames);
        T value = null;

        if (annotation != null) {
            value = value(annotation, propertyName);
        }

        return value != null ? value : defaultValue;
    }

    public static <T> T getDeclaredAnnotationValue(AnnotationTarget type, DotName annotationName, String propertyName) {
        AnnotationInstance annotation = getDeclaredAnnotation(type, annotationName);
        T value = null;

        if (annotation != null) {
            value = value(annotation, propertyName);
        }

        return value;
    }

    public static <T> T getDeclaredAnnotationValue(AnnotationTarget type, DotName annotationName) {
        return getDeclaredAnnotationValue(type, annotationName, VALUE);
    }

    static boolean targetsMethodParameter(AnnotationInstance annotation, int position) {
        AnnotationTarget target = annotation.target();

        return target.kind() == AnnotationTarget.Kind.METHOD_PARAMETER
                && target.asMethodParameter().position() == position;
    }
}
