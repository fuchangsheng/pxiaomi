'use strict';
/**
 * In wechat webs,there may be requests(get/post) need to be sent by ajax.
 * this module provides methods to give response 
 * to wechat web request in ajax. 
 */

var debug = require('debug')('_wechat:ajaxProcesser:');
var userModel = require('../../model/user_info.js');

var processBindMobile = function(req, res) {
	var options = {
		userId: req.body.userId,
		mobile: req.body.mobile
	};
	debug(options);

	var query = {
		match: {
			id: options.userId
		},
		update: {
			mobile: options.mobile
		}
	};
	debug(query);

	userModel.update(query,function(err,rows){
		var data = {
			status: -1
		};
		if(err){
			debug(err);
		}else{
			data.status = 0;
		}
		res.json(data);
		debug(rows);
		res.end();
	});
};

var queryMobile = function(req,res){
	var query = {
		select: {
			name: 'name',
			mobile: 'mobile'
		},
		match: {
			id: req.query.userId || ''
		}
	};
	debug(query);

	var data = {
		status: -1,
		mobile: '未知',
		name: '未知'
	};

	userModel.lookup(query,function(err,rows){
		if(err){
			debug(err);
		}else{
			if(rows.length){
				data.mobile = rows[0].mobile || '未知';
				data.name = rows[0].name || '未知';
				data.status = 0;
			}
		}
		debug(data);
		res.send(data);
		res.end();
	})
}


exports.bindMobile = processBindMobile;
exports.queryMobile = queryMobile;