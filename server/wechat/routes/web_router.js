'use strict';

var express = require('express');
var router = express.Router();
var debug = require('debug')('_wechat:webRouter:');
var os = require('os');
var oauthUtil = require('../utils/web_oauth_util.js');
var osType = os.type().toLowerCase();
var weUserLogic = require('../logic/weUser.logic.js');

var setUserId = function(req, res, next) {
	debug(req.query);
	var oauthQuery = {
		code: req.query.code || '',
		state: req.query.state || ''
	};

	req.userId = '';
	req.hasBinded = false;

	oauthUtil.webATJson(oauthQuery, function(err, webATJson) {
		if (err) {
			debug(err);
			res.end();
		} else {
			weUserLogic.onUserIn(webATJson, function(err, options) {
				if (err) {
					return debug(err);
					next();
				} else {
					req.userId = options.userId;
					req.hasBinded = options.hasBinded;
					debug(options);
					next();
				}
			});
		}
	});
};

var rootPath = function(osType) {
	debug(osType);
	return __dirname.substr(0, __dirname.lastIndexOf((osType === 'linux') ? '/' : '\\'));
};

var sendPage = function(options) {
	var req = options.req;
	var res = options.res;
	var page = options.pagename;
	var mark = options.mark;

	var pagePath = rootPath(osType) + '/views/html/' + page + '.html';
	debug(pagePath);
	debug(req.cookies);
	//mark user by userId
	if (options.setCookie && options.mark) {
		for (var k in mark) {
			debug('mark' + k + ':' + mark[k]);
			res.cookie(k || '', mark[k] || '');
			debug('cookie:' + k + ':' + mark[k]);
		}
	}
	res.sendFile(pagePath);
};

router.use('/views', setUserId);


//page:bindMobile
router.get('/views/bindMobile', function(req, res) {
	var options = {
		req: req,
		res: res,
		setCookie: true,
		mark: {
			userId: req.userId
		}
	}
	if (req.hasBinded) {
		options.pagename = 'bind_ok';
	} else {
		options.pagename = 'bindMobile';
	}
	sendPage(options);
});

//page:user_info
router.get('/views/userInfo', function(req, res) {
	var options = {
		req: req,
		res: res,
		setCookie: true,
		mark: {
			userId: req.userId
		}
	};
	if (req.hasBinded) {
		options.pagename = 'user_info';
	} else {
		options.pagename = 'bindMobile';
	}
	sendPage(options);
});

router.get('/views/tax_info', function(req, res) {
	var options = {
		req: req,
		res: res,
		setCookie: true,
		mark: {
			userId: req.userId
		}
	};
	if(req.hasBinded){
		options.pagename = 'tax_info';
	}else{
		options.pagename = 'bindMobile';
	}
	sendPage(options);
});

router.get('/views_static/bind_ok', function(req, res) {
	var options = {
		req: req,
		res: res,
		setCookie: false,
		mark: {
			name: '',
			value: ''
		},
		pagename: 'bind_ok'
	};
	sendPage(options);
});

exports.webRouter = router;