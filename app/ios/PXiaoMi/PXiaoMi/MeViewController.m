//
//  MeViewController.m
//  PXiaoMi
//
//  Created by dmtec on 7/31/16.
//  Copyright © 2016 dmtec. All rights reserved.
//

#import "MeViewController.h"
#import "AFNetworking.h"
#import "MBProgressHUD+MJ.h"
#import "LoginViewController.h"
#import "UIKit+AFNetworking.h"

@interface MeViewController ()
@property (weak, nonatomic) IBOutlet UIImageView *iv_myHeadPortrait;
@property (weak, nonatomic) IBOutlet UILabel *ll_userName;
@property (weak, nonatomic) IBOutlet UILabel *ll_phoneNumber;

@end

@implementation MeViewController
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
    self.view.backgroundColor = [UIColor whiteColor];
    self.title = @"我的";

    [_iv_myHeadPortrait.layer setCornerRadius:CGRectGetHeight([_iv_myHeadPortrait bounds]) / 2];
    _iv_myHeadPortrait.layer.masksToBounds = YES;
    _iv_myHeadPortrait.layer.borderWidth = 1;
    _iv_myHeadPortrait.layer.borderColor = [[UIColor whiteColor] CGColor];
    [_iv_myHeadPortrait setImageWithURL:[NSURL URLWithString:[self getImageURL]] placeholderImage:nil] ;
    
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
                         _ll_userName.text = userName;
                     }
                         //phoneNumber
                     NSString *phoneNumber =[result objectForKey:@"mobile"];
                     if (![phoneNumber isKindOfClass:[NSNull class]]) {
                         _ll_phoneNumber.text =  phoneNumber;
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


- (NSString *)getImageURL {
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    manager.requestSerializer = [AFHTTPRequestSerializer serializer];
    manager.responseSerializer = [AFJSONResponseSerializer serializer];
    manager.responseSerializer.acceptableContentTypes = [manager.responseSerializer.acceptableContentTypes setByAddingObject:@"text/html"];

    /*
     get usreId
     */
    NSUserDefaults *userDefaults =[NSUserDefaults standardUserDefaults];
    NSString *userId = [userDefaults objectForKey:@"userId"];
    
    __block NSString *imageURL = nil;
    [manager GET:@"http://172.16.1.132:6188v1/user/userInfo"
      parameters:@{@"userId" : userId}
         success:^(AFHTTPRequestOperation *operation, id responseObject) {
            
             NSDictionary *result = [responseObject objectForKey:@"result"];
             NSNumber *status = [responseObject objectForKey:@"status"];

             switch ([status intValue]) {
                 case 0:{
                     imageURL =  [result objectForKey:@"portrait"];
                 }
                     break;

                 default:
                     [MBProgressHUD showError:@"网络异常"];
                     break;
             }
         }
         failure:^(AFHTTPRequestOperation *operation, NSError *error) {
             [MBProgressHUD showError: [NSString stringWithFormat:@"%@",error]];
         }];
    return imageURL;
}

- (IBAction)btnExit:(id)sender {
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    manager.requestSerializer = [AFHTTPRequestSerializer serializer];
    manager.responseSerializer = [AFJSONResponseSerializer serializer];
    manager.responseSerializer.acceptableContentTypes = [manager.responseSerializer.acceptableContentTypes setByAddingObject:@"text/html"];

    /*
        get usreId
     */
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    NSString *userId = [userDefaults objectForKey:@"userId"];

    [manager GET:@"http://172.16.1.132:6188/v1/user/logout"
       parameters:@{@"userId" : userId}
          success:^(AFHTTPRequestOperation *operation, id responseObject) {

              NSNumber *status = [responseObject objectForKey:@"status"];
              NSLog(@"kkflkjdkfjdk");
              switch ([status intValue]) {
                  case 0:{
                      LoginViewController *next = [[LoginViewController alloc] init];
                      next = [self.storyboard instantiateViewControllerWithIdentifier:@"LoginViewController"];
                      next.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
                      [self presentViewController:next animated:true completion:nil];
                  }
                      break;
                  case 1:
                      [MBProgressHUD showError:@"退出出错"];
                      break;

                  default:
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
