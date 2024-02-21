import { WebPlugin } from '@capacitor/core';
import type { Fetch2PluginPlugin } from './definitions';
export class Fetch2PluginWeb extends WebPlugin implements Fetch2PluginPlugin {
  async startFetch(options: { url: string[] }): Promise<{ value: string[] }> {
    console.log('startFetch', options);
    return { value: options.url };
  }
  async startVideo(options: { url: string[] }): Promise<{ value: string[] }> {
    console.log('startVideo', options);
    return { value: options.url };
  }
  async fetchDownloadList(options: string): Promise<{ value: string }> {
    console.log('startVideo', options);
    return { value: options };
  }
}
