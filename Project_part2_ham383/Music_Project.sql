select * from userdetail_mst where user_id like 'dgfe';
select * from login_mst where username like 'user9';
select * from user_music_following where music_category_id=11
                        and music_subcategory_id=111;
-----------------------TABLE CREATION- START -----------------------------
create table USERDETAIL_MST(
  user_id             varchar(100),
  user_firstname      varchar(100),
  user_lastname       varchar(100),
  phone_number        number(10),
  user_dob            date,
  user_city           varchar(100),
  user_state          varchar(100),
  isArtist            number(1),
  turst_score         number(3),
  account_create_date date
);--table created
alter table USERDETAIL_MST
add artist_id varchar(100);

comment on column USERDETAIL_MST.user_id is 'Unique user id for new user';
comment on column USERDETAIL_MST.user_firstname is 'First name for user';
comment on column USERDETAIL_MST.user_lastname is 'Last name for user';
comment on column USERDETAIL_MST.phone_number is 'Phone number of user';
comment on column USERDETAIL_MST.user_dob is 'Date of birth of user';
comment on column USERDETAIL_MST.user_city is 'City of user';
comment on column USERDETAIL_MST.user_state is 'State of user';
comment on column USERDETAIL_MST.isArtist is 'Flag to indicate if user is artist or not. 0-not artist, 1-is artist';
comment on column USERDETAIL_MST.turst_score is 'trust score to add determine if user can add mising events on website';
comment on column USERDETAIL_MST.account_create_date is 'date when user account was created';
comment on column USERDETAIL_MST.artist_id is 'unique id of artist, same as user_id';

alter table USERDETAIL_MST
add constraint pk_user_id PRIMARY KEY(user_id);
alter table USERDETAIL_MST add constraint check_is_artist CHECK (isArtist IN (0,1));
alter table USERDETAIL_MST add constraint unique_artist_id UNIQUE(artist_id);

alter table USERDETAIL_MST add account_updated DATE;           
               
-------------------------
create table ARTIST_DETAIL_MST(
  artist_id             varchar(100),
  band_id               varchar(100),
  band_name             varchar(100),
  band_create_date      date,
  music_category_id     number(3),
  music_subcategory_id  number(3)
);--table created

comment on column ARTIST_DETAIL_MST.artist_id is 'unique id of artist';
comment on column ARTIST_DETAIL_MST.band_id is 'unique id of band';
comment on column ARTIST_DETAIL_MST.band_name is 'band name';
comment on column ARTIST_DETAIL_MST.band_create_date is 'date when band was created';
comment on column ARTIST_DETAIL_MST.music_category_id is 'which music category band belongs to';
comment on column ARTIST_DETAIL_MST.music_subcategory_id is 'which music sub_category band belongs to';

alter table ARTIST_DETAIL_MST
add constraint pk_band_artist_id     PRIMARY KEY(artist_id);
 alter table ARTIST_DETAIL_MST add constraint fk_music_category     FOREIGN KEY(music_category_id) references MUSIC_CATEGORY_MST(music_category_id);
 alter table ARTIST_DETAIL_MST add constraint fk_music_subcategory  FOREIGN KEY(music_subcategory_id) references MUSIC_SUBCATEGORY_MST(music_subcategory_id);

-------------------------------
create table MUSIC_CATEGORY_MST(
  music_category_id number(3),
  category_name     varchar(100),
  music_description varchar(100)
);--table created

comment on column MUSIC_CATEGORY_MST.music_category_id is 'music category id';
comment on column MUSIC_CATEGORY_MST.category_name is 'category name';
comment on column MUSIC_CATEGORY_MST.music_description is 'description of music';

alter table MUSIC_CATEGORY_MST add constraint pk_music_category_id PRIMARY KEY(music_category_id);
alter table MUSIC_CATEGORY_MST add constraint  unique_category_name UNIQUE(category_name);
                
--------------------------------------------
create table MUSIC_SUBCATEGORY_MST(
  music_subcategory_id    number(3),
  music_category_id       number(3),
  subcategory_name        varchar(100),
  subcategory_description varchar(100)
);--table created

comment on column MUSIC_SUBCATEGORY_MST.music_subcategory_id is 'unique subcategory id';
comment on column MUSIC_SUBCATEGORY_MST.music_category_id is 'which category id it belongs to';
comment on column MUSIC_SUBCATEGORY_MST.subcategory_name is 'subcategory name';
comment on column MUSIC_SUBCATEGORY_MST.subcategory_description is 'subcategory description';

alter table MUSIC_SUBCATEGORY_MST add constraint pk_music_subcategory_id PRIMARY KEY(music_subcategory_id);
alter table MUSIC_SUBCATEGORY_MST add constraint  fk_music_category_id FOREIGN KEY(music_category_id) REFERENCES MUSIC_CATEGORY_MST(music_category_id);

------------------------------
create table CONCERT_DETAIL_MST(
  concert_id          number(5),
  artist_id           varchar(100),
  band_id             varchar(100),
  concert_venue_city  varchar(100),
  concert_venue_state varchar(100),
  concert_price       number(3),
  concert_time        date
);--table created
alter table CONCERT_DETAIL_MST add total_tickets number(4);

comment on column CONCERT_DETAIL_MST.concert_id is 'unique id for each concert';
comment on column CONCERT_DETAIL_MST.artist_id is 'artist playing in that concert';
comment on column CONCERT_DETAIL_MST.band_id is 'band playing in that concert';
comment on column CONCERT_DETAIL_MST.concert_venue_city is 'concert venue city';
comment on column CONCERT_DETAIL_MST.concert_venue_state is 'concert venue state';
comment on column CONCERT_DETAIL_MST.concert_price is 'price of tickets for that concert';
comment on column CONCERT_DETAIL_MST.concert_time is 'concert timings';
comment on column CONCERT_DETAIL_MST.total_tickets is 'total capacity for that concert';

alter table CONCERT_DETAIL_MST add constraint pk_concert_id PRIMARY KEY(concert_id);
alter table CONCERT_DETAIL_MST add constraint fk_artist_id FOREIGN KEY(artist_id) references ARTIST_DETAIL_MST(artist_id);
--alter table CONCERT_DETAIL_MST add constraint fk_band_id FOREIGN KEY(band_id) references ARTIST_DETAIL_MST(band_id);
alter table CONCERT_DETAIL_MST add constraint price_check CHECK (concert_price > 0);
alter table CONCERT_DETAIL_MST add constraint total_ticket_check CHECK (total_tickets > 0);

alter table CONCERT_DETAIL_MST rename column concert_time to concert_date;
alter table CONCERT_DETAIL_MST 
add ( concert_time          VARCHAR(50),
      music_category_id    NUMBER(3),
      music_subcategory_id NUMBER(3)
); 
alter table CONCERT_DETAIL_MST add constraint fk_music_cat_id_concert FOREIGN KEY(music_category_id) references  MUSIC_CATEGORY_MST(music_category_id);
alter table CONCERT_DETAIL_MST add constraint fk_music_subcat_id_concert FOREIGN KEY(music_subcategory_id) references  MUSIC_SUBCATEGORY_MST(music_subcategory_id);

alter table CONCERT_DETAIL_MST add concert_name varchar(100);
--------------------------------------
create table concert_attendee(
  concert_id number(5),
  user_id varchar(100),
  concert_review varchar(100),
  concert_rating varchar(100),
  review_time date
);--table created

comment on column concert_attendee.concert_id is 'unique id for each concert';
comment on column concert_attendee.user_id is 'user attending concert';
comment on column concert_attendee.concert_review is 'review for each concert by each user';
comment on column concert_attendee.concert_rating is 'rating for each concert by each user';
comment on column concert_attendee.review_time is 'when review was posted by user';

alter table concert_attendee add constraint pk_concert_user_id PRIMARY KEY(concert_id,user_id);
alter table concert_attendee add constraint fk_concert_id FOREIGN KEY(concert_id) REFERENCES CONCERT_DETAIL_MST(concert_id);
alter table concert_attendee add constraint fk_user_id FOREIGN KEY(user_id) REFERENCES USERDETAIL_MST(user_id);

--------------------------------------
create table CONCERT_RECOMMENDATION(
  recommendation_id number(5),
  user_id varchar(100),
  concert_id number(5),
  recommendation_date date
);--table created

comment on column CONCERT_RECOMMENDATION.recommendation_id is 'unique id for each recommendation';
comment on column CONCERT_RECOMMENDATION.user_id is 'user who created the recommendation';
comment on column CONCERT_RECOMMENDATION.concert_id is 'recommendation for which concert';
comment on column CONCERT_RECOMMENDATION.recommendation_date is 'when recommendation was created';

alter table CONCERT_RECOMMENDATION add constraint pk_recommendation_id PRIMARY KEY(recommendation_id);
alter table CONCERT_RECOMMENDATION add constraint fk_concert_recommend_id FOREIGN KEY(concert_id) REFERENCES CONCERT_DETAIL_MST(concert_id);
alter table CONCERT_RECOMMENDATION add constraint fk_concert_user_id FOREIGN KEY(user_id) REFERENCES USERDETAIL_MST(user_id);

