package org.reactnative.camera.tasks;

import android.util.SparseArray;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.google.android.cameraview.CameraView;
import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import org.reactnative.camera.utils.ImageDimensions;
import org.reactnative.facedetector.FaceDetectorUtils;
import org.reactnative.frame.RNFrame;
import org.reactnative.frame.RNFrameFactory;


public class PixelsProcessedAsyncTask extends android.os.AsyncTask<Void, Void, SparseArray<TextBlock>> {

  private PixelsProcessedAsyncTaskDelegate mDelegate;
  private ThemedReactContext mThemedReactContext;

  private byte[] mImageData;
  private int mWidth;
  private int mHeight;
  private int mRotation;
  private ImageDimensions mImageDimensions;
  private double mScaleX;
  private double mScaleY;
  private int mPaddingLeft;
  private int mPaddingTop;

  public PixelsProcessedAsyncTask(
          PixelsProcessedAsyncTaskDelegate delegate,
          //ThemedReactContext themedReactContext,
          byte[] imageData,
          int width,
          int height,
          int rotation,
          float density,
          int facing,
          int viewWidth,
          int viewHeight,
          int viewHeight,
          int viewPaddingLeft,
          int viewPaddingTop
  ) {
    mDelegate = delegate;
    mThemedReactContext = themedReactContext;
    mImageData = imageData;
    mWidth = width;
    mHeight = height;
    mRotation = rotation;
    mImageDimensions = new ImageDimensions(width, height, rotation, facing);
    mScaleX = (double) (viewWidth) / (mImageDimensions.getWidth() * density);
    mScaleY = (double) (viewHeight) / (mImageDimensions.getHeight() * density);
    mPaddingLeft = viewPaddingLeft;
    mPaddingTop = viewPaddingTop;
  }

  @Override
  protected SparseArray<TextBlock> doInBackground(Void... ignored) {
    if (isCancelled() || mDelegate == null) {
      return null;
    }
   // mPixelsProcessed = new TextRecognizer.Builder(mThemedReactContext).build();
   // RNFrame frame = RNFrameFactory.buildFrame(mImageData, mWidth, mHeight, mRotation);
   // return mTextRecognizer.detect(frame.getFrame());
  }

  @Override
  protected void onPostExecute(SparseArray<TextBlock> textBlocks) {
    super.onPostExecute(textBlocks);

    if (textBlocks != null) {
      WritableArray textBlocksList = Arguments.createArray();
      for (int i = 0; i < textBlocks.size(); ++i) {
        TextBlock textBlock = textBlocks.valueAt(i);
        WritableMap serializedTextBlock = serializeText(textBlock);
        if (mImageDimensions.getFacing() == CameraView.FACING_FRONT) {
          serializedTextBlock = rotateTextX(serializedTextBlock);
        }
        textBlocksList.pushMap(serializedTextBlock);
      }
      mDelegate.onPixelProcessedRecognized(textBlocksList);
    }
    mDelegate.onPixelProcessedTaskCompleted();
  }





}
