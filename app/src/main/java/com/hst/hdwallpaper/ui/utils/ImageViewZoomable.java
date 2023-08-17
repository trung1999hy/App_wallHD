package com.hst.hdwallpaper.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;

public class ImageViewZoomable extends AppCompatImageView {
    static final int CLICK = 3;
    static final int DRAG = 1;
    static final int NONE = 0;
    static final int ZOOM = 2;
    float bmHeight;
    float bmWidth;
    float bottom;
    Context context;
    float height;
    PointF last = new PointF();
    float[] m;
    ScaleGestureDetector mScaleDetector;
    Matrix matrix = new Matrix();
    float maxScale = 4.0f;
    float minScale = 1.0f;
    int mode = 0;
    float origHeight;
    float origWidth;
    float redundantXSpace;
    float redundantYSpace;
    float right;
    float saveScale = 1.0f;
    PointF start = new PointF();
    float width;

    private class ScaleListener extends SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            ImageViewZoomable.this.mode = 2;
            return true;
        }

        public boolean onScale(ScaleGestureDetector detector) {
            float mScaleFactor = detector.getScaleFactor();
            float origScale = ImageViewZoomable.this.saveScale;
            ImageViewZoomable.this.saveScale *= mScaleFactor;
            if (ImageViewZoomable.this.saveScale > ImageViewZoomable.this.maxScale) {
                ImageViewZoomable imageViewZoomable = ImageViewZoomable.this;
                imageViewZoomable.saveScale = imageViewZoomable.maxScale;
                mScaleFactor = ImageViewZoomable.this.maxScale / origScale;
            } else if (ImageViewZoomable.this.saveScale < ImageViewZoomable.this.minScale) {
                ImageViewZoomable imageViewZoomable2 = ImageViewZoomable.this;
                imageViewZoomable2.saveScale = imageViewZoomable2.minScale;
                mScaleFactor = ImageViewZoomable.this.minScale / origScale;
            }
            ImageViewZoomable imageViewZoomable3 = ImageViewZoomable.this;
            imageViewZoomable3.right = ((imageViewZoomable3.width * ImageViewZoomable.this.saveScale) - ImageViewZoomable.this.width) - ((ImageViewZoomable.this.redundantXSpace * 2.0f) * ImageViewZoomable.this.saveScale);
            ImageViewZoomable imageViewZoomable4 = ImageViewZoomable.this;
            imageViewZoomable4.bottom = ((imageViewZoomable4.height * ImageViewZoomable.this.saveScale) - ImageViewZoomable.this.height) - ((ImageViewZoomable.this.redundantYSpace * 2.0f) * ImageViewZoomable.this.saveScale);
            if (ImageViewZoomable.this.origWidth * ImageViewZoomable.this.saveScale <= ImageViewZoomable.this.width || ImageViewZoomable.this.origHeight * ImageViewZoomable.this.saveScale <= ImageViewZoomable.this.height) {
                ImageViewZoomable.this.matrix.postScale(mScaleFactor, mScaleFactor, ImageViewZoomable.this.width / 2.0f, ImageViewZoomable.this.height / 2.0f);
                if (mScaleFactor < 1.0f) {
                    ImageViewZoomable.this.matrix.getValues(ImageViewZoomable.this.m);
                    float x = ImageViewZoomable.this.m[2];
                    float y = ImageViewZoomable.this.m[5];
                    if (mScaleFactor < 1.0f) {
                        if (((float) Math.round(ImageViewZoomable.this.origWidth * ImageViewZoomable.this.saveScale)) < ImageViewZoomable.this.width) {
                            if (y < (-ImageViewZoomable.this.bottom)) {
                                ImageViewZoomable.this.matrix.postTranslate(0.0f, -(ImageViewZoomable.this.bottom + y));
                            } else if (y > 0.0f) {
                                ImageViewZoomable.this.matrix.postTranslate(0.0f, -y);
                            }
                        } else if (x < (-ImageViewZoomable.this.right)) {
                            ImageViewZoomable.this.matrix.postTranslate(-(ImageViewZoomable.this.right + x), 0.0f);
                        } else if (x > 0.0f) {
                            ImageViewZoomable.this.matrix.postTranslate(-x, 0.0f);
                        }
                    }
                }
            } else {
                ImageViewZoomable.this.matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
                ImageViewZoomable.this.matrix.getValues(ImageViewZoomable.this.m);
                float x2 = ImageViewZoomable.this.m[2];
                float y2 = ImageViewZoomable.this.m[5];
                if (mScaleFactor < 1.0f) {
                    if (x2 < (-ImageViewZoomable.this.right)) {
                        ImageViewZoomable.this.matrix.postTranslate(-(ImageViewZoomable.this.right + x2), 0.0f);
                    } else if (x2 > 0.0f) {
                        ImageViewZoomable.this.matrix.postTranslate(-x2, 0.0f);
                    }
                    if (y2 < (-ImageViewZoomable.this.bottom)) {
                        ImageViewZoomable.this.matrix.postTranslate(0.0f, -(ImageViewZoomable.this.bottom + y2));
                    } else if (y2 > 0.0f) {
                        ImageViewZoomable.this.matrix.postTranslate(0.0f, -y2);
                    }
                }
            }
            return true;
        }
    }

    public ImageViewZoomable(Context context2, AttributeSet attr) {
        super(context2, attr);
        super.setClickable(true);
        this.context = context2;
        this.mScaleDetector = new ScaleGestureDetector(context2, new ScaleListener());
        this.matrix.setTranslate(1.0f, 1.0f);
        this.m = new float[9];
        setImageMatrix(this.matrix);
        setScaleType(ScaleType.MATRIX);
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                ImageViewZoomable.this.mScaleDetector.onTouchEvent(event);
                ImageViewZoomable.this.matrix.getValues(ImageViewZoomable.this.m);
                float x = ImageViewZoomable.this.m[2];
                float y = ImageViewZoomable.this.m[5];
                PointF curr = new PointF(event.getX(), event.getY());
                int action = event.getAction();
                if (action == 0) {
                    ImageViewZoomable.this.last.set(event.getX(), event.getY());
                    ImageViewZoomable.this.start.set(ImageViewZoomable.this.last);
                    ImageViewZoomable.this.mode = 1;
                } else if (action == 1) {
                    ImageViewZoomable.this.mode = 0;
                    int yDiff = (int) Math.abs(curr.y - ImageViewZoomable.this.start.y);
                    if (((int) Math.abs(curr.x - ImageViewZoomable.this.start.x)) < 3 && yDiff < 3) {
                        ImageViewZoomable.this.performClick();
                    }
                } else if (action != 2) {
                    if (action == 5) {
                        ImageViewZoomable.this.last.set(event.getX(), event.getY());
                        ImageViewZoomable.this.start.set(ImageViewZoomable.this.last);
                        ImageViewZoomable.this.mode = 2;
                    } else if (action == 6) {
                        ImageViewZoomable.this.mode = 0;
                    }
                } else if (ImageViewZoomable.this.mode == 2 || (ImageViewZoomable.this.mode == 1 && ImageViewZoomable.this.saveScale > ImageViewZoomable.this.minScale)) {
                    float deltaX = curr.x - ImageViewZoomable.this.last.x;
                    float deltaY = curr.y - ImageViewZoomable.this.last.y;
                    float scaleHeight = (float) Math.round(ImageViewZoomable.this.origHeight * ImageViewZoomable.this.saveScale);
                    if (((float) Math.round(ImageViewZoomable.this.origWidth * ImageViewZoomable.this.saveScale)) < ImageViewZoomable.this.width) {
                        deltaX = 0.0f;
                        if (y + deltaY > 0.0f) {
                            deltaY = -y;
                        } else if (y + deltaY < (-ImageViewZoomable.this.bottom)) {
                            deltaY = -(ImageViewZoomable.this.bottom + y);
                        }
                    } else if (scaleHeight < ImageViewZoomable.this.height) {
                        deltaY = 0.0f;
                        if (x + deltaX > 0.0f) {
                            deltaX = -x;
                        } else if (x + deltaX < (-ImageViewZoomable.this.right)) {
                            deltaX = -(ImageViewZoomable.this.right + x);
                        }
                    } else {
                        if (x + deltaX > 0.0f) {
                            deltaX = -x;
                        } else if (x + deltaX < (-ImageViewZoomable.this.right)) {
                            deltaX = -(ImageViewZoomable.this.right + x);
                        }
                        if (y + deltaY > 0.0f) {
                            deltaY = -y;
                        } else if (y + deltaY < (-ImageViewZoomable.this.bottom)) {
                            deltaY = -(ImageViewZoomable.this.bottom + y);
                        }
                    }
                    ImageViewZoomable.this.matrix.postTranslate(deltaX, deltaY);
                    ImageViewZoomable.this.last.set(curr.x, curr.y);
                }
                ImageViewZoomable imageViewZoomable = ImageViewZoomable.this;
                imageViewZoomable.setImageMatrix(imageViewZoomable.matrix);
                ImageViewZoomable.this.invalidate();
                return true;
            }
        });
    }

    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        this.bmWidth = (float) bm.getWidth();
        this.bmHeight = (float) bm.getHeight();
    }

    public void setMaxZoom(float x) {
        this.maxScale = x;
    }


    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = (float) MeasureSpec.getSize(widthMeasureSpec);
        this.height = (float) MeasureSpec.getSize(heightMeasureSpec);
        float scale = Math.min(this.width / this.bmWidth, this.height / this.bmHeight);
        this.matrix.setScale(scale, scale);
        setImageMatrix(this.matrix);
        this.saveScale = 1.0f;
        this.redundantYSpace = this.height - (this.bmHeight * scale);
        this.redundantXSpace = this.width - (this.bmWidth * scale);
        this.redundantYSpace /= 2.0f;
        this.redundantXSpace /= 2.0f;
        this.matrix.postTranslate(this.redundantXSpace, this.redundantYSpace);
        float f = this.width;
        float f2 = this.redundantXSpace;
        this.origWidth = f - (f2 * 2.0f);
        float f3 = this.height;
        float f4 = this.redundantYSpace;
        this.origHeight = f3 - (f4 * 2.0f);
        float f5 = this.saveScale;
        this.right = ((f * f5) - f) - ((f2 * 2.0f) * f5);
        this.bottom = ((f3 * f5) - f3) - ((f4 * 2.0f) * f5);
        setImageMatrix(this.matrix);
    }
}
