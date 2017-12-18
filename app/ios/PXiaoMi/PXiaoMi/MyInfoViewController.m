//
//  MyInfoViewController.m
//  PXiaoMi
//
//  Created by dmtec on 16/8/5.
//  Copyright © 2016年 dmtec. All rights reserved.
//

#import "MyInfoViewController.h"
#import "UIKit+AFNetworking.h"
#import "AFNetworking.h"
#import "MBProgressHUD+MJ.h"

@interface MyInfoViewController ()

@property (weak, nonatomic) IBOutlet UIImageView *iv_myHeadPortrait;
@property (weak, nonatomic) IBOutlet UILabel *ll_gender;
@property (weak, nonatomic) IBOutlet UILabel *ll_age;
@property (weak, nonatomic) IBOutlet UILabel *ll_userName;
@property (weak, nonatomic) IBOutlet UILabel *ll_phoneNumber;
@property (weak, nonatomic) IBOutlet UILabel *ll_email;

@end

@implementation MyInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [_iv_myHeadPortrait.layer setCornerRadius:CGRectGetHeight([_iv_myHeadPortrait bounds]) / 2];
    _iv_myHeadPortrait.layer.masksToBounds = YES;
    _iv_myHeadPortrait.layer.borderWidth = 1;
    _iv_myHeadPortrait.layer.borderColor = [[UIColor whiteColor] CGColor];
    
    [self getUserInfo];
}

- (void)getUserInfo{
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    NSString *userId = [userDefaults objectForKey:@"userId"];
    
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    manager.requestSerializer = [AFHTTPRequestSerializer serializer];
    manager.responseSerializer = [AFJSONResponseSerializer serializer];
    manager.responseSerializer.acceptableContentTypes = [manager.responseSerializer.acceptableContentTypes setByAddingObject:@"text/html"];
    
    [manager GET:@"http://172.16.1.132:6188/v1/user/userInfo"
      parameters:@{@"userId" : userId}
         success:^(AFHTTPRequestOperation *operation, id responseObject) {
             NSDictionary *result = [responseObject objectForKey:@"result"];
             NSString *status = responseObject[@"status"];
             switch ([status intValue]) {
                 case 0:{
                     
                         //gender
                     NSNumber *genderString = [result objectForKey:@"gender"];
                     if (![genderString isKindOfClass:[NSNull class]]) {
                         int gender = [genderString intValue];
                         if (gender == 0) {
                             _ll_gender.text = @"男";
                         }else if(gender == 1){
                             _ll_gender.text = @"女";
                         }else{
                             _ll_gender.text = @" ";
                         }
                     }
                         //age
                     NSNumber *age = [result objectForKey:@"age"];
                     if (![age isKindOfClass:[NSNull class]]) {
                         _ll_age.text = [NSString stringWithFormat:@"%@",age];
                     }else{
                         _ll_age.text = [NSString stringWithFormat:@"%d",0];
                     }
                         //userName
                     NSString *userName = [result objectForKey:@"userName"];
                     if (![userName isKindOfClass:[NSNull class]]) {
                         _ll_userName.text = userName;
                     }
                         //phoneNumber
                     NSString *phoneNumber =[result objectForKey:@"mobile"];
                     if (![phoneNumber isKindOfClass:[NSNull class]]) {
                         _ll_phoneNumber.text =  phoneNumber;
                     }
                         //email
                     NSString *email = [result objectForKey:@"email"];
                     if (![email isKindOfClass:[NSNull class]]) {
                         _ll_email.text = email;
                     }
                     
                     /*
                      not finsih
                      */
                         //  NSString *portrait = [result objectForKey:@"portrait"];
                 }
                     break;
                 case 1:
                     [MBProgressHUD showError:@"参数错误"];
                     break;
                 case 2:
                     [MBProgressHUD showError:@"用户需重新登录"];
                     break;
                 case 3:
                     [MBProgressHUD showError:@"参数错误"];
                     break;
                 default:
                     NSLog(@"default");
                     break;
             }
         }
         failure:^(AFHTTPRequestOperation *operation, NSError *error) {
             [MBProgressHUD showError: [NSString stringWithFormat:@"%@",error]];
         }];

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
