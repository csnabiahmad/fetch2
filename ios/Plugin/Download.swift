//
//  Download.swift
//  Plugin
//
//  Created by Nabi Ahmad on 16/02/2024.
//  Copyright © 2024 Max Lynch. All rights reserved.
//

import Foundation
protocol Download: Codable {

    var id: Int { get}
    var namespace: String { get }
    var url: String { get }
    var file: String { get }
    var group: Int { get }
    var priority: String { get }
    var headers: [String: String] { get }
    var downloaded: Int64 { get }
    var total: Int64 { get }
    var status: String { get }
    var error: Error { get }
    var networkType: String { get }
    var progress: Int { get }
    var created: Int64 { get }
    var request: String? { get }
    var tag: String? { get }
    var enqueueAction: String { get }
    var identifier: Int64 { get }
    var downloadOnEnqueue: Bool { get }
    var extras: String { get }
    var fileUri: URL { get }
    var etaInMilliSeconds: Int64 { get }
    var downloadedBytesPerSecond: Int64 { get }
    var autoRetryMaxAttempts: Int { get }
    var autoRetryAttempts: Int { get }

    func copy() -> Download
}
