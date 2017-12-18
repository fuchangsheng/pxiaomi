'use strict';

var weLogic = require('../../logic/wechat_logic.js');
var fileUtil = require('../../utils/file_util.js');
var debug = require('debug')('_wechat:createMenuTest:');

var path = './wechat/config/menu.json';
fileUtil.readFromFile(path,function(menu){
	debug(menu);
	weLogic.createMenu(menu,function(createResult){
		debug(createResult);
	});
});