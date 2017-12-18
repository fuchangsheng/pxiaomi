#<center>ios app code</center>

## 代码结构：
	
	-PXiaoMi
		-util(工具类)
		-Login
		-Register
		-RetrievePassword
		-MainViewController
			-Invoice
			-InvoiceRecord
			-Mine
	
***


## 模块状态 *(截止至2016.8.25)*
#### 1.Login

此模块除`记住密码 `功能外完成。考虑到之前的方法每次打开应用时都要进入登陆界面，而一般用户的账号和密码是长时间不变的，如此操作非常不便。服务器端刚好保存有用户的登录状态，在每次加载应用时，在`didFinishLaunchingWithOptions`方法中请求用户当前的登录状态，通过判断是应用开启时进入不同的界面。若登录状态，直接进入`MainUITabBarController `界面，若在登出状态，直接进入`LoginViewController`界面。但由于服务端没有提供此接口，所以还未完成。

![](http://a2.qpic.cn/psb?/V13TnsNU0OGQFS/65PRSmz54o*1S6vNUTMOdKkgzP09RhRGhZ.NsMb4PXY!/m/dAkBAAAAAAAAnull&bo=QQE3AkEBNwIDCSw!&rf=photolist&t=5)
 
-
#### 2.Register

此模块已完成。

![getVerifyCode](http://a2.qpic.cn/psb?/V13TnsNU0OGQFS/S0ZZi7e3deZMty5m7ILRBe2mZ4E*nJW2uOJTS3F4PA4!/m/dAkBAAAAAAAAnull&bo=QgE1AkIBNQIDCSw!&rf=photolist&t=5)
![enterPassword](http://a1.qpic.cn/psb?/V13TnsNU0OGQFS/fREifpfmMpfDIPbPe0yjNvVx3VbPkrDaPNg7AsnJCBQ!/m/dHABAAAAAAAAnull&bo=QQE5AkEBOQIDCSw!&rf=photolist&t=5)

-
#### 3.RetrievePassword

此模块已完成

![getVerifyCode](http://a3.qpic.cn/psb?/V13TnsNU0OGQFS/D4s8*RHII.w3yX.IzAQQbeqVciwLyzv1OIYz2DWXJm0!/m/dAoBAAAAAAAAnull&bo=QwE3AkMBNwIDCSw!&rf=photolist&t=5)
![enterPassword](http://a2.qpic.cn/psb?/V13TnsNU0OGQFS/JFuKmncOCLU7KCL0TWNNA0NwZyGD4HPJvXH1NUTsvgA!/m/dAkBAAAAAAAAnull&bo=QQE6AkEBOgIDCSw!&rf=photolist&t=5)

-
#### 4.Invoice

此模块未完成。税务信息显示界面不能显示税务信息，所以在点击开票按钮进入税务信息界面时,不能选择要使用的税务信息，所以不能成功开票。

![INvoice](http://a4.qpic.cn/psb?/V13TnsNU0OGQFS/3B.Zu48IbiTJto1eaCvoZw6rmJPfia0gdBh*EMxzSA8!/m/dOMAAAAAAAAAnull&bo=QQE4AkEBOAIDCSw!&rf=photolist&t=5)

-
#### 5.InvoiceRecord

此模块未完成，税务信息显示界面不能显示税务信息，每次从服务器端请求的数据完于页面的显示。开始在`viewDidLoad`方法中请求获取所需的税务信息，但是在此方法中请求的数据在界面显示之后才能得到，所以不能显示在界面上，之后又尝试在`viewWillAppear`方法中请求需要的税务信息。可以得到，但是仍不能显示。导致后面的开票操作都不能实现。新建税务信息、查看税务信息详情、编辑税务信息、删除税务信息功能已完成。

![](http://a2.qpic.cn/psb?/V13TnsNU0OGQFS/p65LH8dpLSAxQBPnDU621k*qP2T8HjGowRSgOlOXZqk!/m/dAkBAAAAAAAAnull&bo=QQE4AkEBOAIDCSw!&rf=photolist&t=5)

-
#### 6.Mine

![](http://a4.qpic.cn/psb?/V13TnsNU0OGQFS/XnR2sw26Kzln0pcBHC0LyRTs2D6jCmDfDNso.3EG0RQ!/m/dOMAAAAAAAAAnull&bo=QgE7AkIBOwIDCSw!&rf=photolist&t=5)

* 个人信息
	* 查看个人信息详情（已完成）
	* 编辑个人信息（已完成）
* 扫一扫（未完成。由于模拟器不能测试此功能，所以未实现此功能）
* 历史记录（未完成。开票功能为实现，所以未实现此功能）
* 数据统计（未完成。开票功能为实现，所以未实现此功能）
* 修改密码（已完成）
* 退出登录（已完成）

***

## issue list
1.记住密码模块修改为判断登录状态，服务端未提供接口。

2.税务信息展示在界面显示之前不能获取需要展示的税务信息。

3.修改头像，选好头像图片之后，显示的图片是方形的，而不是之前设置好的圆型的。

4.邮箱验证的正则表达式不规范，在网上找了很多都是一样的。

		