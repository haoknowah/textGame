CREATE VIEW getEnv AS SELECT env FROM enemies;

CREATE FUNCTION getRace(loc text) RETURNS TEXT AS
$$
    SELECT race FROM enemies WHERE env = loc;
$$ LANGUAGE SQL;