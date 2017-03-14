//
//  AccountViewController.swift
//  Loopback-Swift-Example
//
//  Created by Kevin Goedecke on 12/9/15.
//  Copyright © 2015 kevingoedecke. All rights reserved.
//

import UIKit

class AccountViewController: UIViewController {
    var currentUser: Client
    var defaults: NSUserDefaults = NSUserDefaults.standardUserDefaults()
    
    required init(coder aDecoder: NSCoder) {
        currentUser = Client()
        super.init(coder: aDecoder)!
    }
    
    @IBOutlet weak var AccessTokenLabel: UILabel!
    @IBOutlet weak var UserIDLabel: UILabel!
    @IBOutlet weak var EmailLabel: UILabel!
    
    @IBAction func ChangeEmailButton(sender: UIButton) {
        let alertController = InputAlertController.getInputAlertController("Email?", message: "Please enter your email", preferredStyle: .Alert)
        alertController.addAction(UIAlertAction(title: "Confirm", style: .Default) { (_) in
            if let field = alertController.textFields![0] as? UITextField {
                // store your data
                self.currentUser.email = field.text
                self.currentUser.saveWithSuccess({ () -> Void in
                    NSLog("sucessfully saved")
                    }, failure: { (error: NSError!) -> Void in
                        NSLog("error saving")
                })
                self.loadUserInformation()
            }
            })
        self.presentViewController(alertController, animated: true, completion: nil)
    }
    @IBAction func LogoutButton(sender: UIButton) {
        BackendUtilities.sharedInstance.clientRepo.logoutWithSuccess({ () -> Void in
            // Reset local Client class object
            NSLog("Successfully logged out")
            
            // Display logout confirmation
            let alertController = UIAlertController(title: "Logout", message:
                "Successfully logged out", preferredStyle: UIAlertControllerStyle.Alert)
            alertController.addAction(UIAlertAction(title: "Dismiss", style: UIAlertActionStyle.Default,handler: nil))
            
            self.presentViewController(alertController, animated: true, completion: nil)
            
            self.currentUser = Client()
            self.loadUserInformation()
            }) { (error: NSError!) -> Void in
                NSLog("Error logging out")
        }
    }
    override func viewDidLoad() {
        
    }
    
    override func viewDidAppear(animated: Bool) {
        BackendUtilities.sharedInstance.clientRepo.findCurrentUserWithSuccess({ (client) -> Void in
            NSLog("Found user")
            if let _ = client    {
                self.currentUser = client as! Client
                self.loadUserInformation()
            }
            else    {
            }
            }) { (error: NSError!) -> Void in
                NSLog("Error fetching current user")
        }
        
    }
    
    func loadUserInformation()  {
        AccessTokenLabel.text = BackendUtilities.sharedInstance.adapter.accessToken
        UserIDLabel.text = currentUser._id as? String
        EmailLabel.text = currentUser.email
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}

