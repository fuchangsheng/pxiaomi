'use strict';

var sha1Util = require('./sha1_util.js');
var debug = require('debug')('_wechat:check_sig:');
/*
 *	Called when you need to confirm that the messega is
 *	from tencent.
 *  
 *  @param :options {
 *				token: 'token',
 *				timestamp: 'timestamp',
 *				nonce: 'nonce',
 *				signature: 'signature'
 *			}
 *  
 *
 */
var checkSignature = function(options){
	var token = options.token;
	var timestamp = options.timestamp;
	var nonce = options.nonce;
	var signature = options.signature;
	debug(options);

	if (!token || !timestamp || !nonce || !signature) {
		return false;
	}
	//sort,join and sha1.
	var param = [token,timestamp,nonce];
	param.sort();
	var newSignature =sha1Util.getSha1Result(param.join(''));
	debug(newSignature);
	debug(signature);
	//compare
	if (newSignature == options.signature){
		return true;
	}else{
		return false;
	}
}

exports.checkSignature = checkSignature;
