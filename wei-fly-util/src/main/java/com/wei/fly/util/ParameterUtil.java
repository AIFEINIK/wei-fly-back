package com.wei.fly.util;



import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public final class ParameterUtil {
    
    private ParameterUtil() {
        
    }

    public static boolean hasNullOrBlank(Object... params) {
        if (ArrayUtils.isEmpty(params)) {
            return true;
        }

        for (Object obj : params) {
            if (obj == null) {
                return true;
            }
            if ((obj instanceof Collection<?> || obj instanceof Object[] ) &&  CollectionUtils.sizeIsEmpty(obj)) {
                return true;
            }
            if (obj instanceof String && StringUtils.isBlank(obj.toString())) {
                return true;
            }
        }

        return false;
    }
}