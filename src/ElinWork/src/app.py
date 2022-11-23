#Author: Elin.Liu
# Date: 2022-11-23 20:34:25
# Last Modified by:   Elin.Liu
# Last Modified time: 2022-11-23 20:34:25
from flask import Flask, request, jsonify
from flask_cors import CORS
import json
from MongoTransformer import MongoTransformer
from Logger import Logger
import yaml
# 初始化Flask App
app = Flask(__name__)
# 配置跨域
CORS(app)

# 定义路由


@app.route('/mongo/transform/v1', methods=['POST'])
def mongo_transform():
    # 获取前端传递的JSON数据
    json_data = json.loads(request.get_data())
    # 获取前端传递的MongoDB集合名
    collection = json_data['collection']
    # 获取前端传递的JSON字符串
    # 请注意，如果POST时以JSON格式传递，则json字符串内的值需要由单引号包裹
    json_string = json_data['json_str']
    # 初始化MongoTransformer
    mongo_transformer = MongoTransformer(collection, json_string)
    # 获取转换结果
    result = mongo_transformer.transform()
    # 返回结果
    return jsonify(result)


if __name__ == '__main__':
    # 读取配置文件
    with open('../config/appConfig.yaml', 'r') as f:
        config = yaml.load(f, Loader=yaml.FullLoader)
    # 获取配置
    host = config['host']
    port = config['port']
    debug = config['isdebug']
    # 启动服务
    app.run(host=host, port=port, debug=debug)
