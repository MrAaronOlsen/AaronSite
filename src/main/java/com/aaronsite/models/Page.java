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
  static final String CAPTION = "caption";
  static final String SLUG = "slug";
  static final String SEQUENCE = "sequence";
  static final String BLOCKS = "blocks";

  private String header;
  private String caption;
  private String slug;
  private String sequence;
  private String blocks;

  public Page() {
    // Default Constructor
  }

  Page(DBRecord record) {
    this.id = record.getId();
    this.header = record.get(HEADER);
    this.caption = record.get(CAPTION);
    this.slug = record.get(SLUG);
    this.sequence = record.get(SEQUENCE);
    this.blocks = record.get(BLOCKS);
  }

  @JsonDeserialize
  public String getHeader() {
    return header;
  }

  @JsonDeserialize
  public String getCaption() {
    return caption;
  }

  @JsonDeserialize
  public String getSlug() {
    return slug;
  }

  @JsonDeserialize
  public String getSequence() {
    return sequence;
  }

  @JsonDeserialize
  public String getBlocks() {
    return blocks;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(HEADER, header)
        .add(CAPTION, caption)
        .add(SLUG, slug)
        .add(SEQUENCE, sequence)
        .add(BLOCKS, blocks);
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
        + CAPTION + ": " + caption + "\n"
        + SEQUENCE + ": " + sequence + "\n"
        + BLOCKS + ": " + blocks + "\n"
        + SLUG + ": " + slug;
  }
}
