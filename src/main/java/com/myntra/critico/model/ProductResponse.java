package com.myntra.critico.model;

import java.util.Date;
import java.util.List;

public class ProductResponse {

    private String productName;
    private String productLargeImgURL;
    private String productDescription;

    private List<Review> socialReviews;
    private List<Review> videoReviews;
    private List<Review> userReviews;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLargeImgURL() {
        return productLargeImgURL;
    }

    public void setProductLargeImgURL(String productLargeImgURL) {
        this.productLargeImgURL = productLargeImgURL;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public List<Review> getSocialReviews() {
        return socialReviews;
    }

    public void setSocialReviews(List<Review> socialReviews) {
        this.socialReviews = socialReviews;
    }

    public List<Review> getVideoReviews() {
        return videoReviews;
    }

    public void setVideoReviews(List<Review> videoReviews) {
        this.videoReviews = videoReviews;
    }

    public List<Review> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<Review> userReviews) {
        this.userReviews = userReviews;
    }
}
