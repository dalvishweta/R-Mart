package com.rmart.electricity.selectoperator.model;

import java.io.Serializable;

public class Operator implements Serializable {

        public String name;
        public String slug;
        public int icon;

        public Operator(String name,String slug, int icon) {
                this.name = name;
                this.icon = icon;
                this.slug = slug;
        }
}
