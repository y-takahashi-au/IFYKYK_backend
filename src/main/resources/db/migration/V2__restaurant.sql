CREATE TABLE `restaurant` (
  `id` VARCHAR(26) NOT NULL,
  `name` VARCHAR(50)  NOT NULL,
  `place` VARCHAR(200)  NOT NULL,
  `category` VARCHAR(30)  NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `restaurant_review` (
  `id` VARCHAR(26) NOT NULL,
  `restaurant_id` VARCHAR(26) NOT NULL,
  `star` TINYINT NOT NULL,
  `review` TEXT,
  `picture` VARCHAR(200),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`restaurant_id`) REFERENCES restaurant(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;