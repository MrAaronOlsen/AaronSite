package com.aaronsite.triggers;

import com.aaronsite.models.Model;
import com.aaronsite.models.Page;
import com.aaronsite.utils.enums.PageMode;
import com.aaronsite.utils.exceptions.TriggerException;

import static com.aaronsite.utils.enums.PageMode.PUBLISHED;
import static com.aaronsite.utils.exceptions.TriggerException.Code.CANNOT_DELETE_PUBLISHED_PAGE;
import static com.aaronsite.utils.exceptions.TriggerException.Code.CANNOT_UPDATE_PUBLISHED_PAGE;

public class PageTriggers extends TableTriggers {

  @Override
  public void preUpdate(Model model) throws TriggerException {
    Page page = (Page) model;

    if (PageMode.get(page.getMode()) == PUBLISHED) {
      throw new TriggerException(CANNOT_UPDATE_PUBLISHED_PAGE);
    }
  }

  @Override
  public void preDelete(Model model) throws TriggerException {
    Page page = (Page) model;

    if (PageMode.get(page.getMode()) == PUBLISHED) {
      throw new TriggerException(CANNOT_DELETE_PUBLISHED_PAGE);
    }
  }
}
