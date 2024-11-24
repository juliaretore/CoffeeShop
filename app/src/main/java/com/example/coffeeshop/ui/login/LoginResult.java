package com.example.coffeeshop.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result: success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private final LoggedInUserView success;
    @Nullable
    private final Integer error;

    LoginResult(@Nullable Integer error) {
        this.success = null;
        this.error = error;
    }

    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
        this.error = null;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }


    boolean isSuccess() {
        return success != null;
    }

    boolean isError() {
        return error != null;
    }
}
