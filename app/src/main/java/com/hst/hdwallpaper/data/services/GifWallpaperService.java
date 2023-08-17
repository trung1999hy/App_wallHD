package com.hst.hdwallpaper.data.services;

import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import com.hst.hdwallpaper.ui.utils.Pref;

import java.io.*;

public class GifWallpaperService extends WallpaperService {

    private class GIFWallpaperEngine extends Engine {
        private final Runnable drawGIF;
        private final Handler handler;
        private SurfaceHolder holder;
        private final Movie movie;
        private float scaleRatio;
        private boolean visible;
        private float x;
        private float y;

        private GIFWallpaperEngine(Movie movie2) {
            super();
            this.drawGIF = new Runnable() {
                public void run() {
                    GIFWallpaperEngine.this.draw();
                }
            };
            this.movie = movie2;
            this.handler = new Handler();
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.holder = surfaceHolder;
        }

        public void onDestroy() {
            super.onDestroy();
            this.handler.removeCallbacks(this.drawGIF);
        }

        public void onSurfaceCreated(SurfaceHolder holder2) {
            super.onSurfaceCreated(holder2);
        }

        public void onSurfaceChanged(SurfaceHolder holder2, int format, int width, int height) {
            super.onSurfaceChanged(holder2, format, width, height);
            this.scaleRatio = Math.max(((float) width) / ((float) this.movie.width()), ((float) height) / ((float) this.movie.height()));
            this.x = (((float) width) - (((float) this.movie.width()) * this.scaleRatio)) / 2.0f;
            float f = (float) height;
            float height2 = (float) this.movie.height();
            float f2 = this.scaleRatio;
            this.y = (f - (height2 * f2)) / 2.0f;
            this.x /= f2;
            this.y /= f2;
        }

        public void onSurfaceDestroyed(SurfaceHolder holder2) {
            super.onSurfaceDestroyed(holder2);
        }


        public void draw() {
            if (this.visible) {
                Canvas canvas = this.holder.lockCanvas();
                canvas.save();
                float f = this.scaleRatio;
                canvas.scale(f, f);
                this.movie.draw(canvas, this.x, this.y);
                canvas.restore();
                this.holder.unlockCanvasAndPost(canvas);
                this.movie.setTime((int) (System.currentTimeMillis() % ((long) this.movie.duration())));
                this.handler.removeCallbacks(this.drawGIF);
                this.handler.postDelayed(this.drawGIF, 0);
            }
        }

        public void onVisibilityChanged(boolean visible2) {
            this.visible = visible2;
            if (visible2) {
                this.handler.post(this.drawGIF);
            } else {
                this.handler.removeCallbacks(this.drawGIF);
            }
        }
    }

    public Engine onCreateEngine() {
        try {
            InputStream mInputStream = new BufferedInputStream(new FileInputStream(new File(Pref.getSharedPref(getApplicationContext()).read("set_wallpaper"))), 16384);
            mInputStream.mark(16384);
            return new GIFWallpaperEngine(Movie.decodeStream(mInputStream));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
