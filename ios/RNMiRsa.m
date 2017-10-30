#import "RNMiRsa.h"
#import "RNMiRsaEventEmitter.h"
#import <Security/Security.h>

static RNMiRsa *sharedInstance;

@interface RNMiRsa()
{
    UIView *currentView;
    RNMiRsaEventEmitter *eventEmitter;
}

@end

@implementation RNMiRsa

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

- (dispatch_queue_t)methodQueue
{
    return self.bridge.uiManager.methodQueue;
}

- (UIView *)view
{
    if (eventEmitter == nil) {
        eventEmitter = [RNMiRsaEventEmitter new];
        self.delegate = eventEmitter;
    }
    
    if (currentView == nil) {
        currentView = [UIView new];
    }
    
    return currentView;
}

RCT_EXPORT_METHOD(encrypt: (nonnull NSString *)content, key: (nonnull NSString *)key)
{
    [self.bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
        UIView *view = viewRegistry[reactTag];
        [self.delegate hasEncrypted: @"hello"];
    }];
}

@end
  
