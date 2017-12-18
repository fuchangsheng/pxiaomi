//
//  RetrievePasswordViewController.m
//  PXiaoMi
//
//  Created by dmtec on 16/7/29.
//  Copyright © 2016年 dmtec. All rights reserved.
//

#import "RetrievePasswordViewController.h"
#import "MainUITabBarController.h"
#import "MBProgressHUD+MJ.h"
#import "AFNetworking.h"

@interface RetrievePasswordViewController ()
@property (weak, nonatomic) IBOutlet UITextField *tf_inputPassword;
@property (weak, nonatomic) IBOutlet UITextField *tf_confirmPassword;



@end

@implementation RetrievePasswordViewController
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

- (IBAction)btnConfirm:(id)sender {
    
    if (![_tf_inputPassword.text isEqualToString:_tf_confirmPassword.text]) {
        [MBProgressHUD showError:@"两次密码输入不同"];
    }else{
        AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
        manager.requestSerializer = [AFHTTPRequestSerializer serializer];
        manager.responseSerializer = [AFJSONResponseSerializer serializer];
        manager.responseSerializer.acceptableContentTypes = [manager.responseSerializer.acceptableContentTypes setByAddingObject:@"text/html"];
        [manager POST:@"http://172.16.1.132:6188/v1/password/inputNewPass"
           parameters:@{@"mobile" : _mobile,
                        @"password" : _tf_inputPassword.text}
              success:^(AFHTTPRequestOperation *operation, id responseObject) {
                  NSNumber *status = [responseObject objectForKey:@"status"];
                  switch ([status intValue]) {
                      case 0:{
                          MainUITabBarController *next = [[MainUITabBarController alloc] init];
                          next = [self.storyboard instantiateViewControllerWithIdentifier:@"MainUITabBarController"];
                          next.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
                          [self presentViewController:next animated:true completion:nil];
                          
                      }
                          break;
                      default:
                          [MBProgressHUD showError:@"修改失败"];
                          break;
                  }
                  
              }
              failure:^(AFHTTPRequestOperation *operation, NSError *error) {
                  [MBProgressHUD showError: [NSString stringWithFormat:@"%@",error]];
              }];
        
    }
}

- (IBAction)btnOK:(id)sender {
    [self btnConfirm:sender];
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
