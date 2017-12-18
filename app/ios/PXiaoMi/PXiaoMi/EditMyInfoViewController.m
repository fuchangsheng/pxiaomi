//
//  EditMyInfoViewController.m
//  PXiaoMi
//
//  Created by dmtec on 16/8/5.
//  Copyright © 2016年 dmtec. All rights reserved.
//

#import "EditMyInfoViewController.h"
#import "MyInfoViewController.h"
#import "NSDate+Helper.h"
#import "AFNetworking.h"
#import "MBProgressHUD+MJ.h"

@interface EditMyInfoViewController ()
@property (weak, nonatomic) IBOutlet UIImageView *iv_portrait;
@property (weak, nonatomic) IBOutlet UILabel *ll_gender;
@property (weak, nonatomic) IBOutlet UITextField *tf_userName;
@property (weak, nonatomic) IBOutlet UILabel *ll_birthday;
@property (weak, nonatomic) IBOutlet UITextField *tf_email;

@end

@implementation EditMyInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [_iv_portrait.layer setCornerRadius:CGRectGetHeight([_iv_portrait bounds]) / 2];
    _iv_portrait.layer.masksToBounds = YES;
    _iv_portrait.layer.borderWidth = 1;
    _iv_portrait.layer.borderColor = [[UIColor whiteColor] CGColor];
    
        // today date
    NSDate *currentDate = [NSDate date];
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"YYYY-MM-dd"];
    NSString *dateString = [dateFormatter stringFromDate:currentDate];
    _ll_birthday.text = dateString;
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
                     
                     //userName
                     NSString *userName = [result objectForKey:@"userName"];
                     if (![userName isKindOfClass:[NSNull class]]) {
                         _tf_userName.text = userName;
                     }
                     
                     NSNumber *gender = [result objectForKey:@"gender"];
                     if (![gender isKindOfClass:[NSNull class]]) {
                         if ([gender intValue] == 0) {
                             _ll_gender.text = @"男";
                         }else if([gender intValue]){
                             _ll_gender.text = @"女";
                         }else{
                             _ll_gender.text = @" ";
                         }
                     }

                     NSString *email = [result objectForKey:@"email"];
                     if (![email isKindOfClass:[NSNull class]]) {
                         _tf_email.text = email;
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

/*
    event choose gender
 */
- (IBAction)clickChooseGender:(id)sender {
    
}

/*
    event choose Birthday by DatePicker
 */
- (IBAction)clickChooseBirthday:(id)sender {
    
    UIDatePicker *picker = [[UIDatePicker alloc]init];
    picker.datePickerMode = UIDatePickerModeDate;
    
    picker.frame = CGRectMake(0, 40, 320, 200);
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"\n\n\n\n\n\n\n\n\n\n\n" message:nil preferredStyle:UIAlertControllerStyleActionSheet];
    
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleCancel handler:^(UIAlertAction *action) {
        NSDate *date = picker.date;
        
        _ll_birthday.text = [date stringWithFormat:@"yyyy-MM-dd"];
        
    }];
    [alertController.view addSubview:picker];
    [alertController addAction:cancelAction];
    [self presentViewController:alertController animated:YES completion:nil];
}

- (IBAction)btnConfirm:(id)sender {
    
   
    if(_tf_email.text.length == 0 || _tf_userName.text.length == 0){
        [MBProgressHUD showError:@"昵称或密码不能为空"];
    }else if(![self isValidateEmail:_tf_email.text]){
        [MBProgressHUD showError:@"邮箱格式不对"];
    }else{
        NSDate *currentDate = [NSDate date];
        NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
        [dateFormatter setDateFormat:@"YYYY-MM-dd"];
        NSString *dateString = [dateFormatter stringFromDate:currentDate];
        NSNumber *age = [NSNumber numberWithInt:[[dateString substringToIndex:4] intValue]-[[_ll_birthday.text substringToIndex:4] intValue]];
        NSNumber *gender = [NSNumber numberWithInt:0];
        if ([_ll_gender.text isEqualToString:@"女"]) {
            gender = [NSNumber numberWithInt:1];
        }
        
        NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
        NSString *userId = [userDefaults objectForKey:@"userId"];
        
        AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
        manager.requestSerializer = [AFHTTPRequestSerializer serializer];
        manager.responseSerializer = [AFJSONResponseSerializer serializer];
        manager.responseSerializer.acceptableContentTypes = [manager.responseSerializer.acceptableContentTypes setByAddingObject:@"text/html"];
        
        [manager POST:@"http://172.16.1.132:6188/v1/user/updateUser"
           parameters:@{@"userId" : userId,
                        @"userName" : _tf_userName.text,
                        @"age" : age,
                        @"gender" : gender,
                        @"email" : _tf_email.text}
              success:^(AFHTTPRequestOperation *operation, id responseObject) {
                  NSString *status = responseObject[@"status"];
                  switch ([status intValue]) {
                      case 0:{
                          MyInfoViewController *next = [[MyInfoViewController alloc] init];
                          next = [self.storyboard instantiateViewControllerWithIdentifier:@"MyInfoViewController"];
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

/*
    verify email
 */
-(BOOL)isValidateEmail:(NSString *)email {
    NSString *emailRegex = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
    return [emailTest evaluateWithObject:email];
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