----------------------------------------
--user's who are following logged in user
create table user_follower(
  user_id varchar(100),
  user_follower_id varchar(100),
  following_date date
);--table created

comment on column user_follower.user_id is 'user who are logged in';
comment on column user_follower.user_follower_id is 'other users following the logged iin user';
comment on column user_follower.following_date is 'when user started following loggedin user';

alter table user_follower add constraint pk_user_follower_id PRIMARY KEY(user_id,user_follower_id);
alter table user_follower add constraint fk_user_id_fol FOREIGN KEY(user_id) references USERDETAIL_MST(user_id);
alter table user_follower add constraint fk_user_follower_id FOREIGN KEY(user_follower_id) references USERDETAIL_MST(user_id);

--------------------------------------
--user's whom logged in user follows
create table user_following(
  user_id varchar(100),
  user_following_id varchar(100),
  following_date date
);--table created

comment on column user_following.user_id is 'user who are logged in';
comment on column user_following.user_following_id is 'user whom logged in user is following';
comment on column user_following.following_date is 'when logged in user started following other user';

alter table user_following add constraint pk_user_following_id PRIMARY KEY(user_id,user_following_id);
alter table user_following add constraint fk_user_id_foling FOREIGN KEY(user_id) references USERDETAIL_MST(user_id);
alter table user_following add constraint fk_user_following_id_ck FOREIGN KEY(user_following_id) references USERDETAIL_MST(user_id);

----------------------------
create table user_band_following(
  user_id varchar(100),
  band_id varchar(100),
  artist_id varchar(100),
  following_date date
);--table created

comment on column user_band_following.user_id is 'user id of logged in';
comment on column user_band_following.band_id is 'band who he is following';
comment on column user_band_following.artist_id is 'artist who he is following';
comment on column user_band_following.following_date is 'when he started following';

alter table user_band_following add constraint pk_user_artist_id PRIMARY KEY(user_id,artist_id);
alter table user_band_following add constraint fk_user_id_band_fol FOREIGN KEY(user_id) references USERDETAIL_MST(user_id);
alter table user_band_following add constraint fk_artist_id_band_fol FOREIGN KEY(artist_id) references ARTIST_DETAIL_MST(artist_id);

-------------------------------
create table user_music_following(
  user_id varchar(100),
  music_category_id number(3),
  music_subcategory_id number(3),
  following_date date
);--table created

comment on column user_music_following.user_id is 'user id of logged in';
comment on column user_music_following.music_category_id is 'category who he is following';
comment on column user_music_following.music_subcategory_id is 'music subcategory who he is following';
comment on column user_music_following.following_date is 'when he started following';

alter table user_music_following add constraint pk_user_category_id PRIMARY KEY(user_id,music_category_id,music_subcategory_id);
alter table user_music_following add constraint fk_user_id_music_fol FOREIGN KEY(user_id) references USERDETAIL_MST(user_id);
alter table user_music_following add constraint fk_music_category_id_music_fol FOREIGN KEY(music_category_id) references MUSIC_CATEGORY_MST(music_category_id);
alter table user_music_following add constraint fk_music_subcat_id_music_fol FOREIGN KEY(music_subcategory_id) references MUSIC_SUBCATEGORY_MST(music_subcategory_id);

--------------------------------------
create table error_log(
  error_code      NUMBER(5),
  error_msg       VARCHAR(500),
  subprogram_name VARCHAR(100),
  time_logged     TIMESTAMP
  );--table created

alter table error_log 
add constraint pk_time_logged PRIMARY KEY(time_logged);

alter table error_log 
modify time_logged varchar(100);
-------------------------------------------
create table login_mst(
  username    VARCHAR(100),
  upassword   VARCHAR(100),
  login_time  VARCHAR(100),
  logout_time VARCHAR(100)
);--table created

alter table login_mst add constraint pk_username PRIMARY KEY(username);
alter table login_mst add constraint fk_username FOREIGN KEY(username) REFERENCES USERDETAIL_MST(user_id);
----------------------- TABLE CREATION- END ------------------------------

----------**********************-------------------------***************************---------------------********************--------------------

----------------------- PROCEDURE CREATION- START ----------------------------------------
create or replace procedure error_log_insert_proc(p_in_errcode          IN  error_log.error_code%TYPE,
                                                  p_in_errMsg           IN  error_log.error_msg%TYPE,
                                                  p_in_subprogram_name  IN  error_log.subprogram_name%TYPE)
/*---------------------------------------------------------------
Purpose:  Procedure to insert into error_log table
Input:    p_in_errcode NUMBER(5), p_in_errMsg VARCHAR(100), p_in_subprogram_name VARCHAR(100)
Output:  
-----------------------------------------------------------------*/                                                  
IS
  v_time_logged error_log.time_logged%TYPE;
BEGIN
  begin
    select  to_char(CURRENT_TIMESTAMP,'YYYY-MM-DD HH24:MI:SS') 
    into    v_time_logged
    from    dual;
  exception
    when others
    then  dbms_output.put_line('Error occured while fetching timestamp');
  end;
  
  insert into error_log(error_code,error_msg,subprogram_name,time_logged)
  values (p_in_errcode,p_in_errMsg,p_in_subprogram_name,v_time_logged);
  commit;
EXCEPTION
  WHEN OTHERS
  THEN dbms_output.put_line('Error occured while insertinf into error_log table');
END error_log_insert_proc;

----------------------------------------------------------------------------------

create or replace procedure sys_login_proc( p_in_username     IN  login_mst.username%TYPE, 
                                            p_in_upassword    IN  LOGIN_MST.UPASSWORD%TYPE, 
                                            p_out_isArtist    OUT USERDETAIL_MST.isArtist%TYPE,
                                            p_out_fname       OUT USERDETAIL_MST.user_firstname%TYPE,
                                            p_out_errMsg      OUT error_log.error_msg%TYPE
                                            )
/*---------------------------------------------------------------
Purpose:  Procedure to authenticate login credentials
Input:    p_in_username VARCHAR, p_in_upassword VARCHAR
Output:   p_out_userFName VARCHAR, p_out_errMsg VARCHAR
-----------------------------------------------------------------*/
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_count     NUMBER(1):=0; --to check if username and password exist
  v_time_logged error_log.time_logged%TYPE;
BEGIN
  begin
    select  to_char(CURRENT_TIMESTAMP,'YYYY-MM-DD HH24:MI:SS') 
    into    v_time_logged
    from    dual;
    
    select count(username)
    into v_count
    from login_mst 
    where username=p_in_username
    and upassword=p_in_upassword;
  exception
    when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'sys_login_proc');
  end;
  if v_count = 1
  then
    update login_mst
    set login_time=v_time_logged
    where username=p_in_username
    and upassword=p_in_upassword;
    
    select isArtist,user_firstname
    into p_out_isArtist,p_out_fname
    from USERDETAIL_MST
    where user_id=p_in_username;
  end if;
EXCEPTION
  when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'sys_login_proc-->'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'sys_login_proc');
END sys_login_proc;
-----------------------------------------------------------------------

create or replace procedure USERSIGNUP_INSERT_PROC( p_in_userid         IN  USERDETAIL_MST.user_id%TYPE,
                                                    p_in_user_firstname IN  USERDETAIL_MST.user_firstname%TYPE,
                                                    p_in_user_lastname  IN  USERDETAIL_MST.user_lastname%TYPE,
                                                    p_in_phone_number   IN  USERDETAIL_MST.phone_number%TYPE,
                                                    p_in_user_dob       IN  USERDETAIL_MST.user_dob%TYPE,
                                                    p_in_user_city      IN  USERDETAIL_MST.user_city%TYPE,
                                                    p_in_user_state     IN  USERDETAIL_MST.user_state%TYPE,
                                                    p_in_isArtist       IN  USERDETAIL_MST.isArtist%TYPE,
                                                    p_in_password       IN  LOGIN_MST.UPASSWORD%TYPE,
                                                    p_out_errMsg        OUT error_log.error_msg%TYPE
                                                  )
/*---------------------------------------------------------------
Purpose:  Procedure to insert value for new user who have sign up, it also calls insert for LOGIN_MST since the newly created username
          and password should be stored for later authorization
Input:    p_in_userid, p_in_user_firstname, p_in_user_lastname, p_in_phone_number, p_in_user_dob, p_in_user_city, p_in_user_state,
          p_in_isArtist, p_in_password
Output:   p_out_errMsg VARCHAR
-----------------------------------------------------------------*/
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  insert into USERDETAIL_MST(user_id,user_firstname,user_lastname,phone_number,user_dob,user_city,user_state,isArtist,turst_score,account_create_date)
  values(p_in_userid,p_in_user_firstname,p_in_user_lastname,p_in_phone_number,p_in_user_dob,p_in_user_city,p_in_user_state,p_in_isArtist,0,sysdate);
  commit;
  LOGIN_MST_INSERT_PROC(p_in_userid,p_in_password,p_out_errMsg);
