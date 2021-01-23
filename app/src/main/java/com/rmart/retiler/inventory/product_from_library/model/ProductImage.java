package com.rmart.retiler.inventory.product_from_library.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductImage {

        @SerializedName("image_id")
        @Expose
        private int imageId;
        @SerializedName("image_name")
        @Expose
        private String imageName;
        @SerializedName("isPrimary")
        @Expose
        private int isPrimary;
        @SerializedName("image")
        @Expose
        private String image;

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

        public int getIsPrimary() {
            return isPrimary;
        }

        public void setIsPrimary(int isPrimary) {
            this.isPrimary = isPrimary;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

