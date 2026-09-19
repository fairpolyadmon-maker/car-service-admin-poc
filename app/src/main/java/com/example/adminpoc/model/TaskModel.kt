package com.example.adminpoc.model

enum class TaskSlot(val label: String, val timeSlot: String, val targetHour: Int, val targetMinute: Int) {
    MORNING("সকাল (ওপেনিং)", "09:00 AM - 09:45 AM", 9, 0),
    MIDDAY("দুপুর (ওয়ার্কশপ ও সেফটি)", "01:00 PM - 02:00 PM", 13, 0),
    PRE_CLOSING("প্রি-ক্লোজিং (তাগিদ)", "05:15 PM - 05:45 PM", 17, 15),
    EVENING("সন্ধ্যা (ক্লোজিং ও রিপোর্ট)", "05:45 PM - 06:00 PM", 17, 45)
}

enum class Priority {
    CRITICAL,
    HIGH,
    NORMAL
}

data class TaskItem(
    val id: String,
    val title: String,
    val bengaliTitle: String,
    val category: String,
    val description: String,
    val slot: TaskSlot,
    val priority: Priority = Priority.HIGH,
    var isCompleted: Boolean = false,
    var completedAt: Long? = null,
    var remarks: String = "",
    var isEscalated: Boolean = false
)

