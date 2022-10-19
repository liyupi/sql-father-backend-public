-- 创建库
create database if not exists sqlfather;

-- 切换库
use sqlfather;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/ admin',
    userPassword varchar(512)                           not null comment '密码',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
)
    comment '用户';

-- 词库表
create table if not exists dict
(
    id            bigint auto_increment comment 'id' primary key,
    name          varchar(512)                       null comment '词库名称',
    content       text                               null comment '词库内容（json 数组）',
    reviewStatus  int      default 0                 not null comment '状态（0-待审核, 1-通过, 2-拒绝）',
    reviewMessage varchar(512)                       null comment '审核信息',
    userId        bigint                             not null comment '创建用户 id',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '词库';

create index idx_name
    on dict (name);

-- 表信息表
create table if not exists table_info
(
    id            bigint auto_increment comment 'id' primary key,
    name          varchar(512)                       null comment '名称',
    content       text                               null comment '表信息（json）',
    reviewStatus  int      default 0                 not null comment '状态（0-待审核, 1-通过, 2-拒绝）',
    reviewMessage varchar(512)                       null comment '审核信息',
    userId        bigint                             not null comment '创建用户 id',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '表信息';

create index idx_name
    on table_info (name);

-- 字段信息表
create table if not exists field_info
(
    id            bigint auto_increment comment 'id' primary key,
    name          varchar(512)                       null comment '名称',
    fieldName     varchar(512)                       null comment '字段名称',
    content       text                               null comment '字段信息（json）',
    reviewStatus  int      default 0                 not null comment '状态（0-待审核, 1-通过, 2-拒绝）',
    reviewMessage varchar(512)                       null comment '审核信息',
    userId        bigint                             not null comment '创建用户 id',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '字段信息';

create index idx_fieldName
    on field_info (fieldName);

create index idx_name
    on field_info (name);

-- 举报表
create table if not exists report
(
    id             bigint auto_increment comment 'id' primary key,
    content        text                               not null comment '内容',
    type           int                                not null comment '举报实体类型（0-词库）',
    reportedId     bigint                             not null comment '被举报对象 id',
    reportedUserId bigint                             not null comment '被举报用户 id',
    status         int      default 0                 not null comment '状态（0-未处理, 1-已处理）',
    userId         bigint                             not null comment '创建用户 id',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint  default 0                 not null comment '是否删除'
) comment '举报';