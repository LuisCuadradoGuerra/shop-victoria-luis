CREATE TABLE app_users
(
    app_user_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    app_user_alias        VARCHAR(50)   NOT NULL UNIQUE,
    app_user_first_name    VARCHAR(50)   NOT NULL,
    app_user_last_name     VARCHAR(50)   NOT NULL,
    app_user_password     VARCHAR(200)   NOT NULL,
    app_user_email        VARCHAR(200)   NOT NULL UNIQUE
);

CREATE TABLE categories
(
    category_id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title                VARCHAR(50)   NOT NULL,
    category_description VARCHAR(1000) NOT NULL,
    category_icon        VARCHAR(300),
    CONSTRAINT unique_title UNIQUE (title) -- Opcional, si deseas que los títulos de las categorías sean únicos
);


CREATE TABLE products
(
    product_id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name        VARCHAR(50) NOT NULL,
    price               DOUBLE      NOT NULL,
    product_description VARCHAR(1000),
    product_icon        VARCHAR(50),
    product_stock       BIGINT      NOT NULL
);

CREATE TABLE feedback
(
    feedback_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    stars       DOUBLE       NOT NULL,
    username    VARCHAR(100) NOT NULL,
    comment     VARCHAR(500) NOT NULL,
    date        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    product_id  BIGINT       NOT NULL,
    CONSTRAINT unique_feedback UNIQUE (feedback_id, username),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE
);



CREATE TABLE shopping_cart_items
(
    shopping_cart_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    items_count           BIGINT    NOT NULL,
    creation_date         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    product_id            BIGINT    NOT NULL,
    CONSTRAINT fk_product_cart FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE,
    CONSTRAINT unique_product UNIQUE (product_id) -- Esto asegura que solo haya una fila por producto en el carrito
);

CREATE TABLE products_categories (
                                     product_id BIGINT NOT NULL,
                                     category_id BIGINT NOT NULL,
                                     PRIMARY KEY (product_id, category_id),
                                     CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
                                     CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE
);



-- Insertar categorías
INSERT INTO categories (title, category_description, category_icon)
VALUES ('Potions',
        'Step into our enchanted apothecary of mystical elixirs, where each potion is meticulously brewed under the light of the full moon. Our master alchemists combine rare ingredients with ancient wisdom to create powerful concoctions that will transform your magical practice.',
        '/img/categories/category1.jpg'),
       ('Ingredients',
        'Discover our carefully curated collection of rare and exotic ingredients, from dragon&#39;s blood to phoenix feathers. Each component is ethically sourced from the most mystical corners of the world, ensuring the highest quality for your magical crafting needs.',
        '/img/categories/category2.jpg'),
       ('Brooms',
        'Elevate your magical transportation with our selection of enchanted brooms. Each masterfully crafted piece combines traditional craftsmanship with powerful levitation enchantments, ensuring both style and supernatural performance.',
        '/img/categories/category3.jpg'),
       ('Tarot',
        'Explore our mystical collection of tarot decks, from traditional Rider-Waite to unique artisanal designs. Each deck is imbued with ancient wisdom and powerful divination energy, perfect for both beginners and advanced readers.',
        '/img/categories/category4.jpg'),
       ('Spell Books',
        'Uncover the secrets of the ages within our collection of rare and powerful grimoires. From ancient scrolls to contemporary magical manuscripts, each book holds centuries of arcane knowledge waiting to be discovered by worthy practitioners.',
        '/img/categories/category5.jpg');

-- Insertar productos
INSERT INTO products (product_name, price, product_description, product_icon, product_stock)
VALUES ('OneLovelyNight', 60,
        'Increments the possibilities but not totally charm your crush. Earn it helping your self, tiger',
        '/img/products/OneLovelyNight.jpg', 100),
       ('Multyjuicy Potion', 1000,
        'USE ONLY WITH HUMAN HAIR. The last one who didn´t can´t warn you with its tentacles',
        '/img/products/Multyjuicy Potion.jpg', 100),
       ('Krafkala', 2, 'Only krafkalas. Crab crap crappity crap', '/img/products/Krafkala.jpg', 100),
       ('Unicorn Horn', 700, 'The rarest horn in the nature. Only on your trusty bunion.',
        '/img/products/Unicorn Horn.jpg', 100),
       ('Dragon Scale', 30,
        'Not that dangerous than one could imagine. It´s simple to get some if you are sneaky enough',
        '/img/products/Dragon Scale.png', 100),
       ('Bone Dust', 10, 'Tovakin, it´s your chance to get some dust without dance with some draugs',
        '/img/products/Bone Dust.png', 100),
       ('Broom Broom', 150,
        'Our new broom perfect for beginners, mostly named "BB", with moderated speed, highly savefty and we garantee you can hit some trees before break the broom',
        '/img/products/Broom Broom.jpg', 100),
       ('Nimbus 2000', 676, 'The best wood ever to got a good broom. To good to go, baby',
        '/img/products/Nimbus 2000.png', 100),
       ('Fire Arrow', 1000, 'Maybe you need to open your Gringot´s security room to pay this, but is worth it',
        '/img/products/Fire Arrow.jpg', 100);




