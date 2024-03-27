package com.anderson.ordermanager.infra.mapper;

import com.anderson.ordermanager.app.entity.Item;
import com.anderson.ordermanager.app.entity.Users;
import com.anderson.ordermanager.infra.entities.UsersEntity;
import com.anderson.ordermanager.infra.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UsersEntity toEntity(Users users);

	Users toDomain(UsersEntity usersEntity);

	@Mapping(target = "id", ignore = true)
	Users toDomain(UserDto userDto);

	@Mapping(target = "name",ignore = true)
	@Mapping(target = "email",ignore = true)
	Users toUserDomain(Long id);
}
