package com.app.secanalyst.model

import com.app.secanalyst.model.switch.ThemeMode
import com.app.secanalyst.ui.theme.SecAnalystPalette

data class DeviceInfoCardPreview(
    val id: String,
    val title: String,
    val items: List<Pair<String, String>>
)

data class ToolboxCardPreview(
    val title: String,
    val subtitle: String,
    val enabled: Boolean
)

data class ToolboxItemPreview(
    val id: String,
    val title: String,
    val subtitle: String
)

object PreviewModel {
    val themeColorPreviewPalette: SecAnalystPalette = SecAnalystPalette.GREEN
    val themeModePreview: ThemeMode = ThemeMode.ALGORITHM

    fun getIpInfoPreview(): IpInfo = IpInfo(
        ip = "192.168.1.105",
        mask = "255.255.255.0",
        gateway = "192.168.1.1"
    )

    fun getDeviceInfoPreviewCards(): List<DeviceInfoCardPreview> = listOf(
        DeviceInfoCardPreview(
            id = "basic",
            title = "基本信息",
            items = listOf(
                "品牌" to "Xiaomi",
                "型号" to "2410DPN6CC",
                "Android 版本" to "4",
                "API 级别" to "36",
                "处理器架构" to "aarch64",
                "开机时间" to "105:04:06",
                "ROM 发布日期" to "2026-01-13"
            )
        ),
        DeviceInfoCardPreview(
            id = "screen",
            title = "屏幕信息",
            items = listOf(
                "分辨率" to "1080x2228",
                "密度" to "Unknown",
                "DPI" to "450"
            )
        ),
        DeviceInfoCardPreview(
            id = "network",
            title = "网络信息",
            items = listOf(
                "连接类型" to "WiFi",
                "上传速度" to "221.46 Mbps",
                "下载速度" to "49.01 Mbps"
            )
        ),
        DeviceInfoCardPreview(
            id = "memory",
            title = "内存信息",
            items = listOf(
                "总内存" to "14GB",
                "已用内存" to "8GB",
                "空闲内存" to "6GB",
                "内存使用率" to "57%"
            )
        ),
        DeviceInfoCardPreview(
            id = "storage",
            title = "存储信息",
            items = listOf(
                "总容量" to "971GB",
                "已使用" to "850GB",
                "剩余空间" to "121GB",
                "使用率" to "88%"
            )
        ),
        DeviceInfoCardPreview(
            id = "battery",
            title = "电池信息",
            items = listOf(
                "电量" to "23%",
                "温度" to "21.6°C",
                "健康度" to "Good",
                "电压" to "3689mV",
                "充电状态" to "未充电"
            )
        )
    )

    fun getToolboxCardPreview(): ToolboxCardPreview =
        ToolboxCardPreview("流量监控", "实时监控网络流量", true)

    fun getToolboxItems(): List<ToolboxItemPreview> = listOf(
        ToolboxItemPreview("traffic", "流量监控", "实时监控网络流量使用情况"),
        ToolboxItemPreview("appUsage", "应用使用情况", "查看各应用流量与使用统计"),
        ToolboxItemPreview("networkDiagnosis", "网络诊断", "检测网络连接与延迟"),
        ToolboxItemPreview("dataPlan", "流量套餐", "管理流量套餐与用量提醒"),
        ToolboxItemPreview("deviceInfo", "设备信息", "查看设备硬件与系统信息"),
        ToolboxItemPreview("wifiScan", "Wifi扫描", "扫描周边 WiFi 信号与强度"),
        ToolboxItemPreview("alertCenter", "预测告警中心", "流量与异常预测告警")
    )
}
