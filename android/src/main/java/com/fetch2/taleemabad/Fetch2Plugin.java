package com.fetch2.taleemabad;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.Downloader;
import com.tonyodev.fetch2okhttp.OkHttpDownloader;

import java.util.ArrayList;
import java.util.List;

public class Fetch2Plugin {
    private static final String TAG = "Fetch2";
    private static final String namespace = "Fetch2";
    private final int groupId = namespace.hashCode();
    private final Context mContext;
    private final Fetch fetch;

    public Fetch2Plugin(Context context) {
        Log.d(TAG, "Fetch2Plugin: ");
        mContext = context;
        fetch =
                Fetch.Impl.getInstance(
                        new FetchConfiguration.Builder(context)
                                .setDownloadConcurrentLimit(3)
                                .setHttpDownloader(new OkHttpDownloader(Downloader.FileDownloaderType.PARALLEL))
                                .setNamespace(namespace)
                                .setGlobalNetworkType(NetworkType.ALL)
                                .enableAutoStart(true)
                                .enableRetryOnNetworkGain(true)
                                .enableFileExistChecks(true)
                                .setAutoRetryMaxAttempts(3)
                                .enableLogging(true)
                                .build()
                );
    }

    public void resumeDownload() {
        fetch.getDownloadsInGroup(groupId, downloads -> {
            for (Download download : downloads) {
                switch (download.getStatus()) {
                    case COMPLETED: {
                        Log.d(TAG, "COMPLETED:: " + download.getId() + " => " + download.getStatus());
                    }
                    case PAUSED: {
                        Log.d(TAG, "PAUSED:: " + download.getId() + " => " + download.getStatus());
                        fetch.resume(download.getId());
                    }
                    case FAILED: {
                        Log.d(TAG, "FAILED:: " + download.getId() + " => " + download.getStatus());
                        fetch.retry(download.getId());
                    }
                    case CANCELLED: {
                        Log.d(TAG, "CANCELLED:: " + download.getId() + " => " + download.getStatus());
                        fetch.resume(download.getId());
                    }
                    default: {
                        Log.d(TAG, "STATUS:: " + download.getId() + " => " + download.getStatus());
                    }
                    break;
                }
            }
        });
    }

    public void initFetch(List<String> urls, FetchListener fetchListener) {
        fetch.addListener(fetchListener);
        fetch.enqueue(
                getFetchRequests(urls),
                updatedRequests -> {
                    Log.d(TAG, "enqueue: " + updatedRequests);
                }
        );
    }

    private String getFilePath(String url, Context context) {
        String fileName = getNameFromUrl(url);
        String dir = getSaveDir(context);
        return dir + "/" + fileName;
    }

    private String getNameFromUrl(String url) {
        return Uri.parse(url).getLastPathSegment();
    }

    private String getSaveDir(Context context) {
        try {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        } catch (Exception e) {
            return String.valueOf(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        }
    }

    private List<Request> getFetchRequests(List<String> urls) {
        Log.d(TAG, "initFetch: " + urls.toString());
        ArrayList<Request> requests = new ArrayList<>();
        for (String url : urls) {
            String fileName = getFilePath(url, mContext);
            Request request = new Request(url, fileName);
            request.setGroupId(groupId);
            request.setPriority(Priority.HIGH);
            request.setTag(fileName);
            request.setNetworkType(NetworkType.ALL);
            requests.add(request);
        }
        return requests;
    }
}
