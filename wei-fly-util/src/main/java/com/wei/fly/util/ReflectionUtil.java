package com.wei.fly.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 反射工具类
 */
public final class ReflectionUtil {
    
    private ReflectionUtil() {
        
    }

    private static final Map<Class<?>, Map<Class<?>, List<Field>>> ANNOTATION_FILED_MAP = new ConcurrentHashMap<>();

    /**
     * 获取指定注解的Field
     *
     * @param classType       类
     * @param annotationClass 注解
     */
    public static List<Field> getFieldsWithAnnotation(Class<?> classType, Class<? extends Annotation> annotationClass) {
        Map<Class<?>, List<Field>> classFieldMap = ANNOTATION_FILED_MAP.get(classType);
        if (classFieldMap == null) {
            classFieldMap = new ConcurrentHashMap<>();
            Map<Class<?>, List<Field>> originMap = ANNOTATION_FILED_MAP.putIfAbsent(classType, classFieldMap);
            if (originMap != null) {
                classFieldMap = originMap;
            }
        }

        List<Field> annotationFieldList = classFieldMap.get(annotationClass);
        if (annotationFieldList == null) {
            annotationFieldList = generateFieldsWithAnnotation(classType, annotationClass);
            List<Field> originList = classFieldMap.putIfAbsent(annotationClass, annotationFieldList);
            if (originList != null) {
                annotationFieldList = originList;
            }
        }
        return annotationFieldList;
    }

    private static List<Field> generateFieldsWithAnnotation(Class<?> classType, Class<? extends Annotation> annotationClass) {
        List<Field> fields = getAllFields(classType);
        return fields.stream().filter(f -> !Modifier.isStatic(f.getModifiers()) &&
                f.isAnnotationPresent(annotationClass))
                .peek(f -> f.setAccessible(true))
                .collect(Collectors.toList());
    }

    private static List<Field> getAllFields(Class<?> classType) {
        List<Field> fields = new ArrayList<>();
        Class<?> current = classType;
        while (current != Object.class) {
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }
        return fields;
    }
}