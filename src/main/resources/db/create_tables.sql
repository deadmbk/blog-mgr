DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
	id serial primary key,
	name varchar(50) unique not null
);

CREATE TABLE users (
	id serial primary key,
	username varchar(30) not null,
	password char(60) not null,
	role_id integer not null references roles ON DELETE restrict ON UPDATE cascade
);

CREATE TABLE articles (
	id serial primary key,
	title varchar(50) not null,
	slug varchar(70) not null,
	content text not null,
	access char(3) not null default 'PUB',
	author_id integer references users ON DELETE set null ON UPDATE cascade,
	created_at timestamp not null,
	updated_at timestamp
);

CREATE TABLE comments (
	id serial primary key,
	content text not null,
	article_id integer not null references articles ON DELETE cascade ON UPDATE cascade,
	author_id integer references users ON DELETE set null ON UPDATE cascade,
	created_at timestamp not null,
	updated_at timestamp
);