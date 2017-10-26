using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Mi.Rsa.RNMiRsa
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNMiRsaModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNMiRsaModule"/>.
        /// </summary>
        internal RNMiRsaModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNMiRsa";
            }
        }
    }
}
