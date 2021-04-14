package com.powerboot.system.consts;

import java.util.Objects;

/**
 * 枚举类父类，code为Integer类型
 */
public interface IntegerEnumInterface extends EnumInterface {

    /**
     * 枚举类获取Integer类型code的方法
     */
    Integer getCode();

    /**
     * 根据code值获取对应的枚举类值
     *
     * @param code       枚举code值
     * @param defaultVal 默认值，不能为空
     * @param <E>        具体的枚举类类型
     * @return 找不到返回默认值(defaultVal)
     */
    @SuppressWarnings("unchecked")
    static <E extends Enum<E> & IntegerEnumInterface> E get(Integer code, E defaultVal) {
        if (defaultVal == null) {
            return null;
        }
        for (Enum<E> e : defaultVal.getClass().getEnumConstants()) {
            if (Objects.equals(((IntegerEnumInterface) e).getCode(), code)) {
                return (E) e;
            }
        }

        return defaultVal;
    }

    /**
     * 根据code值获取对应的枚举类值
     *
     * @param code  枚举code值
     * @param clazz 具体枚举类，不能为空
     * @param <E>   具体的枚举类类型
     * @return 找不到返回空值(null)
     */
    static <E extends Enum<E> & IntegerEnumInterface> E get(Integer code, Class<E> clazz) {
        if (clazz == null) {
            return null;
        }
        for (E e : clazz.getEnumConstants()) {
            if (Objects.equals(e.getCode(), code)) {
                return e;
            }
        }
        return null;
    }

}
