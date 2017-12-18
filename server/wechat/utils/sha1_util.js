'use strict';
/*
 * Called to get sha1 result.
 */

 var crypto = require('crypto');

 var getSha1Result = function(param){
 	var shasum = crypto.createHash('sha1');
 	shasum.update(param);
 	return shasum.digest('hex');
 }

exports.getSha1Result = getSha1Result;