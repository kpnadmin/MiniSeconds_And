package com.android.mylibrary.helper.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class ImageHelper {

    /**
     * https://stackoverflow.com/questions/3585009/convert-string-to-drawable
     * @param image
     * @return
     */
    public static Drawable getDrawable(String image) {
        try {
            InputStream stream = new ByteArrayInputStream(image.getBytes());
            Drawable drawable = Drawable.createFromStream(stream, null);
            //stream.close();
            return drawable;
        }
        catch(Exception ex) {
            return null;
        }
    }

    /**
     * String myBase64Image = encodeToBase64(myBitmap, Bitmap.CompressFormat.JPEG, 100);
     *
     * https://stackoverflow.com/questions/9768611/encode-and-decode-bitmap-object-in-base64-string-in-android
     * @param image
     * @param compressFormat
     * @param quality
     * @return
     */
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);

        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    /**
     * Bitmap myBitmapAgain = decodeBase64(myBase64Image);
     *
     * https://stackoverflow.com/questions/9768611/encode-and-decode-bitmap-object-in-base64-string-in-android
     * @param input
     * @return
     */
    public static Bitmap decodeBase64(String input)  {
        byte[] decodedBytes = Base64.decode(input, 0);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        return bmp;
    }


    /**
     * https://stackoverflow.com/questions/17506428/convert-base64-string-to-image-in-java
     * @param image
     * @return
     */
    public static Bitmap ConvertToImage(String image){
        try{
            InputStream stream = new ByteArrayInputStream(image.getBytes());
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            stream.close();
            return bitmap;
        }
        catch (Exception e) {
            return null;
        }
    }
}