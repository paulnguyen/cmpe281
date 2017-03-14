//
//  InputAlertController.swift
//  Loopback-Swift-Example
//
//  Created by Kevin Goedecke on 12/10/15.
//  Copyright © 2015 kevingoedecke. All rights reserved.
//

import Foundation

class InputAlertController    {
    static func getInputAlertController(title: String?, message: String?, preferredStyle: UIAlertControllerStyle) -> UIAlertController {
        
        let alertController = UIAlertController(title: title, message: message, preferredStyle: preferredStyle)
        let cancelAction = UIAlertAction(title: "Cancel", style: .Cancel) { (_) in }
        alertController.addTextFieldWithConfigurationHandler { (textField) in
            textField.placeholder = title
        }
        
        alertController.addAction(cancelAction)
        
        return alertController
    }
}
