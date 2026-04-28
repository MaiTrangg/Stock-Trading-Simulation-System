package com.trading.demo.common.exception;

import com.trading.demo.common.enums.ErrorCode;

public class OptimisticLockingFailureException extends AppException {
    
    public OptimisticLockingFailureException(String message) {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }
    
    public OptimisticLockingFailureException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
