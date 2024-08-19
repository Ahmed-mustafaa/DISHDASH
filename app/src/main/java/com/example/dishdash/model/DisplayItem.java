package com.example.dishdash.model;


    public class DisplayItem {
        public String name;
        public String imageUrl;
        public ItemType type;
        public String strArea;
        public String strCategory;

        public DisplayItem(String strArea) {
            this.name = strArea;
            this.strArea = strArea;
            this.type = ItemType.COUNTRY;
        }

        // Constructor for Category items
        public DisplayItem(String strCategory, String imageUrl) {
            this.name = strCategory;
            this.imageUrl = imageUrl;
            this.strCategory = strCategory;
            this.type = ItemType.CATEGORY;
        }
        public enum ItemType {
            COUNTRY,
            CATEGORY
        }
        public String getStrArea() {
            return strArea;
        }

        public String getStrCategory() {
            return strCategory;
        }
    }


