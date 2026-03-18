# 主题颜色角色说明

## 建议启用的颜色角色

### 必选

| 角色 | 用途 |
|------|------|
| **primary** 主色 | 品牌主色。主要按钮、FAB、选中态、关键图标、TopAppBar 强调等。 |
| **secondary** 次色 | 辅助强调。次要按钮、Chip、Slider、需区分但不过于抢眼的控件。 |
| **background** 背景 | 最底层。Scaffold、整页背景，与 surface 拉开层级。 |
| **surface** 表面 | 浮在 background 之上。Card、Dialog、BottomSheet、Menu，形成「底板—浮层」层级。 |

### 可选但推荐

| 角色 | 用途 |
|------|------|
| **surfaceContainer** / **surfaceContainerHigh** | 卡片、列表区、Toast 等需「再高一层」的浮层，层次更清晰。 |
| **error** 错误色 | 错误态、校验提示、危险操作，M3 标准角色。 |
| **tertiary** 第三色 | 与主、次色形成对比。输入框焦点、特殊标签、图表对比色块等。 |

**变体规则**：每个方案内仅在同色相中加灰或改透明度，不混入其他色相。

---

## 各颜色完整方案（关键字）

每个颜色对应一套完整角色，且区分**深色主题**与**浅色主题**：深色用 \*80 后缀（如 Red80），浅色用 \*40 后缀（如 Red40），与 `darkColorScheme` / `lightColorScheme` 一致。error 两主题统一用大红系（Red80 / Red40）。

### 🔴 红色系
- **大红** (Red) – 强调、错误提示、热情
- **酒红** (Burgundy) – 奢华、复古
- **珊瑚粉** (Coral) – 柔和、活力

