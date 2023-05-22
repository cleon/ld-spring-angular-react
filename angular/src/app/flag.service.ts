import { Injectable, Inject } from '@angular/core';
import { LDClient, initialize } from 'launchdarkly-js-client-sdk';

@Injectable({ providedIn: 'root' })
export class FlagService {
    private client: LDClient;

    constructor(
        @Inject('ldClientID') private ldClientID: string,
        @Inject('ldContext') private context: any) {
        this.client = initialize(this.ldClientID, context);
    }

    subscribeToChanges(flagKey: string, callback: (value: any, previous: any) => void) {
        this.client.on(`change:${flagKey}`, callback);
    }

    async getFlagValue(flagKey: string) {
        return this.client
            .waitUntilReady()
            .then(() => {
                return this.client.variation(flagKey);
            });
    }
}