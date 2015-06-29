package com.krypto.xyzreader;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Contains most commonly used utility functions
 */
public class Utility {

    private static final Hashtable<String,Typeface> CACHE= new Hashtable<>();

    /**
     * Stores two custom font files in Hashtable,so that only one instance of each is present for the whole application.
     *
     * @param c         Context of the activity
     * @param assetPath Path of the custom font file
     * @return Typeface font from the requested asset path
     */
    public static Typeface getFont(Context c,String assetPath){

        synchronized (CACHE){
            if(!CACHE.contains(assetPath)){
                try{
                    Typeface t= Typeface.createFromAsset(c.getAssets(),assetPath);
                    CACHE.put(assetPath,t);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return CACHE.get(assetPath);
    }
}
