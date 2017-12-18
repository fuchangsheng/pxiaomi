'use strict'

var request = require('request');
var debug = require('debug')('_wechat:request:');

/*
 * Called when you need to do a GET http request.
 * @params:
 *		url : the url you do the request to.
 * 		onResponse:the callback function you use to handle the response body.
 * In this get function ,it will also judge wether you have got a correct response from server. 
 */
var get = function(url,onResponse){
	request(url,function(err,res,body){
		if(err){
			debug(err);
			return onResponse(err,null,null);
		}else if(res.statusCode == 200){
			onResponse(err,res,body);
		}
	});
};

/*
 * Called when you need to post some data(espacially the json data).
 */
var postData = function(postUrl,urlEncodedForms,onResponse){
	debug(urlEncodedForms);
	request.post({url:postUrl,form:urlEncodedForms},function(err,res,body){
		if(err){
			debug(err);
			return onResponse(err,null,null);
		}else if(res.statusCode == 200){
			onResponse(err,res,body);
		}
	});
};

exports.get = get;
exports.postData = postData;