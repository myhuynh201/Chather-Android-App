package edu.uw.tcss450.io;


import android.content.Context;
import android.graphics.Bitmap;

import androidx.collection.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * A class for executions of all requests.
 * @author Charles Bryan
 */
public class RequestQueueSingleton {

    /*
    An instance of the request queue singleton.
     */
    private static RequestQueueSingleton instance;

    /*
    Context for the application.
     */
    private static Context context;

    /*
    A queue for all the requests.
     */
    private RequestQueue mRequestQueue;

    /*
    An image loader.
     */
    private ImageLoader mImageLoader;

    /**
     * A request quest based on the context of the application.
     * @param context The context.
     */
    private RequestQueueSingleton(Context context) {
        RequestQueueSingleton.context = context;
        mRequestQueue = getmRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized RequestQueueSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new RequestQueueSingleton(context);
        }
        return instance;
    }

    /**
     * Get information from the request queue.
     * @return
     */
    public RequestQueue getmRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Add to the request queue.
     * @param req The request
     * @param <T> The array.
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getmRequestQueue().add(req);
    }

    /**
     * Get method for the image loader
     * @return The image loader.
     */
    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }
}
