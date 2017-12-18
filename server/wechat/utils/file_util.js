'use strict';

var fs = require('fs');
var debug = require('debug')('_wechat:fileUtil:');

/**
 * By calling this function,you can get the text content in the file.
 * you need to provide a handle function to process the content text.
 * @param  {String} path   the file path.
 * @param  {function} handle 	handle function with a param.
 * @return {}  
 */
var readFromFile = function(path, handle) {
	var buf = new Buffer(40960);
	fs.open(path, 'r', function(err, fd) {
		if (err) {
			handle(null);
			return debug(err);
		} else {
			fs.read(fd, buf, 0, buf.length, 0, function(err, bytes) {
				if (err) {
					debug(err);
					handle(null);
				} else {
					if (bytes > 0) {
						debug('read ok.');
						var data = buf.slice(0, bytes).toString();
						debug(data);
						handle(data);
					} else {
						handle(null);
					}
				}
			});
		}
	});
};

/**
 * write data to the file 
 * @param  {String} path 	file path 
 * @param  {String} data 
 * @return ;
 */
var writeFile = function(path, data) {
	fs.writeFile(path, data, function(err) {
		if (err) {
			return debug(err);
		} else {
			debug('write  ok .');
		}
	});
}

exports.readFromFile = readFromFile;
exports.writeFile = writeFile;