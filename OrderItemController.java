package com.vivek.Vivek.Ecommerce.project.Controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.Vivek.Ecommerce.project.Dto.OrderRequest;
import com.vivek.Vivek.Ecommerce.project.Dto.Response;
import com.vivek.Vivek.Ecommerce.project.Enums.OrderStatus;
import com.vivek.Vivek.Ecommerce.project.Service.Interf.OrderItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderItemController {

	private final OrderItemService orderItemService;
	@PostMapping("/create")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> placeOrder(@RequestBody OrderRequest orderRequest) {
	    return ResponseEntity.ok(orderItemService.placeOrder(orderRequest));
	}

	
	@PutMapping("/update-item-success/{orderItemId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> updateOrderItemStatus(
	    @PathVariable(name = "orderItemId") Long orderItemId,
	    @RequestParam(name = "status") String status
	) {
	    return ResponseEntity.ok(orderItemService.updateOrderItemStatus(orderItemId, status));
	}

	
	
	@GetMapping("/filter")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> filterOrderItems(
	    @RequestParam(name = "startDate", required = false) 
	    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
	    
	    @RequestParam(name = "endDate", required = false) 
	    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
	    
	    @RequestParam(name = "status", required = false) String status,
	    @RequestParam(name = "ItemId", required = false) Long itemId,
	    @RequestParam(name = "page", defaultValue = "0") int page,
	    @RequestParam(name = "size", defaultValue = "1000") int size
	) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
	    
	    OrderStatus orderStatus = status != null ? OrderStatus.valueOf(status.toUpperCase()) : null;
	    
	    return ResponseEntity.ok(orderItemService.FilterOrderItem(orderStatus, startDate, endDate, itemId, pageable));
	}

}
