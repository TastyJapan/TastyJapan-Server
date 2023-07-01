create table `blog_review` (
                             `blog_review_id` bigint not null auto_increment,
                             `created_date` datetime(6) not null,
                             `is_deleted` bit,
                             `modified_date` datetime(6) not null,
                             `source` varchar(255),
                             `review_url` varchar(255),
                             `restaurant_id` bigint,
                             primary key (`blog_review_id`)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `external_review` (
                                 `blog_review_id` bigint not null auto_increment,
                                 `created_date` datetime(6) not null,
                                 `is_deleted` bit,
                                 `modified_date` datetime(6) not null,
                                 `external_review_content` varchar(255),
                                 `reviewer_nickname` varchar(255),
                                 `rating` double precision,
                                 `external_review_source` varchar(255),
                                 `reviewer_photo_url` varchar(255),
                                 `restaurant_id` bigint,
                                 primary key (`blog_review_id`)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `group_restaurant` (
                                  `group_restaurant_id` bigint not null auto_increment,
                                  `created_date` datetime(6) not null,
                                  `is_deleted` bit,
                                  `modified_date` datetime(6) not null,
                                  `group_id` bigint not null,
                                  `restaurant_id` bigint not null,
                                  primary key (`group_restaurant_id`)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `member` (
                        `member_id` bigint not null auto_increment,
                        `created_date` datetime(6) not null,
                        `is_deleted` bit,
                        `modified_date` datetime(6) not null,
                        `member_email` varchar(255),
                        `member_name` varchar(255),
                        `member_picture` varchar(255),
                        `role` varchar(255),
                        `status` varchar(8),
                        `oauth_id` bigint,
                        primary key (`member_id`)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `menu` (
                      `menu_id` bigint not null auto_increment,
                      `created_date` datetime(6) not null,
                      `is_deleted` bit,
                      `modified_date` datetime(6) not null,
                      `menu_sort` varchar(255),
                      `menu_name` varchar(255),
                      `menu_price` bigint,
                      `restaurant_id` bigint,
                      primary key (`menu_id`)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `menu_pictures` (
                               `menu_menu_id` bigint not null,
                               `menu_pictures` varchar(255)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `oauth` (
                       `oauth_id` bigint not null auto_increment,
                       `created_date` datetime(6) not null,
                       `is_deleted` bit,
                       `modified_date` datetime(6) not null,
                       `provider` varchar(255),
                       `refresh_token` varchar(255),
                       primary key (`oauth_id`)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `restaurant` (
                            `restaurant_id` bigint not null auto_increment,
                            `created_date` datetime(6) not null,
                            `is_deleted` bit,
                            `modified_date` datetime(6) not null,
                            `restaurant_address` varchar(255),
                            `city` varchar(255),
                            `restaurant_latitude` double precision,
                            `restaurant_longitude` double precision,
                            `restaurant_name` varchar(255),
                            `restaurant_rating` double precision,
                            `restaurant_summary` varchar(255),
                            primary key (`restaurant_id`)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `restaurant_picture` (
                                    `restaurant_picture_id` bigint not null auto_increment,
                                    `created_date` datetime(6) not null,
                                    `is_deleted` bit,
                                    `modified_date` datetime(6) not null,
                                    `restaurant_picture_url` varchar(255),
                                    `restaurant_id` bigint,
                                    primary key (`restaurant_picture_id`)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `review` (
                        `review_id` bigint not null auto_increment,
                        `created_date` datetime(6) not null,
                        `is_deleted` bit,
                        `modified_date` datetime(6) not null,
                        `review_content` varchar(255),
                        `rating` double precision,
                        `member_id` bigint,
                        `restaurant_id` bigint,
                        primary key (`review_id`)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table `saved` (
                       `group_id` bigint not null auto_increment,
                       `created_date` datetime(6) not null,
                       `is_deleted` bit,
                       `modified_date` datetime(6) not null,
                       `save_title` varchar(255),
                       `member_id` bigint not null,
                       primary key (`group_id`)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

