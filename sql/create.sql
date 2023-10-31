CREATE TABLE `ORDER_HISTORY` (
                          `ORDER_HISTORY_ID` bigint NOT NULL AUTO_INCREMENT,
                          `ORDER_NAME` varchar(20) DEFAULT NULL,
                          `PRICE` bigint DEFAULT NULL,
                          `ORDER_STATUS` varchar(20) DEFAULT NULL,
                          `CREATE_DATE` datetime DEFAULT NULL,
                          PRIMARY KEY (`ORDER_HISTORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `ORDER_FINISH` (
                                 `ORDER_FINISH_ID` bigint NOT NULL AUTO_INCREMENT,
                                 `ORDER_HISTORY_ID` bigint NOT NULL,
                                 `ORDER_NAME` varchar(20) DEFAULT NULL,
                                 `PRICE` bigint DEFAULT NULL,
                                 `ORDER_STATUS` varchar(20) DEFAULT NULL,
                                 `ORDER_CREATE_DATE` datetime DEFAULT NULL,
                                 `CREATE_DATE` datetime DEFAULT NULL,
                                 PRIMARY KEY (`ORDER_FINISH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;