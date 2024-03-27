package com.anderson.ordermanager.infra.web.pagination;

import com.anderson.ordermanager.infra.web.controller.ItemController;
import com.anderson.ordermanager.infra.web.dto.SortEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Pagination {
	private int currentPage;
	private Link next;
	private Link prev;
	private int quantity;

	public Pagination createPagination(int page, int size, String sortBy, SortEnum sortDirection, Page<?> itemsPage) {
		Link nextLink = itemsPage.hasNext() ?
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class)
						.findAll(page + 1, size, sortBy, sortDirection)).withRel("next") : null;
		Link prevLink = itemsPage.hasPrevious() ?
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class)
						.findAll(page - 1, size, sortBy, sortDirection)).withRel("prev") : null;
		return new Pagination(page, nextLink, prevLink, itemsPage.getContent().size());
	}

	public Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		return PageRequest.of(page, size, sort);
	}

}
