package com.digi.ecommerce.digi_shop.common;

/**
 * Path-related constants.
 *
 * @author Rashed Al Maaitah
 * @version 1.0
 */
public final class PathConstants {

    public static final String AUTH_BASE = "auth";
    public static final String AUTH_SIGNIN = "signin";
    public static final String AUTH_SIGNUP = "signup";
    public static final String AUTH_SIGNOUT = "signout";
    public static final String AUTH_REFRESH_TOKEN = "refresh-token";
    public static final String AUTH_CHANGE_PASSWORD = "change-password";

    public static final String USERS_BASE = "users";

    public static final String PRODUCTS_BASE = "products";
    public static final String PRODUCTS_ID = "{id}";

    public static final String CART_BASE = "cart";
    public static final String CART_ITEM_ID = "{cartItemId}";
    public static final String CART_ID = "{id}";

    public static final String ORDER_BASE = "orders";
    public static final String ORDER_ID = "{orderId}";
    public static final String ORDER_USER_ID = "user/{userId}";
    public static final String ORDER_ID_STATUS = "{orderId}/status";


    private PathConstants() {
    }

}
