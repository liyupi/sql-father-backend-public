#Author: Elin.Liu
#Date: 2022-11-23 20:02:52
#Last Modified by:   Elin.Liu
#Last Modified time: 2022-11-23 20:02:52
from datetime import datetime

# 定义日志类
class Logger:
    global level_list
    level_list = ["debug","error","warnning","info"]
    def __init__(self) -> None:
        pass
    def Logging(level,message):
        try:
            assert level in level_list
        except AssertionError:
            print(f"{datetime.now().ctime()} [error] Invalid Debug Level Specified!")
        return f"{datetime.now().ctime()} [{level}] {message}"
