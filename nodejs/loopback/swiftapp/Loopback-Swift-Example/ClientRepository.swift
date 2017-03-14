//
//  ClientRepository.swift
//  Loopback-Swift-Example
//
//  Created by Kevin Goedecke on 12/9/15.
//  Copyright © 2015 kevingoedecke. All rights reserved.
//

import Foundation

class ClientRepository: LBUserRepository {
    override init!(className name: String!) {
        super.init(className: "Clients")
    }
    override init() {
        super.init(className: "Clients")
    }
}

class Client: LBUser {
    
}