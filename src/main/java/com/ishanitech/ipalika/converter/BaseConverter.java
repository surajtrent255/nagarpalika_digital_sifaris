package com.ishanitech.ipalika.converter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract base converter class for every child converter classes.
 * @param <E>Entity object
 * @param <D> DTO object
 * @author <b> Umesh Bhujel
 * @since 1.0
 */
public abstract class BaseConverter<E, D> {

	public abstract E fromDto(D dto);
	
	public abstract D fromEntity(E entity);
	
	public List<E> fromDto(List<D> dtos) {
		if (dtos == null) return null;
		return dtos.stream().map(this::fromDto).collect(Collectors.toList());
	}
	
	public List<D> fromEntity(List<E> entities) {
		if(entities == null) return null;
		return entities.stream().map(this::fromEntity).collect(Collectors.toList());
	}
}
