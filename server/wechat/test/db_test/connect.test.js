'use strict';

var pool = require('./db.test.js');
var debug = require('debug')('_db:');

var showResult = function(err, result) {
	if (err) {
		return debug(err);
	} else {
		var length = result.rows.length;
		for (var i = 0; i < length; i++) {
			debug(result.rows[i]);
		}
	}
};

var excuteQuery = function(query, fn) {
	pool.getConnection(function(err, connection) {
		if (err) {
			debug(err);
			return;
		} else {
			debug('data base connected');
			connection.query(query.sql, query.values, function(err, rows, info) {
				debug('query finished');
				if(query.release){
					connection.release();
					debug('connection released .');
				};
				fn(err, {
					rows: rows,
					info: info
				});
			});
		}
	});
};

var queryInsert = function(options) {
	this.sql = "INSERT INTO tb_smsCode_info (id,state) VALUES(?,?)";
	this.values = [options.id, 1];
};

var querySelect = {
	sql: 'SELECT * FROM tb_smsCode_info',
	values: null
};

var queryDelete = {
	sql: "DELETE FROM tb_smsCode_info",
	values: null
};

var queryUpdate = {
	sql:"UPDATE tb_smsCode_info SET state = ? WHERE id = ?",
	values:[200,'20']
};

// excuteQuery(queryDelete, function(err, result) {
// 	showResult(err,result);
// });

// for (var i = 0; i < 21; i++) {
// 	excuteQuery(new queryInsert({
// 		id: i
// 	}), function(err, result) {
// 		showResult(err,event.result);
// 	});
// }

excuteQuery(queryUpdate,function(err,result){
	showResult(err,result);
});