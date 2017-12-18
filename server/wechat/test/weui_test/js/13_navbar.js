'use strict';

$(document).ready(function(){
	$('.weui-navbar__item').click(function(){
		$(this).addClass('weui-bar__item_on');
		$(this).siblings('.weui-bar__item_on').removeClass('weui-bar__item_on');
	});

});