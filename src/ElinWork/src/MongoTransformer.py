#Author: Elin.Liu
# Date: 2022-11-23 19:46:03
# Last Modified by:   Elin.Liu
# Last Modified time: 2022-11-23 19:46:03

import json
from Logger import Logger


class MongoTransformer:
    # 初始化构造函数
    def __init__(self, collection, json_string) -> None:
        super(MongoTransformer, self).__init__()
        # 函数接收两个参数
        # - collection: 为指定的MongoDB集合名
        # - json_data: 为指定的JSON数据
        self.collection = collection
        self.json_string = json_string
        # Json合法性检验
        try:
            json.loads(self.json_string)
        except json.decoder.JSONDecodeError:
            print(Logger.Logging("error", "Invalid JSON Data Specified!"))
    # 定义MongoDB命令转换函数

    def transform(self):
        commandHead = f"db.{self.collection}.insertMany("
        commandHead += self.json_string
        commandTail = ");"
        print(Logger.Logging("info", "MongoDB Command Build Success!"))
        return {
            "collectionCreate": f"db.createCollection('{self.collection}');",
            "collectionInsert": commandHead + commandTail
        }
