# QuickImageViewer
Simple image viewer library with swipe and zoom functionality
Functionalitis:
1. Can view multiple images.
2. Swipe left right to switch image.
3. Double tap to zoom image.
4. Save and Share feature.
5. Pull down to close activity.

# Usage:
1. Create ArrayList and add image url

  final ArrayList<ImageModel> imageList = new ArrayList<>();
  imageList.add(new ImageModel("http://u01.appmifile.com/images/2016/10/21/c31c157f-97a2-479c-a2a3-74baf9790bb9.jpg", "Vinod Patil"));
  imageList.add(new ImageModel("https://nebula.wsimg.com/12e33523b6e7341bb7045fa321cdd463?AccessKeyId=63190F15169737A11884&disposition=0&alloworigin=1", ""));
  
2. Intent intent = new Intent(MainActivity.this, ImageViewerActivity.class);
    intent.putExtra(ImageViewerActivity.IMAGE_LIST, imageList);
    intent.putExtra(ImageViewerActivity.CURRENT_ITEM, 0);
    startActivity(intent);
 
# Customization:
1. Hide Save option:
  intent.putExtra(ImageViewerActivity.SHOW_SAVE, "false");

2. Hide Share option:
   intent.putExtra(ImageViewerActivity.SHOW_SHARE, "false");


# Intrgration:
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  dependencies {
	        implementation 'com.github.ervinod:QuickImageViewer:1.0.0'
	}
