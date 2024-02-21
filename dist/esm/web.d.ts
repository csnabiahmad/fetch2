import { WebPlugin } from '@capacitor/core';
import type { Fetch2PluginPlugin } from './definitions';
export declare class Fetch2PluginWeb extends WebPlugin implements Fetch2PluginPlugin {
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
    fetchDownloadList(options: string): Promise<{
        value: string;
    }>;
}
