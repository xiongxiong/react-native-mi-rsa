//
//  RNMiRsaEventEmitter.m
//  RNMiRsa
//
//  Created by 王继荣 on 30/10/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "RNMiRsaEventEmitter.h"

@interface RNMiRsaEventEmitter()
{
    BOOL hasListeners;
}

@end

static RNMiRsaEventEmitter *sharedInstance;

@implementation RNMiRsaEventEmitter

RCT_EXPORT_MODULE();

+ (id)allocWithZone:(struct _NSZone *)zone
{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [super allocWithZone:zone];
    });
    return sharedInstance;
}

+ (instancetype)sharedInstance
{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [self new];
    });
    return sharedInstance;
}

- (id)copyWithZone:(NSZone *)zone
{
    return sharedInstance;
}

#pragma mark - RCTEventEmitter

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"encrypted"];
}

- (void)startObserving { 
    hasListeners = YES;
}

- (void)stopObserving { 
    hasListeners = NO;
}

- (void)hasEncrypted:(NSString *)result {
    [self sendEventWithName:@"encrypted" body:result];
}

@end
