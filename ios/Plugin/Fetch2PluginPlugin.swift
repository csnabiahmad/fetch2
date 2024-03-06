import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(Fetch2PluginPlugin)
public class Fetch2PluginPlugin: CAPPlugin /*, URLSessionDownloadDelegate , DownloadDelegate*/ {
//    public func didFinish(_ donwloads: Download?) {
//        print("Fetch2PluginPlugin => Donwload:: \(String(describing: donwloads))")
//     }
//    
//    public func didError(_ donwloads: Download?) {
//        print("Fetch2PluginPlugin => Donwload:: \(String(describing: donwloads))")
//    }
//    
//    public func didProgress(_ donwloads: Download?) {
//        print("Fetch2PluginPlugin => Donwload:: \(String(describing: donwloads))")
//       
//    }
    
    private let implementation = Fetch2Plugin()
    private var downloadManager : DownloadManager!

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
   
    @objc func fetchDownloadList(_ call: CAPPluginCall) {
        guard let downloads = self.implementation.fetchDownloads() else {
            call.reject("No downloads found!")
            return
        }
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        do {
            let jsonData = try encoder.encode(downloads)
            if let jsonString = String(data: jsonData, encoding: .utf8) {
                CAPLog.print(jsonString)
                call.resolve(["download": jsonString])
            }
        } catch {
            CAPLog.print("Error encoding Download: \(error)")
            call.reject("No downloads found!")
        }
        
    }
    
    @objc func startFetch(_ call: CAPPluginCall) {
//        guard let url = call.getString("url") else { return call.reject("Must provide a URL") }
        guard let urls = call.getArray("url", String.self) else {
            call.reject("No url provided")
            return
        }
        for url in urls{
            let progressEmitter: Fetch2Plugin.ProgressEmitter = { bytes, contentLength in
                let progress = (Double(bytes) / Double(contentLength))*100

                CAPLog.print("Progress",String(describing: progress))
                CAPLog.print("URL",String(describing: url))
                
                if (Int(progress) % 10) == 0 {
                    if let index = self.implementation.downloadList?.firstIndex(where: { $0.url == url }) {
                        if var newDownload = self.implementation.downloadList?[index]{
//                            newDownload.progress = progress
                            newDownload.downloaded = Int(bytes)
                            newDownload.total = Int(contentLength)
                            self.implementation.downloadList?[index] = newDownload
                            self.implementation.saveDownloads()
                            
                            let encoder = JSONEncoder()
                            encoder.outputFormatting = .prettyPrinted
                            do {
                                let jsonData = try encoder.encode(newDownload)
                                if let jsonString = String(data: jsonData, encoding: .utf8) {
                                    CAPLog.print(jsonString)
                                    if(Int(bytes) == Int(contentLength)){
                                        self.notifyListeners("onCompleted", data:["download": jsonString])
                                    }else{
                                        self.notifyListeners("onProgress", data:["download": jsonString])
                                    }
                                }
                            } catch {
                                CAPLog.print("Error encoding Download: \(error)")
                            }
                            
                        }
                    }
                }
            }
            do {
                if let urlString = URL(string: url){
                    try implementation.downloadFile(call: call,url: urlString, emitter: progressEmitter, config: bridge?.config)
                }else {return}
            } catch let error {
                call.reject(error.localizedDescription)
            }
        }
    }
    
//    @objc func startFetch(_ call: CAPPluginCall) {
//        guard let urls = call.getArray("url", String.self) else {
//            call.reject("No url provided")
//            return
//        }
//            var ret = [String: Any]()
//            ret["value"] = urls
//            downloadManager = DownloadManager()
//            downloadManager?.downloadDelegate = self
//        do {
//            try implementation.startFetch(urls)
//        } catch {
//            ret["error"] = error.localizedDescription
//        }
//        call.resolve(ret)
//    }
    
    // MARK: URLSessionDownloadDelegate methods
    
//    public func urlSession(_ session: URLSession, downloadTask: URLSessionDownloadTask, didFinishDownloadingTo location: URL) {
//        print("Download finished: \(location)")
//        // Here you can move the downloaded file from `location` to your desired directory
//    }
//    
//    public func urlSession(_ session: URLSession, task: URLSessionTask, didCompleteWithError error: Error?) {
//        if error != nil {
//            print("Download failed with error: \(String(describing: error?.localizedDescription))")
//        }
//    }
//    
//    public func urlSession(_ session: URLSession, downloadTask: URLSessionDownloadTask, didWriteData bytesWritten: Int64, totalBytesWritten: Int64, totalBytesExpectedToWrite: Int64) {
//        if totalBytesExpectedToWrite > 0 {
//            let progress = Int(totalBytesWritten) / Int(totalBytesExpectedToWrite) * 100
//            print("Progress: \(progress)%")
//            self.notifyListeners("onProgress", data: ["download" : "\(progress)"])
//        }
//    }
}
