'use strict';

var msgBuilder = require('../../utils/xml_message_builder.js');
var debug = require('debug')('_wechat:test');

// var toMsg = {};
// toMsg.ToUserName = 'openId';
// toMsg.FromUserName = 'myId';
// toMsg.CreateTime = Date.now();
// toMsg.MsgType = 'text';
// toMsg.Content = 'hello';

// msgBuilder.buildTextReply(toMsg,function(err,xml){
// 	debug(xml);
// });

var toMsg = {};
toMsg.ToUserName = 'openId';
toMsg.FromUserName = 'myId';
toMsg.CreateTime = Date.now();
toMsg.MsgType = 'image';
toMsg.Image = {
	MediaId: 'myId'
};
msgBuilder.buildImgReply(toMsg,function(err,xml){
	debug(xml);
});