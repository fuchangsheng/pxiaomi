//
//  MD5String.m
//  PXiaoMi
//
//  Created by dmtec on 8/9/16.
//  Copyright © 2016 dmtec. All rights reserved.
//

#import "MD5String.h"

@implementation MD5String
+ (NSString *)md5:(NSString *)input {
    const char* str = [input UTF8String];
    unsigned char result[CC_MD5_DIGEST_LENGTH];
    CC_MD5(str, strlen(str), result);
    NSMutableString *ret = [NSMutableString stringWithCapacity:CC_MD5_DIGEST_LENGTH*2];//

    for(int i = 0; i<CC_MD5_DIGEST_LENGTH; i++) {
        [ret appendFormat:@"%2s",result];
    }
    return ret;
}

@end
