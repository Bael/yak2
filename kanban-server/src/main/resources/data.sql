delete from task;
delete from stage;
delete from board;

insert into board (id, `name`) values (10, 'mainboard');


insert into stage(id, board_id, `name`) values (11, 10, 'backlog');
insert into stage(id, board_id, `name`) values (12, 10, 'plan');
insert into stage(id, board_id, `name`) values (13, 10, 'working');
insert into stage(id, board_id, `name`) values (14, 10, 'done');
