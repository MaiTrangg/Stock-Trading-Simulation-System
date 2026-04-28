package com.trading.demo.common.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnumMapper {

    default <E extends Enum<E>> E toEnum(String value, Class<E> enumClass) {
        return value != null ? Enum.valueOf(enumClass, value) : null;
    }

    default String toString(Enum<?> e) {
        return e != null ? e.name() : null;
    }
}
