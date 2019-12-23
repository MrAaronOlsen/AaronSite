package com.aaronsite.models;

import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.utils.enums.PageMode;
import com.aaronsite.utils.enums.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.bson.Document;

import static com.aaronsite.utils.enums.Table.PAGES;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page extends System implements Model {
  public static final String HEADER = "header";
  public static final String CAPTION = "caption";
  public static final String SLUG = "slug";
  public static final String SEQUENCE = "sequence";
  public static final String BLOCKS = "blocks";
  public static final String MODE = "mode";

  private String header;
  private String caption;
  private String slug;
  private String sequence;
  private String blocks;
  private String mode;

  public Page() {
    // Default Constructor
  }

  public Page(DBRecord record) {
    this.id = record.getId();
    this.header = record.get(HEADER);
    this.caption = record.get(CAPTION);
    this.slug = record.get(SLUG);
    this.sequence = record.get(SEQUENCE);
    this.blocks = record.get(BLOCKS);
    this.mode = record.get(MODE);
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
  public Document getBlocks() {
    return blocks == null ? null : Document.parse(blocks);
  }

  @JsonDeserialize
  public String getMode() {
    return mode;
  }

  public Page setMode(PageMode mode) {
    this.mode = mode.getValue();
    return this;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(HEADER, header)
        .add(CAPTION, caption)
        .add(SLUG, slug)
        .add(SEQUENCE, sequence)
        .add(BLOCKS, blocks)
        .add(MODE, mode);
  }

  @Override
  @JsonIgnore
  public Table getTable() {
    return PAGES;
  }

  @Override
  public String toString() {
    return "Page{" +
        "header='" + header + '\'' +
        ", caption='" + caption + '\'' +
        ", slug='" + slug + '\'' +
        ", sequence='" + sequence + '\'' +
        ", blocks='" + blocks + '\'' +
        ", mode='" + mode + '\'' +
        '}';
  }
}
