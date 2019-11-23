package com.aaronsite.models;

import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.utils.enums.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import static com.aaronsite.utils.enums.Table.POSTS;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post extends System implements Model {
  static final String HEADER = "header";
  static final String BODY = "body";

  private String header;
  private String body;

  public Post() {
    // Default Constructor
  }

  Post(DBRecord record) {
    this.id = record.getId();
    this.header = record.get(HEADER);
    this.body = record.get(BODY);
  }

  @JsonDeserialize
  public String getHeader() {
    return header;
  }

  @JsonDeserialize
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
