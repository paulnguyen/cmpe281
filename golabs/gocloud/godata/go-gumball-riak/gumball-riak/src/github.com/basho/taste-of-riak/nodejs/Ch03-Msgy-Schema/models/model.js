'use strict';

function Model() { }

/*
 * Used to create immutable Model objects
 */
function defineProperties(obj, data) {
    Object.keys(data).forEach(function (prop) {
        Object.defineProperty(obj, prop, {
            get: function () { return data[prop] }
        });
    });
}

Model.prototype.getId = function () {
    if (this.hasOwnProperty('id')) {
        return this.id;
    } else {
        throw 'not implemented';
    }
};

Object.defineProperty(Model.prototype, "id", {
    get: function () { return this.getId() }
});

module.exports = Model;
module.exports.defineProperties = defineProperties;