object DefaultTasks {
    fun createDefaultTasks(): List<TaskItem> {
        return listOf(
            // --- 1. MORNING SLOT (09:00 - 09:45 AM) ---
            TaskItem(
                id = "morn_open_inspect",
                title = "Daily Opening Inspection",
                bengaliTitle = "সার্ভিস সেন্টার দৈনিক ওপেনিং ইনস্পেকশন",
                category = "1. Facility Management",
                description = "মেইন গেট, শাটার, সার্ভিস বে, অফিস ও ওয়াশরুম খোলা ও কার্যক্ষমতা পর্যবেক্ষণ করুন।",
                slot = TaskSlot.MORNING,
                priority = Priority.CRITICAL
            ),
            TaskItem(
                id = "morn_utilities_check",
                title = "Utilities & Power Check",
                bengaliTitle = "বিদ্যুৎ, জেনারেটর ও ইউপিএস (UPS) চেক",
                category = "4. Utilities & Engineering",
                description = "মেইন পাওয়ার সরবরাহ, জেনারেটর ডিজেল/ব্যাটারি লেভেল, কম্প্রেসার ও ইউপিএস ব্যাকআপ সচল আছে কি না পরীক্ষা করুন।",
                slot = TaskSlot.MORNING,
                priority = Priority.CRITICAL
            ),
            TaskItem(
                id = "morn_housekeeping",
                title = "Housekeeping & Cleanliness Standards",
                bengaliTitle = "সার্ভিস বে ও সাধারণ স্থান পরিচ্ছন্নতা তদারকি",
                category = "5. Waste & Housekeeping",
                description = "সুইপার/ক্লিনার উপস্থিত কি না এবং প্রতিটি সার্ভিস বে তেলকালিমুক্ত ও পরিষ্কার রাখা নিশ্চিত করুন।",
                slot = TaskSlot.MORNING,
                priority = Priority.HIGH
            ),
            TaskItem(
                id = "morn_pantry_lounge",
                title = "Customer Waiting Area & Pantry Ready",
                bengaliTitle = "কাস্টমার লাউঞ্জ ও প্যান্ট্রি রেডি নিশ্চিতকরণ",
                category = "1. Facility Management",
                description = "এসি সচল, খাবার পানি/চা ডিসপেনসার রেডি, ওয়াশরুম পরিষ্কার ও পরিপাটি আসন ব্যবস্থা নিশ্চিত করুন।",
                slot = TaskSlot.MORNING,
                priority = Priority.HIGH
            ),
            TaskItem(
                id = "morn_security_attendance",
                title = "Security & Biometric Attendance",
                bengaliTitle = "সিকিউরিটি গার্ড পোস্ট ও বায়োমেট্রিক উপস্থিতি",
                category = "3. Safety & Security",
                description = "নিরাপত্তা প্রহরী উপস্থিত, সিসিটিভি স্ক্রিন সচল এবং স্টাফদের সময়মতো উপস্থিতি মনিটর করুন।",
                slot = TaskSlot.MORNING,
                priority = Priority.NORMAL
            ),

            // --- 2. MID-DAY SLOT (01:00 - 02:00 PM) ---
            TaskItem(
                id = "mid_workshop_support",
                title = "Workshop Support & Facilities Check",
                bengaliTitle = "ওয়ার্কশপ পরিকাঠামো ও সুবিধা পর্যবেক্ষণ",
                category = "2. Workshop Support",
                description = "লাইটিং, ড্রেনেজ, এয়ার পাইপলাইন প্রেশার ও ফোরম্যান/টেকনিশিয়ানদের কাজের পরিবেশ ঠিক আছে কি না নিশ্চিত করুন।",
                slot = TaskSlot.MIDDAY,
                priority = Priority.HIGH
            ),
            TaskItem(
                id = "mid_safety_emergency",
                title = "Fire Safety & Emergency Exits Inspection",
                bengaliTitle = "ফায়ার এক্সটিঙ্গুইশার ও ইমার্জেন্সি এক্সিট পরিদর্শন",
                category = "3. Safety & Security",
                description = "জরুরি বহির্গমন রাস্তা খোলা ও বাধাহীন রাখা, অগ্নিনির্বাপক যন্ত্রের প্রেসার গেজ ঠিক থাকা নিশ্চিত করুন।",
                slot = TaskSlot.MIDDAY,
                priority = Priority.CRITICAL
            ),
            TaskItem(
                id = "mid_contractor_mgmt",
                title = "Vendor & Contractor Work Supervision",
                bengaliTitle = "ভেন্ডর ও মেরামতকারী কাজের অগ্রগতি তদারকি",
                category = "7. Vendor & Contractor Management",
                description = "বহিরাগত টেকনিশিয়ান বা ঠিকাদারদের ওয়ার্ক পারমিট, কাজের মান ও নিরাপত্তা বিধি পর্যবেক্ষণ করুন।",
                slot = TaskSlot.MIDDAY,
                priority = Priority.NORMAL
            ),
            TaskItem(
                id = "mid_waste_disposal",
                title = "Operational Waste & Scrap Segregation",
                bengaliTitle = "অপসারণযোগ্য বর্জ্য, ব্যবহৃত মবিল ও ধাতব স্ক্র্যাপ সরানো",
                category = "5. Waste & Housekeeping",
                description = "ওয়ার্কশপের বিপজ্জনক বর্জ্য এবং ড্রেন ক্লিনিং স্ট্যান্ডার্ড অনুযায়ী সরানো হচ্ছে কি না মনিটর করুন।",
                slot = TaskSlot.MIDDAY,
                priority = Priority.HIGH
            ),

            // --- 3. PRE-CLOSING SLOT (05:15 - 05:45 PM) ---
            TaskItem(
                id = "pre_pending_review",
                title = "Pending Routine Tasks Enforcement",
                bengaliTitle = "অসমাপ্ত ও পেন্ডিং কাজ চিহ্নিতকরণ ও দ্রুত সম্পন্ন করা",
                category = "8. Daily Reporting",
                description = "অফিস ত্যাগের পূর্বে সারাদিনের কোনো রুটিন কাজ অসমাপ্ত থাকলে তা এখনই দ্রুত শেষ করুন।",
                slot = TaskSlot.PRE_CLOSING,
                priority = Priority.CRITICAL
            ),
            TaskItem(
                id = "pre_stationery_supplies",
                title = "Supplies & Administrative Requisitions",
                bengaliTitle = "স্টেশনারি ও অফিস সামগ্রী মজুদ নিরীক্ষণ",
                category = "6. Administrative Support",
                description = "পরবর্তী দিনের জন্য প্রয়োজনীয় ফর্ম, স্টেশনারি বা সাপ্লাই রিকুইজিশন চেক ও লিপিবদ্ধ করুন।",
                slot = TaskSlot.PRE_CLOSING,
                priority = Priority.NORMAL
            ),
            TaskItem(
                id = "pre_incident_breakdown_log",
                title = "Incident & Equipment Breakdown Logging",
                bengaliTitle = "যান্ত্রিক ত্রুটি বা দুর্ঘটনা লগ বইয়ে এন্ট্রি",
                category = "4. Utilities & Engineering",
                description = "সারাদিনের কোনো মেশিন, লিফট বা ইউটিলিটি সমস্যা হয়ে থাকলে রেজিস্টারে এন্ট্রি করুন।",
                slot = TaskSlot.PRE_CLOSING,
                priority = Priority.HIGH
            ),

            // --- 4. EVENING CLOSING SLOT (05:45 - 06:00 PM) ---
            TaskItem(
                id = "eve_daily_report",
                title = "Daily Operational Report Submission",
                bengaliTitle = "হেড অব অ্যাডমিনের কাছে দৈনিক রিপোর্ট পাঠানো",
                category = "8. Daily Reporting",
                description = "দিনের সম্পূর্ণ ইনস্পেকশন ও কাজের অগ্রগতি রিপোর্ট তৈরি করে ম্যানেজমেন্টকে পাঠান (WhatsApp/SMS)।",
                slot = TaskSlot.EVENING,
                priority = Priority.CRITICAL
            ),
            TaskItem(
                id = "eve_critical_escalation",
                title = "Critical Issues Escalation",
                bengaliTitle = "জরুরি বা অমীমাংসিত সমস্যা হেড অফিসে ফ্ল্যাগ করা",
                category = "8. Daily Reporting",
                description = "যেসব সমস্যা আজকের মধ্যে সমাধান হয়নি তা ঊর্ধ্বতন কর্তৃপক্ষের নজরে এনে নির্দেশনা নিন।",
                slot = TaskSlot.EVENING,
                priority = Priority.HIGH
            ),
            TaskItem(
                id = "eve_closing_lockup",
                title = "Daily Closing & Security Lockup Handover",
                bengaliTitle = "সার্ভিস সেন্টার দৈনিক ক্লোজিং ও নাইট সিকিউরিটিকে চাবি হস্তান্তর",
                category = "1. Facility Management",
                description = "অপ্রয়োজনীয় আলো, এসি ও এয়ার কম্প্রেসার বন্ধ, ওয়াটার লাইন লক এবং সিকিউরিটিকে দায়িত্ব বুঝিয়ে দিন।",
                slot = TaskSlot.EVENING,
                priority = Priority.CRITICAL
            )
        )
    }
}
