package com.rk.rkeeper.base;

public interface BaseView <T extends BasePresenter>{

    void setPresenter(T presenter);
}
