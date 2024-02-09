'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const Fetch2Plugin = core.registerPlugin('Fetch2Plugin', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.Fetch2PluginWeb()),
    android: () => Promise.resolve().then(function () { return android; }).then(m => new m.Fetch2PluginAndroid()),
});

class Fetch2PluginWeb extends core.WebPlugin {
    async startFetch(options) {
        console.log('startFetch', options);
        return { value: options.url };
    }
    async startVideo(options) {
        console.log('startVideo', options);
        return { value: options.url };
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    Fetch2PluginWeb: Fetch2PluginWeb
});

class Fetch2PluginAndroid extends core.WebPlugin {
    async startFetch(options) {
        console.log('startFetch', options);
        return { value: options.url };
    }
    async startVideo(options) {
        console.log('startVideo', options);
        return { value: options.url };
    }
}

var android = /*#__PURE__*/Object.freeze({
    __proto__: null,
    Fetch2PluginAndroid: Fetch2PluginAndroid
});

exports.Fetch2Plugin = Fetch2Plugin;
//# sourceMappingURL=plugin.cjs.js.map