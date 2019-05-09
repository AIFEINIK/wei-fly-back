package com.wei.fly.util;

import com.wei.fly.util.exception.BeanUtilsException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Feinik
 * @Discription 实体属性转换工具
 * @Data 2019/3/23
 * @Version 1.0.0
 */
public class BeanUtils {

    /**
     * <pre>
     *     List<UserBean> userBeans = userDao.queryUsers();
     *     List<UserDTO> userDTOs = BeanUtil.batchTransform(UserDTO.class, userBeans);
     * </pre>
     */
    public static <T> List<T> batchTransform(final Class<T> clazz, List<? extends Object> srcList) {
        if (CollectionUtils.isEmpty(srcList)) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>(srcList.size());
        for (Object srcObject : srcList) {
            result.add(transform(clazz, srcObject));
        }
        return result;
    }

    /**
     * 对象属性值拷贝，支持将Date类型的字段值格式化为String类型
     * @param clazz
     * @param srcList
     * @param <T>
     * @return
     */
    public static <T> List<T> batchTransform(final Class<T> clazz, List<? extends Object> srcList, boolean isTransformDateTime) {
        if (CollectionUtils.isEmpty(srcList)) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>(srcList.size());
        for (Object srcObject : srcList) {
            result.add(transform(clazz, srcObject, isTransformDateTime));
        }
        return result;
    }

    /**
     * 封装{@link org.springframework.beans.BeanUtils#copyProperties}，惯用与直接将转换结果返回
     *
     * <pre>
     *      UserBean userBean = new UserBean("username");
     *      return BeanUtil.transform(UserDTO.class, userBean);
     * </pre>
     */
    public static <T> T transform(Class<T> clazz, Object src){
        if (src == null) {
            return null;
        }
        T instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            throw new BeanUtilsException(e);
        }
        org.springframework.beans.BeanUtils.copyProperties(src, instance, getNullPropertyNames(src));
        return instance;
    }

    /**
     * 对象属性值拷贝，支持将Date类型的字段值格式化为String类型
     * @param clazz
     * @param src
     * @param isTransformDateTime
     * @param <T>
     * @return
     */
    public static <T> T transform(Class<T> clazz, Object src, boolean isTransformDateTime) {
        T target = transform(clazz, src);
        if (isTransformDateTime) {
            final BeanWrapper srcBean = new BeanWrapperImpl(src);
            PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();

            for (PropertyDescriptor pd : pds) {
                Object srcBeanVal = srcBean.getPropertyValue(pd.getName());
                if (srcBeanVal instanceof Date) {
                    Date date = (Date) srcBeanVal;
                    final String dateStr = DateUtils.parseDateToString(date, DateUtils.DATE_FORMAT_FULL);
                    try {
                        final PropertyDescriptor targetPropertyDescriptor = getObjPropertyDescriptor(target, pd.getName());
                        if (targetPropertyDescriptor != null && targetPropertyDescriptor.getPropertyType().equals(String.class)) {
                            //获取携带set的方法
                            final Method writeMethod = targetPropertyDescriptor.getWriteMethod();
                            writeMethod.invoke(target, dateStr);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return target;
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private static PropertyDescriptor getObjPropertyDescriptor(Object obj, String propertyName) {
        BeanWrapper src = new BeanWrapperImpl(obj);
        final PropertyDescriptor[] pds = src.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals(propertyName)) {
                return pd;
            }
        }
        return null;
    }

    private static Field deepFindField(Class<? extends Object> clazz, String key) {
        Field field = null;
        while (!clazz.getName().equals(Object.class.getName())) {
            try {
                field = clazz.getDeclaredField(key);
                if (field != null) {
                    break;
                }
            } catch (Exception e) {
                clazz = clazz.getSuperclass();
            }
        }
        return field;
    }

    /**
     * 获取某个对象的某个属性
     */
    public static Object getProperty(Object obj, String fieldName) {
        try {
            Field field = deepFindField(obj.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                return field.get(obj);
            }
        } catch (Exception e) {
            throw new BeanUtilsException(e);
        }
        return null;
    }

    /**
     * 设置某个对象的某个属性
     */
    public static void setProperty(Object obj, String fieldName, Object value) {
        try {
            Field field = deepFindField(obj.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                field.set(obj, value);
            }
        } catch (Exception e) {
            throw new BeanUtilsException(e);
        }
    }

    /**
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * The copy will ignore <em>BaseEntity</em> field
     *
     * @param source
     * @param target
     */
    public static void copyEntityProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }
}
