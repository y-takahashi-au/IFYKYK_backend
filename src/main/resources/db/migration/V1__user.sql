CREATE TABLE `account` (
  `id` VARCHAR(26) NOT NULL,
  `user_id` VARCHAR(8)  NOT NULL UNIQUE,
  `status` VARCHAR(10)  NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `account_info` (
  `account_id` VARCHAR(26) NOT NULL,
  `roll` VARCHAR(15) NOT NULL,
  `given_name` VARCHAR(50) NOT NULL,
  `family_name` VARCHAR(50)  NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `refresh_token` VARCHAR(100)  NOT NULL,
  `refresh_token_issue_at` TIMESTAMP  NOT NULL,
  PRIMARY KEY (`account_id`),
  FOREIGN KEY (`account_id`) REFERENCES account(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
