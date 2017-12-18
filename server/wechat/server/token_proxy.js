'use strict';

var wechatLogic = require('../logic/wechat_logic.js');
var debug = require('debug')('_wechat:token_proxy:');
var fileUtil = require('../utils/file_util.js');
var confPath = './wechat/config/access_token.ini'; //config file path.

var PERIOD = 5400 * 1000;

/*
 * Called when an access_token is got.
 * used to write the tokenOptions into a config file.
 */
var saveToken = function(tokenOptions) {
	debug(tokenOptions);
	fileUtil.writeFile(confPath, JSON.stringify(tokenOptions));
};

/*
 * Called to update and save the access_token,a tokenOptions Object
 * will be passed.
 */
var updateToken = function() {
	wechatLogic.getAccessToken(saveToken);
};


var start = function() {
	updateToken();
	setInterval(updateToken, PERIOD);
};


//use a handle function to do tasks with access_token
var getToken = function(handle) {
	fileUtil.readFromFile(confPath, function(tokenOptions) {
		if (tokenOptions) {
			var token = JSON.parse(tokenOptions);
			handle(token.access_token);
		} else {
			debug('failed to read access_token');
		}
	});
};

exports.updateToken = updateToken;
exports.getToken = getToken;
exports.start = start;