package com.aaronsite.models;

import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.utils.enums.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.aaronsite.utils.enums.Table.POSTS;

public class Post extends System implements Model {
  public static final String HEADER = "header";
  public static final String BODY = "body";

  private String header;
  private String body;

  public Post() {
    // Default Constructor
  }

  public Post(DBRecord record) {
    this.id = record.getId();
    this.header = record.get(HEADER);
    this.body = record.get(BODY);
  }

  public String getHeader() {
    return header;
  }

  public String getBody() {
    return body;
  }

  public Post setHeader(String value) {
    this.header = value;
    return this;
  }

  public Post setBody(String value) {
    this.body = body;
    return this;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(HEADER, header)
        .add(BODY, body);
  }

  @Override
  @JsonIgnore
  public Table getTable() {
    return POSTS;
  }

  @Override
  public String toString() {
    return "Table: " + getTable() + "\n"
        + ID + ": " + id + "\n"
        + HEADER + ": " + header + "\n"
        + BODY + ": " + body;
  }
}
