import { registerPlugin } from '@capacitor/core';

import type { Fetch2PluginPlugin } from './definitions';

const Fetch2Plugin = registerPlugin<Fetch2PluginPlugin>('Fetch2Plugin', {
  web: () => import('./web').then(m => new m.Fetch2PluginWeb()),
});

export * from './definitions';
export { Fetch2Plugin };
