package droider.com.crackme0201;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        EditText edit_userName = (EditText) findViewById(R.id.count);
        EditText edit_sn = (EditText) findViewById(R.id.sign);
        Button button =(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkSN(edit_userName.getText().toString().trim(),edit_sn.getText().toString().trim())){
                    Toast.makeText(MainActivity.this,R.string.fail,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,R.string.success,Toast.LENGTH_SHORT).show();
                    button.setEnabled(false);
                    setTitle("registed!");
                }
            }
        });


    }
    private boolean checkSN(String userName, String sn)
    {
        try {
            if ((userName == null) || (userName.length() == 0))
                return false;
            if ((sn == null) && (sn.length() != 16))
                return false;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(userName.getBytes());
            byte[] bytes = digest.digest();
            String hexstr = toHexString(bytes,"");
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<hexstr.length();i++){
                sb.append(hexstr.charAt(i));
            }
            String userSN = sb.toString();
            Log.d("crackme0201",hexstr);
            Log.d("crackme0201",userSN);
            if(!userSN.equalsIgnoreCase(sn))
                return false;
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private String toHexString(byte[] bytes,String str){
        StringBuilder hexString = new StringBuilder();
        for(byte b:bytes){
            String hex = Integer.toHexString(0xff&b);
            if(hex.length()==1){
                hexString.append('0');
            }
            hexString.append(hex).append(str);
        }
        return hexString.toString();
    }
}