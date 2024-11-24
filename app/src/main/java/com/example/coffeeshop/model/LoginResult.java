package com.example.coffeeshop.model;

import androidx.annotation.Nullable;

/**
 * Authentication result: success (user details) or error message.
 */
public class LoginResult {
    @Nullable
    private final User success;
    @Nullable
    private final Integer error;

    public LoginResult(@Nullable Integer error) {
        this.success = null;
        this.error = error;
    }

    public LoginResult(@Nullable User success) {
        this.success = success;
        this.error = null;
    }

    @Nullable
    public User getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }


    boolean isSuccess() {
        return success != null;
    }

    boolean isError() {
        return error != null;
    }
}