'use strict';

var debug = require('debug')('_wechat:dirTest:');

var debugDir = function(){
	debug(__dirname);
};

exports.debugDir = debugDir;