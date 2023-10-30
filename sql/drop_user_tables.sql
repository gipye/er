alter table OrdinaryUser drop constraint FK_OrdinaryUser_TO_User;
alter table HospitalUser drop constraint FK_HospitalUser_TO_User;
alter table HospitalDepartment drop constraint FK_HospitalDepartment_TO_HospitalUser;

drop table User;
drop table OrdinaryUser;
drop table HospitalUser;
drop table HospitalDepartment;