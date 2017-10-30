#import "RNMiRsa.h"
#import "RCTLog.h"
#import "XRSA.h"

static RNMiRsa *sharedInstance;

@interface RNMiRsa()
{
    
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

RCT_EXPORT_METHOD(encrypt:(nonnull NSString *)content :(nonnull NSString *)pubKey :(nonnull RCTResponseSenderBlock)callback)
{
    RCTLog(@"%@ -- %@", content, pubKey);
//    NSData *data = [[NSData alloc] initWithBase64EncodedString:pubKey options:NSDataBase64DecodingIgnoreUnknownCharacters];
//    XRSA *rsa = [[XRSA alloc] initWithData:data];
//    if (rsa != nil) {
//        callback(@[[[rsa encryptWithString:content] base64EncodedStringWithOptions:NSDataBase64Encoding64CharacterLineLength]]);
//    } else {
//        callback(@[@""]);
//    }
}

@end
  
