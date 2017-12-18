'use strict';

/**
 * this module provides urls for wechat api;
 */

var AppInfo = require('../models/app_info.js');
var debug = require('debug')('_wechat:urls:');
var appInfo = new AppInfo();

//access_token url without params.
var BASE_URL = 'https://api.weixin.qq.com/cgi-bin';
var WEB_URL = 'https://api.weixin.qq.com/sns';

var getAccessTokenUrl = function() {
	var url = BASE_URL + '/token?grant_type=client_credential' + '&appid=' + appInfo.appId + '&secret=' + appInfo.appSecret;
	return url;
};

var getCallbackIpUrl = function(access_token) {
	if (access_token) {
		return BASE_URL + '/getcallbackip?access_token=' + access_token;
	}
};

var createMenuUrl = function(access_token) {
	if (access_token) {
		return BASE_URL + '/menu/create?access_token=' + access_token;
	}
};

var queryMenuUrl = function(access_token) {
	if (access_token) {
		return BASE_URL + '/menu/get?access_token=' + access_token;
	}
};

var deleteMenuUrl = function(access_token) {
	if (access_token) {
		return BASE_URL + '/menu/delete?access_token=' + access_token;
	}
};

var webATUrl = function(oauthQuery) {
	return WEB_URL + '/oauth2/access_token?appid=' + appInfo.appId + '&secret=' + appInfo.appSecret + '&code=' + oauthQuery.code + '&grant_type=authorization_code';
};

var userInfoUrl = function(webATJson) {
	return WEB_URL + '/userinfo?access_token=' + webATJson.access_token + '&openid=' + webATJson.openid + '&lang=zh_CN';
};

exports.getAccessTokenUrl = getAccessTokenUrl;
exports.getCallbackIpUrl = getCallbackIpUrl;
exports.createMenuUrl = createMenuUrl;
exports.queryMenuUrl = queryMenuUrl;
exports.deleteMenuUrl = deleteMenuUrl;
exports.webATUrl = webATUrl;
exports.userInfoUrl = userInfoUrl;