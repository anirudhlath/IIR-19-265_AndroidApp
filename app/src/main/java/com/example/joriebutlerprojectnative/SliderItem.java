/*
 *     Created by Anirudh Lath on 12/16/22, 11:44 AM
 *     anirudhlath@gmail.com
 *     Last modified 12/16/22, 11:43 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.joriebutlerprojectnative;

public class SliderItem {
    private String description;
    private String imageUrl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public SliderItem(String description, String imageUrl) {
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
