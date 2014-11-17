/**
 * Module dependencies
 */

var _ = require('lodash');
var util = require('util');


/**
 * Project `tuples` on `fields`.
 * 
 * @param  { Object[] }  tuples    [i.e. filteredData]
 * @param  { String[]/Object{} }  fields    [i.e. schema]
 * @return { Object[] }
 */
function select (tuples, fields) {

  // Expand splat shortcut syntax
  if (fields === '*') {
    fields = { '*': true };
  }

  // If `fields` are not an Object or Array, don't modify the output.
  if (typeof fields !== 'object') return tuples;

  // If `fields` are specified as an Array, convert them to an Object.
  if (_.isArray(fields)) {
    fields = _.reduce(fields, function arrayToObj(memo, attrName) {
      memo[attrName] = true;
      return memo;
    }, {});
  }

  // If the '*' key is specified, the projection algorithm is flipped:
  // only keys which are explicitly set to `false` will be excluded--
  // all other keys will be left alone (this lasts until the recursive step.)
  var hasSplat = !!fields['*'];
  var fieldsToExplicitlyOmit = _(fields).where(function _areExplicitlyFalse (v,k){ return v === false; }).keys();
  delete fields['*'];


  // Finally, select fields from tuples.
  return _.map(tuples, function (tuple) {

    // Select the requested attributes of the tuple
    if (hasSplat) {
      tuple = _.omit(tuple, function (value, attrName){
        return _.contains(fieldsToExplicitlyOmit, attrName);
      });
    }
    else {
      tuple = _.pick(tuple, Object.keys(fields));
    }


    // || NOTE THAT THIS APPROACH WILL CHANGE IN AN UPCOMING RELEASE
    // \/ TO MATCH THE CONVENTIONS ESTABLISHED IN WL2.

    // Take recursive step if necessary to support nested
    // SELECT clauses (NOT nested modifiers- more like nested
    // WHEREs)
    // 
    // e.g.:
    // like this:
    //   -> { select: { pet: { collarSize: true } } }
    //   
    // not this:
    //   -> { select: { pet: { select: { collarSize: true } } } }
    //
    _.each(fields, function (subselect, attrName) {

      if (typeof subselect === 'object') {
        if (_.isArray(tuple[attrName])) {
          tuple[attrName] = select(tuple[attrName], subselect);
        }
        else if (_.isObject(tuple[attrName])) {
          tuple[attrName] = select([tuple[attrName]], subselect)[0];
        }
      }
    });

    return tuple;
  });
}

module.exports = select;
