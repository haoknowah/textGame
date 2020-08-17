CREATE TABLE enemies
(
    race text,
    env text,
    rhp int,
    ratk int,
    rdef int,
    rmag int,
    boss boolean
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
    id text,
    leng int,
    lmin int,
    lmax int
);


INSERT INTO dungeons(id, leng, lmin, lmax) VALUES('begin', 5, 1, 3);

INSERT INTO enemies(race, env, rhp, ratk, rdef, rmag, boss) VALUES (
    'goblin', 'begin', -2, -1, -2, -3, FALSE
);
INSERT INTO enemies(race, env, rhp, ratk, rdef, rmag, boss) VALUES (
    'hobgoblin', 'begin', -1, 0, -1, -2, FALSE
);
INSERT INTO enemies(race, env, rhp, ratk, rdef, rmag, boss) VALUES (
    'orc', 'begin', 0, 0, 0, -1, FALSE
);
INSERT INTO enemies(race, env, rhp, ratk, rdef, rmag, boss) VALUES (
    'drake', 'begin', 3, 2, 3, 2, TRUE
);

INSERT INTO grimoire(spell, buff, debuff, dmg) VALUES (
    'flame arrow', FALSE, FALSE, 3
);
INSERT INTO grimoire(spell, buff, debuff, dmg) VALUES (
    'light heal', TRUE, FALSE, 1
);
INSERT INTO grimoire(spell, buff, debuff, dmg) VALUES (
    'acid splash', FALSE, TRUE, 2
);

CREATE VIEW opponents AS
SELECT race, rhp, ratk, rdef, rmag
FROM enemies
WHERE env = 'begin';