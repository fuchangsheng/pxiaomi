'use strict';

var userId = getCookie('userId');

function getCookie(cname) {
	var name = cname + '=';
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i].trim();
		if (c.indexOf(name) === 0) {
			return c.substring(name.length, c.length);
		}
	}
	return '';
}

$(document).ready(function() {
	$.get('/wechat/queryMobile', {
		userId: userId
	}, function(data, status) {
		var mobile = data.mobile;
		var name = data.name;
		$('#name').text(name);
		$('#mobile').text(mobile);
	})
});