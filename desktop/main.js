const { app, BrowserWindow, ipcMain, Notification, Tray, Menu, shell } = require('electron');
const path = require('path');
const fs = require('fs');

let mainWindow = null;
let tray = null;
let isQuitting = false;

const DATA_FILE = path.join(app.getPath('userData'), 'admin_poc_desktop_data.json');

function loadStoredData() {
  try {
    if (fs.existsSync(DATA_FILE)) {
      const raw = fs.readFileSync(DATA_FILE, 'utf-8');
      return JSON.parse(raw);
    }
  } catch (err) {
    console.error('Error loading stored data:', err);
  }
  return null;
}

function saveStoredData(data) {
  try {
    fs.writeFileSync(DATA_FILE, JSON.stringify(data, null, 2), 'utf-8');
    return true;
  } catch (err) {
    console.error('Error saving stored data:', err);
    return false;
  }
}

function createWindow() {
  mainWindow = new BrowserWindow({
    width: 1300,
    height: 860,
    minWidth: 1050,
    minHeight: 700,
    title: 'Car Service Center Admin POC - Operations & Incident Hub',
    backgroundColor: '#0f172a',
    webPreferences: {
      preload: path.join(__dirname, 'preload.js'),
      nodeIntegration: false,
      contextIsolation: true
    },
    autoHideMenuBar: true
  });

  mainWindow.loadFile(path.join(__dirname, 'renderer', 'index.html'));

  mainWindow.on('close', (event) => {
    if (!isQuitting) {
      event.preventDefault();
      mainWindow.hide();
      if (Notification.isSupported()) {
        new Notification({
          title: 'Admin POC রানিং রয়েছে',
          body: 'অ্যাপটি ব্যাকগ্রাউন্ড ও সিস্টেম-ট্রেতে সক্রিয় রয়েছে। নির্দিষ্ট সময়ে তাগিদ নোটিফিকেশন দেওয়া হবে।'
        }).show();
      }
    }
  });

  setupTray();
  startBackgroundScheduler();
}

function setupTray() {
  if (tray) return;

  const iconPath = path.join(__dirname, 'renderer', 'icon.png');
  try {
    if (fs.existsSync(iconPath)) {
      tray = new Tray(iconPath);
    }
  } catch (e) {
    console.error('Tray setup failed:', e);
    return;
  }

  if (!tray) return;

  const contextMenu = Menu.buildFromTemplate([
    {
      label: 'ড্যাশবোর্ড ওপেন করুন',
      click: () => {
        if (mainWindow) {
          mainWindow.show();
          mainWindow.focus();
        }
      }
    },
    { type: 'separator' },
    {
      label: '🌅 টেস্ট: সকালের ওপেনিং অ্যালার্ম',
      click: () => triggerTestNotification('MORNING')
    },
    {
      label: '⚠️ টেস্ট: ৫:১৫ প্রি-ক্লোজিং সতর্কবার্তা',
      click: () => triggerTestNotification('PRE_CLOSING')
    },
    {
      label: '🚨 টেস্ট: প্রবলেম SLA ওভারডিউ অ্যালার্ট',
      click: () => triggerTestNotification('SLA_OVERDUE')
    },
    { type: 'separator' },
    {
      label: 'অ্যাপ বন্ধ করুন (Exit)',
      click: () => {
        isQuitting = true;
        app.quit();
      }
    }
  ]);

  if (tray) {
    tray.setToolTip('Car Service Center Admin POC');
    tray.setContextMenu(contextMenu);
    tray.on('double-click', () => {
      if (mainWindow) {
        mainWindow.show();
        mainWindow.focus();
      }
    });
  }
}

function triggerTestNotification(type) {
  if (!Notification.isSupported()) return;

  switch (type) {
    case 'MORNING':
      new Notification({
        title: '🌅 অফিসে স্বাগতম! সকালের ওপেনিং ইনস্পেকশন শুরু করুন',
        body: 'গেট, সার্ভিস বে, পাওয়ার সাপ্লাই, জেনারেটর ডিজেল ও কাস্টমার লাউঞ্জ চেক করুন।'
      }).show();
      break;
    case 'PRE_CLOSING':
      new Notification({
        title: '⚠️ অফিস ছুটির পূর্বে তাগিদ! (৫:১৫ PM)',
        body: 'নরমালি ৬টায় অফিস শেষ। আজকের কোনো রুটিন কাজ বা সমস্যা পেন্ডিং থাকলে দ্রুত সমাধান করুন!'
      }).show();
      break;
    case 'SLA_OVERDUE':
      new Notification({
        title: '🚨 জরুরি সতর্কবার্তা: প্রবলেম SLA সময়সীমা অতিক্রান্ত!',
        body: 'একটি হাই-প্রায়োরিটি সমস্যার সমাধানের ডেডলাইন পার হয়ে গেছে। সংশ্লিষ্ট টেকনিশিয়ান ও ডিপার্টমেন্টকে তাগিদ দিন!'
      }).show();
      break;
  }
}

