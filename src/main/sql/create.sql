drop schema if exists fennel cascade;
create schema fennel;

/*
 * 
 * SEQUENCES
 * 
 */

create sequence associationvalueentry_id_seq start with 1 increment by 1;
create sequence domainevententry_globalindex_seq start with 1 increment by 1;

/*
 * 
 * TABLES - READ_QUERY
 * 
 */

create table users(
  user_id       text not null,
  display_name  text,
  username      text,
  password      text,
  locked        bool,
  
  constraint users_pkey primary key (user_id)
);

create table groups(
  group_name  text not null,
  description text,
  
  constraint groups_pkey primary key (group_name)
);

create table roles(
  role_name   text not null,
  description text,
  
  constraint roles_pkey primary key (role_name)
);

create table permissions(
  permission_name text not null,
  description     text,
  
  constraint permissions_pkey primary key (permission_name)
);

create table user_roles(
  user_id   text not null,
  role_name text not null,
  
  constraint user_roles_pkey primary key (user_id, role_name),
  constraint user_roles_user_id_fkey foreign key (user_id)  references users(user_id),
  constraint user_roles_role_name_fkey foreign key (role_name)  references roles(role_name)
);

create table group_roles(
  group_name  text not null,
  role_name   text not null,
  
  constraint group_roles_pkey primary key (group_name, role_name),
  constraint group_roles_group_name_fkey foreign key (group_name)  references groups(group_name),
  constraint group_roles_role_name_fkey foreign key (role_name)  references roles(role_name)
);

create table role_permissions(
  role_name       text not null,
  permission_name text not null,
  
  constraint role_permissions_pkey primary key (role_name, permission_name),
  constraint role_permissions_role_name_fkey foreign key (role_name)  references roles(role_name),
  constraint role_permissions_permission_name_fkey foreign key (permission_name)  references permissions(permission_name)
);

/*
 * 
 * TABLES - AXON
 * 
 */

create table associationvalueentry (
  id                bigint not null default nextval('associationvalueentry_id_seq'::regclass),
  associationkey    text,
  associationvalue  text,
  sagaid            text,
  sagatype          text,
  
  constraint associationvalueentry_pkey primary key (id)
) ;

create table domainevententry (
  globalindex         bigint not null default nextval('domainevententry_globalindex_seq'::regclass),
  aggregateidentifier text not null,
  sequencenumber      bigint not null,
  "type"              text null,
  eventidentifier     text not null,
  metadata            bytea null,
  payload             bytea not null,
  payloadrevision     text null,
  payloadtype         text not null,
  "timestamp"         text not null,
  
  constraint domainevententry_aggregateidentifier_sequencenumber_key unique (aggregateidentifier,sequencenumber),
  constraint domainevententry_eventidentifier_key unique (eventidentifier),
  constraint domainevententry_pkey primary key (globalindex)
);

create table sagaentry (
  sagaid          text not null,
  revision        text null,
  sagatype        text null,
  serializedsaga  bytea null,
  
  constraint sagaentry_pkey primary key (sagaid)
) ;

create table snapshotevententry (
  aggregateidentifier text not null,
  sequencenumber      bigint not null,
  "type"              text not null,
  eventidentifier     text not null,
  metadata            bytea null,
  payload             bytea not null,
  payloadrevision     text null,
  payloadtype         text not null,
  "timestamp"         text not null,
  
  constraint snapshotevententry_eventidentifier_key unique (eventidentifier),
  constraint snapshotevententry_pkey primary key (aggregateidentifier,sequencenumber)
);

create table tokenentry (
  processorname text not null,
  segment       int not null,
  token         bytea null,
  tokentype     text null,
  "timestamp"   text null,
  owner         text null,
  
  constraint tokenentry_pkey primary key (processorname,segment)
);
