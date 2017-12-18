'use strict';
var userId = getCookie('userId');
var loadingToast = $('#loadingToast');
var infoArr = {};

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
    $('.weui-icon-warn').css('color', type === 0 ? 'white' : 'orange');
    toast.show(200);
    setTimeout(function() {
        toast.hide();
    }, 1800);
}

function showInPage(div, taxInfo, i) {
	console.log(i+1);
	var infoHtml =     '<div class="info" id="info_' + (i+1) + '">'
                     + '<div class="weui-cells">'
                     +     '<a class="weui-cell">'
                     +      '<div class="weui-cell__bd">'
                     +               '<p>发票抬头</p>'
                     +           '</div>'
                     +           '<div class="weui-cell__ft">' + (taxInfo.title || '') + '</div>'
                     +       '</a>'
                     +       '<a class="weui-cell">'
                     +           '<div class="weui-cell__bd">'
                     +              '<p>税号</p>'
                     +          '</div>'
                     +          '<div class="weui-cell__ft">' + (taxInfo.taxNo || '') + '</div>'
                     +       '</a>'
                     +       '<a class="weui-cell weui-cell_access">'
                     +           '<div class="weui-cell__bd">'
                     +              '<p class="click_info">详细信息</p>'
                     +         '</div>'
                     +        '<div class="weui-cell__ft">'
                     +       '</div>'
                     +     '</a>'
                     + '</div>'
                	 + '</div>';
    div.append(infoHtml);
};

function initData() {
    loadingToast.show();
    $.get('/v1/tax/taxInfo', {
        userId: userId
    }, function(data, status) {
        loadingToast.hide();
        if (data.status === 0) {
            infoArr = data.result;
            if (infoArr.length === 0) {
                $('#no-tax_bd').show();
            } else {
                for (var i = 0; i < infoArr.length; i++) {
                    showInPage($('#info_div'), infoArr[i],i);
                    console.log(i);
                }
            }
        }
    });
}

$(document).ready(function() {
    initData();
});
