package com.example.dishdash.model;


    public class DisplayItem {
        public String name;
        public String imageUrl;
        public ItemType type;
        public String strArea;
        public String strCategory;

        public String getName() {
            return name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public void setStrCategory(String strCategory) {
            this.strCategory = strCategory;
        }

        // Constructor for Category items
        public DisplayItem(ItemType type, String name, String imageUrl) {
            if(imageUrl == null){
          this.type = ItemType.COUNTRY;
            } else {
                this.imageUrl = imageUrl;

                this.type = ItemType.CATEGORY;
            }
            this.name = name;
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


