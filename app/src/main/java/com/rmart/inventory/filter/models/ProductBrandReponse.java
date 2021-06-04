package com.rmart.inventory.filter.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductBrandReponse implements Serializable {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("Code")
        @Expose
        private Integer code;
        @SerializedName("start_index")
        @Expose
        private String startIndex;
        @SerializedName("end_index")
        @Expose
        private String endIndex;
        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;
        @SerializedName("request_id")
        @Expose
        private Integer requestId;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(String startIndex) {
            this.startIndex = startIndex;
        }

        public String getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(String endIndex) {
            this.endIndex = endIndex;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public Integer getRequestId() {
            return requestId;
        }

        public void setRequestId(Integer requestId) {
            this.requestId = requestId;
        }

    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

    }
