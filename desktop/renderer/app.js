// CAR SERVICE CENTER ADMIN POC - EXECUTIVE DESKTOP HUB LOGIC

// Default SOP Tasks covering all 8 responsibilities
const DEFAULT_SOP_TASKS = [
  // 1. MORNING (09:00 - 09:45 AM)
  {
    id: "morn_open_inspect",
    title: "Daily Opening Inspection",
    bengaliTitle: "সার্ভিস সেন্টার দৈনিক ওপেনিং ইনস্পেকশন",
    category: "1. Facility Management",
    description: "মেইন গেট, শাটার, সার্ভিস বে, অফিস ও ওয়াশরুম খোলা ও কার্যক্ষমতা পর্যবেক্ষণ করুন।",
    slot: "MORNING",
    priority: "CRITICAL",
    isCompleted: false,
    remarks: ""
  },
  {
    id: "morn_utilities_check",
    title: "Utilities & Power Check",
    bengaliTitle: "বিদ্যুৎ, জেনারেটর ও ইউপিএস (UPS) চেক",
    category: "4. Utilities & Engineering",
    description: "মেইন পাওয়ার সরবরাহ, জেনারেটর ডিজেল/ব্যাটারি লেভেল, কম্প্রেসার ও ইউপিএস ব্যাকআপ সচল আছে কি না পরীক্ষা করুন।",
    slot: "MORNING",
    priority: "CRITICAL",
    isCompleted: false,
    remarks: ""
  },
  {
    id: "morn_housekeeping",
    title: "Housekeeping & Cleanliness Standards",
    bengaliTitle: "সার্ভিস বে ও সাধারণ স্থান পরিচ্ছন্নতা তদারকি",
    category: "5. Waste & Housekeeping",
    description: "সুইপার/ক্লিনার উপস্থিত কি না এবং প্রতিটি সার্ভিস বে তেলকালিমুক্ত ও পরিষ্কার রাখা নিশ্চিত করুন।",
    slot: "MORNING",
    priority: "HIGH",
    isCompleted: false,
    remarks: ""
  },
  {
    id: "morn_pantry_lounge",
    title: "Customer Waiting Area & Pantry Ready",
    bengaliTitle: "কাস্টমার লাউঞ্জ ও প্যান্ট্রি রেডি নিশ্চিতকরণ",
    category: "1. Facility Management",
    description: "এসি সচল, খাবার পানি/চা ডিসপেনসার রেডি, ওয়াশরুম পরিষ্কার ও পরিপাটি আসন ব্যবস্থা নিশ্চিত করুন।",
    slot: "MORNING",
    priority: "HIGH",
    isCompleted: false,
    remarks: ""
  },
  {
    id: "morn_security_attendance",
    title: "Security & Biometric Attendance",
    bengaliTitle: "সিকিউরিটি গার্ড পোস্ট ও বায়োমেট্রিক উপস্থিতি",
    category: "3. Safety & Security",
    description: "নিরাপত্তা প্রহরী উপস্থিত, সিসিটিভি স্ক্রিন সচল এবং স্টাফদের সময়মতো উপস্থিতি মনিটর করুন।",
    slot: "MORNING",
    priority: "NORMAL",
    isCompleted: false,
    remarks: ""
  },

  // 2. MID-DAY (01:00 - 02:00 PM)
  {
    id: "mid_workshop_support",
    title: "Workshop Support & Facilities Check",
    bengaliTitle: "ওয়ার্কশপ পরিকাঠামো ও সুবিধা পর্যবেক্ষণ",
    category: "2. Workshop Support",
    description: "লাইটিং, ড্রেনেজ, এয়ার পাইপলাইন প্রেশার ও ফোরম্যান/টেকনিশিয়ানদের কাজের পরিবেশ ঠিক আছে কি না নিশ্চিত করুন।",
    slot: "MIDDAY",
    priority: "HIGH",
    isCompleted: false,
    remarks: ""
  },
  {
    id: "mid_safety_emergency",
    title: "Fire Safety & Emergency Exits Inspection",
    bengaliTitle: "ফায়ার এক্সটিঙ্গুইশার ও ইমার্জেন্সি এক্সিট পরিদর্শন",
    category: "3. Safety & Security",
    description: "জরুরি বহির্গমন রাস্তা খোলা ও বাধাহীন রাখা, অগ্নিনির্বাপক যন্ত্রের প্রেসার গেজ ঠিক থাকা নিশ্চিত করুন।",
    slot: "MIDDAY",
    priority: "CRITICAL",
    isCompleted: false,
    remarks: ""
  },
  {
    id: "mid_contractor_mgmt",
    title: "Vendor & Contractor Work Supervision",
    bengaliTitle: "ভেন্ডর ও মেরামতকারী কাজের অগ্রগতি তদারকি",
    category: "7. Vendor & Contractor Management",
    description: "বহিরাগত টেকনিশিয়ান বা ঠিকাদারদের ওয়ার্ক পারমিট, কাজের মান ও নিরাপত্তা বিধি পর্যবেক্ষণ করুন।",
    slot: "MIDDAY",
    priority: "NORMAL",
    isCompleted: false,
    remarks: ""
  },
  {
    id: "mid_waste_disposal",
    title: "Operational Waste & Scrap Segregation",
    bengaliTitle: "অপসারণযোগ্য বর্জ্য, ব্যবহৃত মবিল ও ধাতব স্ক্র্যাপ সরানো",
    category: "5. Waste & Housekeeping",
    description: "ওয়ার্কশপের বিপজ্জনক বর্জ্য এবং ড্রেন ক্লিনিং স্ট্যান্ডার্ড অনুযায়ী সরানো হচ্ছে কি না মনিটর করুন।",
    slot: "MIDDAY",
    priority: "HIGH",
    isCompleted: false,
    remarks: ""
  },

  // 3. PRE-CLOSING (05:15 - 05:45 PM)
  {
    id: "pre_pending_review",
    title: "Pending Routine Tasks Enforcement",
    bengaliTitle: "অসমাপ্ত ও পেন্ডিং কাজ চিহ্নিতকরণ ও দ্রুত সম্পন্ন করা",
    category: "8. Daily Reporting",
    description: "অফিস ত্যাগের পূর্বে সারাদিনের কোনো রুটিন কাজ অসমাপ্ত থাকলে তা এখনই দ্রুত শেষ করুন।",
    slot: "PRE_CLOSING",
    priority: "CRITICAL",
    isCompleted: false,
    remarks: ""
  },
  {
    id: "pre_stationery_supplies",
    title: "Supplies & Administrative Requisitions",
    bengaliTitle: "স্টেশনারি ও অফিস সামগ্রী মজুদ নিরীক্ষণ",
    category: "6. Administrative Support",
    description: "পরবর্তী দিনের জন্য প্রয়োজনীয় ফর্ম, স্টেশনারি বা সাপ্লাই রিকুইজিশন চেক ও লিপিবদ্ধ করুন।",
    slot: "PRE_CLOSING",
    priority: "NORMAL",
    isCompleted: false,
    remarks: ""
  },
  {
    id: "pre_incident_breakdown_log",
    title: "Incident & Equipment Breakdown Logging",
    bengaliTitle: "যান্ত্রিক ত্রুটি বা দুর্ঘটনা লগ বইয়ে এন্ট্রি",
    category: "4. Utilities & Engineering",
    description: "সারাদিনের কোনো মেশিন, লিফট বা ইউটিলিটি সমস্যা হয়ে থাকলে রেজিস্টারে এন্ট্রি করুন।",
    slot: "PRE_CLOSING",
    priority: "HIGH",
    isCompleted: false,
    remarks: ""
  },

  // 4. EVENING (05:45 - 06:00 PM)
  {
    id: "eve_daily_report",
    title: "Daily Operational Report Submission",
    bengaliTitle: "হেড অব অ্যাডমিনের কাছে দৈনিক রিপোর্ট পাঠানো",
    category: "8. Daily Reporting",
    description: "দিনের সম্পূর্ণ ইনস্পেকশন ও কাজের অগ্রগতি রিপোর্ট তৈরি করে ম্যানেজমেন্টকে পাঠান (WhatsApp/SMS)।",
    slot: "EVENING",
    priority: "CRITICAL",
    isCompleted: false,
    remarks: ""
  },
  {
    id: "eve_critical_escalation",
    title: "Critical Issues Escalation",
    bengaliTitle: "জরুরি বা অমীমাংসিত সমস্যা হেড অফিসে ফ্ল্যাগ করা",
    category: "8. Daily Reporting",
    description: "যেসব সমস্যা আজকের মধ্যে সমাধান হয়নি তা ঊর্ধ্বতন কর্তৃপক্ষের নজরে এনে নির্দেশনা নিন।",
    slot: "EVENING",
    priority: "HIGH",
    isCompleted: false,
    remarks: ""
  },
  {
    id: "eve_closing_lockup",
    title: "Daily Closing & Security Lockup Handover",
    bengaliTitle: "সার্ভিস সেন্টার দৈনিক ক্লোজিং ও নাইট সিকিউরিটিকে চাবি হস্তান্তর",
    category: "1. Facility Management",
    description: "অপ্রয়োজনীয় আলো, এসি ও এয়ার কম্প্রেসার বন্ধ, ওয়াটার লাইন লক এবং সিকিউরিটিকে দায়িত্ব বুঝিয়ে দিন।",
    slot: "EVENING",
    priority: "CRITICAL",
    isCompleted: false,
    remarks: ""
  }
];

