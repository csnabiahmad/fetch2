# fetch2

Capacitor plugin for android using Fetch library to download files

## Install

```bash
npm install git+https://github.com/csnabiahmad/fetch2.git
npx cap sync android
```

## API

<docgen-index>

* [`startFetch(...)`](#startfetch)
* [`startVideo(...)`](#startvideo)
* [`fetchDownloadList(...)`](#fetchdownloadlist)
* [`addListener(string, ...)`](#addlistenerstring)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startFetch(...)

```typescript
startFetch(options: { url: string[]; }) => Promise<{ value: string[]; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ url: string[]; }</code> |

**Returns:** <code>Promise&lt;{ value: string[]; }&gt;</code>

--------------------


### startVideo(...)

```typescript
startVideo(options: { url: string[]; }) => Promise<{ value: string[]; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ url: string[]; }</code> |

**Returns:** <code>Promise&lt;{ value: string[]; }&gt;</code>

--------------------


### addListener(String, ...)

```typescript
addListener(eventName: String, listenerFunc: (download: { result: string; }) => void) => PluginListenerHandle
```

| Param              | Type                                                    |
| ------------------ | ------------------------------------------------------- |
| **`eventName`**    | <code><a href="#string">String</a></code>               |
| **`listenerFunc`** | <code>(download: { result: string; }) =&gt; void</code> |

**Returns:** <code><a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### Interfaces


#### PluginListenerHandle

This interface represents the different types of download events.

| Prop | Type | Description |
| -------- | ---- | ----------- |
| **`onAdded`** | <code>String</code> | Event when a download is added |
| **`onCancelled`** | <code>String</code> | Event when a download is cancelled |
| **`onCompleted`** | <code>String</code> | Event when a download is completed |
| **`onDeleted`** | <code>String</code> | Event when a download is deleted |
| **`onDownloadBlockUpdated`** | <code>String</code> | Event when a download block is updated |
| **`onError`** | <code>String</code> | Event when an error occurs during download |
| **`onPaused`** | <code>String</code> | Event when a download is paused |
| **`onProgress`** | <code>String</code> | Event when a download makes progress |
| **`onQueued`** | <code>String</code> | Event when a download is queued |
| **`onRemoved`** | <code>String</code> | Event when a download is removed |
| **`onResumed`** | <code>String</code> | Event when a download is resumed |
| **`onStarted`** | <code>String</code> | Event when a download is started |
| **`onWaitingNetwork`** | <code>String</code> | Event when a download is waiting for network |
