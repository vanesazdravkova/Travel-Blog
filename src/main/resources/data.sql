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
        Берлинска стена", 5, "https://www.youtube.com/watch?v=cv38DPCSUNs", 1);


INSERT INTO trips_types(trip_entity_id, types_id) values (1, 3);

INSERT INTO pictures(id, public_id, title, url, author_id, trip_id) VALUES
                                                                          (1, "imacpdnurdompjc9l6sd", "brandenburger_tor.jpg", "https://res.cloudinary.com/dt4o2jhmk/image/upload/v1722713663/imacpdnurdompjc9l6sd.jpg", 1, 1);









































