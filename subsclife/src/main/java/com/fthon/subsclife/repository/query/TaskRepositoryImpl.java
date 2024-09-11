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
                        startFromGoe(cond.getStartFrom()),
                        endToLoe(cond.getEndTo()),
                        containsKeyword(cond.getKeyword()),
                        cursorCondition(cursor.getEndDate(), cursor.getStartDate(), cursor.getTaskId())
                )
                .orderBy(
                        task.period.endDate.asc(),
                        task.period.startDate.asc(),
                        task.id.desc()
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
                .orderBy(task.period.endDate.asc(), task.period.startDate.asc(), task.id.desc())
                .fetch();

        return new PagedItem<>(tasks, hasNext);
    }

    // 마감시간이 적게 남은 것 부터 조회하기 때문에
    // 다음 페이지는 시간이 더 많이 남은 아이템이 되어야 함
    private BooleanExpression endDateGt(LocalDateTime endDate) {
        return endDate != null ? task.period.endDate.gt(endDate) : null;
    }

    private BooleanExpression endDateEq(LocalDateTime endDate) {
        return endDate != null ? task.period.endDate.eq(endDate) : null;
    }

    // 마감시간이 동일하다면
    // 먼저 시작할 태스크부터 조회되어야 함
    private BooleanExpression startDateLt(LocalDateTime startDate) {
        return startDate != null ? task.period.startDate.lt(startDate) : null;
    }

    private BooleanExpression startDateEq(LocalDateTime startDate) {
        return startDate != null ? task.period.startDate.eq(startDate) : null;
    }

    // 시작 & 마감시간이 동일하다면
    // 먼저 등록된 태스크부터 조회되어야 함
    private BooleanExpression taskIdLt(Long taskId) {
        return taskId != null ? task.id.lt(taskId) : null;
    }

    // 마감 시간이 동일할 때는
    // 시작 시간이 빠른걸로
    private BooleanExpression endDateEqAndStartDateLt(LocalDateTime endDate, LocalDateTime startDate) {
        return endDateEq(endDate)
                .and(startDateLt(startDate));
    }

    // 마감 시간이 동일하고 시작 시간이 똑같을 땐
    // 먼저 등록된 순서대로
    private BooleanExpression endDateEqAndStartDateEqAndIdLt(LocalDateTime endDate, LocalDateTime startDate, Long taskId) {
        return endDateEq(endDate)
                .and(startDateEq(startDate))
                .and(taskIdLt(taskId));
    }

    // and() 메소드 직접 호출 시 null check 필요
    private BooleanExpression cursorCondition(LocalDateTime endDate, LocalDateTime startDate, Long taskId) {
        return endDate == null ? null : endDateGt(endDate)
                .or(endDateEqAndStartDateLt(endDate, startDate))
                .or(endDateEqAndStartDateEqAndIdLt(endDate, startDate, taskId));
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
