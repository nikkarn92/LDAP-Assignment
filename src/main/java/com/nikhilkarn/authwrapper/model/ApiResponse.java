/**
 * POC Project for LDAP AUTH WRAPPER
 * Author: Nikhil Karn
 */

package com.nikhilkarn.authwrapper.model;

/**
 * Generic API response object.
 */
public class ApiResponse {

    private boolean success;
    private String message;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static ApiResponse ok(String msg) {
        return new ApiResponse(true, msg);
    }

    public static ApiResponse error(String msg) {
        return new ApiResponse(false, msg);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
