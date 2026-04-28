package com.trading.demo.trading.presentation.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trading.demo.trading.application.dto.response.*;
import com.trading.demo.trading.application.usecase.order.*;
import com.trading.demo.trading.domain.enums.OrderSide;
import com.trading.demo.trading.domain.enums.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trading.demo.auth.application.service.CurrentUserProvider;
import com.trading.demo.common.dto.ApiResponse;
import com.trading.demo.trading.application.dto.request.PlaceOrderRequest;
import com.trading.demo.trading.presentation.dto.request.CreateOrderRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final GetOrdersUseCase getOrdersUseCase;
    private final GetOrderDetailUseCase getOrderDetailUseCase;
    private final GetOrderHistoryUseCase getOrderHistoryUseCase;
    private final CurrentUserProvider currentUserProvider;
    private final GetOrderTradesUseCase getOrderTradesUseCase;
    private final GetOpenOrdersUseCase getOpenOrdersUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        UUID userId = currentUserProvider.getCurrentUserId();

        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
        placeOrderRequest.setStockId(request.getStockId());
        placeOrderRequest.setSide(request.getSide());
        placeOrderRequest.setType(request.getType());
        placeOrderRequest.setPrice(request.getPrice());
        placeOrderRequest.setQuantity(request.getQuantity());

        OrderResponse response = placeOrderUseCase.execute(userId, placeOrderRequest);

        return ResponseEntity.ok(ApiResponse.success(response, "Order created successfully"));
    }

    @GetMapping("/get-orders")
    public ApiResponse<List<OrderResponse>> getOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) UUID stockId,
            @RequestParam(required = false) OrderSide side,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to
    ) {

        UUID userId = currentUserProvider.getCurrentUserId();

        List<OrderResponse> responses = getOrdersUseCase.execute(userId, status, stockId, side, from, to);
        return ApiResponse.success(responses, "Orders retrieved successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> getOrder(
            @PathVariable UUID id) {

        UUID userId = currentUserProvider.getCurrentUserId();

        OrderDetailResponse response = getOrderDetailUseCase.execute(userId, id);

        return ResponseEntity.ok(ApiResponse.success(response, "Order retrieved successfully"));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(
            @PathVariable UUID id) {

        UUID userId = currentUserProvider.getCurrentUserId();

        OrderResponse response = cancelOrderUseCase.cancel(id, userId);

        return ResponseEntity.ok(ApiResponse.success(response, "Order cancelled successfully"));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<ApiResponse<List<OrderHistoryResponse>>> getOrderHistory(
            @PathVariable UUID id) {

        UUID userId = currentUserProvider.getCurrentUserId();

        List<OrderHistoryResponse> response = getOrderHistoryUseCase.execute(userId, id);

        return ResponseEntity.ok(ApiResponse.success(response, "Order history retrieved successfully"));
    }

    @GetMapping("/open")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOpenOrders(
            @RequestParam UUID stockId
    ) {
        UUID userId = currentUserProvider.getCurrentUserId();

        List<OrderResponse> responses =
                getOpenOrdersUseCase.execute(userId, stockId);

        return ResponseEntity.ok(ApiResponse.success(responses, "Open orders retrieved"));
    }

    @GetMapping("/{orderId}/trades")
    public ResponseEntity<ApiResponse<List<TradeResponse>>> getOrderTrades(
            @PathVariable UUID orderId
    ) {
        UUID userId = currentUserProvider.getCurrentUserId();

        List<TradeResponse> responses =
                getOrderTradesUseCase.execute(orderId, userId);

        return ResponseEntity.ok(ApiResponse.success(responses, "Trades retrieved"));
    }
}