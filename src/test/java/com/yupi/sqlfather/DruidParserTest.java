package com.yupi.sqlfather;

import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlCreateTableParser;
import org.junit.jupiter.api.Test;

/**
 * SQL 解析器测试
 *
 * @author https://github.com/liyupi
 */
public class DruidParserTest {

    private static final String SQL = "-- 用户表\n"
            + "create table if not exists yupi_db.user\n"
            + "(\n"
            + "username varchar(256) not null comment '用户名',\n"
            + "id bigint not null auto_increment comment '主键' primary key,\n"
            + "create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',\n"
            + "update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',\n"
            + "is_deleted tinyint default 0 not null comment '是否删除（0-未删，1-已删）'\n"
            + ") comment '用户表';";

    @Test
    void test() {
        MySqlCreateTableParser parser = new MySqlCreateTableParser(SQL);
        SQLCreateTableStatement sqlCreateTableStatement = parser.parseCreateTable();
        System.out.println("表名：" + sqlCreateTableStatement.getTableName());
        System.out.println("库名：" + sqlCreateTableStatement.getSchema());
        System.out.println("表注释：" + sqlCreateTableStatement.getComment());
        for (SQLTableElement sqlTableElement : sqlCreateTableStatement.getTableElementList()) {
            System.out.println("--------------");
            SQLColumnDefinition columnDefinition = (SQLColumnDefinition) sqlTableElement;
            System.out.println("字段名：" + columnDefinition.getNameAsString());
            String metaType = columnDefinition.getDataType().toString();
            System.out.println("字段类型：" + metaType);
            System.out.println("注释：" + columnDefinition.getComment());
            System.out.println("主键：" + columnDefinition.isPrimaryKey());
            System.out.println("自增：" + columnDefinition.isAutoIncrement());
            System.out.println("更新事件：" + columnDefinition.getOnUpdate());
            System.out.println("默认值：" + columnDefinition.getDefaultExpr());
            System.out.println("非空：" + columnDefinition.containsNotNullConstaint());
        }
    }
}
