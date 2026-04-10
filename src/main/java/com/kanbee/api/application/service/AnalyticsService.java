package com.kanbee.api.application.service;

import com.kanbee.api.application.dto.AnalyticsDTO;
import com.kanbee.api.domain.model.CardAssignmentHistory;
import com.kanbee.api.domain.model.CardHistory;
import com.kanbee.api.infrastructure.repository.CardAssignmentHistoryRepository;
import com.kanbee.api.infrastructure.repository.CardHistoryRepository;
import com.kanbee.api.infrastructure.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final CardHistoryRepository cardHistoryRepository;
    private final CardAssignmentHistoryRepository assignmentRepository;
    private final CardRepository cardRepository;

    public List<AnalyticsDTO> getCardAnalytics(UUID cardId) {

        var history = cardHistoryRepository
                .findByCardIdOrderByChangedAt(cardId);

        var assignments = assignmentRepository
                .findByCardId(cardId);

        var statusIntervals = buildStatusIntervals(history);
        var assignmentIntervals = buildAssignmentIntervals(assignments);

        var raw = intersectIntervals(statusIntervals, assignmentIntervals);

        return groupResults(raw);
    }


    // 1. STATUS INTERVALS
    // ========================
    private List<StatusInterval> buildStatusIntervals(List<CardHistory> history) {
        List<StatusInterval> intervals = new ArrayList<>();

        for (int i = 0; i < history.size(); i++) {
            var current = history.get(i);

            LocalDateTime start = current.getChangedAt();
            LocalDateTime end = (i + 1 < history.size())
                    ? history.get(i + 1).getChangedAt()
                    : LocalDateTime.now();

            intervals.add(new StatusInterval(
                    current.getToColumn().getTitle(),
                    start,
                    end
            ));
        }

        return intervals;
    }

    // 2. ASSIGNMENT INTERVALS
    // ========================
    private List<AssignmentInterval> buildAssignmentIntervals(List<CardAssignmentHistory> assignments) {
        List<AssignmentInterval> intervals = new ArrayList<>();

        for (var a : assignments) {

            String userName = a.getAssignedTo().getProfile() != null
                    ? a.getAssignedTo().getProfile().getName()
                    : "Unknown";

            intervals.add(new AssignmentInterval(
                    userName,
                    a.getAssignedAt(),
                    a.getUnassignedAt() != null ? a.getUnassignedAt() : LocalDateTime.now()
            ));
        }

        return intervals;
    }

    // 3. INTERSECTION
    // ========================
    private List<AnalyticsDTO> intersectIntervals(
            List<StatusInterval> statusIntervals,
            List<AssignmentInterval> assignmentIntervals
    ) {

        List<AnalyticsDTO> result = new ArrayList<>();

        for (var status : statusIntervals) {
            for (var assignment : assignmentIntervals) {

                LocalDateTime start = max(status.start, assignment.start);
                LocalDateTime end = min(status.end, assignment.end);

                if (start.isBefore(end)) {
                    long seconds = Duration.between(start, end).getSeconds();

                    result.add(new AnalyticsDTO(
                            assignment.userName,
                            status.listName,
                            seconds,
                            seconds / 60
                    ));
                }
            }
        }

        return result;
    }

    // 4. AGRUPAMENTO
    // ========================
    private List<AnalyticsDTO> groupResults(List<AnalyticsDTO> raw) {

        Map<String, Long> grouped = new HashMap<>();

        for (var item : raw) {

            String key = item.getUserName() + "|" + item.getListName();

            grouped.put(
                    key,
                    grouped.getOrDefault(key, 0L) + item.getTimeInSeconds()
            );
        }

        List<AnalyticsDTO> result = new ArrayList<>();

        for (var entry : grouped.entrySet()) {

            String[] parts = entry.getKey().split("\\|");

            long seconds = entry.getValue();

            result.add(new AnalyticsDTO(
                    parts[0],          // userName
                    parts[1],          // listName
                    seconds,
                    seconds / 60       // minutes
            ));
        }

        return result;
    }

    // HELPERS
    // ========================
    private LocalDateTime max(LocalDateTime a, LocalDateTime b) {
        return a.isAfter(b) ? a : b;
    }

    private LocalDateTime min(LocalDateTime a, LocalDateTime b) {
        return a.isBefore(b) ? a : b;
    }

    public List<AnalyticsDTO> getBoardAnalytics(UUID boardId) {

        var cards = cardRepository.findByColumnBoardIdAndArchivedAtIsNull(boardId);

        List<AnalyticsDTO> allResults = new ArrayList<>();

        for (var card : cards) {

            var history = cardHistoryRepository
                    .findByCardIdOrderByChangedAt(card.getId());

            var assignments = assignmentRepository
                    .findByCardId(card.getId());

            var statusIntervals = buildStatusIntervals(history);
            var assignmentIntervals = buildAssignmentIntervals(assignments);

            var raw = intersectIntervals(statusIntervals, assignmentIntervals);

            allResults.addAll(raw);
        }

        return groupResults(allResults);
    }

    // CLASSES AUXILIARES
    // ========================
    private static class StatusInterval {
        String listName;
        LocalDateTime start;
        LocalDateTime end;

        public StatusInterval(String listName, LocalDateTime start, LocalDateTime end) {
            this.listName = listName;
            this.start = start;
            this.end = end;
        }
    }

    private static class AssignmentInterval {
        String userName;
        LocalDateTime start;
        LocalDateTime end;

        public AssignmentInterval(String userName, LocalDateTime start, LocalDateTime end) {
            this.userName = userName;
            this.start = start;
            this.end = end;
        }
    }
}