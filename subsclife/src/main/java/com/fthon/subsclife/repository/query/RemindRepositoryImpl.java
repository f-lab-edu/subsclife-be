package com.fthon.subsclife.repository.query;

import com.fthon.subsclife.dto.PagedItem;
import com.fthon.subsclife.entity.Remind;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.fthon.subsclife.dto.RemindDto.*;
import static com.fthon.subsclife.entity.QRemind.*;
import static com.fthon.subsclife.entity.QSubscribe.*;
import static com.fthon.subsclife.entity.QTask.*;

@RequiredArgsConstructor
public class RemindRepositoryImpl implements QueryRemindRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public PagedItem<Remind> searchRemindList(Long userId, Cursor cursor) {
        List<Long> remindIds = queryFactory
                .select(remind.id)
                .from(remind)
                .join(remind.task)
                .join(remind.user)
                .where(
                        remind.user.id.eq(userId),
                        cursorCondition(cursor.getStartDate(), cursor.getEndDate(), cursor.getRemindId())
                )
                .orderBy(
                        task.period.endDate.desc(),
                        task.period.startDate.asc(),
                        remind.id.desc()
                )
                .limit(cursor.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if(remindIds.size() > cursor.getPageSize()) {
            remindIds.remove(remindIds.size() - 1);
            hasNext = true;
        }

        List<Remind> reminds = queryFactory
                .selectFrom(remind)
                .join(remind.task, task).fetchJoin()
                .leftJoin(task.subscribes, subscribe).fetchJoin()
                .where(remind.id.in(remindIds))
                .orderBy(task.period.endDate.desc(), task.period.startDate.asc(), remind.id.desc())
                .fetch();

        return new PagedItem<>(reminds, hasNext);
    }

    //
    // 마감 시간이 더 적은것을 조회
    private BooleanExpression endDateLt(LocalDateTime endDate) {
        return endDate != null ? task.period.endDate.lt(endDate) : null;
    }

    private BooleanExpression endDateEq(LocalDateTime endDate) {
        return endDate != null ? task.period.endDate.eq(endDate) : null;
    }

    // 시작 시간이 더 느린 것을 조회
    private BooleanExpression startDateGt(LocalDateTime startDate) {
        return startDate != null ? task.period.startDate.gt(startDate) : null;
    }

    private BooleanExpression startDateEq(LocalDateTime startDate) {
        return startDate != null ? task.period.startDate.eq(startDate) : null;
    }

    // 시작 & 마감시간이 동일하다면
    // 나중에 작성된 회고를 먼저 조회해야 함
    private BooleanExpression remindIdLt(Long remindId) {
        return remindId != null ? remind.id.lt(remindId) : null;
    }

    // 시작 시간이 동일할 때는
    // 마감 시간이 느린걸 먼저
    private BooleanExpression endDateEqAndStartDateLt(LocalDateTime startDate, LocalDateTime endDate) {
        return endDateEq(endDate)
                .and(startDateGt(startDate));
    }

    // 시작 & 마감 시간이 동일할 땐
    // 먼저 등록된 순서대로
    private BooleanExpression endDateEqAndStartDateEqAndRemindItLt(LocalDateTime startDate, LocalDateTime endDate, Long remindId) {
        return endDateEq(endDate)
                .and(startDateEq(startDate))
                .and(remindIdLt(remindId));
    }

    // and() 메소드 직접 호출 시 null check 필요
    private BooleanExpression cursorCondition(LocalDateTime startDate, LocalDateTime endDate, Long remindId) {
        return endDate == null ? null : endDateLt(endDate)
                .or(endDateEqAndStartDateLt(startDate, endDate))
                .or(endDateEqAndStartDateEqAndRemindItLt(startDate, endDate, remindId));
    }

}
