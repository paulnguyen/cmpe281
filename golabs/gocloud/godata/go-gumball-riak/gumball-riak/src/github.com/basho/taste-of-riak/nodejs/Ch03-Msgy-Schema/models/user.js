'use strict';
var Model = require('./model');
var inherits = require('util').inherits;

function User(userName, fullName, email) {
    Model.call(this);

    this.data = Object.freeze({
        userName: userName,
        fullName: fullName,
        email: email
    });

    Model.defineProperties(this, this.data);
}

inherits(User, Model);

User.prototype.getId = function () {
    return this.data.userName;
};

module.exports = User;

