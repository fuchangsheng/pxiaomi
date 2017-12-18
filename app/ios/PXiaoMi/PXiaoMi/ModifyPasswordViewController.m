//
//  ModifyPasswordViewController.m
//  PXiaoMi
//
//  Created by dmtec on 8/11/16.
//  Copyright © 2016 dmtec. All rights reserved.
//

#import "ModifyPasswordViewController.h"
#import "AFNetworking.h"
#import "MBProgressHUD+MJ.h"
#import "MeViewController.h"

@interface ModifyPasswordViewController ()
@property (weak, nonatomic) IBOutlet UITextField *tf_oldPassword;
@property (weak, nonatomic) IBOutlet UITextField *tf_newPassword;
@property (weak, nonatomic) IBOutlet UITextField *tf_confirmPassword;

@end

@implementation ModifyPasswordViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (IBAction)btnOK:(id)sender {
    if (![_tf_newPassword.text isEqualToString:_tf_confirmPassword.text]) {
        [MBProgressHUD showError:@"两次密码输入不同"];
    }else{
        AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
        manager.requestSerializer = [AFHTTPRequestSerializer serializer];
        manager.responseSerializer = [AFJSONResponseSerializer serializer];
        manager.responseSerializer.acceptableContentTypes = [manager.responseSerializer.acceptableContentTypes setByAddingObject:@"text/html"];
        /*
         get usreId
         */
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        NSString *userId = [userDefaults objectForKey:@"userId"];

        [manager POST:@"http://172.16.1.132:6188/v1/user/updatePas"
           parameters:@{@"userId" : userId,
                      @"password" : _tf_oldPassword.text,
                   @"newPassword" : _tf_newPassword.text}
              success:^(AFHTTPRequestOperation *operation, id responseObject) {
                  NSNumber *status = [responseObject objectForKey:@"status"];
                  switch ([status intValue]) {
                      case 0:{
                          MeViewController *next = [[MeViewController alloc] init];
                          next = [self.storyboard instantiateViewControllerWithIdentifier:@"MeViewController"];
                          next.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
                          [self presentViewController:next animated:true completion:nil];

                      }
                          break;
                      case 1:
                          [MBProgressHUD showError:@"原密码输入错误1"];
                          break;
                      case 2:
                          [MBProgressHUD showError:@"原密码输入错误2"];
                          break;
                      case 3:
                          [MBProgressHUD showError:@"新旧密码相同"];
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
