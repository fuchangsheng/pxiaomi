'use strict';

var debug = require('debug')('_weUserLogic');
var oauther = require('../utils/web_oauth_util.js');
var weUserModel = require('../../model/wechat_user_info.js');
var dataHelper = require('../../common/dataHelper.js');
var userModel = require('../../model/user_info.js');


//test ok
var checkExists = function(openId, handle) {
	var query = {
		select: {
			id: 'openId',
			userId: 'userId'
		},
		match: {
			id: openId
		}
	};

	weUserModel.lookup(query, function(err, result) {
		if (err) {
			return handle(err, null);
		} else {
			if (result.length) {
				handle(null, {
					exist: true,
					record: result[0]
				});
			} else {
				handle(null, {
					exist: false
				});
			}
		}
	})
};

//
var checkMobile = function(userId, handle) {
	var query = {
		select: {
			id: 'id',
			mobile: 'mobile'
		},
		match: {
			id: userId
		}
	};

	userModel.lookup(query,function(err,rows){
		if(err){
			debug(err);
			handle(err,null);
		}else{
			var mobile = {
				exist: false
			};
			debug(rows);
			if(rows.length){
				if(rows[0].mobile){
					mobile.exist = true;
				}
			}
			handle(null,mobile);
		}
	});
};

//test ok
var createUserFromWechat = function(info, handle) {
	var values = {
		id: dataHelper.createId(info.openid || info.id),
		password: dataHelper.createSha1Data(info.openid || info.id),
		name: info.nickname,
		gender: info.sex,
		portrait: info.headimgurl,
		loginState: 1
	};

	var query = {
		fields: values,
		values: values
	};
	userModel.create(query, function(err, rows) {
		if (err) {
			var msg = err.msg || err;
			console.error('Failed to create the user!' + msg);
			handle(err, null);
		} else {
			handle(null, {
				userId: values.id
			});
		}
	});
};

//test ok
var saveInfo = function(info, handle) {
	var values = {
		id: info.openid || info.openId || info.id,
		userId: info.userId || '',
		nickname: info.nickname || 'username',
		headimgurl: info.headimgurl || '',
		sex: info.sex || 0,
		country: info.country || '未知',
		province: info.province || '未知',
		city: info.city || '未知',
		privilege: JSON.stringify(info.privilege),
		unionid: info.unionid || ''
	};

	var query = {
		fields: values,
		values: values
	};

	weUserModel.create(query, function(err, rows) {
		if (err) {
			var msg = err.msg || err;
			console.error('Failed to create the wechat_user!' + msg);
			handle(err,null);
		} else {
			debug(rows);
			handle(null,rows);
		}
	});

};

var onUserIn = function(param, handle) {
	var options = {
		userId: '',
		hasBinded: false
	};
	checkExists(param.openid, function(err, result) {
		if (err) {
			debug(err);
			return handle(err, null);
		} else {

			//1.1judge user(openid) exists.
			if (result.exist) {
				options.userId = result.record.userId;
				//1.2 judge mobile exists
				checkMobile(options.userId, function(err, mobile) {
					if (err) {
						return debug(err);
					} else {
						if (mobile.exist) {
							options.hasBinded = true;
						} else {
							options.hasBinded = false;
						}
						debug(options);
						return handle(null, options);
					}
				});
			} else {

			//2.1.query wechat user info
				oauther.userInfo(param, function(err, info) {
					if (err) {
						debug(err);
						return handle(err, null);
					} else {

						//2.2.create a user to tb_user_info by this info
						createUserFromWechat(info, function(err, result) {
							if (err) {
								debug(err);
								return handle(err, null);
							} else {
								options.hasBinded = false;
								options.userId = result.userId;
								handle(null, options);

								//2.3.save info to tb_wechat_user_info (async)
								info.userId = result.userId;
								saveInfo(info, function(err,rows) {
									if (err) {
										return debug(err);
									}
								});

							}
						});

					}
				});
			}
		}
	});
};
exports.onUserIn = onUserIn;
exports.checkMobile = checkMobile;