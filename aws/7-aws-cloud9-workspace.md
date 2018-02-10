
# Amazon Web Services: Cloud9 Workspace Getting Started

```
    DOC:    https://aws.amazon.com/cloud9/getting-started/
            https://docs.aws.amazon.com/cloud9/latest/user-guide/ide.html
            https://docs.aws.amazon.com/cloud9/latest/user-guide/sample-github.html
```

## Setup Amazon IAM Group & User for Cloud9 Worskpace

```
    Follow Steps in:  https://aws.amazon.com/cloud9/getting-started/
    To create your AWS Account and Cloud9 IAM User.

    1. Create an IAM Group Named:  AWSCloud9Group
    2. Attach the following Policies to the Group:
        AWSCloud9Administrator
        AWSCloud9EnvironmentMember
        AWSCloud9User
    3. Create an IAM User Named:  cloud9
    4. User Access Type should be:  AWS Management Console Access with Password
    5. Make sure to assign the group AWSCloud9Group to this user
    6. Amazon will display a URL to login to your Account.  Make a note of this URL.

```

## Setup Cloud9 Environment

```
    Follow Steps in:  https://aws.amazon.com/cloud9/getting-started/
    To create your AWS Cloud9 Environment.

    1. Find the AWS Cloud9 Service
    2. Select option to Create a New AWS Cloud9 Environment
    3. Name your Cloud9 Environment as: CMPE281
    4. Selct Environment Type:          EC2
    5. Select EC2 Instance Type:        t2.micro
    6. Network Settings:                Use Defaults
    7. Cost Savings Setting:            Use Defaults
    
    It make take some time for the Environment to come up
```

## Integration with GitHub

```
    Follow Example:  https://docs.aws.amazon.com/cloud9/latest/user-guide/sample-github.html
    To setup GitHub in your Cloud9 Workspace.

    1. Use the Cloud9 Terminal (Command Line)
    2. Make sure Git is installed and up to date
    3. By Default, Git should already be installed
    4. Check using command:  git --version
    5. On the Terminal, use the following commands to config your GitHub Account:

            git config --global user.name "USER_NAME"
            git config --global user.email EMAIL_ADDRESS

    6. Clone the following GitHub Repo into your Cloud9 Workspace:

            git clone https://github.com/paulnguyen/nodejs

    7. Once git clone completes, you should have a folder named "nodejs" in your Workspace
```

## Running a Node.js Application in Cloud9

```
    1. In your Cloud9 Workspace Terminal Window
    2. Make sure Node.js is installed:

        node --version

    3. Install the Node Module Dependencies in the "nodejs" folder:

        npm install

    4. Using the Cloud9 Editor, edit the "app.js" file and click the "Run" button
    5. Also click the "Preview" menu option to view the running app in the IDE's browser
    6. Get the App URL from the Preview Browser and run the App in a separate Tab or Browser

    Sample Outputs of the Running App is shown as follows (see included images).
```

                             








