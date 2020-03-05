package com.yami.studio.banana.merchantapp.entity.error;

public class ResponseError {

    private Error error;

    public ResponseError(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }

}
