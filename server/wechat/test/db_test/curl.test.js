'use strict';

var debug = require('debug')('__create.test:');
var weUserModel = require('../../../model/wechat_user_info.js');
var weUserLogic = require('../../logic/weUser.logic.js');
var data = weUserModel.dataModel;

// weUserLogic.createUserFromWechat(data,function(err,result){
// 	debug(err);
// 	debug(result);
// });

// weUserLogic.checkMobile('id',function(err,mobile){
// 	debug(err);
// 	debug(mobile);
// });
// weUserLogic.checkExists('open_id',function(err,result){
// 	if(err){
// 		debug(err);
// 	}else{
// 		debug(result);
// 	}
// });
var query_create = {
	fields: data,
	values: data
};
// var query_lookup = {
// 	select: {
// 		id: 'id',
// 		userId: 'userId'
// 	},
// 	match: {
// 		id: 'open_id'
// 	}
// };

// var query_remove = {
// 	match: {
// 		id: 'open_id'
// 	}
// };

// var query_update = {
// 	match: {
// 		id: 'open_id'
// 	},
// 	update: {
// 		city: 'shanghai'
// 	}
// };

// var queryStr = {
// 	sqlstr: 'SELECT * FROM tb_wechat_user_info;'
// };

weUserModel.create(query_create, function(err, result) {
	if (err) {
		debug(err);
	} else {
		debug(result);
		debug('create a record');
	}
});

// weUserModel.lookup(query_lookup,function(err,result){
// 	if(err){
// 		debug(err);
// 	}else{
// 		debug(result);
// 		debug('lookup');
// 	}
// });

// weUserModel.remove(query_remove, function(err,result){
// 	if(err){
// 		debug(err);
// 	}else{
// 		debug(result);
// 		debug('remove');
// 	}	
// });

// weUserModel.update(query_update, function(err, result) {
// 	if (err) {
// 		debug(err);
// 	} else {
// 		debug(result);
// 		debug('update');
// 	}
// });
// 
// weUserModel.query(queryStr,function(err,result){
// 	if(err){
// 		debug(err);
// 	}else{
// 		debug('query finished');
// 		debug(result.length);
// 	}
// });