# SQL Father - 模拟数据生成器（后端）

> SQL 之父项目：快速生成 SQL 和模拟数据，大幅提高开发测试效率！
>
> 前后端全栈项目 By [程序员鱼皮](https://docs.qq.com/doc/DUFFRVWladXVjeUxW)
>
> **制作不易，请勿商用和二次售卖！！！**



在线体验：[http://sqlfather.yupi.icu](http://sqlfather.yupi.icu)

视频演示（使用教程）：[https://www.bilibili.com/video/BV1eP411N7B7/](https://www.bilibili.com/video/BV1eP411N7B7/)



> 如果您觉得本项目有帮助，给 UP 主一个关注和三连就是最大的支持，谢谢！



前端代码仓库：[https://github.com/liyupi/sql-father-frontend-public](https://github.com/liyupi/sql-father-frontend-public)

后端代码仓库：[https://github.com/liyupi/sql-father-backend-public](https://github.com/liyupi/sql-father-backend-public)

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666090763987-f768817c-89d6-4326-b7e1-5ad06d5cc875-20221019132239543-20221019132502106-20221019132511275.png)

⭐️ 我在自己的 [编程知识星球](https://yupi.icu) 内给大家详细解读了这个项目的创作思路 + 技术选型 + 系统设计 + 源码解读 + 简历写法分享，如果你希望把这个项目写到简历上、或者深入学习，欢迎 [加入我的星球](https://yupi.icu) 观看。


## 项目背景

项目的创作起因就是鱼皮为了解决自己开发项目时反复写 SQL 建表和造数据的麻烦，顺便开源给大家一起来学习和完善~

试想一下：如果我做新项目的时候，不用写建表 SQL、不用造数据，能直接得到一个造好了假数据的表，那该有多好！

> 有同学问为什么项目不叫 SQL Mother？那我问问你为什么继承叫父类？



## 应用场景

无论你是前端、后端、测试、数据开发、数据科学，还是研究方向的同学，相信这个工具都会对你有帮助！

主要应用场景如下：

1）通过填写可视化表单的方式，快速生成建表语句、模拟数据和代码，告别重复工作！

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666098192337-c6ef4c6f-1d41-421e-882f-a40b17f8790f-20221019132251475-20221019132502279-20221019132511408.png)

2）支持多种快捷导入方式。比如已经有现成的数据表，可以直接导入建表语句，一键生成模拟数据；还可以直接导入 Excel 表格，快速完成建表；甚至还支持智能导入，输入几个单词就自动生成表格和数据！

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666098420835-59bfcd91-072c-45e9-a415-2fca70afd996-20221019132502336-20221019132511479.png)

3）支持多种生成模拟数据的规则。比如固定值、随机值、正则表达式、递增值，甚至还支持选择词库来生成特定范围内的随机值！

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666099678320-ffafe59e-60c5-49c7-aeb4-1c20ba3c4f4a-20221019132502444-20221019132511599.png)

4）支持词库、表设计、字段信息共享。可以学习或参考其他同学的库表设计，或者直接使用现成的库表和字段，一键生成或进行二次开发，协作万岁！

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666098509897-6fb00eee-79dd-4608-a364-8a0b5271a392-20221019132502525-20221019132511687.png)

5）可以直接使用现成的词库来建立字典表，或者作为研究用的数据集，并支持二次完善词库！

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666099806973-f48785e1-3d6c-49c9-a093-77c234d4f8b1-20221019132502586-20221019132511780.png)



## 值得学习

项目本身功能完整（分为用户前台和管理后台）、达到上线标准、架构设计清晰、目录结构规范。

前端用到了复杂的嵌套 & 动态 & 可折叠表单、代码编辑器；后端用到了多种主流设计模式、AOP 切面鉴权等，还是非常值得朋友们学习的。

> 也请大佬们请多多指教 🙏🏻



## 功能大全

### 用户前台

- 可视化建表
- 快捷导入建表
  - 智能导入
  - 导入表
  - 导入配置
  - 导入建表 SQL
  - 导入 Excel
- 一键生成
  - SQL 建表、插入数据语句
  - 模拟数据
  - JSON 数据
  - Java 代码
  - 前端代码
- 多种模拟数据生成规则
  - 固定值
  - 随机值
  - 正则表达式
  - 递增
  - 定制词库
- 词库共享
  - 创建词库
  - 词库继承
  - 一键创建字典表
  - 根据词库生成模拟数据（可以用来决定午饭吃什么哈哈）
- 表信息共享
  - 创建表信息
  - 一键复制建表语句
  - 一键导入表
- 字段共享
  - 创建字段
  - 一键复制创建字段语句
  - 一键导入字段
- 举报



### 管理后台

- 用户管理
- 词库管理
- 表信息管理
- 字段信息管理
- 举报管理



## 技术栈

### 前端

主要技术：

- React 18
- Umi 4.x
- Ant Design 4.x 组件库
- Ant Design Pro Components 高级组件
- TypeScript 类型控制
- Eslint 代码规范控制
- Prettier 美化代码

依赖库：

- monaco-editor 代码编辑器
- copy-to-clipboard 剪切板复制



### 后端

主要技术：

- Spring Boot 2.7.x
- MyBatis Plus 3.5.x
- MySQL 8.x
- Spring AOP

