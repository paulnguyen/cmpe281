//
//  RegisterViewController.swift
//  Loopback-Swift-Example
//
//  Created by Kevin Goedecke on 12/8/15.
//  Copyright © 2015 kevingoedecke. All rights reserved.
//

import UIKit

class RegisterViewController: UIViewController {

    @IBOutlet weak var EmailTextField: UITextField!
    @IBOutlet weak var PasswordTextField: UITextField!
    @IBOutlet weak var RepeatPasswordTextField: UITextField!
    
    @IBAction func RegisterButton(sender: UIButton) {
        
        if (PasswordTextField.text != RepeatPasswordTextField.text) {
            let alertController = UIAlertController(title: "Password", message:
                "The passwords don't match", preferredStyle: UIAlertControllerStyle.Alert)
            alertController.addAction(UIAlertAction(title: "Dismiss", style: UIAlertActionStyle.Default,handler: nil))
            self.presentViewController(alertController, animated: true, completion: nil)
        }
        else    {
            let user:Client = BackendUtilities.sharedInstance.clientRepo.createUserWithEmail(EmailTextField.text!, password: PasswordTextField.text!) as! Client
        
            user.saveWithSuccess({ () -> Void in
                NSLog("Successfully registered new user with User ID: ")
                NSLog(user._id as! String)
            
                // Display registration confirmation
                let alertController = UIAlertController(title: "Registration", message:
                    "New user successfully registered", preferredStyle: UIAlertControllerStyle.Alert)
                alertController.addAction(UIAlertAction(title: "Dismiss", style: UIAlertActionStyle.Default,handler: nil))
                self.presentViewController(alertController, animated: true, completion: nil)
                
                }) { (error: NSError!) -> Void in
                    NSLog("Error")
                
                    // Display error alert for registration
                    let alertController = UIAlertController(title: "Registration", message:
                        "Error creating new user", preferredStyle: UIAlertControllerStyle.Alert)

                    alertController.addAction(UIAlertAction(title: "Dismiss", style: UIAlertActionStyle.Default,handler: nil))
                    self.presentViewController(alertController, animated: true, completion: nil)
                }
        }
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
