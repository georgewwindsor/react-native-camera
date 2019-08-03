package org.reactnative.camera.tasks;

import com.facebook.react.bridge.WritableArray;

public interface PixelsProcessedAsyncTaskDelegate {
  void onPixelsProcessed(WritableArray serializedData);
  void onPixelsProcessedTaskCompleted();
}
