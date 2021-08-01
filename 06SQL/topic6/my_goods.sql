CREATE TABLE `my_goods`  (
  `id` bigint(20) NOT NULL COMMENT '商品ID',
  `goods_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `price` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '价格',
  `goods_category` bigint(20) NOT NULL COMMENT '商品类目ID',
  `goods_status` tinyint(2) NOT NULL COMMENT '商品状态',
  `pic_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品图片地址',
  `is_delete` tinyint(2) NOT NULL DEFAULT 0 COMMENT '软删除字段',
  `gmt_create` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_goods_name`(`goods_name`) USING BTREE,
  INDEX `idx_goods_category`(`goods_category`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;