EXCEPTION
  when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'USERSIGNUP_INSERT_PROC-->'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'USERSIGNUP_INSERT_PROC');
END USERSIGNUP_INSERT_PROC; 

---------------------------------------------------------------------------------

create or replace procedure LOGIN_MST_INSERT_PROC(  p_in_username     IN  login_mst.username%TYPE, 
                                                    p_in_upassword    IN  LOGIN_MST.UPASSWORD%TYPE, 
                                                    p_out_errMsg      OUT error_log.error_msg%TYPE
                                                  )
/*---------------------------------------------------------------
Purpose:  Procedure to insert value into LOGIN_MST when new user sign's up
Input:    p_in_username,p_in_upassword
Output:   p_out_errMsg VARCHAR
-----------------------------------------------------------------*/
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_time_logged error_log.time_logged%TYPE;
BEGIN
  select  to_char(CURRENT_TIMESTAMP,'YYYY-MM-DD HH24:MI:SS') 
  into    v_time_logged
  from    dual;
  
  insert into LOGIN_MST(username,upassword,login_time,logout_time)
  values(p_in_username,p_in_upassword,v_time_logged,null);
  commit;
EXCEPTION
  when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'LOGIN_MST_INSERT_PROC-->'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'LOGIN_MST_INSERT_PROC');
END LOGIN_MST_INSERT_PROC;
--------------------------------------------------------------------------

create or replace procedure POPULATE_MUSIC_CAT_PROC(  p_in_categoryID           IN  MUSIC_CATEGORY_MST.music_category_id%TYPE,
                                                      p_out_categoryName        OUT MUSIC_CATEGORY_MST.category_name%TYPE,
                                                      p_out_cat_description     OUT MUSIC_CATEGORY_MST.music_description%TYPE,
                                                      p_out_subCategory_Cursor  OUT SYS_REFCURSOR,
                                                      p_out_errMsg              OUT error_log.error_msg%TYPE
                                                    )
/*---------------------------------------------------------------
Purpose:  Procedure to populate music category, which internally calls to populate subcategory
Input:    p_in_categoryID
Output:   p_out_categoryName,p_out_cat_description,p_out_subCategory_Cursor,p_out_errMsg
-----------------------------------------------------------------*/ 
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  /*CURSOR sub_category_cursor
  IS
    select *
    from MUSIC_SUBCATEGORY_MST
    where music_category_id=p_in_categoryID;*/
BEGIN
  begin
    select  category_name,music_description
    into    p_out_categoryName,p_out_cat_description
    from    MUSIC_CATEGORY_MST
    where   music_category_id=p_in_categoryID;
  exception
    when others
      then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'POPULATE_MUSIC_CAT_PROC-->Error while fetching category details'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'POPULATE_MUSIC_CAT_PROC');
  end;
  
  /*open      sub_category_cursor;
  fetch     sub_category_cursor
  into      p_out_subCategory_Cursor;
  exit when sub_category_cursor%NOTFOUND;
  close     sub_category_cursor;*/
  
  open p_out_subCategory_Cursor
  for 
    select music_subcategory_id,music_category_id,subcategory_name,subcategory_description
    from MUSIC_SUBCATEGORY_MST
    where music_category_id= p_in_categoryID;
  
EXCEPTION
  when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'POPULATE_MUSIC_CAT_PROC-->'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'POPULATE_MUSIC_CAT_PROC');
END POPULATE_MUSIC_CAT_PROC;
-----------------------------------------------------------------------------------------------------------------
create or replace procedure user_music_foll_insert_proc( p_in_user_id              IN  user_music_following.user_id%TYPE,
                                                              p_in_music_category_id    IN  user_music_following.music_category_id%TYPE,
                                                              p_in_music_subcategory_id IN  user_music_following.music_subcategory_id%TYPE,
                                                              p_out_errMsg              OUT error_log.error_msg%TYPE
                                                            )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to insert music category and subcategory which user is liking into user_music_following table
Input:    p_in_user_id,p_in_music_category_id,p_in_music_subcategory_id
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_dummy     NUMBER(2):=0;
BEGIN
  select count(1)
  into   v_dummy
  from   user_music_following
  where  user_id=p_in_user_id
  and    music_category_id=p_in_music_category_id
  and    music_subcategory_id=p_in_music_subcategory_id;
  
  if v_dummy=0
  then
    insert into user_music_following(user_id,music_category_id,music_subcategory_id,following_date)
    values(p_in_user_id,p_in_music_category_id,p_in_music_subcategory_id,sysdate);
    commit;
  end if;  
EXCEPTION
  when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'user_music_foll_insert_proc-->'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'user_music_foll_insert_proc');
END user_music_foll_insert_proc;
---------------------------------------------------------------------------------------------

create or replace procedure FETCH_RECCOM_USERS_ARTIST_PROC(  p_in_user_id              IN  user_music_following.user_id%TYPE,
                                                      p_in_music_category_id    IN  user_music_following.music_category_id%TYPE,
                                                      p_in_music_subcategory_id IN  user_music_following.music_subcategory_id%TYPE,
                                                      p_out_userList_Cursor     OUT SYS_REFCURSOR,
                                                      p_out_artistList_Cursor   OUT SYS_REFCURSOR,
                                                      p_out_errMsg              OUT error_log.error_msg%TYPE
                                                    )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to fetch recommended user's once, new user sign up based on category anf sub-category id
Input:    p_in_user_id,p_in_music_category_id,p_in_music_subcategory_id
Output:   p_out_userList_Cursor,p_out_artistList_Cursor,p_out_errMsg
--------------------------------------------------------------------------------------------------*/ 
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_subCat    user_music_following.music_subcategory_id%TYPE := NVL(p_in_music_subcategory_id,0);
BEGIN
  begin
    if v_subCat<>0
    then
      open p_out_userList_Cursor
      for
        select user_id,user_firstname,user_lastname,user_dob,user_city,user_state
        from USERDETAIL_MST
        where user_id<>p_in_user_id --should not give userid of logged in user
        and user_id IN (select unique(user_id)
                          from user_music_following
                          where music_category_id = p_in_music_category_id
                          and music_subcategory_id = p_in_music_subcategory_id); 
    else
      open p_out_userList_Cursor
      for
        select user_id,user_firstname,user_lastname,user_dob,user_city,user_state
        from USERDETAIL_MST
        where user_id<>p_in_user_id --should not give userid of logged in user
        and user_id IN (select unique(user_id)
                          from user_music_following
                          where music_category_id = p_in_music_category_id); 
    end if;  
  exception
    when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'FETCH_RECCOM_USERS_ARTIST_PROC-->error while fetching p_out_userList_Cursor:: '||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'FETCH_RECCOM_USERS_ARTIST_PROC');
  end;
  
  if v_subCat<>0
    then
      open p_out_artistList_Cursor
      for
        select user_id,user_firstname,user_lastname,user_dob,user_city,user_state
        from USERDETAIL_MST,( select artist_id,band_id,band_name
                              from ARTIST_DETAIL_MST asm
                              where music_category_id=p_in_music_category_id
                              and music_subcategory_id=p_in_music_subcategory_id)res
        where user_id IN res.artist_id;
  else
    open p_out_artistList_Cursor
      for
        select user_id,user_firstname,user_lastname,user_dob,user_city,user_state
        from USERDETAIL_MST,( select artist_id,band_id,band_name
                              from ARTIST_DETAIL_MST asm
                              where music_category_id=p_in_music_category_id)res
        where user_id IN res.artist_id; 
  end if;  
  
EXCEPTION
  when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'FETCH_RECCOM_USERS_ARTIST_PROC-->'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'FETCH_RECCOM_USERS_ARTIST_PROC');
END FETCH_RECCOM_USERS_ARTIST_PROC;
-----------------------------------------------------------------------------------------

create or replace procedure USER_FOLLOWING_INSERT_PROC( p_in_loggedin_user_id   IN  user_following.user_id%TYPE,
                                                        p_in_user_id_following  IN  user_following.user_following_id%TYPE,
                                                        p_out_errMsg            OUT error_log.error_msg%TYPE
                                                      )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to insert into user_following table to store userid whom loggedin user is following 
Input:    p_in_loggedin_user_id[ID of user who is logged in] ,p_in_user_id_following [ID of users whom logged in user is following]
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/         
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  insert into user_following(user_id,user_following_id,following_date)
  values(p_in_loggedin_user_id,p_in_user_id_following,sysdate);
  commit;
EXCEPTION
  when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'USER_FOLLOWING_INSERT_PROC-->'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'USER_FOLLOWING_INSERT_PROC');
END USER_FOLLOWING_INSERT_PROC;
-------------------------------------------------------------------------------------------------

