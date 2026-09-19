const { contextBridge, ipcRenderer } = require('electron');

contextBridge.exposeInMainWorld('desktopApi', {
  showNotification: (payload) => ipcRenderer.send('show-notification', payload),
  loadData: () => ipcRenderer.invoke('load-data'),
  saveData: (data) => ipcRenderer.invoke('save-data', data),
  openExternal: (url) => ipcRenderer.send('open-external', url),
  onScheduleTrigger: (callback) => ipcRenderer.on('schedule-trigger', (event, value) => callback(value)),
  minimize: () => ipcRenderer.send('window-minimize'),
  maximize: () => ipcRenderer.send('window-maximize'),
  close: () => ipcRenderer.send('window-close')
});
