package com.aaronsite.models;

import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.utils.annotations.Column;
import com.aaronsite.utils.enums.ColumnType;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.io.Logger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.jsonwebtoken.Claims;
import org.bson.Document;

import static com.aaronsite.utils.enums.Table.USERS;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends System implements Model {
  public static final String USER_NAME = "username";
  public static final String USER_PW = "passhash";
  public static final String USER_ROLLS = "roles";

  private String userName;

  @Column(columnType = ColumnType.ENCRYPTED)
  private String userPw;

  private String roles;

  public User() {
    // Default Constructor
  }

  public User(DBRecord record) {
    Logger.out("Building User from Record: " + record);

    this.id = record.getId();
    this.userName = record.get(USER_NAME);
    this.userPw = record.get(USER_PW);
    this.roles = record.get(USER_ROLLS);
  }

  public User(Claims claims) {
    this.id = claims.getId();
    this.userName = claims.get(USER_NAME, String.class);
    this.userPw = claims.get(USER_PW, String.class);
    this.roles = claims.get(USER_ROLLS, String.class);
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(USER_NAME, userName)
        .add(USER_PW, userPw)
        .add(USER_ROLLS, roles);
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

  public User setRoles(Document roles) {
    this.roles = roles.toJson();
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

  @JsonDeserialize
  public Document getRoles() {
    return roles == null ? null : Document.parse(roles);
  }

  @Override
  public String toString() {
    return "User{" +
        "userName='" + userName + '\'' +
        ", roles='" + roles + '\'' +
        '}';
  }
}
