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

INSERT INTO dungeons VALUES("begin");
CREATE VIEW opponents AS
SELECT race, rhp, ratk, rdef, rmag
FROM enemies
WHERE env = "begin";