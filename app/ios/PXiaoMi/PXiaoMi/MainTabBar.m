//
//  MainTabBar.m
//  PXiaoMi
//
//  Created by ThugKd on 7/31/16.
//  Copyright © 2016 dmtec. All rights reserved.
//
#import "MainTabBar.h"
#import "MainTabBarButton.h"

@interface MainTabBar ()
@property(nonatomic, strong)NSMutableArray *tabbarBtnArray;
@property(nonatomic, weak)MainTabBarButton *selectedButton;
@end

@implementation MainTabBar
- (NSMutableArray *)tabbarBtnArray{
    if (!_tabbarBtnArray) {
        _tabbarBtnArray = [NSMutableArray array];
    }
    return  _tabbarBtnArray;
}

- (instancetype)init{
    if (self = [super init]) {
        self.backgroundColor = [UIColor whiteColor];
            //[self SetupWriteButton];
    }
    
    return self;
}

- (void)layoutSubviews{
    [super layoutSubviews];
        //self.writeButton.center = CGPointMake(self.frame.size.width*0.5, self.frame.size.height*0.5);
    
    CGFloat btnY = 0;
    CGFloat btnW = self.frame.size.width/(self.subviews.count);
    CGFloat btnH = self.frame.size.height;
    
    for (int nIndex = 0; nIndex < self.tabbarBtnArray.count; nIndex++) {
        CGFloat btnX = btnW * nIndex;
        MainTabBarButton *tabBarBtn = self.tabbarBtnArray[nIndex];
        if (nIndex > 2) {
            btnX += btnW;
        }
        tabBarBtn.frame = CGRectMake(btnX, btnY, btnW, btnH);
        tabBarBtn.tag = nIndex;
    }
}

- (void)addTabBarButtonWithTabBarItem:(UITabBarItem *)tabBarItem{
    MainTabBarButton *tabBarBtn = [[MainTabBarButton alloc] init];
    tabBarBtn.tabBarItem = tabBarItem;
    [tabBarBtn addTarget:self action:@selector(ClickTabBarButton:) forControlEvents:UIControlEventTouchDown];
    [self addSubview:tabBarBtn];
    [self.tabbarBtnArray addObject:tabBarBtn];
    
        //default selected first one
    if (self.tabbarBtnArray.count == 1) {
        [self ClickTabBarButton:tabBarBtn];
    }
}

- (void)ClickTabBarButton:(MainTabBarButton *)tabBarBtn{
    
    if ([self.delegate respondsToSelector:@selector(tabBar:didSelectedButtonFrom:to:)]) {
        [self.delegate tabBar:self didSelectedButtonFrom:self.selectedButton.tag to:tabBarBtn.tag];
    }
    
    self.selectedButton.selected = NO;
    tabBarBtn.selected = YES;
    self.selectedButton = tabBarBtn;
}
@end
