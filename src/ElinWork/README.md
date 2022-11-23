### MongoTransformer后端指南

#### 1. 项目结构

```
├── README.md
├── config
│   ├── appConfig.yaml
├── src
│   ├── __init__.py
│   ├── app.py
│   ├── Logger.py
│   ├── MongoTransformer.py
```

#### 2. 配置文件
Flask APP的配置文件为`config/appConfig.yaml`，其中包含了Flask服务器的配置信息，包含如下配置项：
- host：Flask服务器的IP地址
- port：Flask服务器的端口号
- isdebug：是否开启debug模式运行服务器

### 3. API概述
APP仅提供一个API路由，URL路由为：`/mongo/transform/v1`
该API接收一个`POST`请求，请求体为JSON格式，包含如下字段：
- `collection`：指定要转换的MongoDB集合名称
- `json_str`：由上游Java API发送的请求Json字符串，需要指明，该字符串在初始化时，json字符串内部的`"`需要转义为`'`，否则会导致json解析失败。
该API返回一个`JSON`格式的响应，包含如下字段：
- `collectionCreate`：返回创建集合的指令
- `collectionInsert`：返回插入数据的指令

### 4.测试用例

#### 输入参数

```json
{
    "collection":"test",
    "json_str":"[{'name':18,'Message':'Hello'}]"
}
```

#### 返回预期
```json
{
    "collectionCreate": "db.createCollection('test');",
    "collectionInsert": "db.test.insertMany([{'name':18,'Message':'Hello'}]);"
}
```