'use strict';

var debug = require('debug')('_wechat:oauth:');
var request = require('./request.js');
var urls = require('../config/wechat_api_urls.js');
var fileUtil = require('./file_util.js');

/**
 * 	When you need to get infomation about an user,you can use oauth .
 * then,url will have two params:code and state,they will be the properties 
 * of the object oauthQuery like:oauthQuery.code or oauthQuery.state.
 * 	This function is used to get the json contains web_access_token,openid,scope,and some other
 * useful infomation.
 * @param  {Object} oauthQuery [description]
 * @param  {function} handle     [description]
 */
var webATJson = function(oauthQuery, handle) {
	var url = urls.webATUrl(oauthQuery);
	debug(url);
	request.get(url, function(err, res, body) {
		if (err) {
			debug(err);
			return handle(err, null);;
		} else {
			var webATJson = JSON.parse(res.body);
			debug(webATJson);
			if (webATJson.errcode) {
				debug('errcode');
				handle(webATJson.errmsg, null);
			} else {
				debug(webATJson);
				handle(null, webATJson);
			}
		}
	});
};

/**
 * 	Get openId of the user requesting this oauth,call the callback function (handle) when
 * succeed getting openId.if not,openid will be null.
 * @param  {[type]} oauthQuery [description]
 * @param  {[type]} handle     [description]
 * @return {[type]}            [description]
 */
var openId = function(oauthQuery, handle) {
	webATJson(oauthQuery, function(err, webATJson) {
		if (err) {
			debug('err');
			return handle(err, null);
		} else {
			debug(webATJson.openid);
			handle(null, webATJson.openid);
		}
	});
};

var userInfo = function(webATJson, handle) {
	var url = urls.userInfoUrl(webATJson);
	debug(url);
	request.get(url, function(err, res, body) {
		if (err) {
			return handle(err, null);
		} else {
			var info = JSON.parse(res.body);
			debug(info);
			if (info.errcode) {
				handle(info, null);
			} else {
				handle(null, info);
			}
		}
	});
}


exports.openId = openId;
exports.userInfo = userInfo;
exports.webATJson = webATJson;