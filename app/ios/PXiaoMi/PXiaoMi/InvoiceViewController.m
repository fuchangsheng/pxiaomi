//
//  InvoiceViewController.m
//  PXiaoMi
//
//  Created by ThugKd on 7/31/16.
//  Copyright © 2016 dmtec. All rights reserved.
//

#import "InvoiceViewController.h"
#import "ScrollView.h"
#define SCREEN_WIDTH ([UIScreen  mainScreen].bounds.size.width)
#define SCREEN_HEIGHT ([UIScreen mainScreen].bounds.size.height)

@interface InvoiceViewController ()

@end

@implementation InvoiceViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor whiteColor];
    self.title = @"开票";

    ScrollView *banner=[[ScrollView alloc]initWithFrame:CGRectMake(0, 50, SCREEN_WIDTH, 190) arraySource:nil];

    [self.view addSubview:banner];

    
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
