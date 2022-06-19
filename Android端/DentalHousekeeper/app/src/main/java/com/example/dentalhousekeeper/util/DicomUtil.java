package com.example.dentalhousekeeper.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;

public class DicomUtil {

    int[] pixels;
    private static final String TAG = "DicomUtil";

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            try {
                m.setRotate(degree, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                Bitmap bmp2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                bitmap.recycle();
                bitmap = bmp2;
            } catch (Exception ex) {
                System.out.print("创建图片失败！" + ex);
            }
        }
        return bitmap;
    }

    private Bitmap sharpenImageAmeliorate(Bitmap bmp) {
        long start = System.currentTimeMillis();
        // 拉普拉斯矩阵
        int[] laplacian = new int[]{-1, -1, -1, -1, 9, -1, -1, -1, -1};

        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int idx = 0;
        float alpha = 0.3F;
        for (int i = 0; i < width * height; i++) {
            Log.d(TAG, "sharpenImageAmeliorate: " + pixels[i]);
        }
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + n) * width + k + m];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * laplacian[idx] * alpha);
                        newG = newG + (int) (pixG * laplacian[idx] * alpha);
                        newB = newB + (int) (pixB * laplacian[idx] * alpha);
                        idx++;
                    }
                }

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        long end = System.currentTimeMillis();
        Log.d("may", "used time=" + (end - start));
        return bitmap;
    }
}
