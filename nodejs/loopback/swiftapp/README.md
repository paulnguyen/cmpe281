# loopback-swift-user-example
Example user auth and management app in Swift using the LoopBack iOS SDK

## Overview
1. This app uses the official [Loopback iOS SDK](https://github.com/strongloop/loopback-sdk-ios) with [Cocoapods](https://cocoapods.org/)
2. Fully written in Swift
3. Features: register new users, login/logout, display user details, edit user details

## Screenshots
<img src="https://cloud.githubusercontent.com/assets/5519740/11777809/ef47ba8e-a250-11e5-9668-bdb4692b2644.jpg" width="210">
<img src="https://cloud.githubusercontent.com/assets/5519740/11777677/377e776c-a250-11e5-9d45-25a44b184257.jpg" width="210">
<img src="https://cloud.githubusercontent.com/assets/5519740/11777817/f5de92a0-a250-11e5-8988-c07b524027dd.jpg" width="210">
<img src="https://cloud.githubusercontent.com/assets/5519740/11777815/f3bde2fa-a250-11e5-8c12-29bcd1d29676.jpg" width="210">

## Usage
- Deploy LoopBack Testing Backend, available here: [LoopBack Test App](https://github.com/kgoedecke/loopback-swift-user-example/tree/master/Loopback-Swift-Example-Tests/server)
- Insert your LoopBack API URL into the projects AppDelegate.swift

## Tutorial
- [KevinGoedecke.me - Tutorial: LoopBack iOS User Management App using Swift](http://kevingoedecke.me/2015/12/11/tutorial-ios-user-management-app-using-swift-loopback)


## Install Cocoa Pods

	sudo gem install cocoapods
	pod install (in project dir)

	Note: modify Swift Code to point to: http://localhost:3000/api
	      in AppDelegate.swift
	      
	https://guides.cocoapods.org/using/using-cocoapods.html
	https://guides.cocoapods.org/using/getting-started.html
	

# In userauth (loopback project)

	$ node .
	https server is ready at https://localhost:3000.