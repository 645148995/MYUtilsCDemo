package com.SGY.c_utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;


import com.SGY.c_utils.SGYUtils;
import com.SGY.c_utils.content.SGYLogUtils;

import java.io.ByteArrayOutputStream;

/**
 * 图片相关工具类
 */
public class SGYImageUtils {

    private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;

    /**
     * Bitmap 转换为 字节数组
     */
    public static byte[] bitmapToBytes(final Bitmap bmp, final boolean needRecycle, final Bitmap.CompressFormat format, final int quality) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(format, quality, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Bitmap 转换为 字节数组
     * 默认格式为png，质量为100
     */
    public static byte[] bitmapToBytes(final Bitmap bmp, final boolean needRecycle) {

        return SGYImageUtils.bitmapToBytes(bmp, needRecycle, Bitmap.CompressFormat.JPEG, 100);
    }

    /**
     * 提取本地视频缩略图
     */
    public static Bitmap extractVideoThumb(final String path, final int height, final int width, final boolean crop) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            options.inJustDecodeBounds = true;
            Bitmap tmp = BitmapFactory.decodeFile(path, options);
            if (tmp != null) {
                tmp.recycle();
                tmp = null;
            }

            SGYLogUtils.i("extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
            final double beY = options.outHeight * 1.0 / height;
            final double beX = options.outWidth * 1.0 / width;

            SGYLogUtils.i("extractThumbNail: extract beX = " + beX + ", beY = " + beY);

            options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
            if (options.inSampleSize <= 1) {
                options.inSampleSize = 1;
            }

            // NOTE: out of memory error
            while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                options.inSampleSize++;
            }

            int newHeight = height;
            int newWidth = width;
            if (crop) {
                if (beY > beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            } else {
                if (beY < beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            }

            options.inJustDecodeBounds = false;

            SGYLogUtils.i("bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);

            Bitmap bm = BitmapFactory.decodeFile(path, options);
            if (bm == null) {
                SGYLogUtils.i("bitmap decode failed");
                return null;
            }

            SGYLogUtils.i("bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
            final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
            if (scale != null) {
                bm.recycle();
                bm = scale;
            }

            if (crop) {
                final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
                if (cropped == null) {
                    return bm;
                }

                bm.recycle();
                bm = cropped;
                SGYLogUtils.i("bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
            }
            return bm;

        } catch (final OutOfMemoryError e) {
            SGYLogUtils.i("decode bitmap failed: " + e.getMessage());
            options = null;
        }
        return null;
    }

    /**
     * 资源文件图片转换成bitmap
     */
    public static Bitmap resImgToBitmap(int vectorDrawableId) {
        Bitmap bitmap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = SGYUtils.getContext().getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(SGYUtils.getContext().getResources(), vectorDrawableId);
        }
        return bitmap;
    }

}
