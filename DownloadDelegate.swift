//
//  DownloadDelegate.swift
//  Fetch2
//
//  Created by Nabi Ahmad on 04/03/2024.
//

import Foundation

protocol DownloadDelegate {
    func didFinish(_ donwloads: Download?)
    func didError(_ donwloads: Download?)
    func didProgress(_ donwloads: Download?)
}

