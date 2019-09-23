package jpuente.afinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeIntents;


public class Videos extends Fragment implements Runnable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam2;
    //private VideoView videoView;
    private TextView titulo;
    private Button verVideos;
    private Button verUnVideo;

    private String USER_ID = "";

    private OnFragmentInteractionListener mListener;

    private ImageButton botonplay, botonstop, botonpausa;
    private SeekBar barra;
    private MediaPlayer mp;
    private Thread soundThread;

    public Videos() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Videos newInstance(String param1, String param2) {
        Videos fragment = new Videos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.videos, container, false);

//************************************************** Sonido *********************
        botonplay = view.findViewById(R.id.play);
        botonstop = view.findViewById(R.id.stop);
        botonpausa = view.findViewById(R.id.pause);
        mp = MediaPlayer.create(getActivity(), R.raw.coplas);
        barra = view.findViewById(R.id.seekBar);
        barra.setBackgroundColor(Color.GRAY);
        setupListeners();

        soundThread = new Thread(this);
        soundThread.start();

//****************************************************************************************


        //videoView = view.findViewById(R.id.videoView1);

        verVideos = view.findViewById(R.id.btver_videos);
        verUnVideo = view.findViewById(R.id.bt_unvideo);
        titulo = view.findViewById(R.id.titulo);
        SharedPreferences prefe = this.getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        int condicion = prefe.getInt("id",400);

        Folcloricas dbreadable = new Folcloricas(getContext(), "Folcloricas", null, 1);
        final SQLiteDatabase db = dbreadable.getReadableDatabase();

        String[] valores = {"id", "nombre", "videos"};
        Cursor cursor = db.query("folcloricas", valores, "id = " + condicion, null, null, null, null);
        cursor.moveToFirst();
        USER_ID = cursor.getString(1);
        final String video = cursor.getString(2);

        titulo.setText("Videos de " + cursor.getString(1));

        verUnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentunvideo = new Intent(getActivity(), ReproducirYoutube.class);
                intentunvideo.putExtra("videos", video);
                startActivity(intentunvideo);
            }
        });

        verVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), ReproducirYoutube.class);
                //getActivity().startActivity(intent);
                Intent intent = YouTubeIntents.createSearchIntent(getActivity(), USER_ID);
                startActivity(intent);
            }
        });


        cursor.close();
        db.close();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if (soundThread != null){
            soundThread.interrupt();
            mp.stop();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setupListeners(){
        botonplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toastg =Toast.makeText(getContext(),"Disfruta de la Música", Toast.LENGTH_LONG);
                toastg.setGravity(Gravity.BOTTOM|Gravity.RIGHT,0,0);
                toastg.show();
                mp.start();
                //soundThread.interrupt();
            }
        });

        botonstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                barra.setProgress(0);
                mp = MediaPlayer.create(getContext(), R.raw.coplas);
            }
        });

        botonpausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });

        barra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mp.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    @Override
    public void run() {
        int currentPosition = 0;
        int soundTotal = mp.getDuration();
        barra.setMax(soundTotal);

        while (mp != null && currentPosition < soundTotal)
        {
            try
            {
                Thread.sleep(300);
                currentPosition = mp.getCurrentPosition();
            }
            catch (InterruptedException soundException)
            {
                return;
            }
            catch (Exception otherException)
            {
                return;
            }
            barra.setProgress(currentPosition);
        }
    }
}
