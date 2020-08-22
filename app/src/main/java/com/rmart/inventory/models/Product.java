package com.rmart.inventory.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable, Cloneable {
    String category;
    String subCategory;
    String brand;
    String name;
    String description;
    String regionalName;
    String videoLInk;
    String expiryDate;
    String deliveryInDays;
    ArrayList<String> images;
    ArrayList<UnitObject> unitObjects;

    public Product() {
        setDummyData();
    }

    public Product(Product product) {
        category = product.category;
        subCategory = product.subCategory;;
        brand = product.brand;
        name = product.name;
        description = product.description;
        regionalName = product.regionalName;
        videoLInk = product.videoLInk;
        expiryDate = product.expiryDate;
        deliveryInDays = product.deliveryInDays;
        images = new ArrayList<>();
        images.addAll(product.getImages());
        unitObjects = new ArrayList<>();
        unitObjects.addAll(product.getUnitObjects());
    }


    private void setDummyData() {
        category = "Grocery & Staples";
        subCategory = "Atta & Rice Items";
        brand = "Aashirvaad";
        name = "Aashirvad Multigrain Atta";
        description = "Consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolortempora incidun";
        regionalName = "Neque porro";
        videoLInk = "https://www.youtube.com/watch?v=QPS1jbvS4Gk";
        expiryDate = "12 Aug 2023";
        deliveryInDays = "1";
        images = new ArrayList<>();
        unitObjects = new ArrayList<>();
    }

    @NonNull
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public Product(int i, int j) {
        category = "Category " + i;
        subCategory = "Sub category "+j;
        brand = "Aashirvaad";
        name = "Aashirvad Multigrain Atta "+i+""+j;
        description = "Consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolortempora incidun";
        regionalName = "Neque porro";
        videoLInk = "https://www.youtube.com/watch?v=QPS1jbvS4Gk";
        expiryDate = "12 Aug 2023";
        images = new ArrayList<>();
        unitObjects = new ArrayList<>();
//        for (int k = 1 ; k<3; k++) {
//            unitObjects.add(new UnitObject(k));
//        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegionalName() {
        return regionalName;
    }

    public void setRegionalName(String regionalName) {
        this.regionalName = regionalName;
    }

    public String getVideoLInk() {
        return videoLInk;
    }

    public void setVideoLInk(String videoLInk) {
        this.videoLInk = videoLInk;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<UnitObject> getUnitObjects() {
        return unitObjects;
    }

    public void setUnitObjects(ArrayList<UnitObject> unitObjects) {
        this.unitObjects = unitObjects;
    }

    public String getDeliveryInDays() {
        return deliveryInDays;
    }

    public void setDeliveryInDays(String deliveryInDays) {
        this.deliveryInDays = deliveryInDays;
    }
}
