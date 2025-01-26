-- Insertion des utilisateurs
-- tous les mots de passe chiffrés Bcrypt sont 'test123'
INSERT INTO
    USERS (email, username, password, picture, is_admin)
VALUES
    (
        'admin@test.com',
        'AdminTest',
        '$2a$10$mIuUj7YekUqX.ibesAbbl.glhMuLdnnBeBQ/NoTCPz02on82QkeNO',
        'profil1',
        1
    ),
    (
        'linus.thorvalds@test.com',
        'LinusThorvalds',
        '$2a$10$mIuUj7YekUqX.ibesAbbl.glhMuLdnnBeBQ/NoTCPz02on82QkeNO',
        'profil8',
        0
    ),
    (
        'tim.berners.lee@test.com',
        'TimBLee',
        '$2a$10$mIuUj7YekUqX.ibesAbbl.glhMuLdnnBeBQ/NoTCPz02on82QkeNO',
        'profil3',
        0
    ),
    (
        'guido.vrossum@test.com',
        'GuidoV_Rossum',
        '$2a$10$mIuUj7YekUqX.ibesAbbl.glhMuLdnnBeBQ/NoTCPz02on82QkeNO',
        'profil4',
        0
    );

-- Insertion des thèmes
INSERT INTO
    THEMES (title, description, icon, color)
VALUES
    (
        'Angular et librairies',
        'Un projet Angular sans librairie, c’est comme un plat sans épices : fade et sans saveur !',
        'themeA',
        'FF5733'
    ),
    (
        'Base de données',
        'Tout sur les bases de données, les relations',
        'themeB',
        '33FF57'
    ),
    (
        'Spring-boot Java',
        'Tout sous la main, plus qu’à assembler !',
        'themeC',
        '3357FF'
    ),
    (
        'Tests',
        'Le test, c’est comme aller chez le médecin : personne n’aime ça, mais ça évite de gros problèmes.',
        'themeD',
        'F0FF33'
    ),
    (
        'UX/UI',
        'L’UX/UI, c’est le chef d’orchestre du front-end. Avec un design pourri, les utilisateurs fuient.',
        'themeE',
        'FF33A1'
    ),
    (
        'Style CSS',
        'Un décorateur d’intérieur peut cacher le chaos sous une belle facade...',
        'themeF',
        '33FFF0'
    );

-- Insertion des articles
INSERT INTO
    ARTICLES (title, content, author, created_at)
VALUES
    (
        'Bases pour Angular',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        1,'2025-01-01 12:00:00'
    ),
    (
        'Guide sur les bases de données',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        2,'2025-01-01 12:00:00'
    ),
    (
        'Démarrer avec Spring-boot',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        3,'2025-01-01 12:00:00'
    ),
    (
        'Jest et Jasmine',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        4,'2025-01-01 12:00:00'
    ),
    (
        'Design inclusif',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        1,'2025-01-01 12:00:00'
    ),
    (
        'Créer un site avec HTML/CSS',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        2,'2025-01-01 12:00:00'
    ),
    (
        'Programmation orientée objet',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        3,'2025-01-01 12:00:00'
    ),
    (
        'SQL vs NoSQL',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        4,'2025-01-01 12:00:00'
    ),
    (
        'Les algorithmes',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        2,'2025-01-01 12:00:00'
    ),
    (
        'Créer un site avec HTML/CSS',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        3,'2025-01-01 12:00:00'
    ),
    (
        'Optimisation de bases de données',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        2,'2025-01-01 12:00:00'
    ),
    (
        'Créer une application web dynamique',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        1,'2025-01-01 12:00:00'
    ),
    (
        'Tout savoir sur Angular et RxJs',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        2,'2025-01-01 12:00:00'
    ),
    (
        'Spring-Boot Hibernate',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        3,'2025-01-01 12:00:00'
    ),
    (
        'L’importance des tests automatisés',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        4,'2025-01-01 12:00:00'
    ),
    (
        'Améliorer l’UX/UI de vos applications',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        2,'2025-01-01 12:00:00'
    ),
    (
        'Styliser avec CSS',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean nec massa vitae ante eleifend tincidunt. Praesent dignissim elit eros, quis placerat ligula pharetra id. Duis neque ligula, molestie nec mollis eget, gravida sit amet elit. Sed fringilla tellus sed dui euismod euismod. Maecenas ac odio tincidunt, ornare nulla non, cursus odio. Proin venenatis hendrerit egestas. Praesent commodo pharetra felis nec porttitor. Maecenas lacinia turpis eget arcu volutpat, gravida malesuada velit hendrerit. Sed lorem erat, tempor id nunc at, blandit tristique metus. Vivamus magna ligula, facilisis a in. ',
        3,'2025-01-01 12:00:00'
    );

-- Insertion des relations article-thèmes
INSERT INTO
    ARTICLE_THEMES (article_id, theme_id)
VALUES
    (1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6),
    (7, 1), (7, 3), (8, 2), (9, 3), (10, 5), (10, 6),
    (11, 2), (12, 1), (12, 5), (12, 6), (13, 1),
    (14, 3), (15, 4), (16, 5), (17, 6);

    -- Insertion des commentaires
INSERT INTO
    COMMENTS (content, author_id, article_id, created_at)
VALUES
    ('Super article, merci !', 2, 1,'2025-01-01 12:00:00'),
    ('Très instructif, bravo.', 3, 2,'2025-01-01 12:00:00'),
    ('Merci pour les infos.', 4, 3,'2025-01-01 12:00:00'),
    ('Bonne introduction.', 2, 4,'2025-01-01 12:00:00'),
    ('Article bien écrit.', 3, 5,'2025-01-01 12:00:00'),
    ('Très utile, merci !', 4, 6,'2025-01-01 12:00:00'),
    ('J’ai appris beaucoup.', 2, 7,'2025-01-01 12:00:00'),
    ('Merci pour cet article.', 3, 8,'2025-01-01 12:00:00'),
    ('Très pertinent.', 4, 9,'2025-01-01 12:00:00'),
    ('Explications claires.', 2, 10,'2025-01-01 12:00:00'),
    ('Article intéressant.', 3, 11,'2025-01-01 12:00:00'),
    ('J’adore ce thème.', 4, 12,'2025-01-01 12:00:00'),
    ('Bon résumé.', 2, 13,'2025-01-01 12:00:00'),
    ('Informations utiles.', 3, 14,'2025-01-01 12:00:00'),
    ('Contenu très pertinent.', 4, 15,'2025-01-01 12:00:00'),
    ('Merci pour les détails.', 2, 16,'2025-01-01 12:00:00'),
    ('Très bien rédigé.', 3, 17,'2025-01-01 12:00:00'),
    ('C’est exactement ce que je cherchais.', 4, 8,'2025-01-01 12:00:00'),
    ('Bravo pour cet article.', 2, 1,'2025-01-01 12:00:00'),
    ('Hâte de lire la suite.', 3, 5,'2025-01-01 12:00:00');