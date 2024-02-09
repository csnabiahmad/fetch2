package com.fetch2.taleemabad;

import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.google.gson.Gson;
import com.permissionx.guolindev.PermissionX;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2core.DownloadBlock;
import java.io.File;
import java.util.List;

@CapacitorPlugin(
        name = "Fetch2Plugin",
        permissions = {
                @Permission(
                        strings = {
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                                Manifest.permission.POST_NOTIFICATIONS,
                        },
                        alias = "storage"
                )
        }
)
public class Fetch2PluginPlugin extends Plugin implements FetchListener {

    private Fetch2Plugin fetch2Plugin;
    private static final String TAG = "Fetch2";


    @RequiresApi(api = Build.VERSION_CODES.R)
    @PluginMethod
    public void startFetch(PluginCall call) {
        this.getActivity()
                .getMainExecutor()
                .execute(
                        () -> {
                            fetch2Plugin = new Fetch2Plugin(this.getActivity());
                            saveCall(call);
                            checkStoragePermissions();
                        }
                );
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    @PluginMethod
    public void startVideo(PluginCall call) {
        JSArray url = call.getArray("url");
        JSObject ret = new JSObject();
        ret.put("value", url);
        try {
            String videoPath = url.get(0).toString();
            if (!new File(videoPath).exists()) {
                ret.put("error", "File not found");
                call.reject("File not found");
            } else {
                Intent intent = new Intent(this.getActivity(), VideoPlayerActivity.class);
                intent.putExtra("videoPath", videoPath);
                Bundle bundle = new Bundle();
                bundle.putString("videoPath", videoPath);
                startActivity(this.getActivity(), intent, bundle);
                call.resolve(ret);
            }

        } catch (Exception e) {
            ret.put("error", e.getMessage());
            call.reject(e.getMessage());
        }

    }

    private void loadFetch() {
        PluginCall savedCall = getSavedCall();
        JSArray url = savedCall.getArray("url");
        JSObject ret = new JSObject();
        ret.put("value", url);
        try {
            fetch2Plugin.initFetch(url.toList(), this);
        } catch (Exception e) {
            ret.put("error", e.getMessage());
        }
        savedCall.resolve(ret);
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    private void checkStoragePermissions() {
        PermissionX
                .init(this.getActivity())
                .permissions(
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                                ? new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}
                                : new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,}
                )
                .explainReasonBeforeRequest()
                .onExplainRequestReason(
                        (scope, deniedList) ->
                                scope.showRequestReasonDialog(deniedList, "Core fundamental are based on these permissions", "OK", "Cancel")
                )
                .onForwardToSettings(
                        (scope, deniedList) ->
                                scope.showForwardToSettingsDialog(
                                        deniedList,
                                        "You need to allow necessary permissions in Settings manually",
                                        "OK",
                                        "Cancel"
                                )
                )
                .request(
                        (allGranted, grantList, deniedList) -> {
                            if (allGranted) {
                                Toast.makeText(this.getActivity(), "All permissions are granted", Toast.LENGTH_LONG).show();
                                loadFetch();
                            } else {
                                Toast.makeText(this.getActivity(), "These permissions are denied: " + deniedList, Toast.LENGTH_LONG).show();
                            }
                        }
                );
    }

    @Override
    public void onAdded(@NonNull Download download) {
        Log.d(TAG, DownloadEvent.ON_ADDED + " : " + download);
        notifyListeners(DownloadEvent.ON_ADDED, new JSObject().put("download", new Gson().toJson(download)));
    }

    @Override
    public void onCancelled(@NonNull Download download) {
        Log.d(TAG, DownloadEvent.ON_CANCELLED + download);
        notifyListeners(DownloadEvent.ON_CANCELLED, new JSObject().put("download", new Gson().toJson(download)));
    }

    @Override
    public void onCompleted(@NonNull Download download) {
        Log.d(TAG, DownloadEvent.ON_COMPLETED + download);
        notifyListeners(DownloadEvent.ON_COMPLETED, new JSObject().put("download", new Gson().toJson(download)));
    }

    @Override
    public void onDeleted(@NonNull Download download) {
        Log.d(TAG, DownloadEvent.ON_DELETED + download);
        notifyListeners(DownloadEvent.ON_DELETED, new JSObject().put("download", new Gson().toJson(download)));
    }

    @Override
    public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i) {
        Log.d(TAG, DownloadEvent.ON_DOWNLOAD_BLOCK_UPDATED + " : " + download);
        notifyListeners(DownloadEvent.ON_DOWNLOAD_BLOCK_UPDATED, new JSObject().put("download", new Gson().toJson(download)));
    }

    @Override
    public void onError(@NonNull Download download, @NonNull Error error, @Nullable Throwable throwable) {
        Log.d(TAG, DownloadEvent.ON_ERROR + " : " + download);
        notifyListeners(DownloadEvent.ON_ERROR, new JSObject().put("download", new Gson().toJson(download)));
    }

    @Override
    public void onPaused(@NonNull Download download) {
        Log.d(TAG, DownloadEvent.ON_PAUSED + " : " + download);
        notifyListeners(DownloadEvent.ON_PAUSED, new JSObject().put("download", new Gson().toJson(download)));
    }

    @Override
    public void onProgress(@NonNull Download download, long l, long l1) {
        Log.d(TAG, DownloadEvent.ON_PROGRESS + " : " + download);
        notifyListeners(DownloadEvent.ON_PROGRESS, new JSObject().put("download", new Gson().toJson(download)));
    }

    @Override
    public void onQueued(@NonNull Download download, boolean b) {
        Log.d(TAG, DownloadEvent.ON_QUEUED + " : " + download);
        notifyListeners(DownloadEvent.ON_QUEUED, new JSObject().put("download", new Gson().toJson(download)));
    }

    @Override
    public void onRemoved(@NonNull Download download) {
        Log.d(TAG, DownloadEvent.ON_REMOVED + " : " + download);
        notifyListeners(DownloadEvent.ON_REMOVED, new JSObject().put("download", new Gson().toJson(download)));
    }

    @Override
    public void onResumed(@NonNull Download download) {
        Log.d(TAG, DownloadEvent.ON_RESUMED + " : " + download);
        notifyListeners(DownloadEvent.ON_RESUMED, new JSObject().put("download", new Gson().toJson(download)));
    }

    @Override
    public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i) {
        Log.d(TAG, DownloadEvent.ON_STARTED + " : " + download);
        notifyListeners(DownloadEvent.ON_STARTED, new JSObject().put("download", new Gson().toJson(download)));
    }

    @Override
    public void onWaitingNetwork(@NonNull Download download) {
        Log.d(TAG, DownloadEvent.ON_WAITING_NETWORK + download);
        notifyListeners(DownloadEvent.ON_WAITING_NETWORK, new JSObject().put("download", new Gson().toJson(download)));
    }
}
