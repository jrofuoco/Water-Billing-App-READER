package com.example.opencvandfirebase;

import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

public class OCRProcessor {
    private static final String TAG = "OCRProcessor";
    private TessBaseAPI tessBaseAPI;

    public OCRProcessor() {
        tessBaseAPI = new TessBaseAPI();
        String datapath = "assets/eng.traineddata"; // Set the correct path to your tessdata folder
        tessBaseAPI.init(datapath, "eng");
    }

    public String performOCR(Bitmap bitmap) {
        tessBaseAPI.setImage(bitmap);
        String recognizedText = tessBaseAPI.getUTF8Text();
        tessBaseAPI.end();  // End the Tesseract session
        Log.d(TAG, "Recognized text: " + recognizedText);
        return recognizedText;
    }
}