// Initial State
let appData = {
  tasks: DEFAULT_SOP_TASKS,
  tickets: [],
  config: {
    officerName: "Md. Harun-Or-Rashid (Sr. Executive - Admin)",
    centerName: "Mirpur Central Car Service Center",
    headPhone: "+8801700000000"
  },
  activeSlot: "ALL",
  activeIssueStatus: "ALL"
};

// Web Audio Chime Generator (No external mp3 files required)
function playAudioAlert(type = 'normal') {
  try {
    const audioCtx = new (window.AudioContext || window.webkitAudioContext)();
    const osc = audioCtx.createOscillator();
    const gain = audioCtx.createGain();
    osc.connect(gain);
    gain.connect(audioCtx.destination);

    if (type === 'critical') {
      // 3 High alert beeps
      osc.type = 'sawtooth';
      osc.frequency.setValueAtTime(880, audioCtx.currentTime);
      osc.frequency.setValueAtTime(440, audioCtx.currentTime + 0.15);
      osc.frequency.setValueAtTime(880, audioCtx.currentTime + 0.3);
      gain.gain.setValueAtTime(0.3, audioCtx.currentTime);
      gain.gain.exponentialRampToValueAtTime(0.01, audioCtx.currentTime + 0.5);
      osc.start(audioCtx.currentTime);
      osc.stop(audioCtx.currentTime + 0.5);
    } else {
      // Pleasant chime
      osc.type = 'sine';
      osc.frequency.setValueAtTime(587.33, audioCtx.currentTime); // D5
      osc.frequency.setValueAtTime(880, audioCtx.currentTime + 0.1); // A5
      gain.gain.setValueAtTime(0.2, audioCtx.currentTime);
      gain.gain.exponentialRampToValueAtTime(0.01, audioCtx.currentTime + 0.4);
      osc.start(audioCtx.currentTime);
      osc.stop(audioCtx.currentTime + 0.4);
    }
  } catch (e) {
    console.log('Audio alert playback:', e);
  }
}

