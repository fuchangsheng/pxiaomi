//
//  ScrollView.m
//  PXiaoMi
//
//  Created by dmtec on 16/8/1.
//  Copyright © 2016年 dmtec. All rights reserved.
//

#import "ScrollView.h"


@interface ScrollView ()<UIScrollViewDelegate>
@property(nonatomic,strong)NSMutableArray *arraySource;//数据源
@property(nonatomic,strong)UIScrollView   *bannerScrollView;
@property(nonatomic,strong)UIImageView    *bannerImageView;
@property(nonatomic,strong)UIPageControl *pageControl;


@property(nonatomic,strong) NSTimer *timer;
@property(nonatomic,assign) NSTimeInterval autoScrollTime;//轮播时差
@property(nonatomic,assign) BOOL autoBanner;//是否自动轮播, 默认=YES
@end

@implementation ScrollView

-(instancetype)initWithFrame:(CGRect)frame arraySource:(NSMutableArray *)arraySource{

    if (self=[super initWithFrame:frame]) {
        _arraySource=arraySource;
        dispatch_async(dispatch_get_main_queue(), ^{
            [self scrollBannerView];
        });
    }
    return self;
}

-(void)scrollBannerView{
    //初始化滚动试图
    _bannerScrollView=[[UIScrollView alloc]initWithFrame:CGRectMake(10 , 25, SCREEN_WIDTH-20, 200)];
    //设置滚动区域
    _bannerScrollView.contentSize=CGSizeMake(SCREEN_WIDTH*5, 0);
    _bannerScrollView.delegate=self;
    _bannerScrollView.pagingEnabled = YES;//分页效果
    _bannerScrollView.showsVerticalScrollIndicator = NO;
    _bannerScrollView.showsHorizontalScrollIndicator =NO;
    _bannerScrollView.bounces=NO;


    for (int i=0; i<5; i++) {
        _bannerImageView=[[UIImageView alloc]initWithFrame:CGRectMake(SCREEN_WIDTH*i, 0, _bannerScrollView.frame.size.width, _bannerScrollView.frame.size.height)];
        _bannerImageView.userInteractionEnabled=YES;
        //添加点击手势
        UITapGestureRecognizer *top = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(GestureAction:)];
        _bannerImageView.tag=i+1;
        [_bannerImageView addGestureRecognizer:top];
        //取出数组最后一张图片
        if (i==0) {
            _bannerImageView.image=[UIImage imageNamed:@"gao4.jpg"];
        }
        //取出数组第一张图
        else if (i==4){
            _bannerImageView.image=[UIImage imageNamed:@"gao1.jpg"];
        }
        else{
            _bannerImageView.image=[UIImage imageNamed:[NSString stringWithFormat:@"gao%d.jpg",i]];
        }

        [_bannerScrollView addSubview:_bannerImageView];
    }

    //设置偏移量
    [_bannerScrollView setContentOffset:CGPointMake(SCREEN_WIDTH, 0)];
    //默认开启滚动和设置时间
    _autoBanner=YES;
    _autoScrollTime = 3.0f;



    //开启定时器
    [self timerOn];


    _pageControl = [[UIPageControl alloc]initWithFrame:CGRectMake(0, _bannerScrollView.frame.size.height+15, _bannerScrollView.frame.size.width, 30)];
    //设置总页数
    _pageControl.numberOfPages=3;
    _pageControl.pageIndicatorTintColor=[UIColor lightGrayColor];
    //设置显示某页面
    _pageControl.currentPage = 0;
    _pageControl.currentPageIndicatorTintColor=[UIColor orangeColor];

    [self addSubview:_pageControl];
    [self addSubview:_bannerScrollView];



}





#pragma mark-UIScrollView的代理方法
//用户准备拖拽的时候关闭,定时器
-(void)scrollViewWillBeginDecelerating:(UIScrollView *)scrollView{
    [self timerOff];
}
-(void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView{

    //获取当前的偏移量
    CGPoint current = _bannerScrollView.contentOffset;
    CGPoint offset = CGPointMake(current.x + SCREEN_WIDTH, 0);

    //判断是否已经翻到最后
    if (offset.x == 5 * SCREEN_WIDTH)
        {
        //将当前位置设置为原来的第一张图片
        [_bannerScrollView setContentOffset:CGPointMake(SCREEN_WIDTH, 0)];

        }
    //判断是否已经翻到最前
    if (current.x == 0)
        {
        //将当前位置设置为原来的最后一张图片
        [_bannerScrollView setContentOffset:CGPointMake(3 * SCREEN_WIDTH, 0)];
        }

    [self timerOn];




}
//滑动任何偏移的改变都会触发该方法
//解决最后一张有延时的问题
-(void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    CGPoint current = _bannerScrollView.contentOffset;

    if (current.x == 4 * SCREEN_WIDTH)
        {

        [_bannerScrollView setContentOffset:CGPointMake(SCREEN_WIDTH, 0)];
        }

    //显示滑动点的位置
    _pageControl.currentPage= _bannerScrollView.contentOffset.x/SCREEN_WIDTH-1;
}
//开启定时器
-(void)timerOn{

    if (_autoScrollTime < 0.5 || !_autoBanner){

        return  ;
    }
    //通过定时器来实现定时滚动
    self.timer=[NSTimer scheduledTimerWithTimeInterval:_autoScrollTime target:self selector:@selector(changeOffset) userInfo:nil repeats:YES];
    [[NSRunLoop  currentRunLoop] addTimer:_timer forMode:NSDefaultRunLoopMode];

}


//关闭定时器
-(void)timerOff{

    [self.timer invalidate];
    self.timer=nil;

}
#pragma mark-定时器实现的方法
- (void)changeOffset
{

    //设置滚动偏移
    CGPoint current = _bannerScrollView.contentOffset;
    CGPoint offset = CGPointMake(current.x + SCREEN_WIDTH, 0);
    [_bannerScrollView setContentOffset:offset animated:YES];


    //设置为0时,pageControl.currentPage不会加1;
    if ( _pageControl.currentPage==2) {
        _pageControl.currentPage = 0;
        //改变内容显示的位置瞬间改变
    }
    else{
        _pageControl.currentPage++;

    }



}



#pragma mark-手势
-(void)GestureAction:(UITapGestureRecognizer *)top{
    NSLog(@"tag:%ld",(long)top.view.tag);

}

-(void)removeTimer{
    if (!_timer)return;
    [_timer invalidate];
    _timer = nil;
}

@end
