package techkids.vn.android7pomodoro.databases;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import techkids.vn.android7pomodoro.databases.models.Task;
import techkids.vn.android7pomodoro.databases.models.TempTask;


/**
 * Created by huynq on 2/8/17.
 */

public class DbContext{

    public static DbContext instance;

    private Realm realm;

    private static String TAG = DbContext.class.toString();

    public DbContext(Context context){
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    public DbContext(){};

    public <T> void add(T t) {
        realm.beginTransaction();
        realm.copyToRealm((RealmObject) t);
        realm.commitTransaction();
    }

    public void update(Task old, String color,String name,float payment) {
        realm.beginTransaction();
        old.setName(name);
        old.setColor(color);
        old.setPaymentPerHour(payment);
        realm.commitTransaction();
    }

    public <T> void addOrUpdate(T t) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate((RealmObject) t);
        realm.commitTransaction();
    }

    public List<TempTask> allTempTasks() {
        String TAG=DbContext.class.toString();
        RealmResults<TempTask> tasks=realm.where(TempTask.class).findAll();
        return realm.where(TempTask.class).findAll();
    }

    public List<Task> allTask() {
        String TAG=DbContext.class.toString();
        RealmResults<Task> tasks=realm.where(Task.class).findAll();
        return realm.where(Task.class).findAll();
    }


    public void clearAllTask(){
        realm.beginTransaction();
        realm.delete(Task.class);
        realm.commitTransaction();
    }

    public void deleteTask(Task deleteTask) {
        RealmResults<Task> delTasks = realm.where(Task.class).equalTo("local_id",deleteTask.getLocal_id()).findAll();
        if (delTasks != null) {
            for (Task task: delTasks) {
                task.deleteFromRealm();
            }
        }
        realm.commitTransaction();
    }

    public void deleteTempTask(TempTask tempTask) {
        realm.beginTransaction();
        RealmResults<TempTask> delTemps = realm.where(TempTask.class).equalTo("local_id", tempTask.getLocal_id()).findAll();
        if (delTemps != null)
            for (TempTask t : delTemps) {
                t.deleteFromRealm();
            }
        realm.commitTransaction();
    }

}
