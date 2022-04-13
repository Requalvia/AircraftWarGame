package edu.hitsz.DAO;

import java.util.LinkedList;
import java.util.List;

public interface RecordDao {

    void Sort();
    List<GameRecord> getAll();
    void Add(GameRecord r);
    void Delete(int id);
    void addToFile(GameRecord gameRecord);
    LinkedList<GameRecord> getFromFile();
    void clearAll();
    void printAll();

}
