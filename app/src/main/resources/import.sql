INSERT INTO projects (name, icon_url, description) VALUES ('test_project', 'test_icon_url', 'test_description'),('test_project2', 'test_icon_url2', 'test_description2');

INSERT INTO users (is_owner, icon_url, name, introduce, password, email) VALUES (true, 'test_url1', 'test_user1', 'test_introduce1', '$2a$10$owcrsQczWfZlL0yIPSP1OOOGM95ICTAhUqMkS8hcsYhcscpROTtBa', 'test1@gmail.com'),(false, 'test_url2', 'test_user2', 'test_introduce2', '$2a$10$JwMUEwh6hiB/NAMZ5VVOEOSGsdF8Uri4dldDwQI3Mm8R0U69zGFIK', 'test2@gmail.com'),(false, 'test_url3', 'test_user3', 'test_introduce3', '$2a$10$MkIFq60lMNHtQdJPaSXiLOCpopZV2NahWktyGKrQBPm.kgikRV1ZO', 'test3@gmail.com');
-- INSERT INTO users (is_owner, icon_url, name, introduce, password, email) VALUES (false, 'test_url2', 'test_user2', 'test_introduce2', '$2a$10$owcrsQczWfZlL0yIPSP1OOOGM95ICTAhUqMkS8hcsYhcscpROTtBa', 'test2@gmail.com');
-- userの暗号化する前のpasswordはpassword1,password2,password3
INSERT INTO members (project_id, user_id) VALUES (1, 1),(1, 2),(1, 3);
INSERT INTO project_notices (log, project_id) VALUES ('log1', 1),('log2', 1);
INSERT INTO user_notices (log, user_id) VALUES ('log1', 1),('log2', 1);
INSERT INTO directories (name, project_id) VALUES ('directory1', 1),('directory2', 1);
-- INSERT INTO files (name, file_url, directory_id, project_id) VALUES ('file1', 'file_url1', 1, 1),('file2', 'file_url2', 1, 1);
INSERT INTO files (name, file_url, directory_id) VALUES ('file1', 'file_url1', 1),('file2', 'file_url2', 1);
INSERT INTO skills (name, count_log, user_id) VALUES ('skill1', 1, 1),('skill2', 2, 1);
INSERT INTO roles (name, count_log, user_id) VALUES ('role1', 1, 1),('role2', 2, 1);
INSERT INTO chat_rooms (name, project_id) VALUES ('chat_room1', 1),('chat_room1', 1);
-- INSERT INTO channels (name, chat_room_id, user_id) VALUES ('chat_room1', 1, 1),('chat_room2', 1, 1);
INSERT INTO channels (name, chat_room_id, user_id) VALUES ('channel1', 1, 1),('channel2', 1, 1);
-- INSERT INTO messages (text, content, user_id, chat_room_id, channel_id) VALUES ('message1', 'content1', 1, 1, 1),('message2', 'content2', 1, 1, 1);
INSERT INTO messages (text, content, user_id, channel_id) VALUES ('message1', 'content1', 1, 1),('message2', 'content2', 1, 1);
INSERT INTO followers (user_id, follower_id) VALUES (1, 2),(1, 3);
-- INSERT INTO members (project_id, user_id) VALUES (1, 1), (1,2)
INSERT INTO offers (message, scouted_user_id, user_id) VALUES ('message1', 2, 1),('message2', 3, 1);
INSERT INTO owners (project_id, user_id) VALUES (1, 1),(1, 2);
INSERT INTO projects_users (project_id, users_id) VALUES (1, 1),(1, 2),(2, 1);