**深色主题 (DarkColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **大红** Red | Red80 | Red80+灰 | Red80+灰(浅) | Red80+深灰 | Red80+灰 | Red80+灰(中) | Red80+灰(略浅) | Red80 |
| **酒红** Burgundy | Burgundy80 | Burgundy80+灰 | Burgundy80+灰(浅) | Burgundy80+深灰 | Burgundy80+灰 | Burgundy80+灰(中) | Burgundy80+灰(略浅) | Red80 |
| **珊瑚粉** Coral | Coral80 | Coral80+灰 | Coral80+灰(浅) | Coral80+深灰 | Coral80+灰 | Coral80+灰(中) | Coral80+灰(略浅) | Red80 |

**浅色主题 (LightColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **大红** Red | Red40 | Red40+灰 | Red40+灰(浅) | Red40+深灰 | Red40+灰 | Red40+灰(中) | Red40+灰(略浅) | Red40 |
| **酒红** Burgundy | Burgundy40 | Burgundy40+灰 | Burgundy40+灰(浅) | Burgundy40+深灰 | Burgundy40+灰 | Burgundy40+灰(中) | Burgundy40+灰(略浅) | Red40 |
| **珊瑚粉** Coral | Coral40 | Coral40+灰 | Coral40+灰(浅) | Coral40+深灰 | Coral40+灰 | Coral40+灰(中) | Coral40+灰(略浅) | Red40 |

### 🟠 橙色系
- **橙色** (Orange) – 活泼、警告
- **橘红** (Tangerine) – 阳光、食欲
- **杏色** (Apricot) – 温柔、儿童

**深色主题 (DarkColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **橙色** Orange | Orange80 | Orange80+灰 | Orange80+灰(浅) | Orange80+深灰 | Orange80+灰 | Orange80+灰(中) | Orange80+灰(略浅) | Red80 |
| **橘红** Tangerine | Tangerine80 | Tangerine80+灰 | Tangerine80+灰(浅) | Tangerine80+深灰 | Tangerine80+灰 | Tangerine80+灰(中) | Tangerine80+灰(略浅) | Red80 |
| **杏色** Apricot | Apricot80 | Apricot80+灰 | Apricot80+灰(浅) | Apricot80+深灰 | Apricot80+灰 | Apricot80+灰(中) | Apricot80+灰(略浅) | Red80 |

**浅色主题 (LightColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **橙色** Orange | Orange40 | Orange40+灰 | Orange40+灰(浅) | Orange40+深灰 | Orange40+灰 | Orange40+灰(中) | Orange40+灰(略浅) | Red40 |
| **橘红** Tangerine | Tangerine40 | Tangerine40+灰 | Tangerine40+灰(浅) | Tangerine40+深灰 | Tangerine40+灰 | Tangerine40+灰(中) | Tangerine40+灰(略浅) | Red40 |
| **杏色** Apricot | Apricot40 | Apricot40+灰 | Apricot40+灰(浅) | Apricot40+深灰 | Apricot40+灰 | Apricot40+灰(中) | Apricot40+灰(略浅) | Red40 |

### 🟡 黄色系
- **黄色** (Yellow) – 明亮、快乐
- **金色** (Gold) – 高贵、成就
- **米黄** (Beige) – 中性、背景

**深色主题 (DarkColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **黄色** Yellow | Yellow80 | Yellow80+灰 | Yellow80+灰(浅) | Yellow80+深灰 | Yellow80+灰 | Yellow80+灰(中) | Yellow80+灰(略浅) | Red80 |
| **金色** Gold | Gold80 | Gold80+灰 | Gold80+灰(浅) | Gold80+深灰 | Gold80+灰 | Gold80+灰(中) | Gold80+灰(略浅) | Red80 |
| **米黄** Beige | Beige80 | Beige80+灰 | Beige80+灰(浅) | Beige80+深灰 | Beige80+灰 | Beige80+灰(中) | Beige80+灰(略浅) | Red80 |

**浅色主题 (LightColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **黄色** Yellow | Yellow40 | Yellow40+灰 | Yellow40+灰(浅) | Yellow40+深灰 | Yellow40+灰 | Yellow40+灰(中) | Yellow40+灰(略浅) | Red40 |
| **金色** Gold | Gold40 | Gold40+灰 | Gold40+灰(浅) | Gold40+深灰 | Gold40+灰 | Gold40+灰(中) | Gold40+灰(略浅) | Red40 |
| **米黄** Beige | Beige40 | Beige40+灰 | Beige40+灰(浅) | Beige40+深灰 | Beige40+灰 | Beige40+灰(中) | Beige40+灰(略浅) | Red40 |

### 🟢 绿色系
- **绿色** (Green) – 自然、成功
- **薄荷绿** (Mint) – 清新、医疗
- **橄榄绿** (Olive) – 稳重、军事

**深色主题 (DarkColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **绿色** Green | Green80 | Green80+灰 | Green80+灰(浅) | Green80+深灰 | Green80+灰 | Green80+灰(中) | Green80+灰(略浅) | Red80 |
| **薄荷绿** Mint | Mint80 | Mint80+灰 | Mint80+灰(浅) | Mint80+深灰 | Mint80+灰 | Mint80+灰(中) | Mint80+灰(略浅) | Red80 |
| **橄榄绿** Olive | Olive80 | Olive80+灰 | Olive80+灰(浅) | Olive80+深灰 | Olive80+灰 | Olive80+灰(中) | Olive80+灰(略浅) | Red80 |

**浅色主题 (LightColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **绿色** Green | Green40 | Green40+灰 | Green40+灰(浅) | Green40+深灰 | Green40+灰 | Green40+灰(中) | Green40+灰(略浅) | Red40 |
| **薄荷绿** Mint | Mint40 | Mint40+灰 | Mint40+灰(浅) | Mint40+深灰 | Mint40+灰 | Mint40+灰(中) | Mint40+灰(略浅) | Red40 |
| **橄榄绿** Olive | Olive40 | Olive40+灰 | Olive40+灰(浅) | Olive40+深灰 | Olive40+灰 | Olive40+灰(中) | Olive40+灰(略浅) | Red40 |

### 🔵 青色系
- **青色** (Cyan) – 科技、清爽
- **蓝绿** (Teal) – 沉稳、专业

**深色主题 (DarkColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **青色** Cyan | Cyan80 | Cyan80+灰 | Cyan80+灰(浅) | Cyan80+深灰 | Cyan80+灰 | Cyan80+灰(中) | Cyan80+灰(略浅) | Red80 |
| **蓝绿** Teal | Teal80 | Teal80+灰 | Teal80+灰(浅) | Teal80+深灰 | Teal80+灰 | Teal80+灰(中) | Teal80+灰(略浅) | Red80 |

**浅色主题 (LightColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **青色** Cyan | Cyan40 | Cyan40+灰 | Cyan40+灰(浅) | Cyan40+深灰 | Cyan40+灰 | Cyan40+灰(中) | Cyan40+灰(略浅) | Red40 |
| **蓝绿** Teal | Teal40 | Teal40+灰 | Teal40+灰(浅) | Teal40+深灰 | Teal40+灰 | Teal40+灰(中) | Teal40+灰(略浅) | Red40 |

### 🔷 蓝色系
- **蓝色** (Blue) – 信任、冷静
- **海军蓝** (Navy) – 权威、商务
- **天蓝** (Sky Blue) – 开朗、天空

**深色主题 (DarkColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **蓝色** Blue | Blue80 | Blue80+灰 | Blue80+灰(浅) | Blue80+深灰 | Blue80+灰 | Blue80+灰(中) | Blue80+灰(略浅) | Red80 |
| **海军蓝** Navy | Navy80 | Navy80+灰 | Navy80+灰(浅) | Navy80+深灰 | Navy80+灰 | Navy80+灰(中) | Navy80+灰(略浅) | Red80 |
| **天蓝** Sky Blue | SkyBlue80 | SkyBlue80+灰 | SkyBlue80+灰(浅) | SkyBlue80+深灰 | SkyBlue80+灰 | SkyBlue80+灰(中) | SkyBlue80+灰(略浅) | Red80 |

**浅色主题 (LightColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **蓝色** Blue | Blue40 | Blue40+灰 | Blue40+灰(浅) | Blue40+深灰 | Blue40+灰 | Blue40+灰(中) | Blue40+灰(略浅) | Red40 |
| **海军蓝** Navy | Navy40 | Navy40+灰 | Navy40+灰(浅) | Navy40+深灰 | Navy40+灰 | Navy40+灰(中) | Navy40+灰(略浅) | Red40 |
| **天蓝** Sky Blue | SkyBlue40 | SkyBlue40+灰 | SkyBlue40+灰(浅) | SkyBlue40+深灰 | SkyBlue40+灰 | SkyBlue40+灰(中) | SkyBlue40+灰(略浅) | Red40 |

### 🟣 紫色系（当前项目）
- **紫色** (Purple) – 神秘、创意
- **薰衣草紫** (Lavender) – 浪漫、舒缓

**深色主题 (DarkColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **紫色** Purple | Purple80 | Purple80+灰 | Purple80+灰(浅) | Purple80+深灰 | Purple80+灰 | Purple80+灰(中) | Purple80+灰(略浅) | Red80 |
| **薰衣草紫** Lavender | Lavender80 | Lavender80+灰 | Purple80 | Lavender80+深灰 | Lavender80+灰 | Lavender80+灰(中) | Lavender80+灰(略浅) | Red80 |

**浅色主题 (LightColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **紫色** Purple | Purple40 | Purple40+灰 | Purple40+灰(浅) | Purple40+深灰 | Purple40+灰 | Purple40+灰(中) | Purple40+灰(略浅) | Red40 |
| **薰衣草紫** Lavender | Lavender40 | Lavender40+灰 | Purple40 | Lavender40+深灰 | Lavender40+灰 | Lavender40+灰(中) | Lavender40+灰(略浅) | Red40 |

### ⚪ 中性色
- **白色** (White) – 纯洁、留白
- **灰色** (Gray) – 中庸、背景
- **黑色** (Black) – 正式、高端

**深色主题 (DarkColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **白色** White | White80 | White80+灰 | Gray80 | White80+深灰 | White80+灰 | White80+灰(中) | White80+灰(略浅) | Red80 |
| **灰色** Gray | Gray80 | Gray80+灰 | Gray80+灰(浅) | Gray80+深灰 | Gray80+灰 | Gray80+灰(中) | Gray80+灰(略浅) | Red80 |
| **黑色** Black | Black80 | Black80+灰 | Gray80 | Black80+深灰 | Black80+灰 | Black80+灰(中) | Black80+灰(略浅) | Red80 |

**浅色主题 (LightColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **白色** White | White40 | White40+灰 | Gray40 | White40+深灰 | White40+灰 | White40+灰(中) | White40+灰(略浅) | Red40 |
| **灰色** Gray | Gray40 | Gray40+灰 | Gray40+灰(浅) | Gray40+深灰 | Gray40+灰 | Gray40+灰(中) | Gray40+灰(略浅) | Red40 |
| **黑色** Black | Black40 | Black40+灰 | Gray40 | Black40+深灰 | Black40+灰 | Black40+灰(中) | Black40+灰(略浅) | Red40 |

### 🟤 棕色系
- **棕色** (Brown) – 大地、朴实
- **驼色** (Tan) – 温暖、秋冬
- **巧克力色** (Chocolate) – 浓郁

**深色主题 (DarkColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **棕色** Brown | Brown80 | Brown80+灰 | Brown80+灰(浅) | Brown80+深灰 | Brown80+灰 | Brown80+灰(中) | Brown80+灰(略浅) | Red80 |
| **驼色** Tan | Tan80 | Tan80+灰 | Tan80+灰(浅) | Tan80+深灰 | Tan80+灰 | Tan80+灰(中) | Tan80+灰(略浅) | Red80 |
| **巧克力色** Chocolate | Chocolate80 | Chocolate80+灰 | Chocolate80+灰(浅) | Chocolate80+深灰 | Chocolate80+灰 | Chocolate80+灰(中) | Chocolate80+灰(略浅) | Red80 |

**浅色主题 (LightColorScheme)**

| 关键字 | primary | secondary | tertiary | background | surface | surfaceContainer | surfaceContainerHigh | error |
|--------|---------|-----------|----------|------------|---------|------------------|----------------------|-------|
| **棕色** Brown | Brown40 | Brown40+灰 | Brown40+灰(浅) | Brown40+深灰 | Brown40+灰 | Brown40+灰(中) | Brown40+灰(略浅) | Red40 |
| **驼色** Tan | Tan40 | Tan40+灰 | Tan40+灰(浅) | Tan40+深灰 | Tan40+灰 | Tan40+灰(中) | Tan40+灰(略浅) | Red40 |
| **巧克力色** Chocolate | Chocolate40 | Chocolate40+灰 | Chocolate40+灰(浅) | Chocolate40+深灰 | Chocolate40+灰 | Chocolate40+灰(中) | Chocolate40+灰(略浅) | Red40 |

---

**说明**：\*80 用于 `darkColorScheme`，\*40 用于 `lightColorScheme`。「+灰」「+深灰」「(浅)」「(中)」表示同色相内加灰或透明度。error 统一用大红（Red80/Red40）。当前项目为**紫色系**：深色 Purple80 / PurpleGrey80 / Pink80，浅色 Purple40 / PurpleGrey40 / Pink40。
