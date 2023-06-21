DROP TABLE IF EXISTS `statemachine_trace`;
CREATE TABLE `statemachine_trace` (
	`id` bigint NOT NULL,
	`gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`is_deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '逻辑删除,Y/N',
	`tenant_id` varchar(128) NOT NULL COMMENT '租户ID',
	`biz_id` varchar(128) DEFAULT NULL COMMENT '业务ID',
	`biz_type` varchar(128) DEFAULT NULL COMMENT '业务类型',
	`statemachine_id` varchar(128) DEFAULT NULL COMMENT '状态机ID',
	`before_state` varchar(128) DEFAULT NULL COMMENT '流转前状态',
	`after_state` varchar(128) DEFAULT NULL COMMENT '流转后状态',
	`error` char(1) DEFAULT NULL COMMENT '是否异常',
	`json_data` json DEFAULT NULL,
	PRIMARY KEY (`id`),
    KEY `tenant_id_index` (`tenant_id`)
)