// Native Desktop Notification Helper
function sendDesktopNotification(title, body, urgency = 'normal') {
  playAudioAlert(urgency === 'critical' ? 'critical' : 'normal');
  if (window.desktopApi && window.desktopApi.showNotification) {
    window.desktopApi.showNotification({ title, body, urgency });
  } else if ("Notification" in window && Notification.permission === "granted") {
    new Notification(title, { body });
  }
}

// Initialize Application
document.addEventListener("DOMContentLoaded", async () => {
  // Request notification permission if running in browser/PWA fallback
  if ("Notification" in window && Notification.permission !== "granted") {
    Notification.requestPermission();
  }

  await loadData();
  setupEventListeners();
  startLiveClock();
  renderAll();

  // Listen to background schedule triggers from Electron main process
  if (window.desktopApi && window.desktopApi.onScheduleTrigger) {
    window.desktopApi.onScheduleTrigger((payload) => {
      if (payload.slot === 'PRE_CLOSING') {
        playAudioAlert('critical');
      } else {
        playAudioAlert('normal');
      }
      renderAll();
    });
  }
});

// Load Data from Persistent Storage
async function loadData() {
  if (window.desktopApi && window.desktopApi.loadData) {
    const saved = await window.desktopApi.loadData();
    if (saved && saved.tasks) {
      appData = { ...appData, ...saved };
    }
  } else {
    const local = localStorage.getItem("admin_poc_desktop_data");
    if (local) {
      try {
        appData = { ...appData, ...JSON.parse(local) };
      } catch (e) {}
    }
  }
}

// Save Data to Persistent Storage
async function saveData() {
  if (window.desktopApi && window.desktopApi.saveData) {
    await window.desktopApi.saveData(appData);
  } else {
    localStorage.setItem("admin_poc_desktop_data", JSON.stringify(appData));
  }
}

// Live Clock & Auto Pre-Closing Banner Check
function startLiveClock() {
  function update() {
    const now = new Date();
    document.getElementById("liveTime").textContent = now.toLocaleTimeString();
    
    const options = { day: 'numeric', month: 'long', year: 'numeric', weekday: 'long' };
    document.getElementById("liveDate").textContent = now.toLocaleDateString('bn-BD', options);

    // Pre-closing urgency check (from 5:15 PM onwards, if tasks or tickets pending)
    const hours = now.getHours();
    const minutes = now.getMinutes();
    const pendingTasks = appData.tasks.filter(t => !t.isCompleted).length;
    const banner = document.getElementById("preClosingBanner");

    if ((hours > 17 || (hours === 17 && minutes >= 15)) && pendingTasks > 0) {
      banner.classList.remove("hidden");
    } else {
      banner.classList.add("hidden");
    }

    // Check SLA overdues in tickets
    checkTicketSlaStatus();
  }
  update();
  setInterval(update, 1000);
}

