# KanSky - Spigot服务器RPG插件

<h5 align="center">
<a href="#introduction">插件简介</a>
<a href="#features">插件特点</a>
<a href="#config">基本信息及配置</a>
<a href="#tags">标签</a>
</h5>
<h4 align="center">v 1.1.0</h4>

<a name="introduction"></a>
## 插件简介

* Kansky是一个RPG插件,提供了较为基础的属性
* 目前此插件还在测试中,内部api随时会更改,仅供娱乐,bug数量未知

<a name="features"></a>
## 插件特点
* 支持加载JS


<a name="config"></a>
## 基本信息及配置

<a name="tags"></a>
## 插件内置的属性

### 插件内置的伤害属性
如果不是特别标注,则默认支持负数(没有测试过±Infinity,NaN等极端值)

#### 伤害属性
* Damage 基础伤害
* Strength 力量,除以100(可以在配置文件中更改)后与伤害相乘(加1后)
* CritDamage 暴击增幅,除以100(可以在配置文件中更改)后与伤害相乘(加1后)
* CritChance 暴击几率
<br>
#### 防御属性
* Defense 防御,计算公式如下: 
$$
    damage - damage \times \frac{defense}{100} 
$$
