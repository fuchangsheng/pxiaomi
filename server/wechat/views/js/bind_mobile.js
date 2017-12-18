'use strict';

var userId = getCookie('userId');
var bindFlag = {
	mobile: false,
	smsCode: falsecreate
};

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i].trim();
		if (c.indexOf(name) === 0) return c.substring(name.length, c.length);
	}
	return "";
};



function initPage() {
	//initPortrait();
}

function checkCodeInput() {
	$('#code').bind('input propertychange', function() {
		var classNum = $('#bindMobile').attr("class").split(' ').length;
		var codeLength = $('#code').val().length;

		if (codeLength !== 6) {
			if (classNum === 2) {
				$('#bindMobile').addClass('weui-btn_disabled');
				bindFlag.smsCode = false;
			}
		} else {
			if (classNum === 3 && bindFlag.mobile) {
				$('#bindMobile').removeClass('weui-btn_disabled');
				bindFlag.smsCode = true;
			}
		}
	});
}

function showToast(content) {
	var toast = $('#toast');
	var toast_content = $('#toast_content');
	toast_content.text("");
	toast_content.html("<br>" + content);
	toast.show(200);
	setTimeout(function() {
		toast.hide();
	}, 1800);
}

function bindGetSmsCode() {
	var loadingToast = $('#loadingToast');
	var getSmsCode = $('#getSmsCode');
	getSmsCode.click(function() {
		if ($('#mobile').val().length === 11) {
			bindFlag.mobile = true;
			loadingToast.show();
			var param = {};
			param.mobile = $('#mobile').val();
			$.post('/v1/user/smsCode/request', param, function(data, status) {
				loadingToast.fadeOut(200);
				if (data.status != 0) {
					showToast('发送失败.')
				} else {
					showToast('验证码已发送');
				}

			});
		} else {
			bindFlag.mobile = false;
			showToast('请输入正确的手机号码');
		}
	});
}

function setBindListener() {
	var bind = $('#bindMobile');
	var loadingToast = $('#loadingToast');
	bind.click(function() {
		if (bind.attr("class").split(' ').length === 2) {
			loadingToast.show();
			var forms = {};
			forms.mobile = $('#mobile').val();
			forms.smsCode = $('#code').val();
			$.post('/v1/user/smsCode/verify', forms, function(data, status) {
				if (data.status == 0) {
					var params = {
						mobile: forms.mobile,
						userId: userId
					};
					$.post('/wechat/bindMobile', params, function(data, status) {
						loadingToast.hide();
						if (data.status == 0) {
							showToast('绑定成功！');
							window.open('/wechat/views_static/bind_ok');
						}
					});
				} else if (data.status == 1) {
					loadingToast.hide();
					showToast('参数错误');
				} else if (data.status == 2) {
					loadingToast.hide();
					showToast('该手机号已绑定');
				} else if (data.status == 3) {
					loadingToast.hide();
					showToast('验证码输入错误,请重试');
				} else {
					loadingToast.hide();
					showToast('验证码输入超时，请重试');
				}
			});
		}

	});
}

$(document).ready(function() {
	initPage();
	checkCodeInput();
	bindGetSmsCode();
	setBindListener();
});