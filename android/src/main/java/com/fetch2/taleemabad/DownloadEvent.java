package com.fetch2.taleemabad;

import java.util.Arrays;
import java.util.List;

public class DownloadEvent {

    public static final String ON_ADDED = "onAdded";
    public static final String ON_CANCELLED = "onCancelled";
    public static final String ON_COMPLETED = "onCompleted";
    public static final String ON_DELETED = "onDeleted";
    public static final String ON_DOWNLOAD_BLOCK_UPDATED = "onDownloadBlockUpdated";
    public static final String ON_ERROR = "onError";
    public static final String ON_PAUSED = "onPaused";
    public static final String ON_PROGRESS = "onProgress";
    public static final String ON_QUEUED = "onQueued";
    public static final String ON_REMOVED = "onRemoved";
    public static final String ON_RESUMED = "onResumed";
    public static final String ON_STARTED = "onStarted";
    public static final String ON_WAITING_NETWORK = "onWaitingNetwork";

    // Create a list of all function names
    public static final List<String> ALL_EVENTS = Arrays.asList(
        ON_ADDED,
        ON_CANCELLED,
        ON_COMPLETED,
        ON_DELETED,
        ON_DOWNLOAD_BLOCK_UPDATED,
        ON_ERROR,
        ON_PAUSED,
        ON_PROGRESS,
        ON_QUEUED,
        ON_REMOVED,
        ON_RESUMED,
        ON_STARTED,
        ON_WAITING_NETWORK
    );
}
