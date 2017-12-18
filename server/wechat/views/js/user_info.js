'use strict';
var userId = getCookie('userId');
var options = {
	userName: '未知用户',
	portrait: 'http://pxiaomi.com/favicon.ico',
	gender: '0',
	age: '未设置',
	email: '未设置',
	mobile: '未设置'
};

var loadingToast = $('#loadingToast');

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i].trim();
		if (c.indexOf(name) === 0) return c.substring(name.length, c.length);
	}
	return "";
};

function showToast(content, type) {
	var toast = $('#toast');
	var toast_content = $('#toast_content');
	toast_content.text("");
	toast_content.html("<br>" + content);
	var icon = $('#icon-toast');
	if (type === -1) {
		icon.css('color', 'red');
	} else {
		icon.css('color', 'white');
	}
	toast.show(200);
	setTimeout(function() {
		toast.hide();
	}, 1800);
}

function initData() {
	loadingToast.show();
	$.get('/v1/user/userInfo', {
		userId: userId
	}, function(data, status) {
		if (data.status === 0) {
			for (var k in options) {
				options[k] = data.result[k] || options[k];
			}
		}
		loadingToast.hide();
		setInfo(options);
	});
}

function setInfo(options) {
	$('#portrait').attr('src', options.portrait);
	$('#name').text(options.userName);
	$('#mobile').text(options.mobile);
	$('#sex').text((options.gender === 0) ? '未设置' : (options.gender === 1 ? '男' : '女'));
	$('#age').text(options.age);
	$('#email').text(options.email);
	$('#name_in').val(options.userName);
	$('#mobile_in').text(options.mobile);
	$('#x11').attr('checked', options.gender === 1 ? 'checked' : '');
	$('#x11').attr('checked', options.gender === 2 ? 'checked' : '');
	$('#age_in').val(options.age);
	$('#email_in').val(options.email);
}

function getInfo() {
	return {
		userId: userId,
		userName: $('#name_in').val(),
		age: $('#age_in').val(),
		gender: $('#x11').attr('checked') === 'checked' ? 1 : 2,
		email: $('#email_in').val()
	};
}

function isInteger(obj) {
	return (typeof(obj) === 'number') && (obj % 1 === 0);
}

function isEmail(email) {
	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	return reg.test(email);
}

function checkForm(info) {
	if (info.userId) {
		if (!info.userName) {
			showToast('用户名不能为空', -1);
			return false;
		} else if (!(isInteger(Number(info.age)) && Number(info.age) <= 100 && Number(info.age) >= 0)) {
			showToast('请输入正确的年龄', -1);
			return false;
		} else if (!(info.gender === 0 || info.gender === 1 || info.gender === 2)) {
			return false;
		} else if (!isEmail(info.email)) {
			showToast('请输入正确的邮箱', -1);
			return false;
		} else {
			return true;
		}
	}
	return false;
}

function initListener() {
	var modify = $('#modify');
	var confirm = $('#confirm');
	modify.click(function() {
		$('#info_list').hide();
		$('#info_input').show();
	});
	confirm.click(function() {
		var info = getInfo();
		if (checkForm(info)) {
			loadingToast.show();
			$.post('/v1/user/updateUser', info, function(data, status) {
				loadingToast.hide();
				if (data.status === 0) {
					showToast('修改成功', 0);
					$('#info_input').hide();
					setInfo({
						portrait: options.portrait,
						userName: info.userName,
						mobile: options.mobile,
						gender: info.gender,
						email: info.email,
						age: info.age
					});
					$('#info_list').show();
				} else {
					showToast('修改失败', -1);
				}
			});
		}
	});

}

$(document).ready(function() {
	initData();
	initListener();
});