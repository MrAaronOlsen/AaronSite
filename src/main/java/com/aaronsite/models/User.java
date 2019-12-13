package com.aaronsite.models;

import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.utils.annotations.Column;
import com.aaronsite.utils.enums.ColumnType;
import com.aaronsite.utils.enums.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.jsonwebtoken.Claims;

import static com.aaronsite.utils.enums.Table.USERS;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends System implements Model {
  public static final String USER_NAME = "username";
  public static final String USER_PW = "passhash";
  public static final String USER_ROLLS = "roles";

  private String userName;

  @Column(columnType = ColumnType.ENCRYPTED)
  private String userPw;

  private String[] roles;

  public User() {
    // Default Constructor
  }

  public User(DBRecord record) {
    this.id = record.getId();
    this.userName = record.get(USER_NAME);
    this.userPw = record.get(USER_PW);
  }

  public User(Claims claims) {
    this.id = claims.getId();
    this.userName = claims.get(USER_NAME, String.class);
    this.userPw = claims.get(USER_PW, String.class);
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(USER_NAME, userName)
        .add(USER_PW, userPw);
  }

  @Override
  @JsonIgnore
  public Table getTable() {
    return USERS;
  }

  public User setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public User setUserPw(String userPw) {
    this.userPw = userPw;
    return this;
  }

  @JsonDeserialize
  public String getUserName() {
    return userName;
  }

  @JsonDeserialize
  public String getUserPw() {
    return userPw;
  }
}
