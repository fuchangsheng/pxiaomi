//
//  MainUITabBarController.m
//  PXiaoMi
//
//  Created by dmtec on 16/7/29.
//  Copyright © 2016年 dmtec. All rights reserved.
//

#import "MainUITabBarController.h"
#import "MainNavigationController.h"
#import "InvoiceViewController.h"
#import "RecordViewController.h"
#import "MeViewController.h"
#import "MainTabBar.h"
#import "AFNetworking.h"
#import "MBProgressHUD+MJ.h"

@interface MainUITabBarController ()<MainTabBarDelegate>
@property(nonatomic, weak)MainTabBar *mainTabBar;
@property(nonatomic,strong)InvoiceViewController *invoiceVC;
@property(nonatomic,strong)RecordViewController *recordVC;
@property(nonatomic,strong)MeViewController *meVC;
@end

@implementation MainUITabBarController
- (instancetype)init
{
    self = [super init];
    if (self) {

    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    // Do any additional setup after loading the view.
    [self setMainTabBar];
    [self setAllControllers];
}

- (void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    
    for (UIView *child in self.tabBar.subviews) {
        if ([child isKindOfClass:[UIControl class]]) {
            [child removeFromSuperview];
        }
    }
}

- (NSString *)getPictureURL{
//    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
//    manager.requestSerializer = [AFHTTPRequestSerializer serializer];
//    manager.responseSerializer = [AFHTTPResponseSerializer serializer];
//    manager.responseSerializer.acceptableContentTypes = [manager.responseSerializer.acceptableContentTypes setByAddingObject:@"text/html"];
//        //李杰尚未完成
//    [manager POST:@"http://172.16.1.132:6188/v1/user/smsCode/request"
//       parameters:@{@"mobile" : mobile}
//          success:^(AFHTTPRequestOperation *operation, id responseObject) {
//              NSDictionary *getResult = [NSJSONSerialization JSONObjectWithData:responseObject options:NSJSONReadingMutableContainers error:nil];
//              NSString *status = [getResult objectForKey:@"status"];
//              
//              switch ([status intValue]) {
//                  case 0:
//                      break;
//                  case 1:
//                      [MBProgressHUD showError:@"短信发送失败"];
//                      break;
//                  default:
//                      break;
//              }
//              
//          }
//          failure:^(AFHTTPRequestOperation *operation, NSError *error) {
//              [MBProgressHUD showError: [NSString stringWithFormat:@"%@",error]];
//          }];
    return nil;
}

- (void)setMainTabBar{
    MainTabBar *mainTabBar = [[MainTabBar alloc] init];
    mainTabBar.frame = self.tabBar.bounds;
    mainTabBar.delegate = self;
    [self.tabBar addSubview:mainTabBar];
    _mainTabBar = mainTabBar;
}

- (void)setAllControllers{
    NSArray *titles = @[@"开票",@"信息",@"我的"];
    NSArray *images = @[@"invoice_gray",@"record_gray",@"me_gray"];
    NSArray *selectImages = @[@"invoice_blue",@"record_blue",@"me_blue"];
    
    InvoiceViewController *invoiceVC = [[InvoiceViewController alloc] init];
    self.invoiceVC = invoiceVC;
    RecordViewController *recordVC = [[RecordViewController alloc] init];
    self.recordVC = recordVC;
    MeViewController *meVC = [[MeViewController alloc] init];
    self.meVC = meVC;
    
    NSArray *viewControllers = @[invoiceVC,recordVC,meVC];
    
    for (int i = 0; i < viewControllers.count; i++) {
        UIViewController *childVC = viewControllers[i];
        [self setChildVC:childVC title:titles[i] image:images[i] selectedImage:selectImages[i]];
    }
    
}

- (void)setChildVC:(UIViewController *)childVc title:(NSString *)title image:(NSString *)imageName selectedImage:(NSString *)selectedImageName{
    MainNavigationController *nav = [[MainNavigationController alloc] initWithRootViewController:childVc];
    childVc.tabBarItem.image = [UIImage imageNamed:imageName];
    childVc.tabBarItem.selectedImage = [UIImage imageNamed:selectedImageName];
    childVc.tabBarItem.title = title;
    [self.mainTabBar addTabBarButtonWithTabBarItem:childVc.tabBarItem];
    [self addChildViewController:nav];
}


#pragma mark --------------------mainTabBar delegate
- (void)tabBar:(MainTabBar *)tabBar didSelectedButtonFrom:(long)fromBtnTag to:(long)toBtnTag{
    self.selectedIndex = toBtnTag;
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
