package com.aaronsite.actions;

import com.aaronsite.response.ResponseData;
import com.aaronsite.utils.exceptions.ABException;
import org.bson.Document;

public interface Action extends ResponseData {
  Action execute(Document doc) throws ABException;
}
