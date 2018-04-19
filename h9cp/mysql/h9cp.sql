USE mysqldesa;
-- tb: table
-- fk: foreing key
-- h9: hal9000
-- cp: contratos públicos
-- an: anuncio
-- ad: adjudicacion
-- co: contacto
-- pr: proveedor
--
alter table tbh9cpad 
drop 
	foreign key FKh9cpadcppr
;
alter table tbh9cpan 
drop 
	foreign key FKh9cpancpad
;
alter table tbh9cpan 
drop 
	foreign key FKh9cpancpco
;
drop table if exists tbh9cpad;
drop table if exists tbh9cpan;
drop table if exists tbh9cpco;
drop table if exists tbh9cppr;

-- Adjudicacion
create table tbh9cpad (
	h9cpad_id bigint not null auto_increment,
	h9cpad_fecha datetime not null,
	h9cpad_importe float not null,
	h9cpad_proveedorID bigint,
	primary key (h9cpad_id)
	)
;
-- Anuncio
create table tbh9cpan (
	h9cpan_id bigint not null auto_increment,
	h9cpan_objeto varchar(255) not null,
	h9cpan_referencia varchar(5) not null,
	h9cpan_adjudciacionID bigint,
	h9cpan_contactoID bigint,
	primary key (h9cpan_id)
)
;
-- Contacto
create table tbh9cpco (
	h9cpco_id bigint not null auto_increment,
	h9cpco_contacto varchar(60),
	primary key (h9cpco_id)
)
;
-- Proveedor
create table tbh9cppr (
	h9cppr_id bigint not null auto_increment,
	h9cppr_proveedor varchar(60),
	primary key (h9cppr_id)
)
;
-- fk Adjudicacion-Proveedor
alter table tbh9cpad 
	add constraint FKh9cpadcppr 
	foreign key (h9cpad_proveedorID) 
	references tbh9cppr (h9cppr_id)
;-- fk Anuncio-Adjudicacion
alter table tbh9cpan 
	add constraint FKh9cpancpad 
	foreign key (h9cpan_adjudicacionID) 
	references tbh9cpad (h9cpad_id)
;;-- fk Anuncio-Contacto
alter table tbh9cpan 
	add constraint FKh9cpancpco 
	foreign key (h9cpan_contactoID) 
	references tbh9cpco (h9cpco_id)
;

select * from tbh9cpco;
select * from tbh9cpan;
select * from tbh9cppr;
select * from tbh9cpad;

delete from tbh9cpad where h9cpad_id = 1;
delete from tbh9cppr where h9cppr_id = 1;
delete from tbh9cpan where h9cpan_id = 1;
delete from tbh9cpco where h9cpco_id = 1;

insert into tbh9cpco values (1,"Jose Ignacio Hernández García")
;
insert into tbh9cpan (h9cpan_id, h9cpan_referencia, h9cpan_objeto, h9cpan_contactoID) 
	values (1, 3481, "SUMINISTRO SISTEMA CCM (CUSTOMER COMMUNICATION MANAGEMENT PLATFORM)", 1)
	;
insert into tbh9cppr values (1,"INDRA")
;
insert into tbh9cpad (h9cpad_id, h9cpad_fecha, h9cpad_importe, h9cpad_proveedorID) 
	values (1, "2018-03-02", 498000.00, 1)
	;
update tbh9cpan set h9cpan_adjudicacionID = 1 where h9cpan_id = 1;
	
select h9cpan_referencia, h9cpan_objeto
		, h9cpco_contacto
		, h9cpad_fecha, h9cpad_importe
		, h9cppr_proveedor
from tbh9cpan
inner join tbh9cpco 
on h9cpco_id = h9cpan_contactoID
inner join tbh9cpad 
on h9cpad_id = h9cpan_adjudicacionID
inner join tbh9cppr 
on h9cppr_id = h9cpad_proveedorID
;