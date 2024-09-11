package com.fthon.subsclife.repository.query;


import com.fthon.subsclife.dto.PagedItem;
import com.fthon.subsclife.entity.Remind;

import static com.fthon.subsclife.dto.RemindDto.*;

public interface QueryRemindRepository {

    PagedItem<Remind> searchRemindList(Long userId, Cursor cursor);
}
