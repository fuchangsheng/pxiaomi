'use strict';

var mysql = require('mysql');

var config = {
	host: 'localhost',
	user: 'pxiaomi',
	password: 'pxiaomi@dmtec.cn',
	database: 'pxiaomi',
	connectionLimit: 20,
	queueLimit: 30
};

var pool = mysql.createPool(config);

module.exports = pool;