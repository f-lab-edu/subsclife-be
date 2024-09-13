package com.fthon.subsclife.repository.query;

import com.fthon.subsclife.dto.PagedItem;
import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.entity.Task;


public interface QueryTaskRepository {

    PagedItem<Task> searchTaskList(TaskDto.Cursor cursor, TaskDto.SearchCondition cond, Long userId);
}
