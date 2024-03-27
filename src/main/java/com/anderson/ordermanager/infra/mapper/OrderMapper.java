package com.anderson.ordermanager.infra.mapper;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.app.entity.Orders;
import com.anderson.ordermanager.app.entity.Users;
import com.anderson.ordermanager.infra.entities.OrdersEntity;
import com.anderson.ordermanager.infra.web.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	OrdersEntity toEntity(Orders orders);
	Orders toDomain(OrdersEntity ordersEntity);
	@Mapping(target = "id",ignore = true)
	Orders toDomain(OrderDto orderDto);

	OrdersEntity toDto(Orders order);


}
