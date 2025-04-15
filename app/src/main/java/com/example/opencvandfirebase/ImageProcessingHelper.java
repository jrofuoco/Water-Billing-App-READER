package com.example.opencvandfirebase;

import android.graphics.Bitmap;
import android.graphics.Color;

/*
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
*/

import java.util.ArrayList;
import java.util.List;

public class ImageProcessingHelper {

/*    static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
        }*/
    }

    /*// Detect rectangular boxes in the input image
    public static List<Bitmap> detectBox(Bitmap bitmap) {
        List<Bitmap> croppedImages = new ArrayList<>();

        Mat rgba = new Mat();
        Utils.bitmapToMat(bitmap, rgba);

        // Convert the image to grayscale
        Mat gray = new Mat();
        Imgproc.cvtColor(rgba, gray, Imgproc.COLOR_BGR2GRAY);

        // Apply GaussianBlur to reduce noise
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

        // Canny edge detection
        Imgproc.Canny(gray, gray, 50, 150);

        // Find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(gray, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Loop through contours
        for (MatOfPoint contour : contours) {
            MatOfPoint2f approxCurve = new MatOfPoint2f();
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());

            // Approximate contour to a polygon with more accuracy
            double epsilon = 0.02 * Imgproc.arcLength(contour2f, true);
            Imgproc.approxPolyDP(contour2f, approxCurve, epsilon, true);

            // If the polygon has 4 vertices and its area is large enough, it's likely a rectangle
            if (approxCurve.rows() == 4 && Math.abs(Imgproc.contourArea(approxCurve)) > 1000) {
                Point[] points = approxCurve.toArray();
                // Draw rectangle
                for (int j = 0; j < 4; j++) {
                    Imgproc.line(rgba, points[j], points[(j + 1) % 4], new Scalar(255, 0, 0), 3);
                }

                // Crop the image using the rectangle coordinates
                Rect rect = Imgproc.boundingRect(new MatOfPoint(approxCurve.toArray()));
                Mat croppedMat = new Mat(rgba, rect);
                Bitmap croppedBitmap = Bitmap.createBitmap(croppedMat.cols(), croppedMat.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(croppedMat, croppedBitmap);
                croppedImages.add(croppedBitmap);
            }
        }

        return croppedImages;
    }*/
    // Apply GaussianBlur to reduce noise
/*    public static Bitmap applyGaussianBlur(Bitmap bitmap) {
        Mat mat = new Mat();
        Utils Utils = null;
        Utils.bitmapToMat(bitmap, mat);

        // Apply GaussianBlur to reduce noise
        Imgproc.GaussianBlur(mat, mat, new Size(5, 5), 0);

        // Convert back to bitmap
        Bitmap resultBitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, resultBitmap);

        return resultBitmap;
    }
    // Function to invert a color
    public static int invertColor(int color) {
        // Extract RGB components
        int alpha = (color >> 24) & 0xFF;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        // Invert each component
        red = 255 - red;
        green = 255 - green;
        blue = 255 - blue;

        // Combine components back into ARGB color
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    // Apply thresholding
    public static Bitmap applyThreshold(Bitmap bitmap) {
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);

        // Apply thresholding
        Imgproc.threshold(mat, mat, 100, 255, Imgproc.THRESH_BINARY);

        // Convert back to bitmap
        Bitmap resultBitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, resultBitmap);

        return resultBitmap;
    }

    public static Bitmap subtractImage(Bitmap bitmap) {
        // Convert Bitmap to Mat
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);

        // Convert to grayscale
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY);

        // Define the constant value to subtract (adjust as needed)
        double constantValue = 100.0;

        // Subtract constant value from the image
        Core.subtract(mat, new Scalar(constantValue), mat);

        // Convert Mat to Bitmap
        Bitmap resultBitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, resultBitmap);

        return resultBitmap;
    }*/