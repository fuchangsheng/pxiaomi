'use Strict';

/**
 * 
 */

var debug = require('debug')('_wechat:dispatcher:');
var weMsg = require('./message_replyer.js');

/*
 *	Called when message/event received from user.
 *  this function will dispatch message to different logic and reply message to user.
 * @param: dispatch
 * 		msg:message json from user(parsed from xml);
 *		req:req in express;
 *		res:res in express;
 *		handle:handle dispatch result.it has two params
 *		@params；handle
 *			err: whether a message from user is correctly dispatched.
 *			resultCode:  0.....'ok',
 *						-1.....'unknown message',
 *						-2.....'reply failed'
 *			
 */
var dispatch = function(msg, req, res, handle) {
	if (msg.MsgType == 'event') {
		//event msg 
		var eventType = msg.Event;
		if ((eventType == 'subscribe') && (!msg.EventKey)) {
			//type:subscribe
			debug('event:subscribe');
			weMsg.sendText(msg, '欢迎关注kp助手/::|', req, res, function(err) {
				if (err) {
					handle(err, -2);
				} else {
					debug('message sent');
				}
			});
		} else if (eventType == 'unsubscribe') {
			//type: unSubscribe
			debug('event:unsubscribe ');
			weMsg.sendText(msg, '取消关注kp助手/::|', req, res, function(err) {
				if (err) {
					handle(err, -2);
				} else {
					debug('message sent');
				}
			});
		} else if ((eventType == 'subscribe') && (msg.EventKey)) {
			//type:scan(user who has not subscribed)
			debug('event :scan from unsubscribe user');

		} else if (eventType == 'SCAN') {
			//type:scan(user who has subscribed)
			debug('event :scan from subscribe user');

		} else if (eventType == 'LOCATION') {
			//type:location
			debug('event:upload location');

		} else if (eventType == 'CLICK') {
			//type:click
			debug('menu event:click');

		} else if (eventType == 'VIEW') {
			//type:view
			debug('menu event:view');

		} else if (eventType == 'scancode_push') {
			//type:scancode_push
			debug('menu event:scancode_push');

		} else if (eventType == 'scancode_waitmsg') {
			//type:scancode_waitmsg
			debug('menu event:scancode_waitmsg');

		} else if (eventType == 'pic_sysphoto') {
			//type:pic_sysphoto
			debug('menu event:pic_sysphoto');

		} else if (eventType == 'pic_photo_or_album') {
			//type:pic_photo_or_album
			debug('menu event:pic_photo_or_album');

		} else if (eventType == 'pic_weixin') {
			//type:pic_weixin
			debug('pic_weixin');

		} else if (eventType == 'location_select') {
			//type:location_select
			debug('menu event:location_select');

		} else {
			//not a normal evetn type
			debug('event:unknown');
			return handle('event:unknown', -1);
		}

	} else {
		// conversation message
		if (msg.MsgType == 'text') {
			//normal text msg
			debug('normal:text');
			weMsg.sendText(msg, msg.Content.toString(), req, res, function(err) {
				if (err) {
					handle(err, -2);
				} else {
					debug('message sent');
				}
			});
		} else if (msg.MsgType == 'image') {
			//mormal image msg
			debug('normal:image');
			weMsg.sendImg(msg,req,res,function(err){
				if(err){
					handle(err);
				}else{
					debug('img sent');
				}
			});

		} else if (msg.MsgType == 'voice') {
			//normal voice msg
			debug('normal:voice');

		} else if (msg.MsgType == 'video') {
			//normal video msg
			debug('normal:video');

		} else if (msg.MsgType == 'shortvideo') {
			//normal short video msg
			debug('short shortvideo');

		} else if (msg.MsgType == 'location') {
			//normal location msg
			debug('normal:location');

		} else if (msg.MsgType == 'link') {
			//normal link msg
			debug('normal:link');

		} else {
			//not a normal msg
			debug('normal message:unknown');
			return handle('normal message:unknown', -1);
		}
	}

	handle(null, 0);
};


exports.dispatch = dispatch;