INSERT INTO types(id, name) VALUES (1, "BEACH"),
                                   (2, "MOUNTAIN"),
                                   (3, "CITY"),
                                   (4, "SAFARI"),
                                   (5, "CRUISE"),
                                   (6, "ADVENTURE"),
                                   (7, "CULTURAL"),
                                   (8, "HISTORICAL"),
                                   (9, "SKI"),
                                   (10, "DESERT"),
                                   (11, "ISLAND");

INSERT INTO roles(id, role) VALUES (1, "USER"),
                                   (2, "MODERATOR"),
                                   (3, "ADMIN");


INSERT INTO users(id, email, first_name, last_name, password, username, account_verified) VALUES
                                                                            (1, "pesho@gmail.com", "Peter", "Petrov", "f0e98959a250b665387e5aa6fa3a3e809105d3e58fde44b43595226285a22e0ce2984efe2fe1b16a", "pesho96", true),
                                                                            (2, "user@example.com", "User", "Userov", "f0e98959a250b665387e5aa6fa3a3e809105d3e58fde44b43595226285a22e0ce2984efe2fe1b16a", "user", true),
                                                                            (3, "admin@example.com", "Admin", "Adminov", "f0e98959a250b665387e5aa6fa3a3e809105d3e58fde44b43595226285a22e0ce2984efe2fe1b16a", "admin", true);


INSERT INTO users_roles(user_entity_id, roles_id) VALUES
                                                      (1, 1), (2, 1), (3, 1), (3, 3);




