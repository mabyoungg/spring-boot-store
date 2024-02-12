package com.example.store.global.exceptions;

import com.example.store.global.rsData.RsData;
import lombok.Getter;

public class GlobalException extends RuntimeException {
    @Getter
    private RsData<?> rsData;

    public GlobalException(String resultCode, String msg) {
        super(resultCode + ": " + msg);

        rsData = RsData.of(resultCode, msg);
    }
}