create or replace procedure USER_BAND_FOLLOW_INSERT_PROC( p_in_loggedin_user_id    IN  user_band_following.user_id%TYPE,
                                                            p_in_band_id_following    IN  user_band_following.band_id%TYPE,
                                                            p_in_artist_id_following  IN  user_band_following.artist_id%TYPE,
                                                            p_out_errMsg              OUT error_log.error_msg%TYPE
                                                            )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to insert into user_following table to store userid whom loggedin user is following 
Input:    p_in_loggedin_user_id[ID of user who is logged in] ,p_in_band_id_following [ID of band whom logged in user is following],
          p_in_artist_id_following [ID of artist whom logged in user is following]
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/         
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_dummy    NUMBER(2):=0;
BEGIN
  select count(1)
  into  v_dummy
  from  user_band_following
  where user_id=p_in_loggedin_user_id
  and artist_id=p_in_artist_id_following;
  
  if v_dummy=0
  then
    insert into user_band_following(user_id,band_id,artist_id,following_date)
    values(p_in_loggedin_user_id,p_in_band_id_following,p_in_artist_id_following,sysdate);
    commit;
  end if;  
EXCEPTION
  when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'USER_BAND_FOLLOW_INSERT_PROC-->'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'USER_BAND_FOLLOW_INSERT_PROC');
END USER_BAND_FOLLOW_INSERT_PROC;
-----------------------------------------------------------------------------------

create or replace procedure FETCH_USERDATA_PROC(p_in_userid           IN  USERDETAIL_MST.user_id%TYPE,
                                                p_out_user_firstname  OUT USERDETAIL_MST.user_firstname%TYPE,
                                                p_out_user_lastname   OUT USERDETAIL_MST.user_lastname%TYPE,
                                                p_out_phone_number    OUT USERDETAIL_MST.phone_number%TYPE,
                                                p_out_user_dob        OUT USERDETAIL_MST.user_dob%TYPE,
                                                p_out_city            OUT USERDETAIL_MST.user_city%TYPE,
                                                p_out_state           OUT USERDETAIL_MST.user_state%TYPE,
                                                p_out_password        OUT LOGIN_MST.UPASSWORD%TYPE,
                                                p_out_errMsg          OUT error_log.error_msg%TYPE
                                               )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to fetch userdata 
Input:    p_in_userid
Output:   p_out_user_firstname,p_out_user_lastname,p_out_phone_number,p_out_user_dob,p_out_city,p_out_state,p_out_errMsg
--------------------------------------------------------------------------------------------------*/
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  select user_firstname,user_lastname,phone_number,user_dob,user_city,user_state
  into   p_out_user_firstname,p_out_user_lastname,p_out_phone_number,p_out_user_dob,p_out_city,p_out_state
  from   USERDETAIL_MST
  where  user_id like p_in_userid;
  
  select UPASSWORD
  into   p_out_password
  from   LOGIN_MST
  where  USERNAME like p_in_userid;
EXCEPTION
  when no_data_found
  then
    p_out_errMsg:='No data fetched from FETCH_USERDATA_PROC for user_id'||p_in_userid;
  when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'FETCH_USERDATA_PROC-->'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'FETCH_USERDATA_PROC');
END FETCH_USERDATA_PROC;
-----------------------------------------------------------------------------------

create or replace procedure UPDATE_USERDATA_PROC( p_in_userid         IN  USERDETAIL_MST.user_id%TYPE,
                                                  p_in_user_firstname IN  USERDETAIL_MST.user_firstname%TYPE,
                                                  p_in_user_lastname  IN  USERDETAIL_MST.user_lastname%TYPE,
                                                  p_in_phone_number   IN  USERDETAIL_MST.phone_number%TYPE,
                                                  p_in_user_dob       IN  USERDETAIL_MST.user_dob%TYPE,
                                                  p_in_user_city      IN  USERDETAIL_MST.user_city%TYPE,
                                                  p_in_user_state     IN  USERDETAIL_MST.user_state%TYPE,
                                                  p_in_isArtist       IN  USERDETAIL_MST.isArtist%TYPE,
                                                  p_in_password       IN  LOGIN_MST.UPASSWORD%TYPE,
                                                  p_out_errMsg        OUT error_log.error_msg%TYPE
                                                )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to update userdata 
Input:    p_in_userid,p_in_user_firstname
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/         
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_old_password  LOGIN_MST.UPASSWORD%TYPE;
BEGIN
  begin
    update USERDETAIL_MST
    set user_firstname  = p_in_user_firstname,
        user_lastname   = p_in_user_lastname,
        phone_number    = p_in_phone_number,
        user_dob        = p_in_user_dob,
        user_city       = p_in_user_city,
        user_state      = p_in_user_state
    where user_id = p_in_userid;
  exception
    when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'UPDATE_USERDATA_PROC-->error while updating USERDETAIL_MST for userid:'||p_in_userid||'---'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'UPDATE_USERDATA_PROC');
   end;
   
   select UPASSWORD
   into   v_old_password
   from   LOGIN_MST
   where USERNAME like p_in_userid;
   
   if v_old_password<>p_in_password
   then
    update LOGIN_MST
    set UPASSWORD=p_in_password
    where USERNAME like p_in_userid;
   end if;
   commit;
EXCEPTION
  when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'UPDATE_USERDATA_PROC-->for userid:'||p_in_userid||'---'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'UPDATE_USERDATA_PROC');
END UPDATE_USERDATA_PROC;
------------------------------------------------------------------------------------------

create or replace procedure FETCH_USER_FOLLOWING_PROC(p_in_userid               IN  USERDETAIL_MST.user_id%TYPE,
                                                        p_out_userdetail_cursor OUT SYS_REFCURSOR,
                                                        p_out_errMsg            OUT error_log.error_msg%TYPE
                                                      )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to fetch list of users following logged in user
Input:    p_in_userid,p_in_user_firstname
Output:   p_out_userdetail_cursor,p_out_errMsg
--------------------------------------------------------------------------------------------------*/     
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  open p_out_userdetail_cursor
  for
    select user_id,user_firstname,user_lastname,phone_number,user_dob,user_city,user_state
    from USERDETAIL_MST udm,( select unique(user_id) as userid
                              from user_following
                              where user_following_id = p_in_userid) res
    where udm.user_id IN res.userid;
EXCEPTION
  when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := 'FETCH_USER_FOLLOWING_PROC-->for userid:'||p_in_userid||'---'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'FETCH_USER_FOLLOWING_PROC');
END FETCH_USER_FOLLOWING_PROC;
-------------------------------------------------------------------------------------

create or replace procedure UNFOLLLOW_USER_PROC(  p_in_loggedin_user    IN  user_following.user_following_id%TYPE,
                                                  p_in_userid_unfollow  IN  user_following.user_id%TYPE,
                                                  p_out_errMsg          OUT error_log.error_msg%TYPE
                                                )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to unfollow selected user
Input:    p_in_loggedin_user,p_in_userid_unfollow
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/                                       
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_count     NUMBER(3):=0;
BEGIN
  begin
    select count(1)
    into   v_count
    from  user_following
    where user_following_id like p_in_loggedin_user
    and  user_id like p_in_userid_unfollow;
  exception
    when no_data_found
    then
      p_out_errMsg:='No data found for loggedinuserid:'||p_in_loggedin_user||' and selected userid to unfollow:'||p_in_userid_unfollow;
  end;
  
  if v_count=1
  then
    delete from user_following
    where user_following_id like p_in_loggedin_user
    and  user_id like p_in_userid_unfollow;
  else
    p_out_errMsg:= p_out_errMsg||'more then one row found for loggedinuserid:'||p_in_loggedin_user||' and selected userid to unfollow:'||p_in_userid_unfollow;
  end if;  
EXCEPTION
  when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := p_out_errMsg||'UNFOLLLOW_USER_PROC-->for userid:'||p_in_loggedin_user||'---'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'UNFOLLLOW_USER_PROC');
END UNFOLLLOW_USER_PROC;  
--------------------------------------------------------------------------------------

create or replace procedure SHOW_ARTIST_USER_FOLLOW_PROC( p_in_loggedin_userid   IN  USERDETAIL_MST.user_id%TYPE,
                                                          p_out_artist_cursor     OUT SYS_REFCURSOR,
                                                          p_out_userfollow_cursor OUT SYS_REFCURSOR,
                                                          p_out_errMsg            OUT error_log.error_msg%TYPE
                                                        )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to show artists and users logged in user is following
