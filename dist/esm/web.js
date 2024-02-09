import { WebPlugin } from '@capacitor/core';
export class Fetch2PluginWeb extends WebPlugin {
    async startFetch(options) {
        console.log('startFetch', options);
        return { value: options.url };
    }
    async startVideo(options) {
        console.log('startVideo', options);
        return { value: options.url };
    }
}
//# sourceMappingURL=web.js.map