-- Insertar relaciones (Products-Categories)
-- Potions
INSERT INTO products_categories (product_id, category_id)
VALUES (1, 1), -- OneLovelyNight -> Potions
       (2, 1), -- Multyjuicy Potion -> Potions
       (3, 1);
-- Krafkala -> Potions

-- Ingredients
INSERT INTO products_categories (product_id, category_id)
VALUES (4, 2), -- Unicorn Horn -> Ingredients
       (5, 2), -- Dragon Scale -> Ingredients
       (6, 2);
-- Bone Dust -> Ingredients

-- Brooms
INSERT INTO products_categories (product_id, category_id)
VALUES (7, 3), -- Broom Broom -> Brooms
       (8, 3), -- Nimbus 2000 -> Brooms
       (9, 3);
-- Fire Arrow -> Brooms

-- Insertar feedbacks para cada producto

-- Feedbacks para "OneLovelyNight"
INSERT INTO feedback (stars, username, comment, date, product_id) VALUES
                                                                      (4.5, 'MagicLover123', 'Really helped me get through a tough situation. Totally recommend!', '2025-01-14 10:00:00', 1),
                                                                      (3.0, 'PotionCritic42', 'Works okay, but didn’t meet my high expectations.', '2025-01-14 11:00:00', 1);

-- Feedbacks para "Multyjuicy Potion"
INSERT INTO feedback (stars, username, comment, date, product_id)
VALUES (5.0, 'PotionMaster99', 'This potion is incredible! The effects are stunning.', '2025-01-14 12:00:00', 2),
       (4.0, 'CautiousAlchemist', 'Followed the instructions and it worked well, but the price is high.',
        '2025-01-14 13:00:00', 2);

-- Feedbacks para "Krafkala"
INSERT INTO feedback (stars, username, comment, date, product_id)
VALUES (2.5, 'CrabbyFan', 'Not as great as I thought it would be. Still useful.', '2025-01-14 14:00:00', 3),
       (1.0, 'DisappointedWizard', 'Completely useless for my needs.', '2025-01-14 15:00:00', 3);

-- Feedbacks para "Unicorn Horn"
INSERT INTO feedback (stars, username, comment, date, product_id)
VALUES (5.0, 'RareCollector', 'Absolutely beautiful and authentic. A must-have!', '2025-01-14 16:00:00', 4),
       (4.0, 'MysticSeeker', 'Great quality, but shipping took too long.', '2025-01-14 17:00:00', 4);

-- Feedbacks para "Dragon Scale"
INSERT INTO feedback (stars, username, comment, date, product_id)
VALUES (4.5, 'StealthyHunter', 'Easy to handle and very versatile.', '2025-01-14 18:00:00', 5),
       (3.5, 'CuriousMage', 'Good quality, but not as rare as advertised.', '2025-01-14 19:00:00', 5);

-- Feedbacks para "Bone Dust"
INSERT INTO feedback (stars, username, comment, date, product_id)
VALUES (4.0, 'NecroFan', 'Perfect for summoning rituals. Works as described.', '2025-01-14 20:00:00', 6),
       (3.0, 'SkepticalBuyer', 'Okay quality, but could be finer.', '2025-01-14 21:00:00', 6);

-- Feedbacks para "Broom Broom"
INSERT INTO feedback (stars, username, comment, date, product_id)
VALUES (5.0, 'NewbieWitch', 'Excellent for beginners. Very safe and easy to use!', '2025-01-14 22:00:00', 7),
       (4.0, 'YoungWizard', 'Good broom, but a bit slow for me.', '2025-01-14 23:00:00', 7);

-- Feedbacks para "Nimbus 2000"
INSERT INTO feedback (stars, username, comment, date, product_id)
VALUES (5.0, 'BroomLover', 'Top-notch performance! Best broom I’ve ever owned.', '2025-01-15 08:00:00', 8),
       (4.5, 'QuidditchFan', 'Amazing, but quite expensive.', '2025-01-15 09:00:00', 8);

-- Feedbacks para "Fire Arrow"
INSERT INTO feedback (stars, username, comment, date, product_id)
VALUES (5.0, 'SpeedDemon', 'Absolutely worth every galleon. Unmatched speed.', '2025-01-15 10:00:00', 9),
       (4.0, 'BudgetFlyer', 'Great broom but way too pricey.', '2025-01-15 11:00:00', 9);