Input:    p_in_logggedin_userid
Output:   p_out_artist_cursor[details of artist he's liking], p_out_userfollow_cursor[details of user he's liking], p_out_errMsg
--------------------------------------------------------------------------------------------------*/
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  begin
    open p_out_artist_cursor
    for
      select user_id,user_firstname,user_lastname,phone_number,user_dob,user_city,user_state
      from USERDETAIL_MST udm,( select unique(artist_id) as artist_id 
                                from user_band_following
                                where user_id=p_in_loggedin_userid) res
      where  udm.user_id IN res.artist_id;
   exception
    when others
    then
        v_errcode   := SQLCODE;
        v_errormsg  := SQLERRM;
        p_out_errMsg := p_out_errMsg||'SHOW_ARTIST_USER_FOLLOW_PROC-->p_out_artist_cursor->for userid:'||p_in_loggedin_userid||'---'||v_errcode||v_errormsg;
        error_log_insert_proc(v_errcode,v_errormsg,'SHOW_ARTIST_USER_FOLLOW_PROC');
   end;
   
   open p_out_userfollow_cursor
   for
    select user_id,user_firstname,user_lastname,phone_number,user_dob,user_city,user_state
    from USERDETAIL_MST udm,( select unique(user_following_id) as userid
                              from user_following
                              where  user_id= p_in_loggedin_userid) res
    where udm.user_id IN res.userid;
EXCEPTION
 when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'SHOW_ARTIST_USER_FOLLOW_PROC-->for userid:'||p_in_loggedin_userid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'SHOW_ARTIST_USER_FOLLOW_PROC');
END SHOW_ARTIST_USER_FOLLOW_PROC;
-----------------------------------------------------------------------------------------------------

create or replace procedure UNFOLLOW_ARTIST_USER_PROC(  p_in_loggedin_userid    IN  USERDETAIL_MST.user_id%TYPE,
                                                        p_in_userid_unfollow    IN  USERDETAIL_MST.user_id%TYPE,                                                        
                                                        p_out_errMsg            OUT error_log.error_msg%TYPE
                                                      )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to unfollow/delete users whom logged in user is following
Input:    p_in_loggedin_userid,p_in_userid_unfollow[user id of user to be unfollowed]
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/ 
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_count     number(3):=0;
BEGIN
  begin
    select count(1)
    into  v_count
    from  user_following
    where user_id=p_in_loggedin_userid
    and   user_following_id=p_in_userid_unfollow;
  exception
    when no_data_found
    then
      p_out_errMsg:='UNFOLLOW_ARTIST_USER_PROC-->No data fetched for userid:'||p_in_loggedin_userid||'  userUnFolloeid:'||p_in_userid_unfollow;
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      error_log_insert_proc(v_errcode,v_errormsg,'UNFOLLOW_ARTIST_USER_PROC');
  end;
  
  begin
    if v_count=1
    then
      delete from user_following
      where user_id=p_in_loggedin_userid
      and   user_following_id=p_in_userid_unfollow;
    end if;
  exception
    when others
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := p_out_errMsg||'UNFOLLOW_ARTIST_USER_PROC-->while deleting for userid:'||p_in_loggedin_userid||'---'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'UNFOLLOW_ARTIST_USER_PROC');
  end;
  commit;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'UNFOLLOW_ARTIST_USER_PROC-->for userid:'||p_in_loggedin_userid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'UNFOLLOW_ARTIST_USER_PROC');
END UNFOLLOW_ARTIST_USER_PROC;
------------------------------------------------------------------------------------------

create or replace procedure UNFOLLOW_ARTIST_PROC( p_in_loggedin_userid    IN  USERDETAIL_MST.user_id%TYPE,
                                                  p_in_artistid_unfollow  IN  ARTIST_DETAIL_MST.artist_id%TYPE,
                                                  p_out_errMsg            OUT error_log.error_msg%TYPE
                                                )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to unfollow/delete artist whom logged in user is following
Input:    p_in_loggedin_userid,p_in_artistid_unfollow[user id of artist to be unfollowed]
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/      
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_count     number(3):=0;
BEGIN
   begin
    if p_in_artistid_unfollow is not null
    then
      select count(1)
      into   v_count
      from   user_band_following
      where  user_id=p_in_loggedin_userid
      and    artist_id= p_in_artistid_unfollow;
  
    if v_count=1
    then 
      delete from user_band_following
      where user_id=p_in_loggedin_userid
      and    artist_id= p_in_artistid_unfollow;
    end if;
    end if;
  exception
    when no_data_found
    then
      v_errcode   := SQLCODE;
      v_errormsg  := SQLERRM;
      p_out_errMsg := p_out_errMsg||'UNFOLLOW_ARTIST_PROC-->no data found for userid:'||p_in_loggedin_userid||'--- artist id:'||p_in_artistid_unfollow||'----'||v_errcode||v_errormsg;
      error_log_insert_proc(v_errcode,v_errormsg,'UNFOLLOW_ARTIST_PROC');
  end;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'UNFOLLOW_ARTIST_PROC-->for userid:'||p_in_loggedin_userid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'UNFOLLOW_ARTIST_PROC');
END UNFOLLOW_ARTIST_PROC;
------------------------------------------------------------------------------------------

create or replace procedure SHOW_UPCOMING_CONCERT_PROC( p_in_logged_userid      IN  USERDETAIL_MST.user_id%TYPE,
                                                        p_out_artistcon_cursor  OUT  SYS_REFCURSOR,
                                                        p_out_musiccon_cur      OUT  SYS_REFCURSOR,
                                                        p_out_errMsg            OUT error_log.error_msg%TYPE
                                                      )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to fetch upcoming concerts based on artist, music and user's the logged in user is following
Input:    p_in_logged_userid,p_out_artistcon_cursor[cursor based on artist he's liking],
          p_out_musiccon_cur[cursor based on music cat and sub_category he's liking],         
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/                                                       
IS
   v_errcode   error_log.error_code%TYPE:=NULL;
    v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  begin
    open p_out_artistcon_cursor
    for
      select concert_id,cdm.artist_id,cdm.band_id,concert_venue_city,concert_venue_state,concert_price,concert_time,concert_date,concert_name,cdm.music_category_id,cdm.music_subcategory_id,total_tickets,category_name,subcategory_name 
      from CONCERT_DETAIL_MST cdm,(select unique(artist_id) as artist_id
                                    from user_band_following
                                    where user_id=p_in_logged_userid) res1, MUSIC_SUBCATEGORY_MST msm, MUSIC_CATEGORY_MST mcm
      where concert_date >=sysdate
      and cdm.artist_id IN res1.artist_id
      and cdm.music_category_id=mcm.music_category_id
      and cdm.music_subcategory_id=msm.music_subcategory_id
      and cdm.concert_id not in(select unique(ca.concert_id) from concert_attendee ca where ca.user_id=p_in_logged_userid);
  exception
    when no_data_found
    then
      p_out_errMsg:='No concerts found based on artist liking for userid:'||p_in_logged_userid;
    when others
    then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'SHOW_UPCOMING_CONCERT_PROC-->p_out_artistcon_cursor-->for userid:'||p_in_logged_userid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'SHOW_UPCOMING_CONCERT_PROC');  
  end;
  
  open p_out_musiccon_cur
  for
    select concert_id,cdm.artist_id,cdm.band_id,concert_venue_city,concert_venue_state,concert_price,concert_time,concert_date,concert_name,cdm.music_category_id,cdm.music_subcategory_id,total_tickets,category_name,subcategory_name  
    from CONCERT_DETAIL_MST cdm,(select music_category_id,music_subcategory_id
                              from user_music_following
                              where user_id=p_in_logged_userid) res1, MUSIC_SUBCATEGORY_MST msm, MUSIC_CATEGORY_MST mcm
    where concert_date >=sysdate
    and (cdm.music_category_id IN res1.music_category_id
    or cdm.music_subcategory_id IN res1.music_subcategory_id)
    and cdm.music_category_id=mcm.music_category_id
    and cdm.music_subcategory_id=msm.music_subcategory_id
    and cdm.concert_id not in(select unique(ca.concert_id) from concert_attendee ca where ca.user_id=p_in_logged_userid);
EXCEPTION
  when no_data_found
  then
    p_out_errMsg:=p_out_errMsg||'No concerts found based on music liking for userid:'||p_in_logged_userid;
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'SHOW_UPCOMING_CONCERT_PROC-->for userid:'||p_in_logged_userid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'SHOW_UPCOMING_CONCERT_PROC');
END SHOW_UPCOMING_CONCERT_PROC;
-----------------------------------------------------------------------------------------------------------

create or replace procedure concert_attendee_insert_proc( p_in_concertid        IN concert_attendee.concert_id%TYPE,
                                                          p_in_logged_userid    IN concert_attendee.user_id%TYPE,
                                                          p_out_errMsg          OUT error_log.error_msg%TYPE
                                                        )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to insert into concert_attendee table
Input:    p_in_concertid, p_in_logged_userid        
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/     
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  insert into concert_attendee(concert_id,user_id,concert_review,concert_rating,review_time)
  values(p_in_concertid,p_in_logged_userid,null,null,null);
  commit;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'concert_attendee_insert_proc-->for userid:'||p_in_logged_userid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'concert_attendee_insert_proc');
END concert_attendee_insert_proc;
-------------------------------------------------------------------------------------------------

create or replace procedure SHOW_CONCERTS_ATTENDED_PROC(  p_in_logged_userid      IN concert_attendee.user_id%TYPE,
                                                          p_out_concert_attended  OUT SYS_REFCURSOR,
                                                          p_out_errMsg          OUT error_log.error_msg%TYPE
                                                        )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to display concerts attended by user
