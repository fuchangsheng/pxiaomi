'use strict';

$(document).ready(function(){

	// $('#btn_01').on('click',function(){
	// 	$(this).fadeOut(200);
	// 	$(this).fadeIn(200);
	// });

	$('#btn_01').click(function(){
		// $(this).fadeOut(200);
		// $(this).fadeIn(200);
		// $(this).fadeTo(200,0.25);
		$('p').slideDown();
		$('p').slideUp();
		alert($('#btn_01').text());
		alert($('#btn_01').html());
		alert($('head').html());
	});

	$('P').on('click',function(){
		$(this).hide();
		$(this).fadeIn(800);
	});

	$('.para').on('click',function(){
		$(this).fadeOut(300);
		$(this).show();
	});

	$('#btn_02').click(function(){
		var text = $('#inputArea').val();
		alert(text);
		$('#btn_01').text(text);
		$('#inputArea').val('13456');
		$('#btn_02').html('<br/>123<br/>');
		console.log($('#btn_02').html());
	});

	$('.viewAttr').click(function(){
		var attr = $('#aHref').attr('href');
		alert(attr);
		$('#aHref').html('菜鸟');
		$('#aHref').attr('href',function(index,originalUrl){
			return originalUrl + '/?user=2/';
		});
	});

	$('#prepend').click(function(){
		$('.p_1').prepend('<br>333333333333333<br>');
		$('.p_1').append('<br>222222222222222<br>');
	});
});






