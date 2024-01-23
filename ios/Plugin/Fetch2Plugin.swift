import Foundation

@objc public class Fetch2Plugin: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
