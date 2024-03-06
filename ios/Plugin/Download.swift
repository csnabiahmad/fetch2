//
//  Download.swift
//  Plugin
//
//  Created by Nabi Ahmad on 16/02/2024.
//  Copyright © 2024 Max Lynch. All rights reserved.
//

import Foundation

protocol DownloadDelegate:AnyObject{
    func didFinish(_ donwloads: Download?)
    func didError(_ donwloads: Download?)
    func didProgress(_ donwloads: Download?)
}

public struct Download : Codable {
    var id: UUID = UUID()
    var file: String = ""
    var fileUri: String = ""
    var url: String = ""
//    var progress: Float = 0.0
    var status: String = "DONWLOADING"
//    var type: DownloadType
    var error: String = ""
    var downloaded: Int = 0
    var total: Int = 0

}
enum DownloadStatus: Codable {
    case pending
    case downloading
    case paused
    case completed
    case failed
}

enum DownloadType: Codable{
    case file
    case image
    case audio
    case video
}
//Download  {
//    var autoRetryAttempts: Int = 0
//    var autoRetryMaxAttempts: Int = 0
//    var created: Int = 0
//    var downloadOnEnqueue: Bool = false
//    var downloaded: Int = 0
//    var downloadedBytesPerSecond: Int = 0
//    var enqueueAction: String = ""
//    var error: String = ""
//    var etaInMilliSeconds: Int = 0
//    var extras: ExtrasData = ExtrasData(data: [:])
//    var file: String = ""
//    var group: Int = 0
//    var headers: [String: Any] = [:]
//    var id: Int = Int.random(in: 0..<1000000) // Initialize with a random number between 0 and 999999
//    var identifier: Int = 0
//    var namespace: String = ""
//    var networkType: String = ""
//    var priority: String = ""
//    var status: String = ""
//    var tag: String = ""
//    var total: Int = 0
//    var url: String = ""
//}
//
//struct ExtrasData {
//    var data: [String: Any] = [:]
//}

//protocol Download: Codable {
//
//    var id: Int { get}
//    var namespace: String { get }
//    var url: String { get }
//    var file: String { get }
//    var group: Int { get }
//    var priority: String { get }
//    var headers: [String: String] { get }
//    var downloaded: Int64 { get }
//    var total: Int64 { get }
//    var status: String { get }
//    var error: Error { get }
//    var networkType: String { get }
//    var progress: Int { get }
//    var created: Int64 { get }
//    var request: String? { get }
//    var tag: String? { get }
//    var enqueueAction: String { get }
//    var identifier: Int64 { get }
//    var downloadOnEnqueue: Bool { get }
//    var extras: String { get }
//    var fileUri: URL { get }
//    var etaInMilliSeconds: Int64 { get }
//    var downloadedBytesPerSecond: Int64 { get }
//    var autoRetryMaxAttempts: Int { get }
//    var autoRetryAttempts: Int { get }
//
//    func copy() -> Download
//}
