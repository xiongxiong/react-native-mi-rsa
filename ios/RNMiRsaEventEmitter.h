#import "RCTEventEmitter.h"

@protocol RNMiRsaDelegate <NSObject>

@optional
- (void)hasEncrypted: (NSString *)result;

@end

@interface RNMiRsaEventEmitter : RCTEventEmitter <RNMiRsaDelegate>

@end


