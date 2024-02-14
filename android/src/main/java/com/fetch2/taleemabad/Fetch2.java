package com.fetch2.taleemabad;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2.exception.FetchException;
import com.tonyodev.fetch2core.Downloader;
import com.tonyodev.fetch2okhttp.OkHttpDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Fetch2{

    private static final String TAG = "Fetch2";
    private static final String namespace = "Fetch2";
    private final int groupId = namespace.hashCode();
    private static Context mContext;
    private static Fetch fetch;
    private static Fetch2 instance;
    private static FetchListener mFetchListener;

    public static Fetch2 getInstance(@NonNull Context context,FetchListener fetchListener) {
        // If the instance is null, create a new instance
        if (instance == null) {
            instance = new Fetch2(context);
            mContext = context;
            fetch = instance.init();
            mFetchListener = fetchListener;
        }
        // Return the single instance
        return instance;
    }

    public Fetch2(@NonNull Context context) {
        mContext = context;
    }

    public Fetch getFetch() {
        return fetch;
    }

    public String getNamespace() {return namespace;}

    public int getGroupID() {
        return groupId;
    }

    private Fetch init() {
        return Fetch.Impl.getInstance(
                new FetchConfiguration.Builder(mContext)
                        .setDownloadConcurrentLimit(2)
                        .setHttpDownloader(new OkHttpDownloader(Downloader.FileDownloaderType.PARALLEL))
                        .setNamespace(namespace)
                        .setGlobalNetworkType(NetworkType.ALL)
                        .enableAutoStart(true)
                        .enableRetryOnNetworkGain(true)
                        .enableFileExistChecks(true)
                        .enableLogging(true)
                        .build()
        );
    }
    private void startDownloading(List<String> urls, FetchListener fetchListener) {
        fetch.addListener(fetchListener);
        fetch.enqueue(
                getFetchRequests(urls),
                updatedRequests -> {
                    Log.d(TAG, "enqueue: " + updatedRequests);
                }
        );
    }
    private List<Request> getFetchRequests(List<String> urls) {
        Log.d(TAG, "initFetch: " + urls.toString());
        ArrayList<Request> requests = new ArrayList<>();
        for (String url : urls) {
            String fileName = Utils.getFilePath(url, mContext);
            Request request = new Request(url, fileName);
            request.setGroupId(groupId);
            request.setPriority(Priority.HIGH);
            request.setTag(fileName);
            request.setNetworkType(NetworkType.ALL);
            requests.add(request);
        }
        return requests;
    }
    public void initDownloading(PluginCall call) {
        JSArray url = call.getArray("url");
        JSObject ret = new JSObject();
        ret.put("value", url);
        try {
            startDownloading(url.toList(), mFetchListener);
        } catch (Exception e) {
            ret.put("error", e.getMessage());
        }
        call.resolve(ret);
    }


    public void resumeDownloads() {
        if (fetch == null) {
            fetch = init();
        }
        fetch.getFetchGroup(groupId, fetchGroup -> {
            try {
                Log.d(TAG, "FetchGroup: " + fetchGroup.getId());
                List<Download> downloads = fetchGroup.getDownloads();
                for (Download download : downloads) {
                    switch (download.getStatus()) {
                        case COMPLETED: {
                            File file = new File(download.getFile());
                            if (!file.exists()) {
                                fetch.retry(download.getId());
                                Log.d(TAG, "COMPLETED:: Retry because file doesn't exists => " + download.getFile());
                            }
                            else
                                Log.d(TAG, "COMPLETED:: " + download.getId() + " => " + download.getStatus());
                            break;
                        }
                        case PAUSED: {
                            Log.d(TAG, "PAUSED:: " + download.getId() + " => " + download.getStatus());
                            fetch.resume(download.getId());
                            break;
                        }
                        case FAILED: {
                            Log.d(TAG, "FAILED:: " + download.getId() + " => " + download.getStatus());
                            fetch.retry(download.getId());
                            break;
                        }
                        case CANCELLED: {
                            Log.d(TAG, "CANCELLED:: " + download.getId() + " => " + download.getStatus());
                            fetch.resume(download.getId());
                            break;
                        }
                        case QUEUED: {
                            Log.d(TAG, "QUEUED:: " + download.getId() + " => " + download.getStatus());
                            fetch.resume(download.getId());
                            break;
                        }
                        default: {
                            Log.d(TAG, "STATUS:: " + download.getId() + " => " + download.getStatus());
                            break;
                        }
                    }
                }
            }
            catch (FetchException e) {
                Log.d(TAG, "FetchException: " + e.getMessage());
            }
        });
    }
}