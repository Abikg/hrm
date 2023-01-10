package com.makalu.hrm.converter;

public interface IConverter <E,D>{

    /***
     * Convert DTO to Entity
     *
     * @param dto
     * @return entity
     */
    E convertToEntity(D dto) throws Exception;

    /***
     * Convert Entity to DTO
     *
     * @param entity
     * @return entity
     */
    D convertToDto(E entity);

    /***
     * Copy DTO to Entity
     *
     * @param entity , dto
     * @return entity
     */

    E copyConvertToEntity(D dto, E entity);
  

}
