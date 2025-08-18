package com.gabi.career_navigator_was.global.mapper;

import java.util.List;

public interface GenericMapper<D, E> {
	D toDto(E entity);

	E toEntity(D dto);

	List<D> getDtoList(List<E> entityList);

	List<E> getEntityList(List<D> dtoList);
}