Input:    p_in_logged_userid    
Output:   p_out_concert_attended[cursor holding list of concerts],p_out_errMsg
--------------------------------------------------------------------------------------------------*/       
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  open p_out_concert_attended
  for
    select concert_id,cdm.artist_id,cdm.band_id,concert_venue_city,concert_venue_state,concert_price,concert_time,concert_date,concert_name,cdm.music_category_id,cdm.music_subcategory_id,total_tickets,category_name,subcategory_name 
    from CONCERT_DETAIL_MST cdm, MUSIC_SUBCATEGORY_MST msm, MUSIC_CATEGORY_MST mcm
    where concert_id IN (select concert_id from concert_attendee where user_id=p_in_logged_userid)
    and cdm.music_category_id=mcm.music_category_id
    and cdm.music_subcategory_id=msm.music_subcategory_id
    and cdm.concert_date<=sysdate;
EXCEPTION
  when no_data_found
  then
    p_out_errMsg:='SHOW_CONCERTS_ATTENDED_PROC-->no data found for userid:'||p_in_logged_userid;
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'SHOW_CONCERTS_ATTENDED_PROC-->for userid:'||p_in_logged_userid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'SHOW_CONCERTS_ATTENDED_PROC');
END SHOW_CONCERTS_ATTENDED_PROC;
--------------------------------------------------------------------------------------------

create or replace procedure FETCH_PREVIOUS_POST(p_in_concertid  IN  concert_attendee.concert_id%TYPE,
                                                p_out_posts_cur OUT SYS_REFCURSOR,
                                                p_out_errMsg          OUT error_log.error_msg%TYPE
                                               )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to display display post and review of a particular concert
Input:    p_in_concertid    
Output:   p_out_posts_cur[cursor holding list of concerts_attended details ],p_out_errMsg
--------------------------------------------------------------------------------------------------*/     
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  open p_out_posts_cur
  for
    select concert_id,user_id,concert_review,concert_rating 
    from concert_attendee
    where concert_id=p_in_concertid;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'FETCH_PREVIOUS_POST-->for concertid:'||p_in_concertid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'FETCH_PREVIOUS_POST');
END FETCH_PREVIOUS_POST;
-----------------------------------------------------------------------------------------

create or replace procedure concert_attendee_insert_proc( p_in_concertid  IN  concert_attendee.concert_id%TYPE,
                                                          p_in_userid     IN  concert_attendee.user_id%TYPE,
                                                          p_in_review     IN  concert_attendee.concert_review%TYPE,
                                                          p_in_rating     IN  concert_attendee.concert_rating%TYPE,
                                                          p_out_errMsg    OUT error_log.error_msg%TYPE
                                                        )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to insert reviews and rating of concert for a concert
Input:    p_in_concertid,p_in_userid,p_in_review,p_in_rating
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/  
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_dummy   number(2):=0;
BEGIN
  select count(1)
  into v_dummy
  from concert_attendee
  where concert_id=p_in_concertid
  and user_id=p_in_userid;
  
  if v_dummy>0
  then
    update concert_attendee
    set   concert_review=p_in_review,
          concert_rating=p_in_rating,
          review_time=sysdate
    where concert_id=p_in_concertid
    and   user_id=p_in_userid; 
    commit; 
  elsif v_dummy=0
  then
    insert into concert_attendee(concert_id,user_id,concert_review,concert_rating,review_time)
    values(p_in_concertid,p_in_userid,p_in_review,p_in_rating,sysdate);
    commit;  
  end if;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'concert_attendee_insert_proc-->for concertid:'||p_in_concertid||'---userid'||p_in_userid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'concert_attendee_insert_proc');
END concert_attendee_insert_proc;
---------------------------------------------------------------------------------------------

create or replace procedure LOGOUT_PROC(p_in_userid IN  USERDETAIL_MST.user_id%TYPE)
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to logout a user
Input:    p_in_userid
Output:   
--------------------------------------------------------------------------------------------------*/
is
  v_time_logged varchar(100);
begin
  select  to_char(CURRENT_TIMESTAMP,'YYYY-MM-DD HH24:MI:SS') 
  into    v_time_logged
  from    dual;
  
  update LOGIN_MST
  set logout_time=v_time_logged
  where username=p_in_userid;
end LOGOUT_PROC;  
------------------------------------------------------------------------------------------

create or replace procedure FETCH_ARTIST_DETAIL_PROC( p_in_artistid               IN  ARTIST_DETAIL_MST.artist_id%TYPE,
                                                      p_out_bandid                OUT ARTIST_DETAIL_MST.band_id%TYPE,
                                                      p_out_band_name             OUT ARTIST_DETAIL_MST.band_name%TYPE,
                                                      p_out_band_create_date      OUT ARTIST_DETAIL_MST.band_create_date%TYPE,
                                                      p_out_music_category_id     OUT ARTIST_DETAIL_MST.music_category_id%TYPE,
                                                      p_out_music_subcategory_id  OUT ARTIST_DETAIL_MST.music_subcategory_id%TYPE,
                                                      p_out_category_name         OUT MUSIC_CATEGORY_MST.category_name%TYPE,
                                                      p_out_subcategory_name      OUT MUSIC_SUBCATEGORY_MST.subcategory_name%TYPE,
                                                      p_out_errMsg                OUT error_log.error_msg%TYPE
                                                    )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to fetch artist details
Input:    p_in_artistid
Output:   p_out_artistdetail_cur,p_out_errMsg
--------------------------------------------------------------------------------------------------*/
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_dummy     NUMBER(2):=0;
BEGIN
  begin
    select count(1)
    into v_dummy
    from ARTIST_DETAIL_MST
    where artist_id=p_in_artistid;
  exception
    when no_data_found
    then
      p_out_errMsg:='No data fetched for artist_id:'||p_in_artistid;
  end;
  
  if v_dummy=1
  then
      select band_id,band_name,band_create_date,adm.music_category_id,adm.music_subcategory_id,mcm.category_name,msm.subcategory_name
      into p_out_bandid,p_out_band_name,p_out_band_create_date,p_out_music_category_id,p_out_music_subcategory_id,p_out_category_name,p_out_subcategory_name
      from ARTIST_DETAIL_MST adm, MUSIC_SUBCATEGORY_MST msm, MUSIC_CATEGORY_MST mcm
      where artist_id=p_in_artistid
      and  adm.music_category_id=mcm.music_category_id
      and adm.music_subcategory_id=msm.music_subcategory_id;
  end if;    
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'FETCH_ARTIST_DETAIL_PROC-->for artist_id:'||p_in_artistid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'FETCH_ARTIST_DETAIL_PROC');
END FETCH_ARTIST_DETAIL_PROC;

--------------------------------------------------------------------------------------------------------------------------

create sequence concert_id_seq
start with 1300
increment by 1
nocache
nocycle;

create or replace procedure CONCERT_DETAIL_INSERT_PROC( p_in_artistid         IN  CONCERT_DETAIL_MST.artist_id%TYPE,
                                                        p_in_bandid           IN  CONCERT_DETAIL_MST.band_id%TYPE,
                                                        p_in_concertcity      IN  CONCERT_DETAIL_MST.concert_venue_city%TYPE,
                                                        p_in_concertstate     IN  CONCERT_DETAIL_MST.concert_venue_state%TYPE,
                                                        p_in_concertprice     IN  CONCERT_DETAIL_MST.concert_price%TYPE,
                                                        p_in_concertdate      IN  CONCERT_DETAIL_MST.concert_date%TYPE,
                                                        p_in_totalticket      IN  CONCERT_DETAIL_MST.total_tickets%TYPE,
                                                        p_in_concerttime      IN  CONCERT_DETAIL_MST.concert_time%TYPE,
                                                        p_in_music_cat_id     IN  CONCERT_DETAIL_MST.music_category_id%TYPE,
                                                        p_in_music_subcat_id  IN  CONCERT_DETAIL_MST.music_subcategory_id%TYPE,
                                                        p_in_concertname      IN  CONCERT_DETAIL_MST.concert_name%TYPE,
                                                        p_out_errMsg          OUT error_log.error_msg%TYPE
                                                      )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to add new concert detail
Input:    p_in_artistid,p_in_bandid,p_in_concertcity,p_in_concertstate
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/   
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  insert into CONCERT_DETAIL_MST(concert_id,artist_id,band_id,concert_venue_city,concert_venue_state,concert_price,concert_date,total_tickets,concert_time,music_category_id,music_subcategory_id,concert_name)
  values(concert_id_seq.NEXTVAL,p_in_artistid,p_in_bandid,p_in_concertcity,p_in_concertstate,p_in_concertprice,p_in_concertdate,p_in_totalticket,p_in_concerttime,p_in_music_cat_id,p_in_music_subcat_id,p_in_concertname);
  commit;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := 'CONCERT_DETAIL_INSERT_PROC-->for artist_id:'||p_in_artistid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'CONCERT_DETAIL_INSERT_PROC');
