'use strict';

var express = require('express');
var router = express.Router();
var debug = require('debug')('_wechat:ajaxRouter:');
var processer = require('../logic/ajax_processer.js');

router.post('/bindMobile',processer.bindMobile);

router.get('/queryMobile',processer.queryMobile);

exports.ajaxRouter = router;