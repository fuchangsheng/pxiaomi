//
//  MainNavigationController.m
//  PXiaoMi
//
//  Created by ThugKd on 7/31/16.
//  Copyright © 2016 dmtec. All rights reserved.
//

#import "MainNavigationController.h"

@interface MainNavigationController ()<UINavigationControllerDelegate>

@end

@implementation MainNavigationController
- (void)viewDidLoad{
    [super viewDidLoad];
    self.delegate = self;
}

    //这里可以封装成一个分类
- (UIBarButtonItem *)barButtonItemWithImage:(NSString *)imageName highImage:(NSString *)highImageName target:(id)target action:(SEL)action
{
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.bounds = CGRectMake(0, 0, 40, 40);
    button.imageView.contentMode = UIViewContentModeLeft;
    button.imageEdgeInsets = UIEdgeInsetsMake(0, -40, 0, 0);
    button.adjustsImageWhenHighlighted = NO;
    [button setImage:[UIImage imageNamed:imageName] forState:UIControlStateNormal];
    [button setImage:[UIImage imageNamed:highImageName] forState:UIControlStateHighlighted];
    [button addTarget:target action:action forControlEvents:UIControlEventTouchUpInside];
    
    return  [[UIBarButtonItem alloc] initWithCustomView:button];
}


#pragma mark --------navigation delegate
    //该方法可以解决popRootViewController时tabbar的bug
- (void)navigationController:(UINavigationController *)navigationController willShowViewController:(UIViewController *)viewController animated:(BOOL)animated{
        //删除系统自带的tabBarButton
    for (UIView *tabBar in self.tabBarController.tabBar.subviews) {
        if ([tabBar isKindOfClass:NSClassFromString(@"UITabBarButton")]) {
            [tabBar removeFromSuperview];
        }
    }
    
}
@end

