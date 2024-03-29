package com.anderson.ordermanager.infra.mapper;

import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.infra.entities.OrdersEntity;
import com.anderson.ordermanager.infra.web.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	OrdersEntity toEntity(Orders orders);

	Orders toDomain(OrdersEntity ordersEntity);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "userId", target = "user.id")
	@Mapping(source = "itemId", target = "item.id")
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	Orders toDomain(OrderDto orderDto);

	OrdersEntity toDto(Orders order);


}