END CONCERT_DETAIL_INSERT_PROC;

------------------------------------------------------------------------------------------------

create or replace procedure SHOW_CNCRT_PRFMNG_PRFMD_PROC( p_in_artistid  IN  CONCERT_DETAIL_MST.artist_id%TYPE,
                                                          p_out_concertperfrmg_cur  OUT SYS_REFCURSOR,
                                                          p_out_concertperfrmd_cur  OUT SYS_REFCURSOR,
                                                          p_out_errMsg          OUT error_log.error_msg%TYPE
                                                        )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to show which concerts artist is performing and which he has performed
Input:    p_in_artistid
Output:   p_out_concertperfrmg_cur[list of concert artits will be performing],
          p_out_concertperfrmd_cur[list of concert artits has performed],p_out_errMsg
--------------------------------------------------------------------------------------------------*/ 
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  open p_out_concertperfrmg_cur
  for
    select concert_id,artist_id,band_id,CONCERT_VENUE_CITY,CONCERT_VENUE_STATE,CONCERT_PRICE,CONCERT_DATE,TOTAL_TICKETS,CONCERT_TIME,cdm.MUSIC_CATEGORY_ID,cdm.MUSIC_SUBCATEGORY_ID,CONCERT_NAME,mcm.category_name,msm.subcategory_name    
    from concert_detail_mst cdm, MUSIC_SUBCATEGORY_MST msm, MUSIC_CATEGORY_MST mcm 
    where artist_id=p_in_artistid
    and concert_date>=sysdate
    and  cdm.music_category_id=mcm.music_category_id
    and cdm.music_subcategory_id=msm.music_subcategory_id;
    
  open p_out_concertperfrmd_cur
  for
    select concert_id,artist_id,band_id,CONCERT_VENUE_CITY,CONCERT_VENUE_STATE,CONCERT_PRICE,CONCERT_DATE,TOTAL_TICKETS,CONCERT_TIME,cdm.MUSIC_CATEGORY_ID,cdm.MUSIC_SUBCATEGORY_ID,CONCERT_NAME,mcm.category_name,msm.subcategory_name    
    from concert_detail_mst cdm, MUSIC_SUBCATEGORY_MST msm, MUSIC_CATEGORY_MST mcm 
    where artist_id=p_in_artistid
    and concert_date<sysdate
    and  cdm.music_category_id=mcm.music_category_id
    and cdm.music_subcategory_id=msm.music_subcategory_id;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := 'SHOW_CNCRT_PRFMNG_PRFMD_PROC-->for artist_id:'||p_in_artistid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'SHOW_CNCRT_PRFMNG_PRFMD_PROC');
END SHOW_CNCRT_PRFMNG_PRFMD_PROC;
------------------------------------------------------------------------------------------------------

create or replace procedure EDIT_CONCERT_INFO_PROC(p_in_concert_id       IN  CONCERT_DETAIL_MST.concert_id%TYPE,
                                                  p_in_concertcity      IN  CONCERT_DETAIL_MST.concert_venue_city%TYPE,
                                                  p_in_concertstate     IN  CONCERT_DETAIL_MST.concert_venue_state%TYPE,
                                                  p_in_concertprice     IN  CONCERT_DETAIL_MST.concert_price%TYPE,
                                                  p_in_concertdate      IN  CONCERT_DETAIL_MST.concert_date%TYPE,
                                                  p_in_totalticket      IN  CONCERT_DETAIL_MST.total_tickets%TYPE,
                                                  p_in_concerttime      IN  CONCERT_DETAIL_MST.concert_time%TYPE,
                                                  p_in_concertname      IN  CONCERT_DETAIL_MST.concert_name%TYPE,
                                                  p_out_errMsg          OUT error_log.error_msg%TYPE
                                                  )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to update concert info details by artist
Input:    p_in_artistid
Output:   p_out_concertperfrmg_cur[list of concert artits will be performing],
          p_out_concertperfrmd_cur[list of concert artits has performed],p_out_errMsg
--------------------------------------------------------------------------------------------------*/
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_dummy     NUMBER(2):=0;
BEGIN
  begin
    select count(1)
    into v_dummy
    from concert_detail_mst
    where concert_id=p_in_concert_id;
  exception
    when no_data_found
    then
      p_out_errMsg:='EDIT_CONCERT_INFO_PROC--> No data found for concert_id:'||p_in_concert_id;
  end;
  
  if v_dummy=1
  then
    update CONCERT_DETAIL_MST
    set concert_venue_city=p_in_concertcity,
        concert_venue_state=p_in_concertstate,
        concert_price=p_in_concertprice,
        concert_date=p_in_concertdate,
        total_tickets=p_in_totalticket,
        concert_time=p_in_concerttime,
        concert_name=p_in_concertname
    where concert_id=p_in_concert_id;
    commit;
  end if;  
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'EDIT_CONCERT_INFO_PROC-->for concert_id:'||p_in_concert_id||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'EDIT_CONCERT_INFO_PROC');
END EDIT_CONCERT_INFO_PROC;
-----------------------------------------------------------------------------------------------------

create or replace procedure SHOW_USER_FOLLOW_ARTIST(p_in_artistid IN  user_band_following.artist_id%TYPE,
                                                    p_out_userfollow_cur  OUT SYS_REFCURSOR,
                                                    p_out_errMsg          OUT error_log.error_msg%TYPE
                                                    )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to fetch users who are following logged in artist
Input:    p_in_artistid
Output:   p_out_userfollow_cur[list of users following artist],p_out_errMsg
--------------------------------------------------------------------------------------------------*/      
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  open p_out_userfollow_cur
  for
    select user_id,user_firstname,user_lastname,phone_number,user_dob,user_city,user_state
    from USERDETAIL_MST udm,( select unique(user_id) as userid
                              from user_band_following
                              where artist_id = p_in_artistid) res
    where udm.user_id IN res.userid;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'SHOW_USER_FOLLOW_ARTIST-->for artist_id:'||p_in_artistid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'SHOW_USER_FOLLOW_ARTIST');
END SHOW_USER_FOLLOW_ARTIST;
---------------------------------------------------------------------------------------------------------------

create or replace procedure SHOW_CONCERT_TO_RECOM_PROC(p_in_userid  IN  CONCERT_RECOMMENDATION.user_id%TYPE,
                                                       p_out_tobe_recom_cur OUT SYS_REFCURSOR,
                                                       p_out_errMsg          OUT error_log.error_msg%TYPE
                                                      )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to fetch list of possible concerts that logged in user can recommend
          Recommendation is based on concert_id not in CONCERT_RECOMMENDATION table for that user, and music he's following 
Input:    p_in_userid
Output:   p_out_tobe_recom_cur[list of to be recommended concert],p_out_errMsg
--------------------------------------------------------------------------------------------------*/
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  open p_out_tobe_recom_cur
  for
    select unique(cdm.ARTIST_ID),cdm.BAND_ID,cdm.CONCERT_DATE,cdm.CONCERT_ID,cdm.CONCERT_NAME,cdm.CONCERT_PRICE,cdm.CONCERT_TIME,cdm.CONCERT_VENUE_CITY,cdm.CONCERT_VENUE_STATE,msm.SUBCATEGORY_NAME,mcm.CATEGORY_NAME,cdm.MUSIC_CATEGORY_ID,cdm.MUSIC_SUBCATEGORY_ID
    from concert_detail_mst cdm,user_music_following umf, MUSIC_SUBCATEGORY_MST msm, MUSIC_CATEGORY_MST mcm 
    where cdm.concert_id not in(select concert_id from CONCERT_RECOMMENDATION where user_id= p_in_userid)
    and cdm.MUSIC_CATEGORY_ID=umf.MUSIC_CATEGORY_ID
    and cdm.MUSIC_SUBCATEGORY_ID=umf.MUSIC_SUBCATEGORY_ID
    and  cdm.music_category_id=mcm.music_category_id
    and cdm.music_subcategory_id=msm.music_subcategory_id;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'SHOW_CONCERT_TO_RECOM_PROC-->for user_id:'||p_in_userid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'SHOW_CONCERT_TO_RECOM_PROC');
END SHOW_CONCERT_TO_RECOM_PROC;
--------------------------------------------------------------------------------------------------------------

create sequence recommendation_id_seq
start with 14000
increment by 1
nocache
nocycle;

create or replace procedure CONCERT_RECOMMEN_INSERT_PROC( p_in_user_id    IN  CONCERT_RECOMMENDATION.user_id%TYPE,
                                                          p_in_concert_id IN  CONCERT_RECOMMENDATION.concert_id%TYPE,
                                                          p_out_errMsg    OUT error_log.error_msg%TYPE
                                                        )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to insert into concert_recommendation table
          recommendation_id generated through recommendation_id_seq
