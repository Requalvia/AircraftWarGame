package edu.hitsz.DAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Daolmpl implements RecordDao{
    private LinkedList<GameRecord> records;
    public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Path path= Paths.get("Records.txt");

    public Daolmpl(){
        this.records=new LinkedList<>();
        records=getFromFile();

    }

    @Override
    public List<GameRecord> getAll() {
        return records;
    }
    @Override
    public void addToFile(GameRecord gameRecord){
        try {
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String date2=format.format(gameRecord.getDate());
            File file=new File(String.valueOf(path));
            FileWriter fileWritter = new FileWriter(file.getName(),true);

            fileWritter.write(gameRecord.getId()+"\n"
                    +date2+"\n"
                    +gameRecord.getPlayer()+"\n"
                    +gameRecord.getScore()+"\n");

            fileWritter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public LinkedList<GameRecord> getFromFile(){
        List<String> lines= null;
        LinkedList<GameRecord> g=new LinkedList<>();
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<lines.size();i+=4){
            String strTime=lines.get(i+1);

            Date date=new Date();
            try {
                date = format.parse(strTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String player=lines.get(i+2);
            int score= Integer.parseInt(lines.get(i+3));
            int id= Integer.parseInt(lines.get(i));
            g.add(new GameRecord(date,player,score,id));
        }
        return g;

    }

    @Override
    public void Add(GameRecord gameRecord) {
        records.add(gameRecord);
        addToFile(gameRecord);

        Sort();
        return;
    }

    @Override
    public void Delete(int id) {

    }

    @Override
    public void Sort(){

        //保证每次新建record的时候，都排一下序
        GameRecord g=records.getLast();
        records.removeLast();
        int currscore=g.getScore();
        for(int i=0;i<records.size();i++){
            if(records.get(i).getScore()>=currscore){continue;}
            else{
                records.add(i,g);
                break;
            }
        }
        clearAll();
        for(int i=0;i<records.size();i++){
            addToFile(records.get(i));
        }

    }

    @Override
    public void clearAll(){
        File file=new File(String.valueOf(path));
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printAll() {
        System.out.println("-*-*-*-*-排行榜-*-*-*-*-");
        System.out.println("Name\t\tScore\t\tTime");
        for(int i=0;i<records.size();i++){
            System.out.println(records.get(i).getPlayer()+"\t\t"+records.get(i).getScore()
                    +"\t\t"+format.format(records.get(i).getDate()));
        }

    }
}
