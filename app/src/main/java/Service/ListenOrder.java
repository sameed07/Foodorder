package Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.sameedshah.foodorder.Common.Common;
import com.example.sameedshah.foodorder.OrderStatus;
import com.example.sameedshah.foodorder.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.Request;

public class ListenOrder extends Service implements ChildEventListener {

    FirebaseDatabase db;
    DatabaseReference requests;

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        requests.addChildEventListener(this);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        Request request = dataSnapshot.getValue(Request.class);
        showNotification(dataSnapshot.getKey(),request);

    }

    private void showNotification(String key, Request request) {


        Intent i = new Intent(getBaseContext(), OrderStatus.class);
        i.putExtra("userPhone",request.getPhone());
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Toast.makeText(this, "Called", Toast.LENGTH_SHORT).show();

        builder.setAutoCancel(true).
                setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Food Order")
                .setContentInfo("Your order was updated")
                .setContentText("Order #"+ key+ " was update status to "+ Common.convertCodeToStatus(request.getStatus()))
                .setContentIntent(contentIntent)
                .setContentInfo("info")
                .setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager notificationManager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
