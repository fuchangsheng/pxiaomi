'use strict';

var debug = require('debug')('_wechat:replyer:');
var msgBuilder = require('../utils/xml_message_builder.js');


/*
 * Called when it is need to reply user a text message.
 * @params
 *		msg:message json from user;
 *		text:text message you want to send;
 *		req:req in express;
 *		res:res in express;
 *		handle:if an error happend,it will take a not null param err;
 */

var sendText = function(msg,text,req,res,handle){
	//construct toMessage
	var toMsg = {};
	toMsg.ToUserName = msg.FromUserName;
	toMsg.FromUserName = msg.ToUserName;
	toMsg.CreateTime = Date.now();
	toMsg.MsgType = 'text';
	toMsg.Content = text;
	msgBuilder.buildTextReply(toMsg,function(err,xml){
		if(err){
			debug(err);
			return handle(err);
		}else{
			res.send(xml);
			return handle(null);
		}
	});
};

var sendImg = function(msg,req,res,handle){
	//construct toMessage
	var toMsg = {};
	toMsg.ToUserName = msg.FromUserName;
	toMsg.FromUserName = msg.ToUserName;
	toMsg.CreateTime = Date.now();
	toMsg.MsgType = 'image';
	toMsg.Image = {
		MediaId:msg.MediaId
	};
	msgBuilder.buildImgReply(toMsg,function(err,xml){
		if(err){
			debug(err);
			return handle(err);
		}else{
			res.send(xml);
			return handle(null);
		}
	});
};

exports.sendText = sendText;
exports.sendImg = sendImg;