依赖库：

- FreeMarker：模板引擎
- Druid：SQL 解析
- datafaker：模拟数据
- Apache Commons Lang3：工具库
- Hutool：工具库
- Gson：Json 解析
- Easy Excel：Excel 导入导出
- Knife4j：接口文档生成



## 快速启动

### 后端

1. 运行 sql 目录下的 create_table.sql 建表
2. 修改 application.yml 中的数据库地址为自己的
3. 安装完 Maven 依赖后，直接运行即可
4. 已经编写好了 Dockerfile，支持 Docker 镜像部署。



### 前端

安装依赖：

```bash
npm run install
```

运行：

```bash
npm run dev
```

打包：

```bash
npm run build
```



## 系统设计

主要分享系统的整体架构和核心设计，而传统 web 开发部分不做过多介绍。

### 整体架构设计

核心设计理念：将各输入方式统一为明确的 Schema，并根据 Schema 生成各类内容。

架构设计图如下，即任意输入 => 统一 Schema => 任意输出：

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666144811181-37d5bd7f-28fa-4b17-9147-ae7de8de1585-20221019132502647-20221019132511901.png)

系统分为以下几个核心模块，各模块职责分明：

1. Schema 构造器：将各种不同的输入源转为统一的 Table Schema 定义
2. 统一 Schema 定义：本质是一个 Java 类（JSON 配置），用于保存表和字段的信息
3. 生成器：负责根据 Schema 生成数据和代码
4. 共享服务：包括词库、表信息、字段信息共享

> 核心模块的代码都在后端 core 目录下



### Schema 构造器

核心类：TableSchemaBuilder，作用是将不同的参数统一收敛为 TableSchema 对象。

包含如下方法：

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666144943025-0d62bf99-5ee6-4135-89da-a0bf270f3f49-20221019132502695-20221019132512014.png)

其中，buildFromSql（根据 SQL 生成 Schema）使用了 Druid 数据库连接池自带的语法解析器，非常强大。（解析器这种东西一般不要自己写，有这时间你都能做几个项目了，写出来还没人家的好用)



### Schema 定义

用于保存表和字段的信息，结构如下：

```json
{
  "dbName": "库名",
  "tableName": "test_table",
  "tableComment": "表注释",
  "mockNum": 20,
  "fieldList": [{
    "fieldName": "username",
    "comment": "用户名",
    "fieldType": "varchar(256)",
    "mockType": "随机",
    "mockParams": "人名",
    "notNull": true,
    "primaryKey": false,
    "autoIncrement": false
  }]
}
```



### 生成器

#### 多种生成类型

将每种生成类型定义为一个 Builder（core/builder 目录）：

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666145274014-bb582f01-31dd-442c-835a-64c1e9fd61a5-20221019132502741-20221019132512095.png)

其中，对于 SQL 代码生成器（ SqlBuilder），使用方言来支持不同的数据库类型（策略模式），并使用单例模式 + 工厂模式创建方言实例。

对于 Java、前端代码生成器（JavaCodeBuilder、FrontendCodeBuilder），使用 FreeMarker 模板引擎来生成。
模板代码如下：

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666146737562-53f46d04-489d-4d00-ab9b-11cf237cd4a9-20221019132502769-20221019132512184.png)



#### 多种模拟数据生成规则

每种生成规则定义为一个 Generator，使用 DataGeneratorFactory（工厂模式）对多个 Generator 实例进行统一的创建和管理。

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666145631468-8d0d5dad-0cd8-41a5-8a8b-e65c5feb7829-20221019132502828-20221019132512285.png)

使用 dataFaker 库实现随机数据生成（RandomDataGenerator）。

使用 Generex 库实现正则表达式数据生成（RuleDataGenerator)。



#### 统一的生成入口

使用门面模式聚合各种生成类型，提供统一的生成调用和校验方法：

![](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/1/1666145871478-af8512a5-d9c3-4166-8253-0304639c62b0-20221019132502858-20221019132512359.png)



### 共享服务

包括词库、表信息、字段信息共享，其实就是对这些实体的增删改查 web 服务，不多说了。



## 更多讲解

如果你希望获取该项目的完整详细讲解，欢迎加入 [鱼皮的编程知识星球](https://yupi.icu/) 。这是鱼皮的编程学习圈子，我在圈子内带大家从 0 到 1 分析解读这个项目、1 对 1 回答大家的问题。不仅让你也能独立做一个、还会教你怎么把这个项目写到简历上，项目经历 + 1。



## 贡献指南

欢迎各位朋友参与贡献，还请先仔细阅读以下内容：

1. 在贡献前，请确保你对项目的代码和架构设计有一定的了解，而不是凭直觉去修改代码
2. 可以从修复 issues 区 bug、处理反馈意见（[https://support.qq.com/product/440825](https://support.qq.com/product/440825)）、扩展项目功能等角度参与贡献
3. 在提交代码前，请务必遵循项目的代码格式、命名等规范，并且做到最小改动（不需要改的内容，一个字符都不要动）



## 致谢

部分词库来源：[https://github.com/fighting41love/funNLP](https://github.com/fighting41love/funNLP)

示例表信息来源：[https://open.yesapi.cn/list1.html](https://open.yesapi.cn/list1.html)

