import Foundation


@objc public class Fetch2Plugin: NSObject {

    var downloadManager: DownloadManager?

    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }

    @objc public func startFetch(_ url: [String]) {
        downloadManager = DownloadManager()
        if let url1 = URL(string: url[0]){
            downloadManager?.startDownloads(urls: [url1])
        }
    }
    

}
