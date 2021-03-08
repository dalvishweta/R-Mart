package com.rmart.customerservice.mobile.models;

public class SubscriberModule {
    private int id = 0;
    private int image;
    private String name;
    private String key;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;

    public String getPlanKey() {
        return planKey;
    }

    public void setPlanKey(String planKey) {
        this.planKey = planKey;
    }

    private String planKey;
    private boolean isSelected =false;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SubscriberModule(int _id, int _image, String _name, String _key, String planKey) {
        id = _id;
        image = _image;
        name = _name;
        key = _key;
        this.planKey = planKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
