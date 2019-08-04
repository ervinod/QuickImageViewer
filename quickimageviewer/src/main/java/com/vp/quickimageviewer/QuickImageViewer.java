package com.vp.quickimageviewer;

import android.content.Context;

/**
 * Created by Vinod Patil on 4/8/19
 */
public class QuickImageViewer {

    Context context;
    boolean showShareButton, showSaveButton, showDescription;

    QuickImageViewer(Context context){
        this.context= context;
    }

    public void setShowShareButton(boolean showShareButton) {
        this.showShareButton = showShareButton;
    }

    public void setShowSaveButton(boolean showSaveButton) {
        this.showSaveButton = showSaveButton;
    }

    public void setShowDescription(boolean showDescription) {
        this.showDescription = showDescription;
    }
}
