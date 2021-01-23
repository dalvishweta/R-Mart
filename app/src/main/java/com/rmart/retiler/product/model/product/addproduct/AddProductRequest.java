package com.rmart.retiler.product.model.product.addproduct;

import com.google.gson.JsonObject;

import java.io.Serializable;

public class AddProductRequest implements Serializable {
    JsonObject jsonObject;

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
