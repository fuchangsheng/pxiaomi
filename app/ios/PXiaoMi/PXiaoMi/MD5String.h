//
//  MD5String.h
//  PXiaoMi
//
//  Created by dmtec on 8/9/16.
//  Copyright © 2016 dmtec. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CommonCrypto/CommonDigest.h>

@interface MD5String : NSObject
+ (NSString *)md5:(NSString *)input;
@end
