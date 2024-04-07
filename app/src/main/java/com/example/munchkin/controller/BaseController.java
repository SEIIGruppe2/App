package com.example.munchkin.controller;

import com.example.munchkin.model.WebSocketClientModel;

public abstract class BaseController {
    protected WebSocketClientModel model;

    public BaseController(WebSocketClientModel model) {
        this.model = model;
    }

    public abstract void handleMessage(String message);
}
