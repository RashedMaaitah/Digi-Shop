package com.digi.ecommerce.digi_shop.api.dto.response;

import java.util.List;

public class ApiPageResponse<T> extends ApiResponse<T> {
    ApiPageResponse(boolean success, String message, List<T> data, List<String> errors, int errorCode, long timestamp, String path) {
        super(success, message, data, errors, errorCode, timestamp, path);
    }
}
