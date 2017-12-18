'use strict';

var xml2Js = require('xml2js');
var debug = require('debug')('_wechat:xmlUtil:');

var parseXmlString = function(xmlText, handle) {
	xml2Js.parseString(xmlText, {
		explicitArray :  false
	}, function(err, textJson) {
		if (err) {
			debug(err);
			debug('parse failed');
			handle(err, null);
		} else {
			console.log(textJson);
			handle(null, textJson);
		}
	});
};

var fromMessage = function(xmlText, handle) {
	parseXmlString(xmlText, function(err, textJson) {
		if (err) {
			debug(err);
			handle(err, null);
		} else {
			var msg = textJson.xml;
			handle(null, msg);
		}
	});
};


exports.fromMessage = fromMessage;