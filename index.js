import React, {Component} from 'react';
import {NativeModules} from 'react-native';

const {RNMiRsa, RNMiRsaEventEmitter: _RNMiRsaEventEmitter} = NativeModules;
const RNMiRsaEventEmitter = new NativeEventEmitter(_RNMiRsaEventEmitter);

export default {RNMiRsa, RNMiRsaEventEmitter};
