package com.warmer.kgmaker.util;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.StartNode;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class TestUtil {

    private static final String KEY_SOURCE = "sourceid";
    private static final String KEY_TARGET = "targetid";

    public static <T> Map<String, Object> convert(List<T> resultList) {
        Map<String, Object> map = new HashMap<>();

        List<Map<String, Object>> nodeList = getNodes(resultList);
        List<Map<String, Object>> relationList = getRelationships(resultList);
        map.put("node", nodeList);
        map.put("relationship", relationList);
        return map;
    }

    public static <T> List<Map<String, Object>> getNodes(List<T> results) {

        Set<Map<String, Object>> set = new HashSet<>();
        for (T result : results) {
            set.addAll(getNodes(result));
        }

        List<Map<String, Object>> resultList = new ArrayList<>(set);
        return resultList;
    }

    private static <T> List<Map<String, Object>> getNodes(T result) {

        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> node = new HashMap<>();
        Field idField = getFieldByAnnotation(result, Id.class);
        Assert.notNull(idField, "当前节点没有id");
        idField.setAccessible(true);
        try {
            Object id = idField.get(result);
            node.put("uuid", id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Field[] fields = getFieldsNoAnnotation(result);
        for (Field field : fields) {

            field.setAccessible(true);
            String key = field.getName();
            try {
                Object value = field.get(result);
                node.put(key, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            resultList.add(node);

        }

        return resultList;
    }

    public static <T> List<Map<String, Object>> getRelationships(List<T> results) {

        Set<Map<String, Object>> set = new HashSet<>();
        for (T result : results) {
            set.addAll(getRelationships(result));
        }

        List<Map<String, Object>> resultList = new ArrayList<>(set);
        return resultList;
    }

    private static <T> Set<Map<String, Object>> getRelationships(T result) {
        Set<Map<String, Object>> set = new HashSet<>();
        Class<?> clazz = result.getClass();
        Field[] fields = clazz.getDeclaredFields();

        Map<String, Object> relation;
        // 找到带有Relationship注解的域(属性)
        Field[] relationFields = getFieldsByAnnotation(result, Relationship.class);

        for (Field field : relationFields) {
            field.setAccessible(true);
            try {
                // 此处假定是List类型 后续再完善
                List<Object> relationships = (List<Object>) field.get(result);

                if(relationships != null && relationships.size() != 0) {
                    for (Object relationship : relationships) {
                        // 遍历每一个关系属性, 找到关系属性的id、startNode和endNode

                        relation = new HashMap<>();

                        Field idNodeField = getFieldByAnnotation(relationship, Id.class);
                        Assert.notNull(idNodeField, "当前关系没有id");
                        idNodeField.setAccessible(true);
                        Object id = idNodeField.get(relationship);
                        relation.put("uuid", new Long(id.toString()));

                        Field startNodeField = getFieldByAnnotation(relationship, StartNode.class);
                        Assert.notNull(startNodeField, "当前关系没有开始节点");
                        startNodeField.setAccessible(true);
                        Object startNode = startNodeField.get(relationship);
                        Field idFieldOfStartNode = getFieldByAnnotation(startNode, Id.class);
                        idFieldOfStartNode.setAccessible(true);
                        Object idOfStart = idFieldOfStartNode.get(startNode);
                        relation.put(KEY_SOURCE, new Long(idOfStart.toString()));

                        Field endNodeField = getFieldByAnnotation(relationship, EndNode.class);
                        Assert.notNull(endNodeField, "当前关系没有结束节点");
                        endNodeField.setAccessible(true);
                        Object endNode = endNodeField.get(relationship);
                        Field idFieldOfEndNode = getFieldByAnnotation(endNode, Id.class);
                        idFieldOfEndNode.setAccessible(true);
                        Object idOfEnd = idFieldOfEndNode.get(endNode);
                        relation.put(KEY_TARGET, new Long(idOfEnd.toString()));

                        Field[] rfields = getFieldsNoAnnotation(relationship);
                        for (Field rfield : rfields) {
                            rfield.setAccessible(true);
                            Object value = rfield.get(relationship);
                            String key = rfield.getName();
                            relation.put(key, value);
                            set.add(relation);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
            break;
        }
        return set;
    }

    public static  Field[] getFieldsNoAnnotation(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        List<Field> list = new ArrayList<>();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            if(annotations == null || annotations.length == 0) {
                list.add(field);
            }
        }
        return list.toArray(new Field[list.size()]);
    }

    /**
     * 通过指定注解获取指定的属性
     * @param obj
     * @param annotationClazz
     * @param <T>
     * @return
     */
    public static <T extends Annotation> Field getFieldByAnnotation(Object obj, Class<T> annotationClazz) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            T annotation = field.getAnnotation(annotationClazz);
            if(annotation != null) {
                return field;
            }
        }
        return null;
    }

    /**
     * 通过指定注解获取指定的属性
     * @param obj
     * @param annotationClazz
     * @param <T>
     * @return
     */
    public static <T extends Annotation> Field[] getFieldsByAnnotation(Object obj, Class<T> annotationClazz) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        List<Field> list = new ArrayList<>();
        for (Field field : fields) {
            T annotation = field.getAnnotation(annotationClazz);
            if(annotation != null) {
                list.add(field);
            }
        }
        return list.toArray(new Field[list.size()]);
    }
}
