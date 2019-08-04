package com.vp.quickimageviewer;

import java.io.Serializable;

/**
 * Created by Vinod Patil on 4/8/19
 */
public class ImageModel implements Serializable {
    private String imageURL;
    private String imageDescription;

    public ImageModel(String imageURL, String imageDescription) {
        this.imageURL = imageURL;
        this.imageDescription = imageDescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }
}