// Check ticket SLA deadlines
function checkTicketSlaStatus() {
  const now = Date.now();
  let hasOverdue = false;
  appData.tickets.forEach(ticket => {
    if (ticket.deadlineTime && ticket.deadlineTime < now && ticket.status !== 'RESOLVED' && ticket.status !== 'CLOSED') {
      ticket.isOverdue = true;
      hasOverdue = true;
    } else {
      ticket.isOverdue = false;
    }
  });

  const badge = document.getElementById("dutyBadge");
  if (hasOverdue) {
    badge.textContent = "SLA সতর্কবার্তা ⚠️";
    badge.style.background = "rgba(239, 68, 68, 0.2)";
    badge.style.borderColor = "#ef4444";
    badge.style.color = "#f87171";
  } else {
    const allDone = appData.tasks.every(t => t.isCompleted);
    badge.textContent = allDone ? "সব কাজ সম্পন্ন ✅" : "ডিউটি চলমান ⚡";
    badge.style.background = allDone ? "rgba(16, 185, 129, 0.2)" : "rgba(16, 185, 129, 0.15)";
    badge.style.borderColor = "rgba(16, 185, 129, 0.4)";
    badge.style.color = "#34d399";
  }
}

// Render Master Controller
function renderAll() {
  renderProfile();
  renderKPIs();
  renderTasks();
  renderIncidents();
  renderDashboardStats();
  renderReportPreview();
}

function renderProfile() {
  document.getElementById("displayCenterName").innerHTML = `<i class="fa-solid fa-location-dot"></i> ${appData.config.centerName}`;
  document.getElementById("displayOfficerName").textContent = appData.config.officerName;
  document.getElementById("cfgOfficerName").value = appData.config.officerName;
  document.getElementById("cfgCenterName").value = appData.config.centerName;
  document.getElementById("cfgHeadPhone").value = appData.config.headPhone;
}

// Render KPIs
function renderKPIs() {
  const total = appData.tasks.length;
  const completed = appData.tasks.filter(t => t.isCompleted).length;
  const pending = total - completed;
  const percent = total > 0 ? Math.round((completed * 100) / total) : 0;

  document.getElementById("kpiTotalTasks").textContent = total;
  document.getElementById("kpiCompletedTasks").textContent = completed;
  document.getElementById("kpiPendingTasks").textContent = pending;
  document.getElementById("kpiProgressPercent").textContent = `${percent}%`;

  document.getElementById("sidebarPendingBadge").textContent = pending;

  const openTickets = appData.tickets.filter(t => t.status !== 'RESOLVED' && t.status !== 'CLOSED').length;
  document.getElementById("sidebarOpenIssuesBadge").textContent = openTickets;
}

// Render Tasks
function renderTasks() {
  const container = document.getElementById("tasksList");
  container.innerHTML = "";

  // Update Slot Counts
  document.getElementById("countSlotAll").textContent = appData.tasks.length;
  document.getElementById("countSlotMorning").textContent = appData.tasks.filter(t => t.slot === "MORNING").length;
  document.getElementById("countSlotMidday").textContent = appData.tasks.filter(t => t.slot === "MIDDAY").length;
  document.getElementById("countSlotPreClosing").textContent = appData.tasks.filter(t => t.slot === "PRE_CLOSING").length;
  document.getElementById("countSlotEvening").textContent = appData.tasks.filter(t => t.slot === "EVENING").length;

  const filtered = appData.activeSlot === "ALL" 
    ? appData.tasks 
    : appData.tasks.filter(t => t.slot === appData.activeSlot);

  filtered.forEach(task => {
    const card = document.createElement("div");
    card.className = `task-card ${task.isCompleted ? 'completed' : ''}`;
    
    card.innerHTML = `
      <div class="task-check-circle" onclick="toggleTask('${task.id}')">
        ${task.isCompleted ? '<i class="fa-solid fa-check"></i>' : ''}
      </div>
      <div class="task-body">
        <div class="task-meta-row">
          <span class="category-badge">${task.category}</span>
          <span class="priority-badge priority-${task.priority.toLowerCase()}">${task.priority}</span>
          ${task.completedAt ? `<span style="font-size:11px; color:#10b981;">সম্পন্ন: ${new Date(task.completedAt).toLocaleTimeString()}</span>` : ''}
        </div>
        <div class="task-title">${task.bengaliTitle} <span style="font-size:12px; color:#94a3b8; font-weight:normal;">(${task.title})</span></div>
        <div class="task-desc">${task.description}</div>
        ${task.remarks ? `<div class="task-notes-box"><strong>নোট:</strong> ${task.remarks}</div>` : ''}
        <div class="task-action-row">
          <button class="btn-outline btn-small" onclick="promptTaskNote('${task.id}')">
            <i class="fa-solid fa-pen"></i> ${task.remarks ? 'নোট সম্পাদনা' : 'নোট যোগ করুন'}
          </button>
        </div>
      </div>
    `;
    container.appendChild(card);
  });
}

