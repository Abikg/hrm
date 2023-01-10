package com.makalu.hrm.converter;

import java.util.List;

/**
 * @param <E>
 * @param <D>
 * @author manohar-td-003
 */
public interface IListConvertable<E, D> {

    /***
     * Convert Entity List to DTO listSale
     *
     * @param entities
     * @return
     */
    List<D> convertToDtoList(List<E> entities);

    /***
     * Convert DTO List to Entity List
     *
     * @param dtoList
     * @return
     */
    List<E> convertToEntityList(List<D> dtoList);
}

