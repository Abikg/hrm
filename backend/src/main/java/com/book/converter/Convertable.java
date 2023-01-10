package com.book.converter;

import com.book.utils.ParseUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Convertable <E,D> implements IConverter<E , D>, IListConvertable<E , D>{

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public String formatDate(Date date){
        if (date == null){
            return "";
        }
        return sdf.format(date);
    }

    @Override
    public E convertToEntity(D dto) {
        return null;
    }

    @Override
    public D convertToDto(E entity) {
        return null;
    }

    @Override
    public E copyConvertToEntity(D dto, E entity) {
        return null;
    }

    @Override
    public List<D> convertToDtoList(List<E> entities) {

        if (entities == null||entities.isEmpty()){
            return List.of();
        }

        return entities.parallelStream().map(this::convertToDto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<E> convertToEntityList(List<D> dtoList) {

        if (dtoList == null || dtoList.isEmpty()){
            return List.of();
        }

        return dtoList.parallelStream().map(this::convertToEntity).filter(Objects::nonNull).collect(Collectors.toList());
    }

    protected String trimString(String value){
        return ParseUtils.trimString(value);
    }

    protected double doubleFormatter(Double value){
        return ParseUtils.doubleFormatter(value);
    }

}
