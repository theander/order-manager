package com.anderson.ordermanager.infra.mapper;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.infra.entities.ItemEntity;
import com.anderson.ordermanager.infra.web.dto.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {

	ItemEntity toEntity(Item item);
	Item toDomain(ItemEntity itemEntity);
	ItemDto toDto(Item item);
	@Mapping(target = "id",ignore = true)
	Item toDomain(ItemDto itemDto);
	@Mapping(target = "name",ignore = true)
	@Mapping(target = "id", source = "id")
	Item toItemDomain(Long id);
}
