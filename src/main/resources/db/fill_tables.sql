INSERT INTO roles VALUES 
	(nextval('roles_id_seq'), 'ROLE_ADMIN'),
	(nextval('roles_id_seq'), 'ROLE_EDITOR'),
	(nextval('roles_id_seq'), 'ROLE_USER');

INSERT INTO users VALUES 
	(nextval('users_id_seq'), 'admin', '$2a$10$3v1yLB7jk8n6BpQ2EuhJ4OmpPKHdwZThFFTBvXRMVotD0bQefFXBa', (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')),
	(nextval('users_id_seq'), 'mbk', '$2a$10$Yn1TuuDwbpCGLG5amt4gXeCBT4tXumMb5.h7gMaSfXMvIfbk9FR9e', (SELECT id FROM roles WHERE name = 'ROLE_EDITOR')),
	(nextval('users_id_seq'), 'shadow', '$2a$10$Q339nqY2LOGvQhC2l2PcmOgp08/Y.0V3NmA2awAh5zvxogu.p8N92', (SELECT id FROM roles WHERE name = 'ROLE_EDITOR')),
	(nextval('users_id_seq'), 'test', '$2a$10$0wOGBDyAt6rKySiAxDwTeeUWsfwqiDDugwS626VTXjIQS1fUQJvi6', (SELECT id FROM roles WHERE name = 'ROLE_USER')),
	(nextval('users_id_seq'), 'pietrek', '$2a$10$ZvfeQmbMLjfEE.SDPP5ebu76xwp5713gjaj9FyZ3VZkfh3Qceq6hK', (SELECT id FROM roles WHERE name = 'ROLE_USER')),
	(nextval('users_id_seq'), 'rafal', '$2a$10$r4VNMJ0NHOEcDdvnEDC.Mery3vnCS.M4ggSMSJlPxzqXu1yEGifDa', (SELECT id FROM roles WHERE name = 'ROLE_USER'));
	
INSERT INTO articles VALUES
	(nextval('articles_id_seq'), 'Lorem ipsum', 'lorem-ipsum', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas lacinia nisl vestibulum justo venenatis consectetur. Nullam id nulla vel velit luctus pharetra non sit amet elit. Morbi iaculis iaculis eros auctor rhoncus. Quisque nisi metus, rutrum at posuere in, facilisis eget dui. Etiam laoreet consectetur aliquet. Phasellus rutrum sagittis venenatis. Suspendisse et aliquam nibh, eu lobortis massa. Quisque vitae lorem non diam mattis placerat eget a magna. Sed nunc ipsum, tincidunt sed orci id, scelerisque porttitor augue. Nam vel varius ligula. Donec eu tincidunt dolor. Sed at laoreet est.', 'PUB', (SELECT id FROM users WHERE username = 'admin'), LOCALTIMESTAMP, null),
	(nextval('articles_id_seq'), 'The continuation', 'the-continuation', 'Etiam sit amet metus vel erat eleifend laoreet id ac lacus. Suspendisse nec ultrices lectus, sed blandit ex. Cras sed leo mauris. Cras tincidunt varius leo sit amet laoreet. Aenean nisi tellus, aliquet et tincidunt ut, blandit ac urna. Maecenas ornare tellus a lobortis facilisis. Nunc ante leo, tincidunt in rhoncus id, finibus rhoncus augue. Ut semper justo vel leo molestie lobortis eu non nibh. Duis auctor, erat non sollicitudin auctor, nulla tortor aliquet leo, ut vehicula nunc dui vel nibh. Donec mollis, lorem vel mattis varius, turpis velit faucibus ligula, eu porttitor augue augue luctus lorem. Maecenas tristique erat ut orci aliquet euismod.', 'PUB', (SELECT id FROM users WHERE username = 'mbk'), LOCALTIMESTAMP, null);
	
INSERT INTO acl_class VALUES
	(nextval('acl_class_id_seq'), 'pl.edu.agh.blog.models.Article');

INSERT INTO acl_sid VALUES
	(nextval('acl_sid_id_seq'), true, 'admin'),
	(nextval('acl_sid_id_seq'), true, 'mbk'),
	(nextval('acl_sid_id_seq'), true, 'shadow'),
	(nextval('acl_sid_id_seq'), true, 'test'),
	(nextval('acl_sid_id_seq'), true, 'pietrek'),
	(nextval('acl_sid_id_seq'), true, 'rafal');

INSERT INTO acl_object_identity VALUES
	(nextval('acl_object_identity_id_seq'), (SELECT id FROM acl_class WHERE class = 'pl.edu.agh.blog.models.Article'), (SELECT id FROM articles WHERE slug = 'lorem-ipsum'), null, (SELECT id FROM acl_sid WHERE sid = 'admin'), true),
	(nextval('acl_object_identity_id_seq'), (SELECT id FROM acl_class WHERE class = 'pl.edu.agh.blog.models.Article'), (SELECT id FROM articles WHERE slug = 'the-continuation'), null, (SELECT id FROM acl_sid WHERE sid = 'mbk'), true);
	
INSERT INTO acl_entry VALUES
	(nextval('acl_entry_id_seq'), (SELECT oid.id FROM acl_object_identity oid JOIN articles a ON oid.object_id_identity = a.id WHERE a.slug = 'lorem-ipsum'), 0, (SELECT id FROM acl_sid WHERE sid = 'admin'), 31, true, false, false),
	(nextval('acl_entry_id_seq'), (SELECT oid.id FROM acl_object_identity oid JOIN articles a ON oid.object_id_identity = a.id WHERE a.slug = 'the-continuation'), 0, (SELECT id FROM acl_sid WHERE sid = 'mbk'), 31, true, false, false);