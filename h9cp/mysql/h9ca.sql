USE mysqldesa;
-- tb: table
-- fk: foreing key
-- h9: hal9000
-- ca: control de acceso
-- us: usuario
-- pe: perfil
-- up: usuarioxperfil
alter table tbh9caup 
drop 
	foreign key FKh9caupcaus
;
alter table tbh9caup 
drop 
	foreign key FKh9caupcape
;
drop table if exists tbh9caup;
drop table if exists tbh9caus;
drop table if exists tbh9cape;

-- Usuario
create table tbh9caus (
	h9caus_id bigint not null auto_increment,
	h9caus_usuario varchar(10) not null,
	h9caus_userid varchar(8) null,
	primary key (h9caus_id)
	)
;
-- Perfil
create table tbh9cape (
	h9cape_id bigint not null auto_increment,
	h9cape_perfil varchar(10) not null,
	primary key (h9cape_id)
)
;
-- UsuarioXPerfil
create table tbh9caup (
	h9caup_usuarioID bigint not null,
	h9caup_perfilID bigint not null,
	primary key (h9caup_usuarioID, h9caup_perfilID)
)
;
-- fk UsuarioXPerfil-Usuario
alter table tbh9caup 
	add constraint FKh9caupcaus 
	foreign key (h9caup_usuarioID) 
	references tbh9caus (h9caus_id)
;
-- fk UsuarioXPerfil-Perfil
alter table tbh9caup 
	add constraint FKh9caupcape 
	foreign key (h9caup_perfilID) 
	references tbh9cape (h9cape_id)
;

select * from tbh9caus;
select * from tbh9cape;
select * from tbh9caup;

insert into tbh9caus (h9caus_id, h9caus_usuario, h9caus_userid) 
	values (1,"jgarcial", "gl001")
	, (2,"jatxopitea", "tg00")
	, (3,"jgarciali", null)
	, (4,"vcasas", "cd15")
;
insert into tbh9cape (h9cape_id, h9cape_perfil) 
	values (1, "Responsable del contrato")
	, (2, "Responsable del servicio")
	, (3, "Técnico Remedy")
	, (4, "Técnico de Equipo de solvencia")
	, (5, "Técnico de Equipo apoyo")
	, (6, "Empleado Indra sin técnico")
	;
insert into tbh9caup values (1,1), (1,3), (1,5)
	, (2,2), (2,3), (2,5)
	, (3,6)
	, (4,3), (4,4)
;

select h9caup_usuarioID, h9caup_perfilID
		, h9caus_usuario, h9caus_userid
		, h9cape_perfil
from tbh9caup
inner join tbh9caus 
on h9caus_id = h9caup_usuarioID
inner join tbh9cape 
on h9cape_id = h9caup_perfilID
;