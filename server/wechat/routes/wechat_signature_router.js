'use strict';

var express = require('express');
var router = express.Router();
var checkSig = require('../utils/check_signature.js');
var debug = require('debug')('_wechat:wechatSignature:');
var AppInfo = require('../models/app_info.js');
var appInfo = new AppInfo();
var application = require('../../app.js');

var onRequest = function(req, res, next) {
	debug('Request received from ' + req.ip);
	//get queryStr
	var query = req.query;
	debug(query);

	//generate options for check signature from wechat.
	var options = {};
	options.signature = query.signature;
	options.timestamp = query.timestamp;
	options.nonce = query.nonce;
	options.token = appInfo.token;

	debug(appInfo);
	if (checkSig.checkSignature(options)) {
		res.send(query.echostr);
		res.end();
	} else {
		res.end('');
		debug('failed to check the signature .');
	}
};

router.get('/MP_verify_2MqdnfDI71aSkDSI.txt',function(req,res){
	res.sendFile(application.rootPath + '/wechat/config/mp/MP_verify_2MqdnfDI71aSkDSI.txt');
	debug('file sent');
});

router.get('/', onRequest);

exports.wechatSignatureRouter = router;