Input:    p_in_userid,p_in_concert_id
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/   
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  insert into CONCERT_RECOMMENDATION(recommendation_id,user_id,concert_id,recommendation_date)
  values(recommendation_id_seq.NEXTVAL,p_in_user_id,p_in_concert_id,sysdate);
  commit;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'CONCERT_RECOMMEN_INSERT_PROC-->for user_id:'||p_in_user_id||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'CONCERT_RECOMMEN_INSERT_PROC');
END CONCERT_RECOMMEN_INSERT_PROC;
---------------------------------------------------------------------------------------------

create or replace procedure VIEW_SELF_CONCERT_RECCO_PROC( p_in_userid          IN  CONCERT_RECOMMENDATION.user_id%TYPE,
                                                          p_out_self_recom_cur OUT SYS_REFCURSOR,
                                                          p_out_errMsg         OUT error_log.error_msg%TYPE
                                                        )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to fetch recommendation list made by logged in user
          recommendation_id generated through recommendation_id_seq
Input:    p_in_userid
Output:   p_out_self_recom_cur[list of concerts recommended by logged in user],p_out_errMsg
--------------------------------------------------------------------------------------------------*/ 
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  open p_out_self_recom_cur
  for
    select unique(cdm.ARTIST_ID),cdm.BAND_ID,cdm.CONCERT_DATE,cdm.CONCERT_ID,cdm.CONCERT_NAME,cdm.CONCERT_PRICE,cdm.CONCERT_TIME,cdm.CONCERT_VENUE_CITY,cdm.CONCERT_VENUE_STATE,msm.SUBCATEGORY_NAME,mcm.CATEGORY_NAME,cdm.MUSIC_CATEGORY_ID,cdm.MUSIC_SUBCATEGORY_ID
    from concert_detail_mst cdm, MUSIC_SUBCATEGORY_MST msm, MUSIC_CATEGORY_MST mcm 
    where cdm.concert_id in(select concert_id from CONCERT_RECOMMENDATION where user_id= p_in_userid)
    and  cdm.music_category_id=mcm.music_category_id
    and cdm.music_subcategory_id=msm.music_subcategory_id;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'VIEW_SELF_CONCERT_RECCO_PROC-->for user_id:'||p_in_userid||'---'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'VIEW_SELF_CONCERT_RECCO_PROC');
END VIEW_SELF_CONCERT_RECCO_PROC;
----------------------------------------------------------------------------------------------------------

create or replace procedure DELETE_RECOMMENDATION_PROC( p_in_userid     IN  CONCERT_RECOMMENDATION.user_id%TYPE,
                                                        p_in_concertid  IN  CONCERT_RECOMMENDATION.concert_id%TYPE,
                                                        p_out_errMsg    OUT error_log.error_msg%TYPE
                                                      )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to delete a recommendation
Input:    p_in_userid,p_in_concertid
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/  
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  delete from CONCERT_RECOMMENDATION
  where CONCERT_ID=p_in_concertid
  and user_id=p_in_userid;
  commit;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'DELETE_RECOMMENDATION_PROC-->for user_id:'||p_in_userid||'--- concert_id:'||p_in_concertid||'--'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'DELETE_RECOMMENDATION_PROC');
END DELETE_RECOMMENDATION_PROC;
---------------------------------------------------------------------------------------

create or replace procedure SHOW_OTHER_RECOMMEND_PROC(  p_in_userid             IN  CONCERT_RECOMMENDATION.user_id%TYPE,
                                                        p_out_other_reccom_cur  OUT  SYS_REFCURSOR,
                                                        p_out_errMsg            OUT error_log.error_msg%TYPE
                                                      )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to fetch list of concert recommendation, made by other user's whom logged in user is following
Input:    p_in_userid
Output:   p_out_other_reccom_cur[list of concert recommendation, made by other and logged in user is not attending yet],p_out_errMsg
--------------------------------------------------------------------------------------------------*/   
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  open p_out_other_reccom_cur
  for
    select unique(cdm.ARTIST_ID),cdm.BAND_ID,cdm.CONCERT_DATE,cdm.CONCERT_ID,cdm.CONCERT_NAME,cdm.CONCERT_PRICE,cdm.CONCERT_TIME,cdm.CONCERT_VENUE_CITY,cdm.CONCERT_VENUE_STATE,msm.SUBCATEGORY_NAME,mcm.CATEGORY_NAME,cdm.MUSIC_CATEGORY_ID,cdm.MUSIC_SUBCATEGORY_ID
    from concert_detail_mst cdm, MUSIC_SUBCATEGORY_MST msm, MUSIC_CATEGORY_MST mcm 
    where cdm.concert_id in(select concert_id from CONCERT_RECOMMENDATION where user_id IN (select user_following_id from user_following where user_id=p_in_userid))
    and cdm.concert_id not in (select concert_id from concert_attendee where user_id=p_in_userid)
    and  cdm.music_category_id=mcm.music_category_id
    and cdm.music_subcategory_id=msm.music_subcategory_id;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'DELETE_RECOMMENDATION_PROC-->for user_id:'||p_in_userid||'--'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'DELETE_RECOMMENDATION_PROC');
END SHOW_OTHER_RECOMMEND_PROC;
------------------------------------------------------------------------------------------------------------------------

create or replace procedure SHOW_CONCERT_ATTENDING_PROC(  p_in_userid                 IN  concert_attendee.user_id%TYPE,
                                                          p_out_concertattending_cur  OUT SYS_REFCURSOR,
                                                          p_out_errMsg                OUT error_log.error_msg%TYPE
                                                        )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to fetch list of concert that logged in user is going to attend
Input:    p_in_userid
Output:  p_out_concertattending_cur[list of concerts which user is gong to attend],p_out_errMsg
--------------------------------------------------------------------------------------------------*/
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
BEGIN
  open p_out_concertattending_cur
  for
    select unique(cdm.ARTIST_ID),cdm.BAND_ID,cdm.CONCERT_DATE,cdm.CONCERT_ID,cdm.CONCERT_NAME,cdm.CONCERT_PRICE,cdm.CONCERT_TIME,cdm.CONCERT_VENUE_CITY,cdm.CONCERT_VENUE_STATE,msm.SUBCATEGORY_NAME,mcm.CATEGORY_NAME,cdm.MUSIC_CATEGORY_ID,cdm.MUSIC_SUBCATEGORY_ID
    from concert_detail_mst cdm, MUSIC_SUBCATEGORY_MST msm, MUSIC_CATEGORY_MST mcm 
    where cdm.concert_id in(select concert_id from concert_attendee where user_id=p_in_userid)
    and cdm.CONCERT_DATE>=sysdate
    and  cdm.music_category_id=mcm.music_category_id
    and cdm.music_subcategory_id=msm.music_subcategory_id;
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'SHOW_CONCERT_ATTENDING_PROC-->for user_id:'||p_in_userid||'--'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'SHOW_CONCERT_ATTENDING_PROC');
END SHOW_CONCERT_ATTENDING_PROC;
-----------------------------------------------------------------------------------------------------------

create or replace procedure DEL_CONCERT_ATTENDING_PROC( p_in_userid     IN  concert_attendee.user_id%TYPE,
                                                        p_in_concertid  IN  concert_attendee.concert_id%TYPE,
                                                        p_out_errMsg    OUT error_log.error_msg%TYPE
                                                      )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to remove concert from attending list by logged in user
Input:    p_in_userid,p_in_concertid
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/                                                  
IS
  v_errcode   error_log.error_code%TYPE:=NULL;
  v_errormsg  error_log.error_msg%TYPE:=NULL;
  v_dummy     NUMBER(2):=0;
BEGIN
  select count(1)
  into v_dummy
  from concert_attendee
  where user_id=p_in_userid
  and concert_id=p_in_concertid;
  
  if v_dummy=1
  then
    delete from concert_attendee
    where user_id=p_in_userid
    and concert_id=p_in_concertid;
    commit;
  end if;  
EXCEPTION
  when others
  then
    v_errcode   := SQLCODE;
    v_errormsg  := SQLERRM;
    p_out_errMsg := p_out_errMsg||'DEL_CONCERT_ATTENDING_PROC-->for user_id:'||p_in_userid||'--'||v_errcode||v_errormsg;
    error_log_insert_proc(v_errcode,v_errormsg,'DEL_CONCERT_ATTENDING_PROC');
END DEL_CONCERT_ATTENDING_PROC;
----------------------------------------------------------------------------------
create or replace procedure FETCH_GLOBAL_SEARCH_PROC( p_in_querysearch IN  VARCHAR,
                                                      p_out_user_cur    OUT SYS_REFCURSOR,
                                                      p_out_artist_cur  OUT SYS_REFCURSOR,
                                                      p_out_concert_cur OUT SYS_REFCURSOR,
                                                      p_out_errMsg    OUT error_log.error_msg%TYPE
                                                    )
/*----------------------------------------------------------------------------------------------------
Purpose:  Procedure to fetch result for user, artist, bamn
Input:    p_in_userid,p_in_concertid
Output:   p_out_errMsg
--------------------------------------------------------------------------------------------------*/




select * from music_subcategory_mst;