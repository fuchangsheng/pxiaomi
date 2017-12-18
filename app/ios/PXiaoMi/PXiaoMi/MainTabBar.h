//
//  MainTabBar.h
//  PXiaoMi
//
//  Created by ThugKd on 7/31/16.
//  Copyright © 2016 dmtec. All rights reserved.
//

#import <UIKit/UIKit.h>

@class MainTabBar;

@protocol MainTabBarDelegate <NSObject>

@optional
- (void)tabBar:(MainTabBar *)tabBar didSelectedButtonFrom:(long)fromBtnTag to:(long)toBtnTag;
- (void)tabBarClickWriteButton:(MainTabBar *)tabBar;
@end

@interface MainTabBar : UIView
- (void)addTabBarButtonWithTabBarItem:(UITabBarItem *)tabBarItem;
@property(nonatomic, weak)id <MainTabBarDelegate>delegate;
@end
