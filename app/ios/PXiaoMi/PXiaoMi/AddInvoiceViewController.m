//
//  AddInvoiceViewController.m
//  PXiaoMi
//
//  Created by dmtec on 16/8/19.
//  Copyright © 2016年 dmtec. All rights reserved.
//

#import "AddInvoiceViewController.h"
#import "MBProgressHUD+MJ.h"
#import "AFNetworking.h"

@interface AddInvoiceViewController ()
@property (nonatomic, weak) IBOutlet UITextField *tf_title;
@property (nonatomic, weak) IBOutlet UITextField *tf_taxNo;
@property (nonatomic, weak) IBOutlet UITextField *tf_bankDeposit;
@property (nonatomic, weak) IBOutlet UITextField *tf_accountNo;
@property (nonatomic, weak) IBOutlet UITextField *tf_address;
@property (nonatomic, weak) IBOutlet UITextField *tf_mobile;

@end

@implementation AddInvoiceViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (IBAction)btnOK:(id)sender {
    if(_tf_title.text.length == 0 ||
       _tf_title.text.length == 0 ||
       _tf_bankDeposit.text.length == 0 ||
       _tf_accountNo.text.length == 0 ||
       _tf_address.text.length == 0 ||
       _tf_mobile.text.length == 0){
        
        [MBProgressHUD showError:@"填写内容不能为空"];
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
                         @"title" : _tf_title.text,
                         @"taxNo" : _tf_taxNo.text,
                   @"bankDeposit" : _tf_bankDeposit.text,
                     @"accountNo" : _tf_accountNo.text,
                       @"address" : _tf_address.text,
                        @"mobile" : _tf_mobile.text}
              success:^(AFHTTPRequestOperation *operation, id responseObject) {
                  NSNumber *status = [responseObject objectForKey:@"status"];
                  switch ([status intValue]) {
                      case 0:{
                          [MBProgressHUD showSuccess:@"添加成功"];
                          
                      }
                          break;
                      default:
                          [MBProgressHUD showError:@"添加失败"];
                          break;
                  }
                  
              }
              failure:^(AFHTTPRequestOperation *operation, NSError *error) {
                  [MBProgressHUD showError: [NSString stringWithFormat:@"%@",error]];
              }];
    }
    
}

- (IBAction)btn_cancel:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
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
