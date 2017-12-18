//
//  RegisterEnterPasswordViewController.m
//  PXiaoMi
//
//  Created by dmtec on 8/11/16.
//  Copyright © 2016 dmtec. All rights reserved.
//

#import "RegisterEnterPasswordViewController.h"
#import "AFNetworking.h"
#import "MBProgressHUD+MJ.h"
#import "MainUITabBarController.h"


@interface RegisterEnterPasswordViewController ()
@property (weak, nonatomic) IBOutlet UITextField *tf_inputPassword;
@property (weak, nonatomic) IBOutlet UITextField *tf_confirmPassword;

@end

@implementation RegisterEnterPasswordViewController
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
        register success! enter MainUITabBarController
     */
- (IBAction)btnEnterMain:(id)sender {
    if (![_tf_inputPassword.text isEqualToString:_tf_confirmPassword.text]) {
        [MBProgressHUD showError:@"两次密码输入不同"];
    }else{
        AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
        manager.requestSerializer = [AFHTTPRequestSerializer serializer];
        manager.responseSerializer = [AFJSONResponseSerializer serializer];
        manager.responseSerializer.acceptableContentTypes = [manager.responseSerializer.acceptableContentTypes setByAddingObject:@"text/html"];
 
        [manager POST:@"http://172.16.1.132:6188/v1/user/register"
           parameters:@{@"mobile" : _mobile,
                      @"userName" : @"票小秘",
                      @"password" : _tf_inputPassword.text}
              success:^(AFHTTPRequestOperation *operation, id responseObject) {
                  NSDictionary *result = [[NSDictionary alloc] init];
                  NSNumber *status = [responseObject objectForKey:@"status"];
                  switch ([status intValue]) {
                      case 0:{
                          result = [responseObject objectForKey:@"result"];
                          NSString *userId = [result objectForKey:@"userId"];
                          NSLog(@"%@",userId);
                          [self saveUserId:userId];

                          MainUITabBarController *next = [[MainUITabBarController alloc] init];
                          next = [self.storyboard instantiateViewControllerWithIdentifier:@"MainUITabBarController"];
                          next.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
                          [self presentViewController:next animated:true completion:nil];

                      }
                          break;
                      default:
                          [MBProgressHUD showError:@"密码格式不对"];
                          break;
                  }
                  
              }
              failure:^(AFHTTPRequestOperation *operation, NSError *error) {
                  [MBProgressHUD showError: [NSString stringWithFormat:@"%@",error]];
              }];

    }
}

- (IBAction)btnOK:(id)sender {
    [self btnEnterMain:sender];
}

/*
    save userId to user.plist
 */
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
