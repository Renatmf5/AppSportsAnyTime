package Services.Endereco;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class DescobrirLatLong {

    public static GeoPoint descobrir(Context context, String strEndereco){

        Geocoder coder = new Geocoder(context);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strEndereco,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            return new GeoPoint(
                    (double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
