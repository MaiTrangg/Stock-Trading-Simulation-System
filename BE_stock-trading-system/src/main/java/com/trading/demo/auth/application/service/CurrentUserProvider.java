package com.trading.demo.auth.application.service;

import java.util.UUID;

public interface CurrentUserProvider {
    UUID getCurrentUserId();
}
