import { registerPlugin } from '@capacitor/core';
const Fetch2Plugin = registerPlugin('Fetch2Plugin', {
    web: () => import('./web').then(m => new m.Fetch2PluginWeb()),
});
export * from './definitions';
export { Fetch2Plugin };
//# sourceMappingURL=index.js.map