// Toggle Task Completion
window.toggleTask = async function(id) {
  const task = appData.tasks.find(t => t.id === id);
  if (task) {
    task.isCompleted = !task.isCompleted;
    task.completedAt = task.isCompleted ? Date.now() : null;
    playAudioAlert(task.isCompleted ? 'normal' : 'normal');
    await saveData();
    renderAll();
  }
};

window.promptTaskNote = async function(id) {
  const task = appData.tasks.find(t => t.id === id);
  if (task) {
    const note = prompt("টাস্কের জন্য মিটার রিডিং, পর্যবেক্ষণ বা কাজের নোট লিখুন:", task.remarks || "");
    if (note !== null) {
      task.remarks = note.trim();
      await saveData();
      renderAll();
    }
  }
};

// Render Incidents / Problem Tickets
function renderIncidents() {
  const container = document.getElementById("incidentsList");
  container.innerHTML = "";

  // Update Status Filters Count
  const all = appData.tickets.length;
  const open = appData.tickets.filter(t => t.status === 'OPEN').length;
  const prog = appData.tickets.filter(t => t.status === 'IN_PROGRESS').length;
  const spare = appData.tickets.filter(t => t.status === 'PENDING_SPARE').length;
  const resolved = appData.tickets.filter(t => t.status === 'RESOLVED' || t.status === 'CLOSED').length;

  document.getElementById("countIssueAll").textContent = all;
  document.getElementById("countIssueOpen").textContent = open;
  document.getElementById("countIssueProgress").textContent = prog;
  document.getElementById("countIssueSpare").textContent = spare;
  document.getElementById("countIssueResolved").textContent = resolved;

  const filtered = appData.activeIssueStatus === "ALL"
    ? appData.tickets
    : appData.tickets.filter(t => t.status === appData.activeIssueStatus);

  if (filtered.length === 0) {
    container.innerHTML = `
      <div style="grid-column: 1/-1; text-align: center; padding: 40px; color: #64748b;">
        <i class="fa-solid fa-clipboard-check" style="font-size: 38px; margin-bottom: 12px; color: #10b981;"></i>
        <p>এই ফিল্টারে কোনো সমস্যা নেই। নতুন সমস্যা যোগ করতে উপরে "+ নতুন সমস্যা যোগ করুন" বাটনে ক্লিক করুন।</p>
      </div>
    `;
    return;
  }

  filtered.forEach(ticket => {
    const card = document.createElement("div");
    card.className = `incident-card ${ticket.priority === 'CRITICAL' ? 'critical-border' : ''} ${ticket.isOverdue ? 'overdue-pulse' : ''}`;

    const deadline = new Date(ticket.deadlineTime);
    const isOverdue = ticket.isOverdue;
    const timeLeftStr = isOverdue ? '⚠️ ডেডলাইন অতিক্রান্ত (OVERDUE)' : `সমাধানের লক্ষ্য: ${deadline.toLocaleString()}`;

    card.innerHTML = `
      <div>
        <div class="incident-top">
          <span class="category-badge">${ticket.category}</span>
          <span class="priority-badge priority-${ticket.priority.toLowerCase()}">${ticket.priority}</span>
        </div>
        <div class="incident-title">${ticket.title}</div>
        <div class="incident-location"><i class="fa-solid fa-location-dot"></i> স্থান: ${ticket.location}</div>

        <div class="sla-badge ${isOverdue ? 'overdue' : ''}">
          <i class="fa-regular fa-clock"></i> ${timeLeftStr}
        </div>

        <div class="incident-instructions-box">
          <strong>নির্দেশনা / SOP:</strong><br>
          ${ticket.instructions.replace(/\n/g, '<br>')}
        </div>

        <div class="tags-row">
          ${ticket.assignees.map(a => `<span class="person-tag"><i class="fa-solid fa-user"></i> ${a}</span>`).join('')}
          ${ticket.departments.map(d => `<span class="dept-tag"><i class="fa-solid fa-building"></i> ${d}</span>`).join('')}
        </div>

        ${ticket.resolutionNotes ? `<div style="font-size:12px; background:rgba(16,185,129,0.1); border-left:3px solid #10b981; padding:6px; margin-bottom:10px; color:#6ee7b7;"><strong>কাজের আপডেট:</strong> ${ticket.resolutionNotes}</div>` : ''}
      </div>

      <div class="incident-bottom">
        <span class="status-pill status-${ticket.status}">${ticket.status}</span>
        <button class="btn-outline btn-small" onclick="openUpdateModal('${ticket.id}')">
          <i class="fa-solid fa-pen-to-square"></i> স্ট্যাটাস পরিবর্তন
        </button>
      </div>
    `;

    container.appendChild(card);
  });
}

