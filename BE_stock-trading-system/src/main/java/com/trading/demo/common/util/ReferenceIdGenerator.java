package com.trading.demo.common.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Utility for generating deterministic reference IDs for financial operations.
 * Ensures idempotency by creating consistent UUIDs for the same action.
 */
public class ReferenceIdGenerator {

    public static UUID generate(UUID aggregateId, String action) {
        String input = aggregateId + "_" + action;
        return UUID.nameUUIDFromBytes(input.getBytes(StandardCharsets.UTF_8));
    }

    public static UUID generate(UUID userId, UUID orderId, String action) {
        String input = userId + "_" + orderId + "_" + action;
        return UUID.nameUUIDFromBytes(input.getBytes(StandardCharsets.UTF_8));
    }

    public static UUID generate(UUID userId, UUID stockId, UUID orderId, String action) {
        String input = userId + "_" + stockId + "_" + orderId + "_" + action;
        return UUID.nameUUIDFromBytes(input.getBytes(StandardCharsets.UTF_8));
    }
}
