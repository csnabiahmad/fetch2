import { WebPlugin } from '@capacitor/core';
export class Fetch2PluginAndroid extends WebPlugin {
    async startFetch(options) {
        console.log('startFetch', options);
        return { value: options.url };
    }
    async startVideo(options) {
        console.log('startVideo', options);
        return { value: options.url };
    }
}
//# sourceMappingURL=android.js.map