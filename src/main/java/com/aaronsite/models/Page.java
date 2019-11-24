package com.aaronsite.models;

import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.utils.enums.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import static com.aaronsite.utils.enums.Table.PAGES;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page extends System implements Model {
  static final String HEADER = "header";
  static final String PREVIEW = "preview";
  static final String BODY = "body";
  static final String SEQUENCE = "sequence";

  private String header;
  private String preview;
  private String body;
  private String sequence;

  public Page() {
    // Default Constructor
  }

  Page(DBRecord record) {
    this.id = record.getId();
    this.header = record.get(HEADER);
    this.preview = record.get(PREVIEW);
    this.body = record.get(BODY);
    this.sequence = record.get(SEQUENCE);
  }

  @JsonDeserialize
  public String getHeader() {
    return header;
  }

  @JsonDeserialize
  public String getPreview() {
    return preview;
  }

  @JsonDeserialize
  public String getBody() {
    return body;
  }

  @JsonDeserialize
  public String getSequence() {
    return sequence;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(HEADER, header)
        .add(PREVIEW, preview)
        .add(BODY, body)
        .add(SEQUENCE, sequence);
  }

  @Override
  @JsonIgnore
  public Table getTable() {
    return PAGES;
  }

  @Override
  public String toString() {
    return "Table: " + getTable() + "\n"
        + ID + ": " + id + "\n"
        + HEADER + ": " + header + "\n"
        + PREVIEW + ": " + preview + "\n"
        + SEQUENCE + ": " + sequence + "\n"
        + BODY + ": " + body;
  }
}
