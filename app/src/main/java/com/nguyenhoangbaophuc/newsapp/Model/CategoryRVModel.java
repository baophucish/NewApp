package com.nguyenhoangbaophuc.newsapp.Model;

public class CategoryRVModel {
    private String category;
    private String categoryImage;
    private String categoryShow;

    public CategoryRVModel(String category, String categoryImage, String categoryShow) {
        this.category = category;
        this.categoryImage = categoryImage;
        this.categoryShow = categoryShow;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
    public String getCategoryShow() {
        return categoryShow;
    }

    public void setCategoryShow(String categoryShow) {
        this.categoryShow = categoryShow;
    }
}

