import type { PluginListenerHandle } from '@capacitor/core';
export interface Fetch2PluginPlugin {
    startFetch(options: {
        url: string[];
    }): Promise<{
        value: string[];
    }>;
    startVideo(options: {
        url: string[];
    }): Promise<{
        value: string[];
    }>;
    addListener(eventName: String, listenerFunc: (download: {
        result: string;
    }) => void): PluginListenerHandle;
}