// Background scheduler running every 60 seconds
let lastMorningTriggerDate = '';
let lastMiddayTriggerDate = '';
let lastPreClosingTriggerDate = '';

function startBackgroundScheduler() {
  setInterval(() => {
    const now = new Date();
    const hours = now.getHours();
    const minutes = now.getMinutes();
    const dateStr = now.toISOString().split('T')[0];

    const data = loadStoredData();
    const pendingTasksCount = data?.tasks ? data.tasks.filter(t => !t.isCompleted).length : 0;
    const openTickets = data?.tickets ? data.tickets.filter(t => t.status !== 'RESOLVED' && t.status !== 'CLOSED') : [];

    // 1. Morning Alarm at 09:00 AM
    if (hours === 9 && minutes === 0 && lastMorningTriggerDate !== dateStr) {
      lastMorningTriggerDate = dateStr;
      if (Notification.isSupported()) {
        new Notification({
          title: '🌅 অফিসে স্বাগতম! সকালের ওপেনিং ইনস্পেকশন শুরু করুন',
          body: `সার্ভিস বে, জেনারেটর, কাস্টমার লাউঞ্জ ও সিকিউরিটি চেক এখনই সম্পন্ন করুন। (${pendingTasksCount} টি কাজ বাকি)`
        }).show();
      }
      if (mainWindow) mainWindow.webContents.send('schedule-trigger', { slot: 'MORNING' });
    }

    // 2. Midday Alarm at 01:00 PM (13:00)
    if (hours === 13 && minutes === 0 && lastMiddayTriggerDate !== dateStr) {
      lastMiddayTriggerDate = dateStr;
      if (Notification.isSupported()) {
        new Notification({
          title: '☀️ দুপুর: ওয়ার্কশপ সাপোর্ট ও সেফটি ইনস্পেকশন',
          body: 'টেকনিশিয়ানদের পরিকাঠামো সুবিধা, ফায়ার সেফটি ও ভেন্ডর মনিটরিং করুন।'
        }).show();
      }
      if (mainWindow) mainWindow.webContents.send('schedule-trigger', { slot: 'MIDDAY' });
    }

    // 3. Pre-Closing Urgent Alarm at 05:15 PM (17:15)
    if (hours === 17 && minutes === 15 && lastPreClosingTriggerDate !== dateStr) {
      lastPreClosingTriggerDate = dateStr;
      if (Notification.isSupported()) {
        new Notification({
          title: '⚠️ অফিস ছুটির পূর্বে তাগিদ! (৫:১৫ PM)',
          body: `৬টায় অফিস শেষ হওয়ার আগে আপনার এখনও ${pendingTasksCount} টি কাজ ও ${openTickets.length} টি সমস্যা পেন্ডিং রয়েছে! দ্রুত সম্পন্ন করুন।`
        }).show();
      }
      if (mainWindow) mainWindow.webContents.send('schedule-trigger', { slot: 'PRE_CLOSING' });
    }

    // 4. Check for SLA Overdue in Open Problem Tickets
    const currentTimeMs = now.getTime();
    openTickets.forEach(ticket => {
      if (ticket.deadlineTime && ticket.deadlineTime < currentTimeMs && !ticket.overdueAlerted) {
        ticket.overdueAlerted = true;
        if (Notification.isSupported()) {
          new Notification({
            title: `🚨 প্রবলেম SLA Overdue: ${ticket.title}`,
            body: `ক্যাটাগরি: ${ticket.category} | স্থান: ${ticket.location}। সমাধানের নির্ধারিত সময় পার হয়ে গেছে!`
          }).show();
        }
      }
    });

  }, 60000); // Check every minute
}

// IPC handlers
ipcMain.on('show-notification', (event, payload) => {
  if (Notification.isSupported()) {
    new Notification({
      title: payload.title || 'Admin POC নোটিফিকেশন',
      body: payload.body || '',
      silent: false
    }).show();
  }
});

ipcMain.handle('load-data', async () => {
  return loadStoredData();
});

ipcMain.handle('save-data', async (event, data) => {
  return saveStoredData(data);
});

ipcMain.on('open-external', (event, url) => {
  shell.openExternal(url);
});

ipcMain.on('window-minimize', () => {
  if (mainWindow) mainWindow.minimize();
});

ipcMain.on('window-maximize', () => {
  if (mainWindow) {
    if (mainWindow.isMaximized()) mainWindow.unmaximize();
    else mainWindow.maximize();
  }
});

ipcMain.on('window-close', () => {
  if (mainWindow) mainWindow.close();
});

app.whenReady().then(createWindow);

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) {
    createWindow();
  }
});
