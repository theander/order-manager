package com.anderson.ordermanager.web.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginationResponse {
	private List<?> items;
	private Pagination pagination;
}
