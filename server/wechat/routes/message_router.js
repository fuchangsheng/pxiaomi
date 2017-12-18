'use strict';

var express = require('express');
var router = express.Router();
var debug = require('debug')('_wechat:msgRouter:');
var xmlParser = require('../utils/xml_message_parser.js');
var msgDispatcher = require('../logic/message_dispatcher.js');

var onRequest = function(req,res){
	xmlParser.fromMessage(req.body.toString(),function(err,msg){
		if(err){
			res.send(err);
			return debug(err);
		}else{
			msgDispatcher.dispatch(msg,req,res,function(err,resultCode){
				if(err || resultCode != 0){
					debug(err + resultCode);
					res.end('success');
				}else{
					debug('ok');
					res.end('');
				}

			});
		}
	});
};

router.post('/',onRequest);

exports.msgRouter = router;