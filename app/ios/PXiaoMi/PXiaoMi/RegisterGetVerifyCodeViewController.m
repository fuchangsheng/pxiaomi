//
//  RegisterGetVerifyCodeViewController.m
//  PXiaoMi
//
//  Created by dmtec on 8/11/16.
//  Copyright © 2016 dmtec. All rights reserved.
//

#import "RegisterGetVerifyCodeViewController.h"
#import "RegisterEnterPasswordViewController.h"
#import "MBProgressHUD+MJ.h"
#import "AFNetworking.h"

@interface RegisterGetVerifyCodeViewController ()
@property (weak, nonatomic) IBOutlet UITextField *tf_phoneNumber;
@property (weak, nonatomic) IBOutlet UITextField *tf_verifyCode;

@end

@implementation RegisterGetVerifyCodeViewController
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
    get verifyCode
 */
- (IBAction)btnGetVerify:(id)sender {
    NSString *mobile = self.tf_phoneNumber.text;
    if (mobile.length == 0 || ![self verifyPhoneNumber:mobile]) {
        [MBProgressHUD showError:@"手机号格式错误"];
    }else{
        AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
        manager.requestSerializer = [AFHTTPRequestSerializer serializer];
        manager.responseSerializer = [AFHTTPResponseSerializer serializer];
        manager.responseSerializer.acceptableContentTypes = [manager.responseSerializer.acceptableContentTypes setByAddingObject:@"text/html"];
        
        [manager POST:@"http://172.16.1.132:6188/v1/user/smsCode/request"
           parameters:@{@"mobile" : mobile}
              success:^(AFHTTPRequestOperation *operation, id responseObject) {
                  NSDictionary *getResult = [NSJSONSerialization JSONObjectWithData:responseObject options:NSJSONReadingMutableContainers error:nil];
                  NSString *status = [getResult objectForKey:@"status"];
                
                  switch ([status intValue]) {
                      case 0:
                          break;
                      case 1:
                          [MBProgressHUD showError:@"短信发送失败"];
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

/*
    send request with mobile and verifyCode ,get response
 */
- (IBAction)btn_Register:(id)sender {

    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    manager.requestSerializer = [AFHTTPRequestSerializer serializer];
    manager.responseSerializer = [AFJSONResponseSerializer serializer];
    manager.responseSerializer.acceptableContentTypes = [manager.responseSerializer.acceptableContentTypes setByAddingObject:@"text/html"];

    [manager POST:@"http://172.16.1.132:6188/v1/user/smsCode/verify"
       parameters:@{@"mobile" : self.tf_phoneNumber.text,
                   @"smsCode" : self.tf_verifyCode.text}
          success:^(AFHTTPRequestOperation *operation, id responseObject) {
              NSString *status = responseObject[@"status"];
              switch ([status intValue]) {
                  case 0:{
                      RegisterEnterPasswordViewController *next = [[RegisterEnterPasswordViewController alloc] init];
                      next = [self.storyboard instantiateViewControllerWithIdentifier:@"RegisterEnterPasswordViewController"];
                      next.mobile = _tf_phoneNumber.text;
                      next.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
                      [self presentViewController:next animated:true completion:nil];
                  }
                      break;
                  case 1:
                      [MBProgressHUD showError:@"参数错误"];
                      break;
                  case 2:
                      [MBProgressHUD showError:@"用户已注册"];
                      break;
                  case 3:
                      [MBProgressHUD showError:@"验证码错误"];
                      break;
                  case 4:
                      [MBProgressHUD showError:@"验证码已过时"];
                      break;
                  default:
                      break;
              }
          }
          failure:^(AFHTTPRequestOperation *operation, NSError *error) {
              [MBProgressHUD showError: [NSString stringWithFormat:@"%@",error]];
          }];
}

- (IBAction)btnOK:(id)sender {
    [self btn_Register:sender];
}

/**
 *  手机号码验证
 *
 *  @param phoneNumber 传入的手机号码
 *
 *  @return 格式正确返回YES  错误 返回NO
 */

- (BOOL)verifyPhoneNumber:(NSString *)phoneNumber{
    /**
     * 手机号码
     * 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
     * 联通：130,131,132,152,155,156,185,186
     * 电信：133,1349,153,180,189,181(增加)
     */
    NSString * MOBIL = @"^1(3[0-9]|5[0-35-9]|8[025-9])\\d{8}$";
    /**
     10         * 中国移动：China Mobile
     11         * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
     12         */
    NSString * CM = @"^1(34[0-8]|(3[5-9]|5[017-9]|8[2378])\\d)\\d{7}$";
    /**
     15         * 中国联通：China Unicom
     16         * 130,131,132,152,155,156,185,186
     17         */
    NSString * CU = @"^1(3[0-2]|5[256]|8[56])\\d{8}$";
    /**
     20         * 中国电信：China Telecom
     21         * 133,1349,153,180,189,181(增加)
     22         */
    NSString * CT = @"^1((33|53|8[019])[0-9]|349)\\d{7}$";
    
    NSPredicate *regextestmobile = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", MOBIL];
    NSPredicate *regextestcm = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", CM];
    NSPredicate *regextestcu = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", CU];
    NSPredicate *regextestct = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", CT];
    
    if (([regextestmobile evaluateWithObject:phoneNumber]
         || [regextestcm evaluateWithObject:phoneNumber]
         || [regextestct evaluateWithObject:phoneNumber]
         || [regextestcu evaluateWithObject:phoneNumber])) {
        return YES;
    }
    
    return NO;
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
