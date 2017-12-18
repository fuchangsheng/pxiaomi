'use strict';

var weLogic = require('../../logic/wechat_logic.js');
var debug = require('debug')('_wechat:queryMenuTest:');

weLogic.queryMenu(function(menu){
	console.dir(menu,{depth:5});
});