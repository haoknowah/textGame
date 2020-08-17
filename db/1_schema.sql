CREATE TABLE enemies
(
    race text,
    env text,
    rhp int,
    ratk int,
    rdef int,
    rmag int
);

CREATE TABLE grimoire
(
    spell text,
    buff boolean,
    debuff boolean,
    dmg int
);

CREATE TABLE dungeons
(
    id text
);


INSERT INTO dungeons(id) VALUES('begin');

INSERT INTO enemies(race, env, rhp, ratk, rdef, rmag) VALUES (
    'goblin', 'begin', -2, -1, -2, -3
);
INSERT INTO enemies(race, env, rhp, ratk, rdef, rmag) VALUES (
    'hobgoblin', 'begin', -1, 0, -1, -2
);
INSERT INTO enemies(race, env, rhp, ratk, rdef, rmag) VALUES (
    'orc', 'begin', 0, 0, 0, -1
);
INSERT INTO enemies(race, env, rhp, ratk, rdef, rmag) VALUES (
    'drake', 'begin', 3, 2, 3, 2
);

INSERT INTO grimoire(spell, buff, debuff, dmg) VALUES (
    'flame arrow', FALSE, FALSE, 3
);
INSERT INTO grimoire(spell, buff, debuff, dmg) VALUES (
    'light heal', TRUE, FALSE, 1
);

CREATE VIEW opponents AS
SELECT race, rhp, ratk, rdef, rmag
FROM enemies
WHERE env = 'begin';