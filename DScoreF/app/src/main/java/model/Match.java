package model;

import android.os.AsyncTask;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Match extends AsyncTask<String, Void, Object> implements Serializable{

    private int id;
    private String firstTeam;
    private String secondTeam;
    private String time;

    private String result;



    public Match(){}

    public Match(int id, String time, String firstTeam, String secondTeam, String firstTeamResult,String secondTeamResult) {
        this.id=id;
        this.time=time;
        this.firstTeam=firstTeam;
        this.secondTeam=secondTeam;
        this.result=firstTeamResult.contentEquals("0")&&secondTeamResult.contentEquals("0")? "Utakmica nije odigrana" : new String(firstTeamResult+" : "+secondTeamResult);

    }


    @Override
    protected Object doInBackground(String... strings) {
        Connection conn = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://mssql11.orion.rs/miho", "mihomadjija", "miho011!!!");
            switch (strings[0]){
                case "1":
                    PreparedStatement preparedStatement =
                            conn.prepareStatement("SELECT M.ID, M.Time, T.Name AS FirstTeam, P.Name AS SecondTeam, (ISNULL(R.A1,0)+ISNULL(R.A2,0)+ISNULL(R.A3,0)+ ISNULL(R.A4,0)+ISNULL(R.A_extra,0)) AS FirstTeamResult, \n" +
                            "(ISNULL(R.B1,0)+ISNULL(R.B2,0)+ISNULL(R.B3,0)+ ISNULL(R.B4,0)+ISNULL(R.B_extra,0)) AS SecondTeamResult FROM [Match] M JOIN \n" +
                            "Team T ON  M.FirstTeam=T.ID JOIN Team P ON M.SecondTeam=P.ID JOIN MatchScore R ON M.ID=R.MatchID WHERE Refree1=?or Refree2=? or Refree3=? or Refree4=? ORDER BY M.Time DESC");
                    int id = Integer.parseInt(strings[1]);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setInt(2, id);
                    preparedStatement.setInt(3, id);
                    preparedStatement.setInt(4, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    List<Match> list = new ArrayList<>();
                    while (resultSet.next()) {
                        Match match = new Match(resultSet.getInt(1),  resultSet.getString(2),
                                resultSet.getString(3), resultSet.getString(4),resultSet.getString(5),resultSet.getString(6));
                        list.add(match);
                    }
                    return list;
                /*case "2":
                    PreparedStatement preparedStatement = conn.prepareStatement("SELECT M.ID, M.Time, T.Name, P.Name FROM [Match] M JOIN Team T ON  M.FirstTeam=T.ID JOIN Team P ON M.SecondTeam=P.ID WHERE Refree1=? or Refree2=? or Refree3=? or Refree4=?");
                    int id = Integer.parseInt(strings[1]);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setInt(2, id);
                    preparedStatement.setInt(3, id);
                    preparedStatement.setInt(4, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    Map <Integer,List>map = new HashMap<>();
                    List<Integer> ids = new ArrayList<>();
                    List <String> time = new ArrayList<>();
                    List<String> firstTeam = new ArrayList<>();
                    List <String> secondTeam = new ArrayList<>();
                    List<String> result = new ArrayList<>();
                    while (resultSet.next()) {
                        ids.add(resultSet.getInt(1));
                        time.add(resultSet.getString(2));
                        firstTeam.add(resultSet.getString(3));
                        secondTeam.add(resultSet.getString(4));
                       *//* result.add(resultSet.getString(5));*//*
                    }
                    map.put(0,ids);
                    map.put(1,time);
                    map.put(2,firstTeam);
                    map.put(3,secondTeam);
                   *//* map.put(4,result);*//*
                    return map;
                    String.valueOf(id),a1,b1,a2,b2,a3,b3,a4,b4,a_extra,b_extra
                    */

                case "3":
                    PreparedStatement preparedStatement2 = conn.prepareStatement("INSERT INTO MatchScore VALUES (?,?,?,?,?,?,?,?,?,?,?)");
                    Integer matchID=Integer.valueOf(strings[1]);
                    Integer a1=Integer.valueOf(strings[2]);
                    Integer b1=Integer.valueOf(strings[3]);
                    Integer a2=Integer.valueOf(strings[4]);
                    Integer b2=Integer.valueOf(strings[5]);
                    Integer a3=Integer.valueOf(strings[6]);
                    Integer b3=Integer.valueOf(strings[7]);
                    Integer a4=Integer.valueOf(strings[8]);
                    Integer b4=Integer.valueOf(strings[9]);
                    Integer a_extra=Integer.valueOf(strings[10]);
                    Integer b_extra=Integer.valueOf(strings[11]);


                    preparedStatement2.setInt(1, matchID);
                    preparedStatement2.setInt(2, a1);
                    preparedStatement2.setInt(3, b1);
                    preparedStatement2.setInt(4, a2);
                    preparedStatement2.setInt(5, b2);
                    preparedStatement2.setInt(6, a3);
                    preparedStatement2.setInt(7, b3);
                    preparedStatement2.setInt(8, a4);
                    preparedStatement2.setInt(9, b4);
                    preparedStatement2.setInt(10, a_extra);
                    preparedStatement2.setInt(11, b_extra);
                    boolean b = preparedStatement2.execute();
                    return b;

        }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(String firstTeam) {
        this.firstTeam = firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(String secondTeam) {
        this.secondTeam = secondTeam;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
