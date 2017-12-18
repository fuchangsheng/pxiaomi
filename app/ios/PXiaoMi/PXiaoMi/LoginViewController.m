//
//  LoginViewController.m
//  PXiaoMi
//
//  Created by dmtec on 16/7/29.
//  Copyright © 2016年 dmtec. All rights reserved.
//


#import "LoginViewController.h"
#import "MD5String.h"
#import "MBProgressHUD+MJ.h"
#import "AFNetworking.h"
#import "MainUITabBarController.h"
#import "RegisterGetVerifyCodeViewController.h"

@interface LoginViewController ()
@property (weak, nonatomic) IBOutlet UITextField *tf_phoneNumber;
@property (weak, nonatomic) IBOutlet UITextField *tf_password;

@end

@implementation LoginViewController


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
}
/*
    login
 */

- (IBAction)btnLogin:(id)sender {

    NSString *mobile = self.tf_phoneNumber.text;
    NSString *password = self.tf_password.text;

    if (mobile.length == 0 || password.length == 0) {
        [MBProgressHUD showError:@"手机号或密码不能为空"];
    }else{
        //MD5 encrypt
        //password = [MD5String md5:password];

        AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
        manager.requestSerializer = [AFHTTPRequestSerializer serializer];
        manager.responseSerializer = [AFJSONResponseSerializer serializer];
        manager.responseSerializer.acceptableContentTypes = [manager.responseSerializer.acceptableContentTypes setByAddingObject:@"text/html"];
        [manager POST:@"http://172.16.1.132:6188/v1/user/login"
           parameters:@{@"mobile" : mobile,
                        @"password" : password}
              success:^(AFHTTPRequestOperation *operation, id responseObject) {
                  NSNumber *status = [responseObject objectForKey:@"status"];
                  NSDictionary *result = [responseObject objectForKey:@"result"];
                  
                  switch ([status intValue]) {
                      case 0:{
                          NSString *userId = [result objectForKey:@"userId"];
                          [self saveUserId:userId];
                          MainUITabBarController *next = [[MainUITabBarController alloc] init];
                          next = [self.storyboard instantiateViewControllerWithIdentifier:@"MainUITabBarController"];
                          next.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
                          [self presentViewController:next animated:true completion:nil];
                      }
                          break;
                      case 1:
                          [MBProgressHUD showError:@"手机号或密码有误"];
                          break;
                      case 2:
                          [MBProgressHUD showError:@"用户不存在"];
                          break;
                      case 3:
                          [MBProgressHUD showError:@"手机号或密码有误"];
                          break;
                      case 4:
                          [MBProgressHUD showError:@"手机号或密码有误"];
                          break;

                      default:
                          break;
                  }
              }
              failure:^(AFHTTPRequestOperation *operation, NSError *error) {
                  [MBProgressHUD showError: [NSString stringWithFormat:@"%@",error]];
              }];

    }
}


- (IBAction)btnSavePassword:(id)sender {
    

}

- (void)saveUserId:(NSString*)userId{
    NSUserDefaults *UserDefaults = [NSUserDefaults standardUserDefaults];
    [UserDefaults setObject:userId forKey:@"userId"];
    [UserDefaults synchronize];
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
