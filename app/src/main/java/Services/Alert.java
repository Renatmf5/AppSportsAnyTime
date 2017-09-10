package Services;

import android.app.Activity;
import android.widget.Toast;

public class Alert {

    public static void alert(Activity activity, String msg) {
        Toast.makeText(
                activity,
                msg,
                Toast.LENGTH_SHORT
        ).show();
    }

}