// Render Dashboard Analytics
function renderDashboardStats() {
  const list = document.getElementById("deptStatsList");
  list.innerHTML = "";

  const deptCounts = {};
  appData.tickets.forEach(t => {
    t.departments.forEach(d => {
      deptCounts[d] = (deptCounts[d] || 0) + 1;
    });
  });

  const keys = Object.keys(deptCounts);
  if (keys.length === 0) {
    list.innerHTML = `<p style="color:#64748b; font-size:13px;">এখনও কোনো সমস্যা টিকিট নেই।</p>`;
  } else {
    keys.forEach(dept => {
      const row = document.createElement("div");
      row.style.display = "flex";
      row.style.justifyContent = "space-between";
      row.style.padding = "8px 0";
      row.style.borderBottom = "1px solid #2b4374";
      row.innerHTML = `
        <span style="color:#cbd5e1; font-size:13px;"><i class="fa-solid fa-building"></i> ${dept}</span>
        <span style="background:#2563eb; color:white; padding:2px 8px; border-radius:10px; font-size:12px; font-weight:bold;">${deptCounts[dept]} টি ইস্যু</span>
      `;
      list.appendChild(row);
    });
  }

  // SLA Stats
  const resolved = appData.tickets.filter(t => t.status === 'RESOLVED' || t.status === 'CLOSED').length;
  const overdue = appData.tickets.filter(t => t.isOverdue).length;
  document.getElementById("slaOnTimeCount").textContent = resolved;
  document.getElementById("slaOverdueCount").textContent = overdue;
}

// Format Daily Report Preview
function renderReportPreview() {
  const total = appData.tasks.length;
  const done = appData.tasks.filter(t => t.isCompleted).length;
  const pending = total - done;
  const percent = total > 0 ? Math.round((done * 100) / total) : 0;

  const now = new Date();
  const dateStr = now.toLocaleDateString('bn-BD', { day: 'numeric', month: 'long', year: 'numeric', weekday: 'long' });

  let text = `📋 CAR SERVICE CENTER - DAILY EXECUTIVE ADMIN REPORT\n`;
  text += `━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n`;
  text += `🏢 সার্ভিস সেন্টার: ${appData.config.centerName}\n`;
  text += `👤 অ্যাডমিন পিওসি: ${appData.config.officerName}\n`;
  text += `📅 তারিখ: ${dateStr}\n`;
  text += `📊 দৈনিক অগ্রগতি: ${done}/${total} সম্পন্ন (${percent}% Completed)\n`;
  if (pending > 0) {
    text += `⚠️ পেন্ডিং রুটিন কাজ: ${pending} টি\n`;
  } else {
    text += `✅ আজকের সব রুটিন ইনস্পেকশন ১০০% সম্পন্ন!\n`;
  }
  text += `━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n`;

  text += `🔹 রুটিন ইনস্পেকশন সারাংশ:\n`;
  const slots = [
    { key: "MORNING", name: "সকালের ওপেনিং চেক (০৯:০০ - ০৯:৪৫ AM)" },
    { key: "MIDDAY", name: "দুপুর ও ওয়ার্কশপ তদারকি (০১:০০ - ০২:০০ PM)" },
    { key: "PRE_CLOSING", name: "প্রি-ক্লোজিং রিভিউ (০৫:১৫ - ০৫:৪৫ PM)" },
    { key: "EVENING", name: "ক্লোজিং ও রিপোর্ট (০৫:৪৫ - ০৬:০০ PM)" }
  ];

  slots.forEach(s => {
    const sTasks = appData.tasks.filter(t => t.slot === s.key);
    text += `• ${s.name}:\n`;
    sTasks.forEach(t => {
      text += `   ${t.isCompleted ? '✅' : '❌'} ${t.bengaliTitle}\n`;
      if (t.remarks) text += `      ↳ নোট: ${t.remarks}\n`;
    });
    text += `\n`;
  });

  // Include Problems/Incidents
  text += `━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n`;
  text += `🚨 চোখে পড়া সমস্যা ও সমাধান অগ্রগতি (${appData.tickets.length} টি):\n`;
  if (appData.tickets.length === 0) {
    text += `কোনো মেকানিক্যাল বা পরিকাঠামো ত্রুটি নেই। সবকিছু স্বাভাবিক।\n`;
  } else {
    appData.tickets.forEach((tick, i) => {
      text += `${i+1}. [${tick.status}] ${tick.title} (${tick.category})\n`;
      text += `   • স্থান: ${tick.location} | প্রায়োরিটি: ${tick.priority}\n`;
      text += `   • সমাধান ডেডলাইন: ${new Date(tick.deadlineTime).toLocaleString()}\n`;
      text += `   • সম্পৃক্ত বিভাগ: ${tick.departments.join(', ')}\n`;
      text += `   • দায়িত্বে নিয়োজিত: ${tick.assignees.join(', ')}\n`;
      if (tick.resolutionNotes) text += `   • কাজের অগ্রগতি: ${tick.resolutionNotes}\n`;
      text += `\n`;
    });
  }

  text += `━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n`;
  text += `Generated via Car Service Center Admin Desktop Hub`;

  document.getElementById("reportPreviewText").textContent = text;
  return text;
}

