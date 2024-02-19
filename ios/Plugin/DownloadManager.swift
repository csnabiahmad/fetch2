//
//  DownloadManager.swift
//  App
//
//  Created by Nabi Ahmad on 16/02/2024.
//

import Foundation

public class DownloadManager: NSObject, URLSessionDownloadDelegate {
    var downloadTasks: [URLSessionDownloadTask] = []
    var backgroundSession: URLSession!
    var downloadQueue: OperationQueue!
    
    override init() {
        super.init()
        
        let backgroundSessionConfiguration = URLSessionConfiguration.background(withIdentifier: "backgroundSession")
        backgroundSession = URLSession(configuration: backgroundSessionConfiguration, delegate: self, delegateQueue: OperationQueue.main)
        
        downloadQueue = OperationQueue()
        downloadQueue.maxConcurrentOperationCount = 2
    }
    
    func startDownloads(urls: [URL]) {
        for url in urls {
            downloadQueue.addOperation {
                let downloadTask = self.backgroundSession.downloadTask(with: url)
                downloadTask.resume()
                self.downloadTasks.append(downloadTask)
            }
        }
    }
    
    // MARK: URLSessionDownloadDelegate methods
    
    public func urlSession(_ session: URLSession, downloadTask: URLSessionDownloadTask, didFinishDownloadingTo location: URL) {
        print("Download finished: \(location)")
        // Here you can move the downloaded file from `location` to your desired directory
    }
    
    public func urlSession(_ session: URLSession, task: URLSessionTask, didCompleteWithError error: Error?) {
        if error != nil {
            print("Download failed with error: \(String(describing: error?.localizedDescription))")
        }
    }
    
    public func urlSession(_ session: URLSession, downloadTask: URLSessionDownloadTask, didWriteData bytesWritten: Int64, totalBytesWritten: Int64, totalBytesExpectedToWrite: Int64) {
        if totalBytesExpectedToWrite > 0 {
            let progress = Double(totalBytesWritten) / Double(totalBytesExpectedToWrite)
            print("Progress \(downloadTask): \(progress)")
        }
    }
}
