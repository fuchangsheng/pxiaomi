'use strict';

var xml2js = require('xml2js');
var debug = require('debug')('_wechat:xmlBuilder:');
var builder = new xml2js.Builder({
	cdata:true,
	rootName:'xml',
	headless:true
});


/*{
 *	toMsg:{
 *		ToUserName:OpenId,
 *		FromUserName:开发者微信号,
 *		CreateTime:createTime,
 *		MsgType:'text',
 *		Content:'hello user'
 *  }
 *}
 *
 */
var buildTextReply = function(toMsg,handle){
	var xml = builder.buildObject(toMsg);
	if(xml){
		console.log(xml);
		handle(null,xml);
	}else{
		handle('build failed',null);
	}
};

var buildImgReply = function(toMsg,handle){
	var xml = builder.buildObject(toMsg);
	if(xml){
		console.log(xml);
		handle(null,xml);
	}else{
		handle('build failed',null);
	}
};

exports.buildTextReply = buildTextReply;
exports.buildImgReply = buildImgReply;