// Event Listeners Setup
function setupEventListeners() {
  // Tab Switching
  document.querySelectorAll(".nav-item").forEach(button => {
    button.addEventListener("click", () => {
      document.querySelectorAll(".nav-item").forEach(b => b.classList.remove("active"));
      document.querySelectorAll(".tab-pane").forEach(p => p.classList.remove("active"));

      button.classList.add("active");
      const tabId = `tab-${button.dataset.tab}`;
      document.getElementById(tabId).classList.add("active");
    });
  });

  // Slot Filtering
  document.querySelectorAll(".slot-pill").forEach(pill => {
    pill.addEventListener("click", () => {
      document.querySelectorAll(".slot-pill").forEach(p => p.classList.remove("active"));
      pill.classList.add("active");
      appData.activeSlot = pill.dataset.slot;
      renderTasks();
    });
  });

  // Incident Status Filtering
  document.querySelectorAll(".status-chip").forEach(chip => {
    chip.addEventListener("click", () => {
      document.querySelectorAll(".status-chip").forEach(c => c.classList.remove("active"));
      chip.classList.add("active");
      appData.activeIssueStatus = chip.dataset.status;
      renderIncidents();
    });
  });

  // Modals
  const problemModal = document.getElementById("newProblemModal");
  const openModal = () => {
    // Set default deadline to 3 hours from now
    const d = new Date(Date.now() + 3 * 3600 * 1000);
    const localIso = new Date(d.getTime() - d.getTimezoneOffset() * 60000).toISOString().slice(0, 16);
    document.getElementById("probDeadline").value = localIso;
    problemModal.classList.remove("hidden");
  };

  document.getElementById("btnOpenNewProblem").addEventListener("click", openModal);
  document.getElementById("btnNewProblemSecondary").addEventListener("click", openModal);
  document.getElementById("btnCloseProblemModal").addEventListener("click", () => problemModal.classList.add("hidden"));
  document.getElementById("btnCancelProblem").addEventListener("click", () => problemModal.classList.add("hidden"));

  // Form Submit: New Problem
  document.getElementById("problemForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const title = document.getElementById("probTitle").value.trim();
    const category = document.getElementById("probCategory").value;
    const location = document.getElementById("probLocation").value.trim();
    const priority = document.getElementById("probSeverity").value;
    const deadlineStr = document.getElementById("probDeadline").value;
    const instructions = document.getElementById("probInstructions").value.trim();

    const assignees = Array.from(document.querySelectorAll("#assigneeSelector input:checked")).map(i => i.value);
    const departments = Array.from(document.querySelectorAll("#deptSelector input:checked")).map(i => i.value);

    const newTicket = {
      id: "ticket_" + Date.now(),
      title,
      category,
      location,
      priority,
      deadlineTime: new Date(deadlineStr).getTime(),
      instructions,
      assignees: assignees.length > 0 ? assignees : ["ফোরম্যান"],
      departments: departments.length > 0 ? departments : ["Workshop Operations"],
      status: "OPEN",
      createdAt: Date.now(),
      resolutionNotes: "",
      isOverdue: false
    };

    appData.tickets.unshift(newTicket);
    await saveData();

    problemModal.classList.add("hidden");
    document.getElementById("problemForm").reset();

    // Trigger immediate alert
    sendDesktopNotification(
      `🚨 নতুন সমস্যা রেজিস্টার করা হয়েছে: ${title}`,
      `স্থান: ${location} | প্রায়োরিটি: ${priority} | বিভাগ: ${departments.join(', ')}`,
      priority === 'CRITICAL' ? 'critical' : 'normal'
    );

    renderAll();
  });

  // Update Status Modal
  const updateModal = document.getElementById("updateProblemModal");
  document.getElementById("btnCloseUpdateModal").addEventListener("click", () => updateModal.classList.add("hidden"));
  document.getElementById("btnCancelUpdate").addEventListener("click", () => updateModal.classList.add("hidden"));

  window.openUpdateModal = function(id) {
    const ticket = appData.tickets.find(t => t.id === id);
    if (!ticket) return;

    document.getElementById("updateTicketId").value = ticket.id;
    document.getElementById("updateStatusSelect").value = ticket.status;
    document.getElementById("updateNotes").value = ticket.resolutionNotes || "";
    document.getElementById("updateTicketSummary").innerHTML = `
      <strong>${ticket.title}</strong><br>
      <span style="font-size:12px; color:#94a3b8;">${ticket.category} | ${ticket.location}</span>
    `;
    updateModal.classList.remove("hidden");
  };

  document.getElementById("updateStatusForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const id = document.getElementById("updateTicketId").value;
    const status = document.getElementById("updateStatusSelect").value;
    const notes = document.getElementById("updateNotes").value.trim();

    const ticket = appData.tickets.find(t => t.id === id);
    if (ticket) {
      ticket.status = status;
      ticket.resolutionNotes = notes;
      if (status === 'RESOLVED' || status === 'CLOSED') {
        ticket.isOverdue = false;
        ticket.resolvedAt = Date.now();
      }
      await saveData();
      updateModal.classList.add("hidden");
      renderAll();

      sendDesktopNotification(
        `সমস্যার স্ট্যাটাস আপডেট: ${ticket.title}`,
        `নতুন স্ট্যাটাস: ${status} | নোট: ${notes || 'কোনো নোট দেওয়া হয়নি'}`
      );
    }
  });

  // Report Actions
  document.getElementById("btnOpenDailyReport").addEventListener("click", () => {
    document.querySelector('[data-tab="reports"]').click();
  });

  document.getElementById("btnCopyReport").addEventListener("click", () => {
    const text = renderReportPreview();
    navigator.clipboard.writeText(text).then(() => {
      alert("রিপোর্ট ক্লিপবোর্ডে কপি করা হয়েছে!");
    });
  });

  document.getElementById("btnSendWhatsApp").addEventListener("click", () => {
    const text = encodeURIComponent(renderReportPreview());
    const phone = appData.config.headPhone.replace(/[^0-9]/g, '');
    const url = `https://wa.me/${phone}?text=${text}`;
    if (window.desktopApi && window.desktopApi.openExternal) {
      window.desktopApi.openExternal(url);
    } else {
      window.open(url, '_blank');
    }
  });

  document.getElementById("btnPrintReport").addEventListener("click", () => {
    window.print();
  });

  // SOS Button
  document.getElementById("btnTriggerSos").addEventListener("click", () => {
    sendDesktopNotification(
      "🚨 জরুরি SOS সতর্কবার্তা!",
      `সার্ভিস সেন্টারে জরুরি সমস্যা চিহ্নিত করা হয়েছে। ম্যানেজমেন্টের দৃষ্টি আকর্ষণ করা হচ্ছে।`,
      'critical'
    );
    alert(`🚨 জরুরি SOS অ্যালার্ট ট্রিগার করা হয়েছে!\nহেড অব অ্যাডমিন (${appData.config.headPhone}) নম্বরে সতর্কবার্তা পাঠানো হয়েছে।`);
  });

  // Save Settings
  document.getElementById("btnSaveConfig").addEventListener("click", async () => {
    appData.config.officerName = document.getElementById("cfgOfficerName").value.trim();
    appData.config.centerName = document.getElementById("cfgCenterName").value.trim();
    appData.config.headPhone = document.getElementById("cfgHeadPhone").value.trim();

    await saveData();
    renderAll();
    alert("সেটিংস সফলভাবে সংরক্ষিত হয়েছে!");
  });

  // Test Alerts
  document.getElementById("testMorningAlert").addEventListener("click", () => {
    sendDesktopNotification(
      "🌅 অফিসে স্বাগতম! সকালের ওপেনিং ইনস্পেকশন শুরু করুন",
      "গেট, সার্ভিস বে, পাওয়ার সাপ্লাই, জেনারেটর ডিজেল ও কাস্টমার লাউঞ্জ চেক করুন।"
    );
  });

  document.getElementById("testMiddayAlert").addEventListener("click", () => {
    sendDesktopNotification(
      "☀️ দুপুর: ওয়ার্কশপ সাপোর্ট ও সেফটি ইনস্পেকশন",
      "টেকনিশিয়ানদের পরিকাঠামো সুবিধা, ফায়ার সেফটি ও ভেন্ডর মনিটরিং করুন।"
    );
  });

  document.getElementById("testPreClosingAlert").addEventListener("click", () => {
    sendDesktopNotification(
      "⚠️ অফিস ছুটির পূর্বে তাগিদ! (৫:১৫ PM)",
      "নরমালি ৬টায় অফিস শেষ। আজকের কোনো রুটিন কাজ বা সমস্যা পেন্ডিং থাকলে দ্রুত সমাধান করুন!",
      'critical'
    );
  });

  document.getElementById("testSlaAlert").addEventListener("click", () => {
    sendDesktopNotification(
      "🚨 জরুরি সতর্কবার্তা: প্রবলেম SLA সময়সীমা অতিক্রান্ত!",
      "একটি হাই-প্রায়োরিটি সমস্যার সমাধানের ডেডলাইন পার হয়ে গেছে। সংশ্লিষ্ট টেকনিশিয়ান ও ডিপার্টমেন্টকে তাগিদ দিন!",
      'critical'
    );
  });
}
