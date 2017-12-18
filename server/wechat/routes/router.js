'use strict';

var express = require('express');
var router = express.Router();
var WSRouter = require('./wechat_signature_router.js').wechatSignatureRouter;
var bodyParser = require('body-parser');
// var multer = require('multer');
var msgRouter = require('./message_router.js').msgRouter;
var webRouter = require('./web_router.js').webRouter;
var ajaxRouter = require('./ajax_router.js').ajaxRouter;

router.use('/wechat',bodyParser.raw({type:'text/xml'}));
router.use('/wechat',bodyParser.urlencoded({extended:false}));
router.use('/wechat',bodyParser.json());

//wechatSignatureRouter :
router.use('/wechat',WSRouter);
//messageRouter:
router.use('/wechat',msgRouter);
//web router
router.use('/wechat',webRouter);
//web ajax router
router.use('/wechat',ajaxRouter);

exports.wechatRouter = router;