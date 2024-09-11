package com.fthon.subsclife.repository.query;

import com.fthon.subsclife.dto.PagedItem;
import com.fthon.subsclife.dto.TaskDto;
import com.fthon.subsclife.entity.Task;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.fthon.subsclife.entity.QSubscribe.*;
import static com.fthon.subsclife.entity.QTask.*;

@RequiredArgsConstructor
public class TaskRepositoryImpl implements QueryTaskRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public PagedItem<Task> searchTaskList(TaskDto.Cursor cursor, TaskDto.SearchCondition cond) {
        List<Long> pagedTaskIds = queryFactory
                .select(task.id)
                .from(task)
                .where(
                        isClosedTask(),
                        startFromGoe(cond.getStartFrom()),
                        endToLoe(cond.getEndTo()),
                        containsKeyword(cond.getKeyword()),
                        cursorCondition(cursor.getStartDate(), cursor.getEndDate(), cursor.getTaskId())
                )
                .orderBy(
                        task.period.startDate.asc(),
                        task.period.endDate.asc(),
                        task.id.asc()
                )
                .limit(cond.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if(pagedTaskIds.size() > cond.getPageSize()) {
            pagedTaskIds.remove(pagedTaskIds.size() - 1);
            hasNext = true;
        }

        List<Task> tasks = queryFactory
                .selectFrom(task)
                .leftJoin(task.subscribes, subscribe).fetchJoin()
                .where(task.id.in(pagedTaskIds))
                .orderBy(task.period.startDate.asc(), task.period.endDate.asc(), task.id.asc())
                .fetch();

        return new PagedItem<>(tasks, hasNext);
    }

    // 마감된 태스크는 조회되면 안됨
    private BooleanExpression isClosedTask() {
        return task.period.startDate.gt(LocalDateTime.now());
    }

    // 마지막 아이템보다 시작 시간이 더 많이 남은 것을 조회
    private BooleanExpression startDateGt(LocalDateTime startDate) {
        return startDate != null ? task.period.startDate.gt(startDate) : null;
    }

    private BooleanExpression startDateEq(LocalDateTime startDate) {
        return startDate != null ? task.period.startDate.eq(startDate) : null;
    }

    // 시작 시간이 동일하다면
    // 마감 시간이 더 많이 남은 것을 조회
    private BooleanExpression endDateGt(LocalDateTime endDate) {
        return endDate != null ? task.period.endDate.gt(endDate) : null;
    }

    private BooleanExpression endDateEq(LocalDateTime endDate) {
        return endDate != null ? task.period.endDate.eq(endDate) : null;
    }

    // 시작 & 마감시간이 동일하다면
    // 나중에 등록된 태스크를 조회
    private BooleanExpression taskIdGt(Long taskId) {
        return taskId != null ? task.id.gt(taskId) : null;
    }

    // 시작 시간이 동일할 때는
    // 마감 시간이 빠른걸로
    private BooleanExpression startDateEqAndEndDateGt(LocalDateTime startDate, LocalDateTime endDate) {
        return startDateEq(startDate)
                .and(endDateGt(endDate));
    }

    // 시작 & 마감 시간이 동일할 땐
    // 먼저 등록된 순서대로
    private BooleanExpression startDateEqAndEndDateLtAndTaskIdGt(LocalDateTime startDate, LocalDateTime endDate, Long taskId) {
        return startDateEq(startDate)
                .and(endDateEq(endDate))
                .and(taskIdGt(taskId));
    }

    // and() 메소드 직접 호출 시 null check 필요
    private BooleanExpression cursorCondition(LocalDateTime startDate, LocalDateTime endDate, Long taskId) {
        return endDate == null ? null : startDateGt(startDate)
                .or(startDateEqAndEndDateGt(startDate, endDate))
                .or(startDateEqAndEndDateLtAndTaskIdGt(startDate, endDate, taskId));
    }

    // 특정 일자 이후에 시작하는 태스크를 필터링
    private BooleanExpression startFromGoe(LocalDateTime startFrom) {
        return startFrom != null ? task.period.startDate.goe(startFrom) : null;
    }

    // 특정 일자 이전에 마감되는 태스크를 필터링
    private BooleanExpression endToLoe(LocalDateTime endTo) {
        return endTo != null ? task.period.endDate.loe(endTo) : null;
    }

    // 특정 키워드를 필터링
    private BooleanExpression containsKeyword (String keyword) {
        return keyword != null ? task.title.contains(keyword) : null;
    }

}
