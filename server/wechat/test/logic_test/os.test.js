'use strict';

var os = require('os');
var debug = require('debug')('_wechat:OSTest');

debug(os.type());
debug(os.platform());
debug(os.hostname());