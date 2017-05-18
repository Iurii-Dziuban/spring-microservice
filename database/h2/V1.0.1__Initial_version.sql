create table WEBSERVICE.USER (
       ID varchar2(16) not null,
       NAME varchar2(20) not null,
       BIRTH_DATE DATE not null,
       UPDATED_TIME TIMESTAMP not null,
       MONEY NUMBER not null,
   constraint PK_USER PRIMARY KEY (ID)
);

grant select, insert, update, delete on WEBSERVICE.USER to SERVICE_USER;

comment on column WEBSERVICE.USER.ID is
   'User Identifier. Unique';

comment on column WEBSERVICE.USER.NAME is
   'Name of the user';

comment on column WEBSERVICE.USER.BIRTH_DATE is
   'Birth date of the user';

comment on column WEBSERVICE.USER.UPDATED_TIME is
   'Time in UTC record is updated';

comment on column WEBSERVICE.USER.MONEY is
   'Amount that belongs to user';