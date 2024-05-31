package com.summary.common.core.utils;

//import net.sf.cglib.beans.BeanCopier;

import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 对象拷贝 - 性能比较好
 * jdk 17 Cglib 启动需要指定jvm参数 ： java --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/sun.net.util=ALL-UNNAMED
 *  java.lang.reflect.InaccessibleObjectException: Unable to make protected final java.lang.Class java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain) throws java.lang.ClassFormatError accessible: module java.base does not "opens java.lang" to unnamed module @7960847b
 * @author jie.luo
 * @since 2024/5/29
 */
public class ConvertUtils {

    public static <S, T> T convert(S source, Class<T> dest, Function<T, T> function) {
        if (source == null) {
            return null;
        }
        try {
            T result = dest.newInstance();
            final BeanCopier copier = BeanCopier.create(source.getClass(), dest, false);
            copier.copy(source, result, null);
            if (function != null) {
                function.apply(result);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <S, T> T convert(S source, Class<T> dest) {
        return convert(source, dest, null);
    }

    public static <S, T> T convert(S source, T dest) {
        if (source == null || dest == null) {
            return null;
        }
        T result = dest;
        final BeanCopier copier = BeanCopier.create(source.getClass(), dest.getClass(), false);
        copier.copy(source, result, null);
        return result;
    }

    public static <S, T> List<T> convertListEmpty(List<S> source, Class<T> dest) {
        return convertListEmpty(source, dest, null);
    }

    public static <S, T> List<T> convertListEmpty(List<S> source, Class<T> dest, ConvertCallback<S, T> callback) {
        if (source == null || source.size() == 0) {
            return new ArrayList<>();
        }
        return source.stream().map(s -> {
            T result = null;
            try {
                result = dest.newInstance();
                convert(s, result);
                if (callback != null) {
                    callback.callback(s, result);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return result;
        }).collect(Collectors.toList());
    }

    public static <S, T> List<T> convertList(List<S> source, Class<T> dest) {
        return convertList(source, dest, null);
    }

    public static <S, T> List<T> convertList(List<S> source, Class<T> dest, ConvertCallback<S, T> callback) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> {
            T result = null;
            try {
                result = dest.newInstance();
                convert(s, result);
                if (callback != null) {
                    callback.callback(s, result);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return result;
        }).collect(Collectors.toList());
    }

    public interface ConvertCallback<S, D> {
        void callback(S source, D dest);
    }

}
