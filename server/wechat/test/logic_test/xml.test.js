'use strict';

var xmlMsgParser = require('../../utils/xml_message_parser.js');
var fileUtil = require('../../utils/file_util.js');
var debug = require('debug')('_wechat:xmlTest:');
var msgDispatcher = require('../../logic/message_dispatcher.js');

fileUtil.readFromFile('./test/xml.txt',function(xmltext){
	xmlMsgParser.fromMessage(xmltext,function(err,msg){
	if(err){
		debug(err);
	}else{
		console.dir(msg,{depth:5});
		msgDispatcher.dispatch(msg,function(err,handleResult){
			if(err || handleResult != 0){
				debug(err + handleResult);
			}else{
				debug('ok');
			}
		});
	}
});
});