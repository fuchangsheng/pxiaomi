'use strict';

var weLogic = require('../../logic/wechat_logic.js');
var debug = require('debug')('_wechat:deleteMenu:');

weLogic.deleteMenu(function(deleteResult){
	debug(JSON.stringify(deleteResult));
});