package com.kartersanamo.algoVision.managers;

import com.kartersanamo.algoVision.models.VisualizationSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks active visualization sessions and exposes control operations.
 * Concrete visualizer creation is handled by VisualizerFactory.
 */
public class VisualizationManager {

    private final Map<UUID, VisualizationSession> activeSessions = new ConcurrentHashMap<>();
    private final Map<UUID, List<UUID>> playerSessions = new ConcurrentHashMap<>();

    public VisualizationManager() {
    }

    public void registerSession(VisualizationSession session) {
        UUID id = session.getSessionId();
        activeSessions.put(id, session);
        playerSessions.computeIfAbsent(session.getPlayerId(), k -> new ArrayList<>()).add(id);
    }

    public void stopVisualization(UUID sessionId) {
        VisualizationSession session = activeSessions.remove(sessionId);
        if (session != null) {
            session.stop();
            List<UUID> list = playerSessions.get(session.getPlayerId());
            if (list != null) {
                list.remove(sessionId);
                if (list.isEmpty()) {
                    playerSessions.remove(session.getPlayerId());
                }
            }
        }
    }

    public void pauseVisualization(UUID sessionId) {
        VisualizationSession session = activeSessions.get(sessionId);
        if (session != null) {
            session.pause();
        }
    }

    public void resumeVisualization(UUID sessionId) {
        VisualizationSession session = activeSessions.get(sessionId);
        if (session != null) {
            session.resume();
        }
    }

    public void stepVisualization(UUID sessionId, int steps) {
        VisualizationSession session = activeSessions.get(sessionId);
        if (session != null) {
            session.step(steps);
        }
    }

    public List<VisualizationSession> getPlayerSessions(UUID playerId) {
        List<UUID> ids = playerSessions.getOrDefault(playerId, List.of());
        List<VisualizationSession> result = new ArrayList<>();
        for (UUID id : ids) {
            VisualizationSession s = activeSessions.get(id);
            if (s != null) result.add(s);
        }
        return result;
    }

    public VisualizationSession getSession(UUID sessionId) {
        return activeSessions.get(sessionId);
    }

    public VisualizationSession getMostRecentSession(UUID playerId) {
        List<VisualizationSession> sessions = getPlayerSessions(playerId);
        if (sessions.isEmpty()) return null;
        sessions.sort(Comparator.comparingLong(VisualizationSession::getStartTime).reversed());
        return sessions.getFirst();
    }

    public void cleanupPlayerSessions(UUID playerId) {
        List<UUID> ids = playerSessions.remove(playerId);
        if (ids != null) {
            for (UUID id : ids) {
                VisualizationSession session = activeSessions.remove(id);
                if (session != null) {
                    session.stop();
                }
            }
        }
    }

    public void cleanupExpiredSessions() {
        // Placeholder: could remove very old completed sessions if we track completion time.
    }

    public void stopAll() {
        for (UUID id : new ArrayList<>(activeSessions.keySet())) {
            stopVisualization(id);
        }
    }

}
