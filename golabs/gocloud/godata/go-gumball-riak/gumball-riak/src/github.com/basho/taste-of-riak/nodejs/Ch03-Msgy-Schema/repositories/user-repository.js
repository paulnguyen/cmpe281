'use strict';

var Repository = require('./repository');
var User = require('../models/user');
var inherits = require('util').inherits;

function UserRepository(client) {
    Repository.call(this, client);
    this.bucketName = 'Users';
}

inherits(UserRepository, Repository);

UserRepository.prototype.buildModel = function (rslt) {
    if (rslt.values) {
        var rv = null,
            riakObj = rslt.values.shift();
        if (riakObj && riakObj.value) {
            var userJsonObj = riakObj.value;
            rv = new User(userJsonObj.data.userName,
                userJsonObj.data.fullName, userJsonObj.data.email);
        }
        return rv;
    }
};

module.exports = UserRepository;

