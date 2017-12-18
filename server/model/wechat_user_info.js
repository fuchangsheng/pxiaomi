'use strict';
var model_name = 'wechat_user_info.model';
var table_name = 'tb_wechat_user_info';

var debug = require('debug')('_' + model_name);
var DBModel = require('./DBModel.js');

//Model operation


//reference model
var refModel = {
	id: 'openid_01',
	userId: 'userid_01',
	nickname: 'nickname',
	headimgurl: 'portrait url',
	sex: 0,
	country: '中国',
	province: '',
	city: '',
	privilege: '',
	unionid: '',
    createTime: new Date(),
    updateTime: new Date(),
    state: 0 //0-valid 1-iivalid 2-deleted
};

var dbModel = new DBModel({
	model_name: model_name,
	table_name: table_name,
	refModel: refModel,
	debug: debug
});

var lookup = function(query,fn){
	dbModel.lookup(query,fn);
};

var create = function(query,fn){
	dbModel.create(query,fn);
};

var update = function(query,fn){
	dbModel.update(query,fn);
};

var remove = function(query,fn){
	dbModel.remove(query,fn);
}

var count = function(query,fn){
	dbModel.count(query,fn);
};

var query = function(sqlStr,fn){
	dbModel.query(sqlStr,fn);
};

exports.create = create;
exports.lookup = lookup;
exports.update = update;
exports.remove = remove;
exports.count = count;
exports.query = query;
exports.dataModel = refModel;
exports.table_name = table_name;

