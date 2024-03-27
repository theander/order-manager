package com.anderson.ordermanager.infra.mapper;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.app.entity.StockMovement;
import com.anderson.ordermanager.infra.entities.StockMovementEntity;
import com.anderson.ordermanager.infra.web.dto.StockMovementDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMovementMapper {
	StockMovementEntity toEntity(StockMovement stockMovement);
	StockMovement toDomain(StockMovementEntity stockMovementEntity);
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	StockMovement toDomain(StockMovementDto stockMovementDto);

}
