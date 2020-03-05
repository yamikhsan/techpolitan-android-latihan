package com.yami.studio.banana.merchantapp.entity.user;

import com.yami.studio.banana.merchantapp.entity.Merchant;

public class GetUser {
    private long code;
    private User user;
    private Merchant merchant;

    public GetUser(long code, User user, Merchant merchant) {
        this.code = code;
        this.user = user;
        this.merchant = merchant;
    }

    public long getCode() {
        return code;
    }

    public User getUser() {
        return user;
    }

    public Merchant getMerchant() {
        return merchant;
    }
}
