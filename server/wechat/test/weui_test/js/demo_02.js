'use strict';

$(document).ready(function(){

	$('#btn_a').click(function(){
		$('#linkA').attr({
			"href":"http://www.baidu.com/",
			"title":"baidu"
		});
	});

	$('#btn_01').click(function(){
		alert($('.myParagraph').text());
	});

	$('#btn_02').click(function(){
		alert($('#input_text').val());
	});

	$('#btn_03').click(function(){
		alert($('#myForm').html());
		alert($('#myForm').attr("id"));
	})
	
	$('#btn_04').click(function(){
		$('.myParagraph').text(function(index,text){
			return '更改后：' + text;
		});
	});

	$('#btn_05').click(function(){
		$('#input_text').val(function(index,valText){
			return '更改后：' + valText;
		});
	});

	$('#btn_06').click(function(){
		$('#myForm').html(function(index,htmlText){
			return htmlText + '<br>' + '输入框：' + '<input id="input_text_2" type="input" name="text+2"></input>';
		});
	});
});