//
//  Download.swift
//  Plugin
//
//  Created by Nabi Ahmad on 16/02/2024.
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
    var status: String = ""
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