INSERT INTO trips(id, continent, description, pricing_level, name, countries_visited, landmarks, days, video_url, author_id)
VALUES (1, "EUROPE", "I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin
I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin ", "LUXURY",
        "My Berlin trip", 1,
        "Берлинска стена
        Бранденбургска врата
        Техно клуб
        Музей на евреите
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена", 5, "https://www.youtube.com/watch?v=cv38DPCSUNs", 1);


INSERT INTO trips_types(trip_entity_id, types_id) values (1, 1);

INSERT INTO trips(id, continent, description, pricing_level, name, countries_visited, landmarks, days, video_url, author_id)
VALUES (2, "ASIA", "I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin
I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin ", "BUDGET",
        "My Berlin trip", 2,
        "Берлинска стена
        Бранденбургска врата
        Техно клуб
        Музей на евреите
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена", 10, "https://www.youtube.com/watch?v=cv38DPCSUNs", 2);


INSERT INTO trips_types(trip_entity_id, types_id) values (2, 2);

INSERT INTO trips(id, continent, description, pricing_level, name, countries_visited, landmarks, days, video_url, author_id)
VALUES (3, "AFRICA", "I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin
I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin ", "BUDGET",
        "My Berlin trip", 3,
        "Берлинска стена
        Бранденбургска врата
        Техно клуб
        Музей на евреите
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена", 12, "https://www.youtube.com/watch?v=cv38DPCSUNs", 3);


INSERT INTO trips_types(trip_entity_id, types_id) values (3, 3);

INSERT INTO trips(id, continent, description, pricing_level, name, countries_visited, landmarks, days, video_url, author_id)
VALUES (4, "SOUTH_AMERICA", "I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin
I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin ", "BUDGET",
        "My Berlin trip", 4,
        "Берлинска стена
        Бранденбургска врата
        Техно клуб
        Музей на евреите
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена", 15, "https://www.youtube.com/watch?v=cv38DPCSUNs", 1);


INSERT INTO trips_types(trip_entity_id, types_id) values (4, 4);

INSERT INTO trips(id, continent, description, pricing_level, name, countries_visited, landmarks, days, video_url, author_id)
VALUES (5, "SOUTH_AMERICA", "I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin
I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin ", "BUDGET",
        "My Berlin trip", 4,
        "Берлинска стена
        Бранденбургска врата
        Техно клуб
        Музей на евреите
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена", 20, "https://www.youtube.com/watch?v=cv38DPCSUNs", 1);


INSERT INTO trips_types(trip_entity_id, types_id) values (5, 5);

INSERT INTO trips(id, continent, description, pricing_level, name, countries_visited, landmarks, days, video_url, author_id)
VALUES (6, "NORTH_AMERICA", "I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin
I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin ", "BUDGET",
        "My Berlin trip", 4,
        "Берлинска стена
        Бранденбургска врата
        Техно клуб
        Музей на евреите
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена", 25, "https://www.youtube.com/watch?v=cv38DPCSUNs", 1);


INSERT INTO trips_types(trip_entity_id, types_id) values (6, 6);

INSERT INTO trips(id, continent, description, pricing_level, name, countries_visited, landmarks, days, video_url, author_id)
VALUES (7, "NORTH_AMERICA", "I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin
I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin ", "BUDGET",
        "My Berlin trip", 4,
        "Берлинска стена
        Бранденбургска врата
        Техно клуб
        Музей на евреите
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена", 25, "https://www.youtube.com/watch?v=cv38DPCSUNs", 1);


INSERT INTO trips_types(trip_entity_id, types_id) values (7, 7);

INSERT INTO trips(id, continent, description, pricing_level, name, countries_visited, landmarks, days, video_url, author_id)
VALUES (8, "ANTARCTICA", "I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin
I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin ", "BUDGET",
        "My Berlin trip", 4,
        "Берлинска стена
        Бранденбургска врата
        Техно клуб
        Музей на евреите
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена", 25, "https://www.youtube.com/watch?v=cv38DPCSUNs", 1);


INSERT INTO trips_types(trip_entity_id, types_id) values (8, 8);

INSERT INTO trips(id, continent, description, pricing_level, name, countries_visited, landmarks, days, video_url, author_id)
VALUES (9, "NORTH_AMERICA", "I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin
I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin ", "BUDGET",
        "My Berlin trip", 4,
        "Берлинска стена
        Бранденбургска врата
        Техно клуб
        Музей на евреите
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена", 25, "https://www.youtube.com/watch?v=cv38DPCSUNs", 1);


INSERT INTO trips_types(trip_entity_id, types_id) values (9, 9);

INSERT INTO trips(id, continent, description, pricing_level, name, countries_visited, landmarks, days, video_url, author_id)
VALUES (10, "NORTH_AMERICA", "I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin
I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin ", "BUDGET",
        "My Berlin trip", 4,
        "Берлинска стена
        Бранденбургска врата
        Техно клуб
        Музей на евреите
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена", 25, "https://www.youtube.com/watch?v=cv38DPCSUNs", 1);


INSERT INTO trips_types(trip_entity_id, types_id) values (10, 10);

INSERT INTO trips(id, continent, description, pricing_level, name, countries_visited, landmarks, days, video_url, author_id)
VALUES (11, "AUSTRALIA", "I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin
I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin I was in Berlin ", "BUDGET",
        "My Berlin trip", 4,
        "Берлинска стена
        Бранденбургска врата
        Техно клуб
        Музей на евреите
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена
        Берлинска стена", 25, "https://www.youtube.com/watch?v=cv38DPCSUNs", 1);


INSERT INTO trips_types(trip_entity_id, types_id) values (11, 11);

INSERT INTO pictures(id, public_id, title, url, author_id, trip_id) VALUES
                                                                          (1, "imacpdnurdompjc9l6sd", "brandenburger_tor.jpg", "https://res.cloudinary.com/dt4o2jhmk/image/upload/v1722713663/imacpdnurdompjc9l6sd.jpg", 1, 1),
                                                                          (2, "imacpdnurdompjc9l6sd", "brandenburger_tor.jpg", "https://res.cloudinary.com/dt4o2jhmk/image/upload/v1722713663/imacpdnurdompjc9l6sd.jpg", 1, 2),
                                                                          (3, "imacpdnurdompjc9l6sd", "brandenburger_tor.jpg", "https://res.cloudinary.com/dt4o2jhmk/image/upload/v1722713663/imacpdnurdompjc9l6sd.jpg", 1, 3),
                                                                          (4, "imacpdnurdompjc9l6sd", "brandenburger_tor.jpg", "https://res.cloudinary.com/dt4o2jhmk/image/upload/v1722713663/imacpdnurdompjc9l6sd.jpg", 1, 4),
                                                                          (5, "imacpdnurdompjc9l6sd", "brandenburger_tor.jpg", "https://res.cloudinary.com/dt4o2jhmk/image/upload/v1722713663/imacpdnurdompjc9l6sd.jpg", 1, 5),
                                                                          (6, "imacpdnurdompjc9l6sd", "brandenburger_tor.jpg", "https://res.cloudinary.com/dt4o2jhmk/image/upload/v1722713663/imacpdnurdompjc9l6sd.jpg", 1, 6),
                                                                          (7, "imacpdnurdompjc9l6sd", "brandenburger_tor.jpg", "https://res.cloudinary.com/dt4o2jhmk/image/upload/v1722713663/imacpdnurdompjc9l6sd.jpg", 1, 7),
                                                                          (8, "imacpdnurdompjc9l6sd", "brandenburger_tor.jpg", "https://res.cloudinary.com/dt4o2jhmk/image/upload/v1722713663/imacpdnurdompjc9l6sd.jpg", 1, 8),
                                                                          (9, "imacpdnurdompjc9l6sd", "brandenburger_tor.jpg", "https://res.cloudinary.com/dt4o2jhmk/image/upload/v1722713663/imacpdnurdompjc9l6sd.jpg", 1, 9),
                                                                          (10, "imacpdnurdompjc9l6sd", "brandenburger_tor.jpg", "https://res.cloudinary.com/dt4o2jhmk/image/upload/v1722713663/imacpdnurdompjc9l6sd.jpg", 1, 10),
                                                                          (11, "imacpdnurdompjc9l6sd", "brandenburger_tor.jpg", "https://res.cloudinary.com/dt4o2jhmk/image/upload/v1722713663/imacpdnurdompjc9l6sd.jpg", 1, 11);









































