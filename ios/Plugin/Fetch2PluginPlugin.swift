import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(Fetch2PluginPlugin)
public class Fetch2PluginPlugin: CAPPlugin , URLSessionDownloadDelegate {
    private let implementation = Fetch2Plugin()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }

    @objc func startFetch(_ call: CAPPluginCall) {
        // let value = call.getString("value") ?? ""
        // call.resolve([
        //     "value": implementation.echo(value)
        // ])


        guard let urls = call.getArray("url", String.self) else {
        call.reject("No url provided")
        return
    }
    var ret = [String: Any]()
    ret["value"] = urls
    do {
        try implementation.startFetch(urls)
    } catch {
        ret["error"] = error.localizedDescription
    }
    call.resolve(ret)
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
            let progress = Int(totalBytesWritten) / Int(totalBytesExpectedToWrite) * 100
            print("Progress: \(progress)%")
            self.notifyListeners("onProgress", data: ["download" : "\(progress)"])
        }
    }
}
