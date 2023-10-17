CREATE TABLE sk_example_table (
    id SERIAL,
    obj JSONB NOT NULL,
    version bigint NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO sk_example_table (obj, version) VALUES ('{"current": 0}',0);

CREATE TABLE sk_example_table_ref (
    id SERIAL,
    name varchar(255) not null,
    sk_example_table_id INTEGER references sk_example_table(id),
    PRIMARY KEY(id)
);

INSERT INTO sk_example_table_ref (name, sk_example_table_id)
VALUES ('counter', (SELECT id FROM sk_example_table WHERE obj = '{"current": 0}'));