create table alumni_advisors (alumniid integer not null auto_increment, primary key (alumniid)) engine=InnoDB;
create table application (applicationid bigint not null auto_increment, dateofapplication date, is_selected varchar(255), applicantbecomesmember_id integer, applicationstovertical_id integer, applicnforrecruitment_id integer, applicnpartofvertical_id integer, studapplies_id integer, primary key (applicationid)) engine=InnoDB;
create table award (awardid integer not null auto_increment, award_type varchar(255), certificate varchar(255), year integer, primary key (awardid)) engine=InnoDB;
create table bills (billid integer not null auto_increment, date_of_billing date, purpose varchar(255), status varchar(255), vendorbilledby_id integer, primary key (billid)) engine=InnoDB;
create table book_bookings (bookingid integer not null auto_increment, date_taken date, expected_return date, personal_rating double precision, return_date date, status varchar(255), booking_for_book_id integer, studorderbooking_id integer, primary key (bookingid)) engine=InnoDB;
create table books (bookid integer not null auto_increment, author varchar(255), available_copies integer, book_name varchar(255), book_rating double precision, status varchar(255), total_no_of_copies integer, total_reads bigint, primary key (bookid)) engine=InnoDB;
create table counselling_sessions (sessionid integer not null auto_increment, date date, feedback varchar(255), from_time time, to_time time, counseled_by_id integer, counsels_to_id integer, primary key (sessionid)) engine=InnoDB;
create table counselor (counselor_id integer not null auto_increment, current_status varchar(255), date_of_joining varchar(255), designation varchar(255), email_id varchar(255), job_type varchar(255), name varchar(255), phone_no bigint, qualification varchar(255), timings varchar(255), primary key (counselor_id)) engine=InnoDB;
create table event (eventid integer not null auto_increment, date date, from_time time, minutesof_meeting varchar(255), no_of_attendees integer, resources varchar(255), to_time time, seekpermission_id integer, venued_at_id integer, primary key (eventid)) engine=InnoDB;
create table events_permission (permissionid integer not null auto_increment, attested_by varchar(255), granted_by varchar(255), membertakespermission_id integer, permissionattestedby_id integer, permissiongrantedby_id integer, primary key (permissionid)) engine=InnoDB;
create table faculty (faculty_id integer not null auto_increment, department varchar(255), email_id varchar(255), name varchar(255), phone_no bigint, qualification varchar(255), primary key (faculty_id)) engine=InnoDB;
create table facultycounselsverticals (faculty_faculty_id integer not null, vertical_verticalid integer not null, primary key (faculty_faculty_id, vertical_verticalid)) engine=InnoDB;
create table guests_speakers (guestid integer not null auto_increment, account_number bigint, designation varchar(255), emailid varchar(255), name varchar(255), phone_number time, qualification varchar(255), resources varchar(255), primary key (guestid)) engine=InnoDB;
create table honorarium (honorariumid integer not null auto_increment, amount double precision, guest_feedback varchar(255), status varchar(255), transaction_number bigint, honorarirum_responsibles_id integer, honored_for_id integer, honored_to_guests_id integer, primary key (honorariumid)) engine=InnoDB;
create table imgroups (groupid integer not null auto_increment, i_mgroupsinprogram_id integer, i_mgrpssupervisedby_fac_id integer, primary key (groupid)) engine=InnoDB;
create table inductionmentor (mentorid integer not null auto_increment, nodal_mentorid integer, imgrpcontainsim_id integer, i_mmentoredbynm_id integer, i_mpartof_induction_id integer, primary key (mentorid)) engine=InnoDB;
create table induction_program (induction_id integer not null auto_increment, chairman varchar(255), duration varchar(255), no_of_attendees integer, year integer, primary key (induction_id)) engine=InnoDB;
create table initiatives_permission (permissionid integer not null auto_increment, attested_by varchar(255), granted_by varchar(255), initpermissattestedby_id integer, initpermissgranted_id integer, memberseekinitiativepermission_id integer, primary key (permissionid)) engine=InnoDB;
create table inititatives (initiatives_id integer not null auto_increment, minutes_of_meeting varchar(255), no_of_attendees bigint, resources varchar(255), start_date date, timings time, title varchar(255), initiatvehostedat_id integer, permissionforinitiative_id integer, primary key (initiatives_id)) engine=InnoDB;
create table membersattendmeet (scsmembers_member_id integer not null, teammeetings_meetingid integer not null, primary key (scsmembers_member_id, teammeetings_meetingid)) engine=InnoDB;
create table monthly_reports (reportid integer not null auto_increment, compiled_on date, from_date date, report_link varchar(255), to_date date, studntcompilesreports_id integer, primary key (reportid)) engine=InnoDB;
create table recruitment (recruitmentid integer not null auto_increment, por varchar(255), year integer, primary key (recruitmentid)) engine=InnoDB;
create table repositories (repoid integer not null auto_increment, compiled_on date, repo_link varchar(255), year integer, student_compilesrepos_id integer, vertical_repos_id integer, primary key (repoid)) engine=InnoDB;
create table scsmembers (member_id integer not null auto_increment, current_position varchar(255), from_date date, to_date date, memberbecomesim_id integer, membersresponsblebills_id integer, verticalmemberbelongs_id integer, primary key (member_id)) engine=InnoDB;
create table student (student_roll_no integer not null auto_increment, date_of_birth date, department varchar(255), e_mail_id varchar(255), name varchar(255), phone_number bigint, program varchar(255), year_of_gradn integer, year_ofjoin integer, scsmembership_id integer, student_passed_out_id integer, studspartofimgp_id integer, primary key (student_roll_no)) engine=InnoDB;
create table studentawarded (student_student_roll_no integer not null, award_awardid integer not null, primary key (student_student_roll_no, award_awardid)) engine=InnoDB;
create table studinitiativepart (student_student_roll_no integer not null, inititatives_initiatives_id integer not null, primary key (student_student_roll_no, inititatives_initiatives_id)) engine=InnoDB;
create table studparticipateevent (student_student_roll_no integer not null, event_eventid integer not null, primary key (student_student_roll_no, event_eventid)) engine=InnoDB;
create table teammeetings (meetingid integer not null auto_increment, date date, from_time time, kind_of_meeting varchar(255), location varchar(255), no_of_attendees integer, resources_link varchar(255), to_time time, meetbelongtovertical_id integer, primary key (meetingid)) engine=InnoDB;
create table vendors (vendorid integer not null auto_increment, address varchar(255), contact_no bigint, item varchar(255), vendor_name varchar(255), primary key (vendorid)) engine=InnoDB;
create table venue (venueid integer not null auto_increment, capacity bigint, contactnumber bigint, contactperson varchar(255), location varchar(255), venue_name varchar(255), primary key (venueid)) engine=InnoDB;
create table vertical (verticalid integer not null auto_increment, name_of_vertical varchar(255), vertical_budgets_id integer, primary key (verticalid)) engine=InnoDB;
create table yearly_budgets (verticalid integer not null auto_increment, amount_spent double precision, total_amount double precision, year integer, primary key (verticalid)) engine=InnoDB;
alter table application add constraint FKdh748ro6uuh4vpcrqss11w2tw foreign key (applicantbecomesmember_id) references scsmembers (member_id);
alter table application add constraint FK9vyj2jlwavcn76d4p2khglljk foreign key (applicationstovertical_id) references vertical (verticalid);
alter table application add constraint FK1an64q8ts2a7hpii1d9t7l8gn foreign key (applicnforrecruitment_id) references recruitment (recruitmentid);
alter table application add constraint FKkqj9ik141yedi9k134npl960y foreign key (applicnpartofvertical_id) references vertical (verticalid);
alter table application add constraint FKr4srujsx2tol7eljd9dmdywi1 foreign key (studapplies_id) references student (student_roll_no);
alter table bills add constraint FKjdwjvfyvh121rk3b4ntxsv6ub foreign key (vendorbilledby_id) references vendors (vendorid);
alter table book_bookings add constraint FKc3l6k8pl8b9qcmpsuv44p84qa foreign key (booking_for_book_id) references books (bookid);
alter table book_bookings add constraint FK5s1xk1n84w5lf2cerua326m6u foreign key (studorderbooking_id) references student (student_roll_no);
alter table counselling_sessions add constraint FKou5mons1n58vclgju1hgf1aer foreign key (counseled_by_id) references counselor (counselor_id);
alter table counselling_sessions add constraint FKesbs136ktjfw2cof90ilsbm8n foreign key (counsels_to_id) references student (student_roll_no);
alter table event add constraint FK9uj65qabfvd7tov4hfvey9nkw foreign key (seekpermission_id) references events_permission (permissionid);
alter table event add constraint FK9be81kghf7nh8bb4fx0oun6v3 foreign key (venued_at_id) references venue (venueid);
alter table events_permission add constraint FKw6wmjtf6b4c1u55s1cn2u90c foreign key (membertakespermission_id) references scsmembers (member_id);
alter table events_permission add constraint FKe0ba3j0ajhmx7ymbmgsnnaw3v foreign key (permissionattestedby_id) references faculty (faculty_id);
alter table events_permission add constraint FK8b27cqrhc6nej53xn15pwpt9i foreign key (permissiongrantedby_id) references faculty (faculty_id);
alter table facultycounselsverticals add constraint FKdrgvhnqmii25eoq5w8p380m45 foreign key (vertical_verticalid) references vertical (verticalid);
alter table facultycounselsverticals add constraint FK7vaehnpxlveni53tlghxvb0dn foreign key (faculty_faculty_id) references faculty (faculty_id);
alter table honorarium add constraint FK72x63p6p1gtvogbidk82t943j foreign key (honorarirum_responsibles_id) references scsmembers (member_id);
alter table honorarium add constraint FK7fasmo6u5g3fqy6rdooi37h1k foreign key (honored_for_id) references event (eventid);
alter table honorarium add constraint FKgdn0yha03tj4e25y42rvgvq3f foreign key (honored_to_guests_id) references guests_speakers (guestid);
alter table imgroups add constraint FK6q33yfctxaoravif3wufys574 foreign key (i_mgroupsinprogram_id) references induction_program (induction_id);
alter table imgroups add constraint FKbvyc8lo03urd4vqfmx1q9cj8y foreign key (i_mgrpssupervisedby_fac_id) references faculty (faculty_id);
alter table inductionmentor add constraint FKlftokwy1ta49opbu5p0v2p1v3 foreign key (imgrpcontainsim_id) references imgroups (groupid);
alter table inductionmentor add constraint FK3qrsirsuxhmkwwakrvq4r2kwx foreign key (i_mmentoredbynm_id) references inductionmentor (mentorid);
alter table inductionmentor add constraint FK60i6vfva8eeak39gq6ncg0mxs foreign key (i_mpartof_induction_id) references induction_program (induction_id);
alter table initiatives_permission add constraint FKtb9f1r9b1tbsiskhgm48n21lt foreign key (initpermissattestedby_id) references faculty (faculty_id);
alter table initiatives_permission add constraint FKfl1csn0sg8lq1m6875jcu30nu foreign key (initpermissgranted_id) references faculty (faculty_id);
alter table initiatives_permission add constraint FK1k4tp13r7bsma3g2i5i1bbjrj foreign key (memberseekinitiativepermission_id) references scsmembers (member_id);
alter table inititatives add constraint FK66e5ycu7qsyj6vgeuqoj3rcdx foreign key (initiatvehostedat_id) references venue (venueid);
alter table inititatives add constraint FKiw57dokxfulmxd7kkqf6h3vr foreign key (permissionforinitiative_id) references initiatives_permission (permissionid);
alter table membersattendmeet add constraint FK1hgrjc8sc9pc0o12dneaifwn5 foreign key (teammeetings_meetingid) references teammeetings (meetingid);
alter table membersattendmeet add constraint FKh25hw4xrv5hsr5ickoyo7n6u5 foreign key (scsmembers_member_id) references scsmembers (member_id);
alter table monthly_reports add constraint FKcx8lmqql1sueuskc9kjsgw4pa foreign key (studntcompilesreports_id) references student (student_roll_no);
alter table repositories add constraint FKbf1cn5oc4x49y2wn7mgbp6cce foreign key (student_compilesrepos_id) references student (student_roll_no);
alter table repositories add constraint FKiqstkdod1hycjk3pq9wd2q425 foreign key (vertical_repos_id) references vertical (verticalid);
alter table scsmembers add constraint FK162n04t2na4xvm5k715xcna92 foreign key (memberbecomesim_id) references inductionmentor (mentorid);
alter table scsmembers add constraint FK9hoxfihdfbt0f20ok035j4utm foreign key (membersresponsblebills_id) references bills (billid);
alter table scsmembers add constraint FKcdsim01dpxkwjuvuxdd6qlmhb foreign key (verticalmemberbelongs_id) references vertical (verticalid);
alter table student add constraint FK1rl8a8nmdrpec44wdir4dv48u foreign key (scsmembership_id) references scsmembers (member_id);
alter table student add constraint FK65yh8p8gs4gim279cjoedkqyw foreign key (student_passed_out_id) references alumni_advisors (alumniid);
alter table student add constraint FK7ya13vxn30af4ux3unjuajx6j foreign key (studspartofimgp_id) references imgroups (groupid);
alter table studentawarded add constraint FKr9jti7f7y8abjvjde6fkjuodx foreign key (award_awardid) references award (awardid);
alter table studentawarded add constraint FKfhio0r7vwxd0c0t26l3eamudn foreign key (student_student_roll_no) references student (student_roll_no);
alter table studinitiativepart add constraint FKrneypkg28sxyu70jnacbhe8de foreign key (inititatives_initiatives_id) references inititatives (initiatives_id);
alter table studinitiativepart add constraint FKqbngrin3ilk3ya5jiii6xrqob foreign key (student_student_roll_no) references student (student_roll_no);
alter table studparticipateevent add constraint FKt4tqh1cftml74sbcyvlywwjvh foreign key (event_eventid) references event (eventid);
alter table studparticipateevent add constraint FKonry8eq6333tpn1f57brr6bi3 foreign key (student_student_roll_no) references student (student_roll_no);
alter table teammeetings add constraint FKcqf6by6h3auwqyppx1cdan1pf foreign key (meetbelongtovertical_id) references vertical (verticalid);
alter table vertical add constraint FKo524ku8ungc4phgfm9p32hij6 foreign key (vertical_budgets_id) references yearly_budgets (verticalid);
