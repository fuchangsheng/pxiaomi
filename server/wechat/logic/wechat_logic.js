'use strict';

var urls = require('../config/wechat_api_urls.js');
var request = require('../utils/request.js');
var debug = require('debug')('_wechat:wechatLogic:');
var tokenProxy = require('../server/token_proxy.js');


/*
 * Called to get access_token
 * it's a json like :
 * {
 *	accesstoken:'access_token', //access_token string
 *	expires_in:expires_in 		//7200 usually
 *	}
 * @param handle : 
 *  	a handle function to handle the tokenOptions given above.
 */
var getAccessToken = function(handle){
	request.get(urls.getAccessTokenUrl(),function(err,res,body){
		var tokenOptions = JSON.parse(body);
		debug('access_token got .');
		handle(tokenOptions);
	});	
};

/*
 * Called to get ipList of wechat server.
 * {ip_list:['ip_01','ip_02'...]};
 * 
 * @param handle:a function to handle ipList
 */
var getCallbackIp = function(handle){
	tokenProxy.getToken(function(access_token){
		var url = urls.getCallbackIpUrl(access_token);
		request.get(url,function(err,res,body){
			var ipList = JSON.parse(body);
			debug(ipList);
			handle(ipList);
		});
	});
}

//attention !:
// menu must be a string , not a json object
var createMenu = function(menu,handle){
	tokenProxy.getToken(function(access_token){
		var url = urls.createMenuUrl(access_token);
		request.postData(url,menu,function(err,res,body){
			var createResult = JSON.parse(body);
			debug(createResult);
			handle(createResult);
		})
	});
}

var queryMenu = function(handle){
	tokenProxy.getToken(function(access_token){
		var url = urls.queryMenuUrl(access_token);
		request.get(url,function(err,res,body){
			var menu = JSON.parse(body);
			handle(menu);
		});
	});
};

var deleteMenu = function(handle){
	tokenProxy.getToken(function(access_token){
		var url = urls.deleteMenuUrl(access_token);
		request.get(url,function(err,res,body){
			var deleteResult = JSON.parse(body);
			handle(deleteResult);
		})
	});
}


exports.getAccessToken = getAccessToken;
exports.getCallbackIp = getCallbackIp;
exports.createMenu = createMenu;
exports.queryMenu = queryMenu;
exports.deleteMenu = deleteMenu;