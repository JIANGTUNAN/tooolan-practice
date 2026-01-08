package com.tooolan.practice.ddd.application.assembler;

import org.springframework.beans.BeanUtils;

/**
 * DTO转换器抽象基类
 * 提供通用的DTO转换方法
 *
 * @param <DO> 领域对象类型
 * @param <DTO> DTO类型
 * @author tooolan
 */
public abstract class AbstractAssembler<DO, DTO> {

    /**
     * 领域对象转DTO
     *
     * @param domainObject 领域对象
     * @return DTO对象
     */
    public DTO toDTO(DO domainObject) {
        if (domainObject == null) {
            return null;
        }
        DTO dto = createDTO();
        BeanUtils.copyProperties(domainObject, dto);
        return dto;
    }

    /**
     * DTO转领域对象
     *
     * @param dto DTO对象
     * @return 领域对象
     */
    public DO toDomain(DTO dto) {
        if (dto == null) {
            return null;
        }
        DO domainObject = createDomainObject();
        BeanUtils.copyProperties(dto, domainObject);
        return domainObject;
    }

    /**
     * 创建DTO对象
     *
     * @return DTO对象
     */
    protected abstract DTO createDTO();

    /**
     * 创建领域对象
     *
     * @return 领域对象
     */
    protected abstract DO createDomainObject();

}
