CREATE TABLE `sample_rule`
(
    `id`           BIGINT       NOT NULL,
    `gmt_create`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_del`       CHAR(1)      NOT NULL DEFAULT 'N' COMMENT '逻辑删除,Y/N',
    `status`       VARCHAR(64)   NULL COMMENT '状态',
    `country_code` VARCHAR(64)   NULL COMMENT '国家简码',
    `channel_id`   VARCHAR(64)   NULL COMMENT '通道ID',
    `json_data`    JSON COMMENT 'json_data',
    PRIMARY KEY (`id`)
) COMMENT='国家规则';