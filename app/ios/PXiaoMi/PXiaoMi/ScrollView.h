//
//  ScrollView.h
//  PXiaoMi
//
//  Created by dmtec on 16/8/1.
//  Copyright © 2016年 dmtec. All rights reserved.
//

#import <UIKit/UIKit.h>
#define SCREEN_WIDTH ([UIScreen  mainScreen].bounds.size.width)
#define SCREEN_HEIGHT ([UIScreen mainScreen].bounds.size.height)

@interface ScrollView : UIView
-(instancetype)initWithFrame:(CGRect)frame arraySource:(NSMutableArray *)arraySource;
@end