package org.example.track;

import org.example.checkpoints.Checkpoint;

import java.util.ArrayList;

public interface Track {
    /**
     * Получает список контрольных точек всего трека
     * @return {@code ArrayList<Checkpoint>} - список контрольных точек, если контрольных точек меньше двух то возвращает {@code null}
     */
    ArrayList<Checkpoint> getCheckpoints();

    /**
     * Проверяет, является ли контрольная точка финальной
     * @param checkpoint - проверяемая контрольная точка
     * @return {@code true} - если переданная точка является финишной, иначе  {@code false}
     */
    boolean isLastCheckpoint(Checkpoint checkpoint);

    /**
     * Добавляет контрольную точку в массив трека
     * @param checkpoint добавляемая контрольная точка
     */
    void addCheckpoint(Checkpoint checkpoint);
}
