'use strict';

$(document).ready(function(){

        var $progress = $('.js_progress'),
            $btnUpload = $('#btnUpload');
        var progress = 0;

        function next() {
            if(progress > 100){
                progress = 0;
                $btnUpload.removeClass('weui-btn_disabled');
                return;
            }
            $progress.css({width: progress + '%'});
            progress = ++progress;
            setTimeout(next, 20);
        }

        $btnUpload.on('click', function(){
            if ($btnUpload.hasClass('weui-btn_disabled')) return;

            $btnUpload.addClass('weui-btn_disabled');
            next();
        });

});