package com.got.bestapps.gameofthrones.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.got.bestapps.gameofthrones.model.AppInfo;
import com.got.bestapps.gameofthrones.model.Game;
import com.got.bestapps.gameofthrones.model.PlayerState;
import com.got.bestapps.gameofthrones.model.Question;
import com.got.bestapps.gameofthrones.model.Rankings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CancellationException;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "gameOfThronesDb";
    private static final int DATABASE_VERSION = 1;

    private static final String PLAYER_STATE_TABLE = "PlayerState";
    private static final String PLAYER_STATE_ID_KEY = "id";
    private static final String NAME = "name";

    private static final String GAMES_TABLE = "Games";
    private static final String GAMES_ID_KEY = "id";
    private static final String GAMES = "gamesNumber";
    private static final String PLAYER_STATE_ID_FK = "playerStateIdFk";

    private static final String QUESTION_TABLE = "Question";
    private static final String QUESTION_ID_KEY = "id";
    private static final String TEXT = "text";
    private static final String TYPE = "type";
    private static final String ANSWEAR1 = "answear1";
    private static final String ANSWEAR2 = "answear2";
    private static final String ANSWEAR3 = "answear3";
    private static final String CORRECT_ANSWEAR = "correctAnswear";
    private static final String ANSWEAR_POINTS = "answearPoints";

    private static final String RANKINGS_TABLE = "Rankings";
    private static final String RANKINGS_ID_KEY = "id";
    private static final String POINTS = "points";

    private static final String APP_INFO_TABLE = "AppInfo";
    private static final String APP_INFO_ID = "id";
    private static final String STARTED_TIME = "lastPlayedTime";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYER_STATE_TABLE = "create table " + PLAYER_STATE_TABLE +
                " ( "
                    + PLAYER_STATE_ID_KEY + " integer primary key , "
                    + NAME + " text " +
                " )" ;

        String CREATE_GAMES_TABLE = "create table " + GAMES_TABLE +
                " ( "
                    + GAMES_ID_KEY + " integer primary key , "
                    + GAMES + " integer, "
                    + PLAYER_STATE_ID_FK + " integer " +
                " )";

        String CREATE_QUESTION_TABLE = "create table " + QUESTION_TABLE +
                " ( "
                    + QUESTION_ID_KEY + " integer primary key autoincrement, "
                    + TEXT + " text, "
                    + TYPE + " text, "
                    + ANSWEAR1 + " text, "
                    + ANSWEAR2 + " text, "
                    + ANSWEAR3 + " text, "
                    + CORRECT_ANSWEAR + " text, "
                    + ANSWEAR_POINTS + " integer, "
                    + PLAYER_STATE_ID_FK + " integer " +
                " ) ";

        String CREATE_RANKINGS_TABLE = "create table " + RANKINGS_TABLE +
                " ( "
                    + RANKINGS_ID_KEY + " integer primary key autoincrement, "
                    + POINTS + " integer, "
                    + PLAYER_STATE_ID_FK + " integer " +
                " ) ";

        String CREATE_APPINFO_TABLE = "create table " + APP_INFO_TABLE +
                " ( "
                    + APP_INFO_ID + " integer primary key, "
                    + STARTED_TIME + " integer " +
                " ) ";

        db.execSQL(CREATE_PLAYER_STATE_TABLE);
        db.execSQL(CREATE_GAMES_TABLE);
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_RANKINGS_TABLE);
        db.execSQL(CREATE_APPINFO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + PLAYER_STATE_TABLE);
        db.execSQL("drop table if exists " + GAMES_TABLE);
        db.execSQL("drop table if exists " + QUESTION_TABLE);
        db.execSQL("drop table if exists " + RANKINGS_TABLE);
        db.execSQL("drop table if exists " + APP_INFO_TABLE);
        onCreate(db);
    }

    public void addPlayerState(PlayerState playerState) {
        SQLiteDatabase database = getWritableDatabase();
        String ADD_PLAYER_STATE = "insert into " + PLAYER_STATE_TABLE +
                " values('" + playerState.getId() + "', '" + playerState.getName() + "')";
        database.execSQL(ADD_PLAYER_STATE);
        database.close();
    }

    public void addGame(Game game) {
        SQLiteDatabase database = getWritableDatabase();
        String ADD_GAME = "insert into " + GAMES_TABLE +
                " values(0, '"
                    + game.getGames_number() + "', '"
                    + game.getPlayer_state_id() +
                "')";
        database.execSQL(ADD_GAME);
        database.close();
    }

    public void addQuestion(Question question) {
        SQLiteDatabase database = getWritableDatabase();
        String ADD_QUESTION = "insert into " + QUESTION_TABLE +
                " values(null, '"
                    + question.getText() + "', '"
                    + question.getType() + "', '"
                    + question.getAnswear1() + "', '"
                    + question.getAnswear2() + "', '"
                    + question.getAnswear3() + "', '"
                    + question.getCorrect_answear() + "', '"
                    + question.getPoints() + "', '"
                    + question.getPlayer_state_id() +
                "')";
        database.execSQL(ADD_QUESTION);
        database.close();
    }

    public void addRankings(Rankings ranking) {
        SQLiteDatabase database = getWritableDatabase();
        String ADD_RANKING = "insert into " + RANKINGS_TABLE +
                " values(null, '"
                    + ranking.getPoints() + "', '"
                    + ranking.getPlayer_state_id() +
                "')";
        database.execSQL(ADD_RANKING);
        database.close();
    }

    public void addAppInfo(AppInfo appInfo) {
        SQLiteDatabase database = getWritableDatabase();
        String ADD_APPINFO = "insert into " + APP_INFO_TABLE +
                " values(0, '"
                    + appInfo.getLastTimePlayed() +
                "')";
        database.execSQL(ADD_APPINFO);
        database.close();
    }

    public void deletePlayerStateObjectFromDatabaseById(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String DELETE_PLAYER_STATE_BY_ID = "delete from " + PLAYER_STATE_TABLE +
                " where " + PLAYER_STATE_ID_KEY + " = " + id;
        database.execSQL(DELETE_PLAYER_STATE_BY_ID);
        database.close();
    }

    public void deleteGameObjectFromDatabaseById(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String DELETE_GAME_BY_ID = "delete from " + GAMES_TABLE +
                " where " + GAMES_ID_KEY + " = " + id;
        database.execSQL(DELETE_GAME_BY_ID);
        database.close();
    }

    public void deleteQuestionObjectFromDatabaseById(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String DELETE_QUESTION_BY_ID = "delete from " + QUESTION_TABLE +
                " where " + QUESTION_ID_KEY + " = " + id;
        database.execSQL(DELETE_QUESTION_BY_ID);
        database.close();
    }

    public void deleteRankingObjectFromDatabaseById(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String DELETE_RANKING_BY_ID = "delete from " + RANKINGS_TABLE +
                " where " + RANKINGS_ID_KEY + " = " + id;
        database.execSQL(DELETE_RANKING_BY_ID);
        database.close();
    }


    public void modifyPlayerStateObject(int id, String name) {
        SQLiteDatabase database = getWritableDatabase();
        String UPDATE_PLAYER_STATE_OBJECT = "update " + PLAYER_STATE_TABLE +
                " set "
                    + NAME + " = '" + name + "' "
                    + " where " + PLAYER_STATE_ID_KEY + " = " + id;
        database.execSQL(UPDATE_PLAYER_STATE_OBJECT);
        database.close();
    }

    public synchronized void modifyGameObject(int games_number, int player_state_id_fk) {
        SQLiteDatabase database = getWritableDatabase();
        String UPDATE_GAME_OBJECT = "";
        if (games_number > 7) {
        UPDATE_GAME_OBJECT = "update " + GAMES_TABLE +
                " set "
                    + GAMES + " = '" + 7 + "', "
                    + PLAYER_STATE_ID_FK + " = '" + player_state_id_fk + "' "
                    + " where " + GAMES_ID_KEY + " = " + 0;
        } else {
            UPDATE_GAME_OBJECT = "update " + GAMES_TABLE +
                    " set "
                    + GAMES + " = '" + games_number + "', "
                    + PLAYER_STATE_ID_FK + " = '" + player_state_id_fk + "' "
                    + " where " + GAMES_ID_KEY + " = " + 0;
        }
        database.execSQL(UPDATE_GAME_OBJECT);
        database.close();
    }

    public void modifyQuestionObject(int id, String text, String type, String answear1, String answear2, String answear3,
                                     String correct_answear,int answearPoints, int player_state_id_fk) {
        SQLiteDatabase database = getWritableDatabase();
        String UPDATE_QUESTION_OBJECT = "update " + QUESTION_TABLE +
                " set "
                    + TEXT + " = '" + text + "' "
                    + TYPE + " = '" + type + "' "
                    + ANSWEAR1 + " = '" + answear1 + "' "
                    + ANSWEAR2 + " = '" + answear2 + "' "
                    + ANSWEAR3 + " = '" + answear3 + "' "
                    + CORRECT_ANSWEAR + " = '" + correct_answear + "' "
                    + ANSWEAR_POINTS + " = '" + answearPoints + "' "
                    + PLAYER_STATE_ID_FK + " = '" + player_state_id_fk + "' "
                    + " where " + QUESTION_ID_KEY + " = " + id;
        database.execSQL(UPDATE_QUESTION_OBJECT);
        database.close();
    }

    public void modifyRankingsObject(int id, int points, int player_state_id_fk) {
        SQLiteDatabase database = getWritableDatabase();
        String UPDATE_RANKING_OBJECT = "update " + RANKINGS_TABLE +
                " set "
                    + POINTS + " = '" + points + "' "
                    + PLAYER_STATE_ID_FK + " = '" + player_state_id_fk + "' "
                    + " where " + RANKINGS_ID_KEY + " = " + id;
        database.execSQL(UPDATE_RANKING_OBJECT);
        database.close();
    }

    public List<PlayerState> getAllPlayerState() {
        SQLiteDatabase database = getReadableDatabase();
        String SELECT_ALL_PLAYER_STATE = "select * from " + PLAYER_STATE_TABLE;
        Cursor cursor = database.rawQuery(SELECT_ALL_PLAYER_STATE, null);

        List<PlayerState> playerStateList = new ArrayList<>();

        while (cursor.moveToNext()) {
            PlayerState playerState = new PlayerState(cursor.getInt(0), cursor.getString(1));
            playerStateList.add(playerState);
        }
        cursor.close();
        database.close();
        return playerStateList;
    }

    public synchronized List<Game> getAllGames() {
        SQLiteDatabase database = getReadableDatabase();
        String SELECT_ALL_GAMES = "select * from " + GAMES_TABLE;
        Cursor cursor = database.rawQuery(SELECT_ALL_GAMES, null);

        List<Game> gameList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Game game = new Game(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
            gameList.add(game);
        }
        cursor.close();
        database.close();
        return gameList;
    }

    public List<Question> getAllQuestions() {
        SQLiteDatabase database = getReadableDatabase();
        String SELECT_ALL_QUESTIONS = "select * from " + QUESTION_TABLE;
        Cursor cursor = database.rawQuery(SELECT_ALL_QUESTIONS, null);
        List<Question> questionList = new ArrayList<>();

        while (cursor.moveToNext()) {
            Question question = new Question(cursor.getInt(0), cursor.getString(1),
                                            cursor.getString(2), cursor.getString(3),
                                            cursor.getString(4), cursor.getString(5),
                                            cursor.getString(6), cursor.getInt(7),
                                            cursor.getInt(8));
            questionList.add(question);
        }
        cursor.close();
        database.close();
        return questionList;
    }

    public List<Rankings> getAllRankings() {
        SQLiteDatabase database = getReadableDatabase();
        String SELECT_ALL_RANKINGS = "select * from " + RANKINGS_TABLE;
        Cursor cursor = database.rawQuery(SELECT_ALL_RANKINGS, null);
        List<Rankings> rankingsList = new ArrayList<>();

        while (cursor.moveToNext()) {
            Rankings ranking = new Rankings(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
            rankingsList.add(ranking);
        }
        cursor.close();
        database.close();
        return rankingsList;
    }

    public AppInfo getAppInfo() {
        SQLiteDatabase database = getReadableDatabase();
        String SELECT_APPINFO = "select * from " + APP_INFO_TABLE;
        Cursor cursor = database.rawQuery(SELECT_APPINFO, null);
        AppInfo appInfo = null;
        if (cursor.moveToFirst()) {
            appInfo = new AppInfo(cursor.getLong(0), cursor.getLong(1));
        }
        cursor.close();
        database.close();
        return appInfo;
    }

    public PlayerState getPlayerStateById(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String SELECT_PLAYER_STATE_BY_ID = "select * from " + PLAYER_STATE_TABLE +
                " where " + PLAYER_STATE_ID_KEY + " = " + id;
        Cursor cursor = database.rawQuery(SELECT_PLAYER_STATE_BY_ID, null);
        PlayerState playerState = null;

        if (cursor.moveToFirst()) {
            playerState = new PlayerState(cursor.getInt(0), cursor.getString(1));
        }
        cursor.close();
        database.close();
        return playerState;
    }

    public synchronized Game getGameById(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String SELECT_GAME_BY_ID = "select * from " + GAMES_TABLE +
                " where " + GAMES_ID_KEY + " = " + id;
        Cursor cursor = database.rawQuery(SELECT_GAME_BY_ID, null);
        Game game = null;

        if (cursor.moveToFirst()) {
            game = new Game(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
        }
        cursor.close();
        database.close();
        return game;
    }

    public Question getQuestionById(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String SELECT_QUESTION_BY_ID = "select * from " + QUESTION_TABLE +
                " where " + QUESTION_ID_KEY + " = " + id;
        Cursor cursor = database.rawQuery(SELECT_QUESTION_BY_ID, null);
        Question question = null;

        if (cursor.moveToFirst()) {
            question = new Question(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getInt(7),
                    cursor.getInt(8));
        }
        cursor.close();
        database.close();
        return question;
    }

    public Rankings getRankingById(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String SELECT_QUESTION_BY_ID = "select * from " + QUESTION_TABLE +
                " where " + QUESTION_ID_KEY + " = " + id;
        Cursor cursor = database.rawQuery(SELECT_QUESTION_BY_ID, null);
        Rankings ranking = null;

        if (cursor.moveToFirst()) {
            ranking = new Rankings(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
        }
        cursor.close();
        database.close();
        return ranking;
    }

    public void updateRankings(List<Rankings> rankings) {
        List<Rankings> rankings1 = DatabaseData.getRankings();
        for(int i = 0; i < rankings1.size(); i++) {
            deleteRankingObjectFromDatabaseById(rankings1.get(i).getId());
        }
        Collections.sort(rankings, (Rankings r1, Rankings r2) -> r1.getPoints() - r2.getPoints());
        for (int i = 0; i < rankings.size(); i++ ) {
            addRankings(rankings.get(i));
        }
    }

    public void updateAppInfo(long startedTime, long endedTime) {
        SQLiteDatabase database = getWritableDatabase();
        //TODO updata app info
    }

}
