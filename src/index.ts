import { registerPlugin } from '@capacitor/core';

import type { Fetch2PluginPlugin } from './definitions';

const Fetch2Plugin = registerPlugin<Fetch2PluginPlugin>('Fetch2Plugin', {
  web: () => import('./web').then(m => new m.Fetch2PluginWeb()),
  android: () => import('./android').then(m => new m.Fetch2PluginAndroid()),
});

export * from './definitions';
export { Fetch2Plugin };
