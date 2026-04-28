package com.trading.demo.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class NumberUtil {

    // Khong cho phep khoi tao class utility
    private NumberUtil() {
        throw new UnsupportedOperationException("This is a utility class");
    }

    /**
     * chuyen doi Object sang BigDecimal
     */
    public static BigDecimal toBigDecimal(Object obj) {
        if (obj == null || obj.toString().isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(obj.toString());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * cast Integer (dung cho quantity1, quantity2...)
     */
    public static int toInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        try {
            return Double.valueOf(obj.toString()).intValue();
        } catch (Exception e) {
            return 0;
        }
    }
}
