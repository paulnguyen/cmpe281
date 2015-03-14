// 2.20 - This simulation is possible in JavaScript because we have first-class 
//        functions and passing functions—a callback is a well-supported pattern 
//        in the language
function longRunningOperation(callback) {
    // simulate a 3 second operation
    setTimeout(callback, 3000);
}

function userClicked() {
    console.log('starting a long operation');
    longRunningOperation(function () {
        console.log('ending a long operation');
    });
}
// simulate a user action
userClicked();