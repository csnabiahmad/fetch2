//
//  DownloadManager.swift
//  App
//
//  Created by Nabi Ahmad on 16/02/2024.
//

import Foundation




public class DownloadManager: NSObject, URLSessionDownloadDelegate  {
    
    var downloadTasks: [URLSessionDownloadTask] = []
    var backgroundSession: URLSession!
    var downloadQueue: OperationQueue!
    var downloadDelegate : (DownloadDelegate)?
    var downloadList = [Download]()

    override init() {
        super.init()
        
        let backgroundSessionConfiguration = URLSessionConfiguration.background(withIdentifier: "backgroundSession")
        backgroundSession = URLSession(configuration: backgroundSessionConfiguration, delegate: self, delegateQueue: OperationQueue.main)
        downloadQueue = OperationQueue()
        downloadQueue.maxConcurrentOperationCount = 2
        
     }
    
    func startDownloads(urls: [URL]) {
        for url in urls {
            addDownload(download: Download(file: url.absoluteString,url: url.absoluteString))

            downloadQueue.addOperation {
                let downloadTask = self.backgroundSession.downloadTask(with: url)
                downloadTask.resume()
                self.downloadTasks.append(downloadTask)
            }
        }
    }
    
    private func createDestinationURL(url: URL) -> String {
        let documentsDirectory = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)[0]
        let destinationURL = documentsDirectory.appendingPathComponent(url.lastPathComponent)
        return "\(destinationURL)"
    }
    
    // MARK: URLSessionDownloadDelegate methods
    
    public func urlSession(_ session: URLSession, downloadTask: URLSessionDownloadTask, didFinishDownloadingTo location: URL) {
        
        guard let originalURL = downloadTask.originalRequest?.url?.absoluteString else { return }
        let download = self.downloadList.first(where: { $0.url == originalURL })
        downloadDelegate?.didFinish(download)
//        print("Download finished: \(download)")
    }
    
    public func urlSession(_ session: URLSession, task: URLSessionTask, didCompleteWithError error: Error?) {
        if error != nil {
            guard let originalURL = task.originalRequest?.url?.absoluteString else { return }
            if var download = self.downloadList.first(where: { $0.url == originalURL }) {
                let error = error?.localizedDescription
                download.error = error ?? ""
                downloadDelegate?.didError(download)
            }
//            print("Download failed with error: \(String(describing: error?.localizedDescription))")
        }
    }
    
    public func urlSession(_ session: URLSession, downloadTask: URLSessionDownloadTask, didWriteData bytesWritten: Int64, totalBytesWritten: Int64, totalBytesExpectedToWrite: Int64) {

        
        guard let originalURL = downloadTask.originalRequest?.url?.absoluteString else { return }
        updateDownloadProgress(with: originalURL , progress:  (Float(totalBytesWritten) / Float(totalBytesExpectedToWrite)) )
//        for dd in downloadList{
//            print("List Down:: \(dd.url)")
//        }
//        print("DownloadList Size:: \(downloadList.count)")
//        if let foundDownload = downloadList.firstIndex(where: { $0.url ==  originalURL}) {
//            // Object found
//            print("Found download: \(foundDownload)")
//            print("Found originalURL: \(originalURL)")
//        } else {
//            // Object not found
//            print("Download not found")
//            print("Found originalURL: \(originalURL)")
//        }
        
//        guard let originalURL = downloadTask.currentRequest?.url else { return }
//        print("Original URL => \(originalURL)")
//        if var download = downloads.first(where: { $0.url == "\(originalURL)" }) {
//            let progress = Float(totalBytesWritten) / Float(totalBytesExpectedToWrite)
//                download.progress = progress
//                downloadDelegate?.didProgress(download)
//        }
        
//        if totalBytesExpectedToWrite > 0 {
//            let progress = Double(totalBytesWritten) / Double(totalBytesExpectedToWrite)
//            print("Progress: \(progress) => URL: \(downloadTask.currentRequest?.url)")
//        }
    }
    
    
    // Function to add a new download
    func addDownload(download: Download) {
        print("DonwloadList Before:: Size")
        print(self.downloadList.count)
        downloadList.append(download)
        print("DonwloadList After:: Size")
        print(self.downloadList.count)
        saveDownloads()
    }

    // Function to save the downloads to UserDefaults
    func saveDownloads() {
        let encoder = JSONEncoder()
        if let encoded = try? encoder.encode(self.downloadList) {
            let defaults = UserDefaults.standard
            defaults.set(encoded, forKey: "SavedDownloads")
        }
    }
    
    func updateDownload(with url: String, newDownload: Download) {
        if let index = self.downloadList.firstIndex(where: { $0.url == url }) {
            self.downloadList[index] = newDownload
            saveDownloads()
        }
    }
    
    
    func updateDownloadProgress(with url: String, progress: Float) {
        print("DonwloadList :: Size")
        print(self.downloadList.count)
        if let index = self.downloadList.firstIndex(where: { $0.url == url }) {
             saveDownloads()
        }
    }

    // Function to load the downloads from UserDefaults
    func loadDownloads() {
        let defaults = UserDefaults.standard
        if let savedDownloads = defaults.object(forKey: "SavedDownloads") as? Data {
            let decoder = JSONDecoder()
            if let loadedDownloads = try? decoder.decode([Download].self, from: savedDownloads) {
                self.downloadList = loadedDownloads
            }
        }
    }
}
