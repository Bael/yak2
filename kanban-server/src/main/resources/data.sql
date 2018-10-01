delete from task;
delete from stage;
delete from board;

insert into board (id, `name`) values (10, 'mainboard');
insert into board (id, `name`) values (20, 'hometasks');

insert into stage(id, board_id, `name`) values (11, 10, 'backlog');
insert into stage(id, board_id, `name`) values (12, 10, 'plan');
insert into stage(id, board_id, `name`) values (13, 10, 'working');
insert into stage(id, board_id, `name`) values (14, 10, 'done');


insert into stage(id, board_id, `name`) values (21, 20, 'plan');
insert into stage(id, board_id, `name`) values (22, 20, 'doing');
insert into stage(id, board_id, `name`) values (24, 20, 'done');
